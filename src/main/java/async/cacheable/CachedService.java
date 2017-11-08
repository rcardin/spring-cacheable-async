package async.cacheable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
public class CachedService {

    private static final Logger logger = LoggerFactory.getLogger(CachedService.class);

    private final AsyncService service;
    private final Random random;
    private final Executor executor;

    @Autowired
    public CachedService(AsyncService service, Random random, Executor executor) {
        this.service = service;
        this.random = random;
        this.executor = executor;
    }

    @Cacheable(cacheNames = "ints")
    public CompletableFuture<Integer> randomIntUsingSpringAsync() throws InterruptedException {
        final CompletableFuture<Integer> promise = new CompletableFuture<>();
        service.performTask(promise);
        return promise;
    }

    @Cacheable(cacheNames = "ints1")
    public CompletableFuture<Integer> randomIntNativelyAsync() throws
            InterruptedException {
        return CompletableFuture.supplyAsync(this::getAsyncInteger, executor);
    }

    private Integer getAsyncInteger() {
        logger.info("Entering performTask");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return random.nextInt(1000);
    }
}
