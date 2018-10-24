package pl.filipowm.discovery.infrastructure.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface CompoundProcessor {

    String COMPOUND_REQUEST = "compounds_request";
    String COMPOUND_RESPONSE = "compounds_response";

    @Input(COMPOUND_RESPONSE)
    SubscribableChannel response();

    @Output(COMPOUND_REQUEST)
    MessageChannel request();

}
