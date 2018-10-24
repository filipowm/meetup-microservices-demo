package pl.filipowm.warehouse.infrastructure.config;

import pl.filipowm.warehouse.infrastructure.OrderProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Slf4j
@EnableAspectJAutoProxy
@EnableBinding(OrderProcessor.class)
@EnableScheduling
public class WarehouseConfiguration implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Warehouse service configured.");
    }
}
