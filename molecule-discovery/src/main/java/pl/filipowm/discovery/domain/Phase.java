package pl.filipowm.discovery.domain;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public enum Phase {

    AGGREGATE(molecule -> {
    }),
    RESEARCH(molecule -> {
    }),
    TEST(molecule -> {
    }),
    RELEASED(molecule -> {
    }),
    DROPPED(molecule -> {
    });

    private final Consumer<Molecule> handler;

    public void handle(Molecule molecule) {
        handler.accept(molecule);
    }

}
