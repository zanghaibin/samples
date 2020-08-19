import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author ：zanghaibin
 * @description：
 * @date ：Created in 2020/8/10 16:14
 */
public class CompletableFutureDemo {
    public static void main(String[] args) {
        // 使用ForkJoinPool.commonPool()作为它的线程池执行异步代码。
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Hello");
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("CompletableFuture");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");

        try {
            System.out.println(future1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("CompletableFuture");
    }
}
