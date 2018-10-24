package pl.filipowm.discovery.application;

import lombok.Data;

import java.util.Set;

@Data
public class MoleculeDto extends MoleculeListDto {

    private Set<CompoundDto> ingredients;

}
