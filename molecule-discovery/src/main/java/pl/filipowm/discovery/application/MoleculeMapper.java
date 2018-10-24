package pl.filipowm.discovery.application;

import org.mapstruct.*;
import pl.filipowm.discovery.application.command.MoleculeDiscoveryCommand;
import pl.filipowm.discovery.domain.Compound;
import pl.filipowm.discovery.domain.Molecule;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public abstract class MoleculeMapper {

    @Mappings({
            @Mapping(target = "phase", constant = "AGGREGATE"),
            @Mapping(target = "discoveryId", expression = "java( java.util.UUID.randomUUID().toString() )")
    })
    public abstract Molecule forCreate(MoleculeDiscoveryCommand command);

    public abstract MoleculeDto forGet(Molecule molecule);

    public abstract MoleculeListDto forList(Molecule molecule);

    public abstract CompoundDto toCompoundDto(Compound compound);

    public abstract Compound toCompound(CompoundDto dto);

    @AfterMapping
    protected void applyMoleculeToCompounds(@MappingTarget Molecule molecule) {
        molecule.getIngredients().forEach(compound -> compound.setMolecule(molecule));
    }
}
