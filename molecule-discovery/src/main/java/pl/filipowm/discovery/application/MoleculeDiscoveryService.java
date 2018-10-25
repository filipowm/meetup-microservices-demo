package pl.filipowm.discovery.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.filipowm.discovery.application.command.MoleculeDiscoveryCommand;
import pl.filipowm.discovery.domain.Molecule;
import pl.filipowm.discovery.domain.MoleculeFinder;
import pl.filipowm.discovery.domain.MoleculeRepository;
import pl.filipowm.discovery.domain.Phase;
import pl.filipowm.discovery.infrastructure.DiscoveryProbabilityClient;
import pl.filipowm.discovery.infrastructure.messaging.CompoundProcessor;
import pl.filipowm.discovery.infrastructure.messaging.CompoundsOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class MoleculeDiscoveryService implements DiscoveryService, MoleculeFinder {

    private final MoleculeRepository repository;
    private final MoleculeMapper mapper;
    private final CompoundProcessor processor;
    private final DiscoveryProbabilityClient client;

    @Override
    public Mono<MoleculeDto> startDiscovery(MoleculeDiscoveryCommand command) {
        log.info("Starting discovery of a drug {}", command.getName());
        return Mono.just(command)
                .map(mapper::forCreate)
                .map(repository::save)
                .doOnNext(this::sendCompoundsOrder)
                .map(mapper::forGet);

    }

    private void sendCompoundsOrder(Molecule molecule) {
        CompoundsOrder order = CompoundsOrder.forMolecule(molecule);
        processor.request().send(MessageBuilder.withPayload(order).build());
    }

    @StreamListener(CompoundProcessor.COMPOUND_RESPONSE)
    void receivedCompounds(String discoveryId) {
        Optional<Molecule> molecule = repository.findOneByDiscoveryId(discoveryId);
        log.info("Received compounds for discovery {} and molecule {}", discoveryId, molecule.isPresent() ? molecule.get().getName() : "[NOT FOUND]");
        molecule.map(Molecule::startResearch)
                .map(repository::save)
                .map(this::doResearch)
                .ifPresent(repository::save);


    }

    private Molecule doResearch(Molecule molecule) {
        try {
            log.info("Checking if drug discovery {} will succeed", molecule.getDiscoveryId());
            Double probability = client.predict(molecule.getDiscoveryId());
            if (probability >= 0.8) {
                log.warn("Yuppy! Your drug discovery {} will succeed!", molecule.getDiscoveryId());
                molecule.proceedToPhase(Phase.RELEASED);
            } else {
                log.warn("Try another compound mixture. This drag {} was bad...", molecule.getDiscoveryId());
                molecule.proceedToPhase(Phase.DROPPED);
            }
        } catch (Exception e) {
            log.error("Noone know if drug discovery {} will succeed", molecule.getDiscoveryId(), e);
        }
        return molecule;
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
