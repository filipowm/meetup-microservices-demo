package pl.filipowm.discovery.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.filipowm.discovery.application.command.MoleculeDiscoveryCommand;
import pl.filipowm.discovery.domain.Molecule;
import pl.filipowm.discovery.domain.MoleculeFinder;
import pl.filipowm.discovery.domain.MoleculeRepository;
import pl.filipowm.discovery.infrastructure.messaging.CompoundProcessor;
import pl.filipowm.discovery.infrastructure.messaging.CompoundRequestHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
class MoleculeDiscoveryService implements DiscoveryService, MoleculeFinder {

    private final MoleculeRepository repository;
    private final MoleculeMapper mapper;
    private final CompoundRequestHandler requestHandler;

    @Override
    public Mono<MoleculeDto> startDiscovery(MoleculeDiscoveryCommand command) {
        log.info("Starting discovery of a drug {}", command.getName());
        Mono<Molecule> moleculeMono = Mono.just(command)
                .map(mapper::forCreate)
                .map(repository::save);

        requestHandler.requestCompounds(moleculeMono);

        return moleculeMono
                .map(mapper::forGet);
    }

    @StreamListener(CompoundProcessor.COMPOUND_RESPONSE)
    void receivedCompounds(String discoveryId) {
        log.warn("Received compounds for discovery {}", discoveryId);
        Optional<Molecule> molecule = repository.findOneByDiscoveryId(discoveryId);
        molecule.map(Molecule::startResearch)
                .ifPresent(repository::save);
    }

    public Flux<MoleculeListDto> findAllMolecules() {
        return Mono.fromCallable(repository::findAll)
                .publishOn(Schedulers.elastic())
                .flatMapMany(Flux::fromIterable)
                .map(mapper::forList);
    }

    public Mono<MoleculeDto> getMolecule(Long id) {
        return Mono.fromCallable(() -> repository.findOneById(id))
                .publishOn(Schedulers.elastic())
                .map(molecule -> molecule.orElseThrow(() -> new RuntimeException("Molecule with id=" + id + " does not exist")))
                .map(mapper::forGet);
    }

}