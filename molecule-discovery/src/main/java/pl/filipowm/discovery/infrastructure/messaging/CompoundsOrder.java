package pl.filipowm.discovery.infrastructure.messaging;

import lombok.Data;
import pl.filipowm.discovery.application.CompoundDto;
import pl.filipowm.discovery.domain.Compound;
import pl.filipowm.discovery.domain.Molecule;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
class CompoundsOrder {

    private String discoveryId;
    private Collection<CompoundDto> compounds;

    static CompoundsOrder forMolecule(Molecule molecule) {
        CompoundsOrder order = new CompoundsOrder();
        order.setDiscoveryId(molecule.getDiscoveryId());
        List<CompoundDto> collect = molecule.getIngredients()
                .stream()
                .map(CompoundsOrder::map)
                .collect(Collectors.toList());
        order.setCompounds(collect);
        return order;
    }

    private static CompoundDto map(Compound compound) {
        CompoundDto dto = new CompoundDto();
        dto.setAmount(compound.getAmount());
        dto.setName(compound.getName());
        dto.setUnit(compound.getUnit());
        return dto;
    }
}
