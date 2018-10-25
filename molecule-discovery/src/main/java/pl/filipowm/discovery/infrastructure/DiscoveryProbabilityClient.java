package pl.filipowm.discovery.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "probabilityClient", url = "${prediction.url}")
public interface DiscoveryProbabilityClient {

    @GetMapping("/api/discovery/prediction/{id}")
    Double predict(@PathVariable("id") String id);

}
