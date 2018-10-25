package pl.filipowm.warehouse.infrastructure;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
class LaboratoryOrder {
    private String discoveryId;
    private Collection<CompoundOrder> compounds = new ArrayList<>();
}