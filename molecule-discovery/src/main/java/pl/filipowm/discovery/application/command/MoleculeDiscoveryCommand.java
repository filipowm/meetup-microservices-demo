package pl.filipowm.discovery.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.filipowm.discovery.application.CompoundDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoleculeDiscoveryCommand {

    @NotBlank
    private String name;

    @NotEmpty
    private Set<CompoundDto> ingredients;

}
