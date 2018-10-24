package pl.filipowm.discovery.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Compound implements Comparable<Compound> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private long amount;
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOLECULE_ID")
    private Molecule molecule;

    @Override
    public int compareTo(Compound o) {
        if (name != null && o.name != null) {
            return name.compareTo(o.name);
        }
        return 0;
    }
}
