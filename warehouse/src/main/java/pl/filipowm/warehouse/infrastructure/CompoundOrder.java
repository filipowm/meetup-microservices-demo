package pl.filipowm.warehouse.infrastructure;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CompoundOrder {
    private String name;
    private long amount;
    private String unit;
}
