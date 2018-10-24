package pl.filipowm.discovery.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.filipowm.discovery.application.DiscoveryService;
import pl.filipowm.discovery.application.MoleculeDto;
import pl.filipowm.discovery.application.command.MoleculeDiscoveryCommand;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/discovery")
@RequiredArgsConstructor
class DiscoveryController {

    private final DiscoveryService discoveryService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<MoleculeDto> discover(@Valid @RequestBody MoleculeDiscoveryCommand command) {
        return discoveryService.startDiscovery(command);
    }
}
