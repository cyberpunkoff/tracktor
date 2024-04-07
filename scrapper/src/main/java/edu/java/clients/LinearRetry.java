package edu.java.clients;

import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetrySpec;
import java.time.Duration;

public class LinearRetry extends RetryB {
    private final Duration retryDuration;
    private final int retryAmount;

    public LinearRetry(Duration retryDuration, int retryAmount) {
        this.retryDuration = retryDuration;
        this.retryAmount = retryAmount;
    }

    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> flux) {
        return flux.flatMap(this::getRetry);
    }

    private Mono<Long> getRetry(Retry.RetrySignal rs) {
        if (rs.totalRetries() < retryAmount) {
            Duration delay = retryDuration.multipliedBy(rs.totalRetries() + 1);
            return Mono.delay(delay)
                .thenReturn(rs.totalRetries());
        } else {
            throw Exceptions.propagate(rs.failure());
        }
    }
}
