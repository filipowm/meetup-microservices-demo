package pl.filipowm.discovery.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.filipowm.discovery.domain.Molecule;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CompoundRequestHandler {

    private final CompoundProcessor processor;

    //    @SendTo(CompoundProcessor.COMPOUND_REQUEST)
    public CompoundsOrder requestCompounds(Mono<Molecule> moleculeMono) {
//        processor.request().send(MessageBuilder.withPayload(moleculeMono).build());
        return null;
//        CompoundsOrder order = CompoundsOrder.forMolecule(moleculeMono.block());
//        processor.request().send(MessageBuilder.withPayload(order).build());
//        return moleculeMono.map(CompoundsOrder::forMolecule).block();
    }
}
