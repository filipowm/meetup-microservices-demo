package pl.filipowm.warehouse.infrastructure;

import pl.filipowm.warehouse.domain.order.Order;
import pl.filipowm.warehouse.domain.order.OrderService;
import pl.filipowm.warehouse.domain.order.OrderState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
class OrderProcessingJob {

    private final OrderService service;

    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    void startProcessingOrders() {
        processInState(OrderState.PLACED, service::process);
    }

    @Scheduled(fixedDelay = 15000, initialDelay = 10000)
    void finalizeOrders() {
        processInState(OrderState.IN_PROCESS, service::fulfill);
    }

    private void processInState(OrderState status, Consumer<Order> consumer) {
        service.findAllInState(status)
                .parallelStream()
                .peek(order -> log.info("Processing order with status={} for discovery={}", status, order.getDiscoveryId()))
                .forEach(consumer);
    }
}
