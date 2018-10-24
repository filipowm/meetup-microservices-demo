package pl.filipowm.discovery.domain;

import pl.filipowm.discovery.application.MoleculeDto;
import pl.filipowm.discovery.application.MoleculeListDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MoleculeFinder {

    Flux<MoleculeListDto> findAllMolecules();

    Mono<MoleculeDto> getMolecule(Long id);
}
