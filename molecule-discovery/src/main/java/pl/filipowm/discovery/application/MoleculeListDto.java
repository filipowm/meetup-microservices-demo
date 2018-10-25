package pl.filipowm.discovery.application;

import lombok.Data;
import pl.filipowm.discovery.domain.Phase;

@Data
public class MoleculeListDto {

    private Long id;
    private String name;
    private String discoveryId;
    private Phase phase;

}
