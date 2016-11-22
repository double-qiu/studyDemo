package cn.didadu.sample.thread.forkjoin;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangjing on 2015/9/10.
 * 在Java7中，JDK提供对多线程开发提供了一个非常强大的框架，就是Fork/Join框架。
 * 这个是对原来的Executors更进一步，在原来的基础上增加了并行分治计算中的一种Work-stealing策略，就是指的是。
 * 当一个线程正在等待他创建的子线程运行的时候，当前线程如果完成了自己的任务后，就会寻找还没有被运行的任务并且运行他们，
 * 这样就是和Executors这个方式最大的区别，更加有效的使用了线程的资源和功能。所以非常推荐使用Fork/Join框架。
 * 下面我们以一个例子来说明这个框架如何使用，主要就是创建一个含有10000个资源的List，分别去修改他的内容
 */
public class Task extends RecursiveAction {

    // These attributes will determine the block of products this task has to
    // process.
    private List<Product> products;
    private int first;
    private int last;
    // store the increment of the price of the products
    private double increment;

    public Task(List<Product> products, int first, int last, double increment) {
        super();
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrices();
        } else {
            int middle = (first + last) / 2;
            System.out.printf("Task: Pending tasks:%s\n", getQueuedTaskCount());
            Task t1 = new Task(products, first, middle + 1, increment);
            Task t2 = new Task(products, middle + 1, last, increment);
            invokeAll(t1, t2);
        }
    }

    private void updatePrices() {
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (1 + increment));
        }
    }

    public static void main(String[] args) {

        ProductListGenerator productListGenerator = new ProductListGenerator();
        List<Product> products = productListGenerator.generate(10000);
        Task task = new Task(products, 0, products.size(), 0.2);

        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(task);

        do {
            System.out.printf("Main: Thread Count: %d\n",
                    pool.getActiveThreadCount());
            System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        pool.shutdown();

        if(task.isCompletedNormally()) {
            System.out.printf("Main: The process has completed normally.\n");
        }

        for(Product product : products) {
            if(product.getPrice() != 12) {
                System.out.printf("Product %s: %f\n",product.getName(),product.getPrice());
            }
        }

        System.out.println("Main: End of the program.\n");
    }

}
