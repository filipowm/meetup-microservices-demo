package pl.filipowm.discovery.application;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.filipowm.discovery.domain.Unit;

@Data
@NoArgsConstructor
public class CompoundDto {

    private String name;
    private long amount;
    private Unit unit;

}
