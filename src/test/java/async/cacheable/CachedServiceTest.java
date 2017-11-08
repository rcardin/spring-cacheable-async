package async.cacheable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CachedServiceTest {

    @MockBean
    private Random random;

    @Autowired
    private CachedService cachedService;

    @Test
    public void randomIntUsingSpringAsyncShouldProperlyCacheCompletableFutures() throws Exception {
        given(random.nextInt(1000)).willReturn(42);
        final CompletableFuture<Integer> aFuture = cachedService.randomIntUsingSpringAsync();
        final CompletableFuture<Integer> anotherFuture = cachedService.randomIntUsingSpringAsync();
        assertThat(aFuture).isEqualTo(anotherFuture);
        assertThat(aFuture.get()).isEqualTo(42);
        assertThat(anotherFuture.get()).isEqualTo(42);
        verify(random, times(1)).nextInt(1000);
    }

    @Test
    public void randomIntNativelyAsyncShouldProperlyCacheCompletableFutures() throws Exception {
        given(random.nextInt(1000)).willReturn(42);
        final CompletableFuture<Integer> aFuture = cachedService.randomIntNativelyAsync();
        final CompletableFuture<Integer> anotherFuture = cachedService.randomIntNativelyAsync();
        assertThat(aFuture).isEqualTo(anotherFuture);
        assertThat(aFuture.get()).isEqualTo(42);
        assertThat(anotherFuture.get()).isEqualTo(42);
        verify(random, times(1)).nextInt(1000);
    }
}