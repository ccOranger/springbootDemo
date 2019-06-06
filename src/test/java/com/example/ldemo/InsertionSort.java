package com.example.ldemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @package: com.example.ldemo
 * @className: ${TYPE_NAME}
 * @description: 排序
 * @author: 李臣臣
 * @createDate: 2019/5/15 14:43
 * @updateUser: 李臣臣
 * @updateDate: 2019/5/15 14:43
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertionSort {

    public static int[] array = {28, 12, 412, 1, 12, 656, 23, 2, 97, 34, 62, 41, 26, 20, 1234, 4};

    @Test
    public void sort() {
        //   bubbleSort(array);
        //  _quickSort(array,0,array.length);
        //inner(array);
        quickSort(array,0,array.length-1);
        System.out.print("排序后：");
        for (int i = 1; i < array.length; i++) {
            System.out.print((int) array[i] + " ");
        }

    }


    /**
     * 在长度为N的无序数组中，第一次遍历n-1个数，找到最小的数值与第一个元素交换；
     * 第二次遍历n-2个数，找到最小的数值与第二个元素交换；
     * 。。。
     * 第n-1次遍历，找到最小的数值与第n-1个元素交换，排序完成。
     * 平均时间复杂度：O(n2)
     */
    public static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
            int temp; // 记录临时中间值
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end)) i++;
                while ((numbers[j] > base) && (j > start)) j--;
                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j)
                quickSort(numbers, start, j);
            if (end > i)
                quickSort(numbers, i, end);
        }



    }

    /**
     * 直接插入
     */
    public void inner(int[] numbers) {
        int length = numbers.length;
        int num;
        for (int i = 1; i < length; i++) {
            num = numbers[i];
            int j = i - 1;//已经排序好的序列元素个数
            while (j >= 0 && numbers[j] > num) {
                numbers[j + 1] = numbers[j];//元素移动一格
                j--;
            }
            numbers[j + 1] = num;//将需要插入的数放在要插入的位置
        }

        System.out.print("排序后：");
        for (int i = 1; i < numbers.length; i++) {
            System.out.print((int) numbers[i] + " ");
        }
    }


    /**
     * 冒泡排序
     * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 针对所有的元素重复以上的步骤，除了最后一个。
     * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     * 时间复杂度O(n2)
     *
     * @param numbers 需要排序的整型数组
     */
    public void bubbleSort(int[] numbers) {

        int temp;//临时变量
        int size = numbers.length;
        for (int i = 0; i < size - 1; i++) {   //表示趟数，一共arr.length-1次。

            for (int j = 0; j < size - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
            /*for(int j=size-1; j>i; j--){

                if(numbers[j] < numbers[j-1]){
                    temp = numbers[j];
                    numbers[j] = numbers[j-1];
                    numbers[j-1] = temp;
                }
            }*/
        }
        System.out.print("排序后：");
        for (int i = 1; i < numbers.length; i++) {
            System.out.print((int) numbers[i] + " ");
        }
    }

}
