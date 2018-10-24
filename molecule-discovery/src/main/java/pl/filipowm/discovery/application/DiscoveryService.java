package pl.filipowm.discovery.application;

import pl.filipowm.discovery.application.command.MoleculeDiscoveryCommand;
import reactor.core.publisher.Mono;

public interface DiscoveryService {

    Mono<MoleculeDto> startDiscovery(MoleculeDiscoveryCommand command);

}
