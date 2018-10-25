package pl.filipowm.warehouse.infrastructure;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompoundOrder {
    private String name;
    private long amount;
    private String unit;
}
