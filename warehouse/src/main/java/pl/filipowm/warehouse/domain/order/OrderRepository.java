package pl.filipowm.warehouse.domain.order;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Collection<Order> findAllByState(OrderState state);

}
