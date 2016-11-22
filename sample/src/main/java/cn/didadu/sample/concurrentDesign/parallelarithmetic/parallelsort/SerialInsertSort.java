package cn.didadu.sample.concurrentDesign.parallelarithmetic.parallelsort;

/**
 * Created by jinggg on 16/3/24.
 */
public class SerialInsertSort {

    public static void main(String[] args){
        int score[] = { 10, 9, 8, 7, 6, 5, 4, 3,  2, 1 };
        //SerialInsertSort.insertSort(score);
        SerialInsertSort.shellSort(score);
        System.out.println("--------最终排序结果：--------");
        for (int a = 0; a < score.length; a++) {
            System.out.print(score[a] + "\t");
        }
    }

    /**
     * 普通插入排序
     * @param score
     */
    public static void insertSort(int[] score){
        int length = score.length;

        int key;
        int j;

        for(int i = 1; i < length; i++){
            //key为准备要插入的元素
            key = score[i];
            j = i - 1;
            while(j >= 0 && score[j] > key){
                score[j + 1] = score[j];
                j--;
            }
            score[j + 1] = key;

            System.out.print("第" + i + "次排序结果：");
            for (int a = 0; a < score.length; a++) {
                System.out.print(score[a] + "\t");
            }
            System.out.println("");
        }
    }

    /**
     * 希尔排序
     */
    public static void shellSort(int[] score){
        //计算出最大的h值
        int h = 1;
        while(h <= score.length / 3){
            h = h * 3 + 1;
        }

        while (h > 0){
            for(int i = h; i < score.length; i++){
                if(score[i] < score[i - h]){
                    int temp = score[i];
                    int j = i - h;
                    while (j >= 0 && score[j] > temp){
                        score[j + h] = score[j];
                        j -= h;
                    }
                    score[j + h] = temp;

                    System.out.print("第" + i + "次排序结果：");
                    for (int a = 0; a < score.length; a++) {
                        System.out.print(score[a] + "\t");
                    }
                    System.out.println("");
                }
            }
            h = (h - 1) / 3;
        }
    }
}
