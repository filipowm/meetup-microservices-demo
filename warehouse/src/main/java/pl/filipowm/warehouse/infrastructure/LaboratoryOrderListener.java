package pl.filipowm.warehouse.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.filipowm.warehouse.domain.order.Order;
import pl.filipowm.warehouse.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
class LaboratoryOrderListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @StreamListener(OrderProcessor.COMPOUNDS_REQUEST)
    void receiveOrder(LaboratoryOrder laboratoryOrder) throws IOException {
        String orderJson = readOrder(laboratoryOrder.getCompounds());
        log.info("Received order for discovery {}: {}", laboratoryOrder.getDiscoveryId(), orderJson);
        Order order = Order.create(laboratoryOrder.getDiscoveryId(), orderJson);
        orderService.receive(order);
    }

    private String readOrder(Collection<CompoundOrder> compoundOrders) throws IOException {
        try (StringWriter sw = new StringWriter()) {
            objectMapper.writeValue(sw, compoundOrders);
            return sw.toString();
        } catch (IOException e) {
            log.warn("Unable to read order for compounds {}", compoundOrders);
            throw e;
        }
    }
}
