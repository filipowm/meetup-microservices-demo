package pl.filipowm.discovery.prediction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/api/discovery/prediction")
class PredictionController {

    private final Random random = new Random(100);

    @GetMapping("{id}")
    Double predict(@PathVariable String id) {
        double predicition = random.nextDouble();
        log.info("Discovery {} has {} chances to succeed", id, predicition);
        return predicition;
    }
}
