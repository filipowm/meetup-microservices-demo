package pl.filipowm.warehouse.domain.order;

import pl.filipowm.warehouse.infrastructure.OrderProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderProcessor processor;

    public void receive(Order order) {
        order.markNew();
        repository.save(order);
    }

    public void fulfill(Order order) {
        order.markFullfilled();
        repository.save(order);
        processor.request().send(MessageBuilder.withPayload(order.getDiscoveryId()).build());
    }

    public void process(Order order) {
        order.markInProgress();
        repository.save(order);
    }

    public Collection<Order> findAllInState(OrderState status) {
        return repository.findAllByState(status);
    }
}
