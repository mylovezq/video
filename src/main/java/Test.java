import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {


    public static void main(String[] args) {
         ExecutorService executor = Executors.newFixedThreadPool(100000);

        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                for (int j = 0; j < 10000000; j++) {
                    System.out.println(Thread.currentThread().getName()+"第---"+j);
                }
            });
        }

    }
}
