package async.cacheable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncService {

    private static Logger logger = LoggerFactory.getLogger(AsyncService.class);

    private final Random random;

    @Autowired
    public AsyncService(Random random) {
        this.random = random;
    }

    @Async
    void performTask(CompletableFuture<Integer> promise) throws InterruptedException {
        logger.info("Entering performTask");
        Thread.sleep(2000);
        promise.complete(random.nextInt(1000));
    }
}
