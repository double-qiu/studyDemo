package cn.didadu.sample.concurrentDesign.parallelarithmetic.parallelsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 并行搜索
 * Created by jinggg on 16/3/23.
 */
public class ParallelSearch {

    static int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10};
    static ExecutorService pool = Executors.newCachedThreadPool();
    static final int Thread_Num = 2;
    static AtomicInteger result = new AtomicInteger(-1);

    /**
     * 最终的搜索方法
     * @param searchValue
     * @param beginPos
     * @param endPos
     * @return
     */
    public static int search(int searchValue, int beginPos, int endPos){
        for(int i = beginPos; i < endPos; i++){
            if(result.get() > 0){
                return result.get();
            }
            if(arr[i] == searchValue){
                //
                if(!result.compareAndSet(-1,i)){
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }


    public static class SearchTask implements Callable<Integer>{

        int begin,end,searchValue;

        public SearchTask(int searchValue, int begin, int end){
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }

        @Override public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getId() + "启动！");
            return search(searchValue,begin,end);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int searchValue = 7;
        int subAttrSize = arr.length / Thread_Num + 1;
        List<Future<Integer>> re = new ArrayList<>();
        for(int i = 0; i < arr.length; i += subAttrSize){
            int end = i + subAttrSize;
            if(end >= arr.length){
                end = arr.length;
            }
            re.add(pool.submit(new SearchTask(searchValue,i,end)));
        }

        for(Future<Integer> fu : re){
            if(fu.get() > 0){
                System.out.println("在数组的第" + (fu.get() + 1) + "个");
            }
        }
        pool.shutdown();
    }


}
