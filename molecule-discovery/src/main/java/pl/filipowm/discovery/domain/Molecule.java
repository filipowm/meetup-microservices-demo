package pl.filipowm.discovery.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.TreeSet;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Molecule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String discoveryId;
    @NotNull
    private String name;
    @Enumerated(EnumType.STRING)
    private Phase phase;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "molecule",
            orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private Set<Compound> ingredients = new TreeSet<>();

    public boolean isDiscovery() {
        return !(isDropped() || isReleased());
    }

    public boolean isDropped() {
        return phase == Phase.DROPPED;
    }

    public boolean isReleased() {
        return phase == Phase.RELEASED;
    }

    public void proceedToPhase(Phase phase) {
        this.phase = phase;
        phase.handle(this);
    }

    public Molecule startResearch() {
        proceedToPhase(Phase.RESEARCH);
        return this;
    }
}
