package cn.didadu.sample.concurrentDesign.parallelarithmetic.parallelsort;

/**
 * Created by jinggg on 16/3/24.
 */
public class SerialBubbleSort {

    public static void main(String[] args) {
        int score[] = { 67, 88, 75, 120, 55, 55, 99, 100 };
        //SerialBubbleSort.bubbleSort(score);
        SerialBubbleSort.oddEvenSort(score);
        System.out.print("最终排序结果：");
        for (int a = 0; a < score.length; a++) {
            System.out.print(score[a] + "\t");
        }
    }

    /**
     * 普通冒泡算法
     * @param score
     */
    public static void bubbleSort(int[] score){
        int size = score.length;
        int temp;

        for (int i = 0; i < size - 1; i++) {
            for(int j=0;j<size-1-i;j++){
                if(score[j] > score[j+1]){
                    temp = score[j];
                    score[j] = score[j+1];
                    score[j+1] = temp;
                }
            }
            System.out.print("第" + (i + 1) + "次排序结果：");
            for (int a = 0; a < score.length; a++) {
                System.out.print(score[a] + "\t");
            }
            System.out.println("");
        }

    }


    public static void oddEvenSort(int[] arr){
        //是否有数据交换标志
        int exchFlag = 1;
        //奇偶排序标志：0、偶排序 1、奇排序
        int start = 0;
        //上次发生数据交换，或者上一次是偶排序时（奇偶排序必须成对出现）进入循环体
        while (exchFlag == 1 || start == 1){
            //假设本次排序没有数据交换
            exchFlag = 0;
            for(int i = start; i < arr.length - 1; i += 2){
                if(arr[i] > arr[i+1]){
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    exchFlag = 1;
                }
            }

            System.out.print("本次排序结果：");
            for (int a = 0; a < arr.length; a++) {
                System.out.print(arr[a] + "\t");
            }
            System.out.println("本次的:exchFlag = " + exchFlag + " && start = " + start);

            //切换奇偶排序标志
            if(start == 0){
                start = 1;
            }else{
                start = 0;
            }
        }
    }

}
