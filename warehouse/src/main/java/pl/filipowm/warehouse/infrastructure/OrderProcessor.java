package pl.filipowm.warehouse.infrastructure;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderProcessor {

    String COMPOUNDS_REQUEST = "compounds_request";
    String COMPOUNDS_RESPONSE = "compounds_response";

    @Input(COMPOUNDS_REQUEST)
    SubscribableChannel compounds();

    @Output(COMPOUNDS_RESPONSE)
    MessageChannel request();

}