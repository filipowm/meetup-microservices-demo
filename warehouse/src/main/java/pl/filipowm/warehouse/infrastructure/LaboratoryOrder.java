package pl.filipowm.warehouse.infrastructure;

import lombok.Builder;
import lombok.Value;

import java.util.Collection;
import java.util.HashSet;

@Value
@Builder
class LaboratoryOrder {
    private String discoveryId;
    private Collection<CompoundOrder> compounds = new HashSet<>();
}
