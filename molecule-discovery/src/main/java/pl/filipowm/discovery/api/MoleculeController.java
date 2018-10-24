package pl.filipowm.discovery.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.filipowm.discovery.application.MoleculeDto;
import pl.filipowm.discovery.application.MoleculeListDto;
import pl.filipowm.discovery.domain.MoleculeFinder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/molecule")
@RequiredArgsConstructor
class MoleculeController {
    private final MoleculeFinder finder;

    @GetMapping
    Flux<MoleculeListDto> listAll() {
        return finder.findAllMolecules();
    }

    @GetMapping("{id}")
    Mono<MoleculeDto> getDetails(@PathVariable String id) {
        return finder.getMolecule(Long.valueOf(id));
    }

}
