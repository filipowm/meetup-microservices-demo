package pl.filipowm.warehouse.domain.order;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String data;

    @Column(nullable = false)
    private String discoveryId;

    @Enumerated(EnumType.STRING)
    private OrderState state = OrderState.PLACED;

    public static Order create(String discoveryId, String data) {
        Order order = new Order();
        order.setDiscoveryId(discoveryId);
        order.setData(data);
        return order;
    }

    OrderState markFullfilled() {
        return withState(OrderState.FULFILLED);
    }

    OrderState markInProgress() {
        return withState(OrderState.IN_PROCESS);
    }

    OrderState markNew() {
        return withState(OrderState.PLACED);
    }

    OrderState withState(OrderState state) {
        this.state = state;
        return this.state;
    }
}
