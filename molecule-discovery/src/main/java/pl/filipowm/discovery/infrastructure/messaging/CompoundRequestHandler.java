package pl.filipowm.discovery.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import pl.filipowm.discovery.domain.Molecule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompoundRequestHandler {

    private final CompoundProcessor processor;

    public Flux<CompoundsOrder> requestCompounds(Mono<Molecule> moleculeMono) {
//        processor.request().send(MessageBuilder.withPayload(moleculeMono).build());
//        return null;
        moleculeMono.
                map(CompoundsOrder::forMolecule)
                .flux()
                .doOnNext(c -> log.info("Sending: {}", c.getDiscoveryId()))
                .subscribe(c -> processor.request().send(MessageBuilder.withPayload(c).build()));
//        CompoundsOrder order = CompoundsOrder.forMolecule(moleculeMono.block());
//        processor.request().send(MessageBuilder.withPayload(order.).build());
        return null;
    }
}
