package edu.java.configuration;

import org.springframework.data.util.ReactiveWrappers;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import java.time.Duration;
import java.util.List;

public class RetryConfiguration {

    public static final int MAX_ATTEMPTS = 5;
    public static final Duration FIXED_DELAY = Duration.ofSeconds(10);

    void test() {
//        Retry.fixedDelay(MAX_ATTEMPTS, FIXED_DELAY).fi;
    }

    private boolean filterError(Throwable throwable, List<Integer> codes) {
        return throwable instanceof WebClientResponseException &&
            codes.contains(((WebClientResponseException) throwable).getStatusCode().value());
    }

    RetryBackoffSpec createRetryPolicy(ApplicationConfig.RetryBackoffPolicy type, List<Integer> codes) {
        RetryBackoffSpec retryBackoffSpec = null;

        switch (type) {
            case CONSTANT -> retryBackoffSpec = Retry.fixedDelay(MAX_ATTEMPTS, FIXED_DELAY);
            case EXPONENT -> retryBackoffSpec = Retry.backoff(MAX_ATTEMPTS, FIXED_DELAY);
        }

        retryBackoffSpec = retryBackoffSpec.filter(error -> filterError(error, codes));

        return retryBackoffSpec;
    }
}
