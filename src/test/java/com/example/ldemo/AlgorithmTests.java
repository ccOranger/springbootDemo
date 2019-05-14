package com.example.ldemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @package: com.example.ldemo
 * @className: ${TYPE_NAME}
 * @description: 类作用描述
 * @author: 李臣臣
 * @createDate: 2019/5/14 14:03
 * @updateUser: 李臣臣
 * @updateDate: 2019/5/14 14:03
 * @updateRemark: The modified content
 * @version: 1.0
 * <p>copyright: Copyright (c) 2019</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AlgorithmTests {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void  solution(){
       // int[] t = twoSum(new int[]{2, 7, 11, 15},9);
       // logger.debug("twoSum==={}",t);

      //  logger.debug("sssss===={}",reverse1(120))  ;

      //  logger.debug("isPalindrome===={}",isPalindrome(10));

        logger.debug("longestCommonPrefix===={}",longestCommonPrefix(new String[] {"fl","flow","flight"}))  ;

    }


    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        for(int i=0;i<nums.length;i++){
            for (int j=i+1;j<nums.length;j++){
                if (nums[i] + nums[j] ==target){
                    return new int[] { i, j };
                }
            }
        }
        return  null;
    }

    public  int[] twoSum2(int[] nums ,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<nums.length;i++){
            map.put(nums[0],i);
        }
        for(int i=0;i<nums.length;i++){
            int c = target-nums[i];
            if (map.containsKey(c) && i != map.get(c)){
                return new int[] { i, map.get(c) };
            }
        }

        return null;
    }

    public int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }


    /***
     * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     * @param x
     * @return
     */
    public int reverse(int x) {
        String s = x+"";
        char[] c= s.toCharArray();
        char[] cc ;
        char[] ccc;
        int j =0;
        boolean b =false;
        if (c.length>1&& c[0] == '-'){
            cc = new char[c.length-1];
            System.arraycopy(c,1,cc,0,c.length-1);
            b= true;
        }else {
            cc = new char[c.length];
            System.arraycopy(c,0,cc,0,c.length);
        }

        ccc = new char[cc.length];
        for (int i=cc.length-1;i>=0;i--){
            ccc[j] = cc[i];
            j++;
        }
        StringBuffer k = new StringBuffer();
        for (int i=0;i<ccc.length;i++){
            if (i ==0 && ccc[0] == '0'){
                continue;
            }
             k.append(ccc[i]);
        }

        if (b){
            return Integer.parseInt("-"+k.toString());
        }
        return Integer.parseInt(k.toString());

    }


    public int reverse1(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }
    /***
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     */
    public boolean isPalindrome(int x) {

        if (x<0 || (x%10 == 0 && x != 0)){
            return  false;
        }
        int rev = 0;
        int y = x ;
        while (y != 0){
            int pop =y % 10;
            y /= 10;
            rev = rev * 10 + pop;
        }

        return x == rev;
    }


    /***
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * ，返回空字符串 ""。
     */

    public String longestCommonPrefix(String[] strs) {
        int j =1;
        String temp = "";
        while (true){
            if (strs.length == 0 || j>strs[0].length()){
                return temp ;
            }else {
                for(int i =0;i<strs.length;i++ ){
                    if (strs[i].length()<j || !strs[0].substring(0,j).equals(strs[i].substring(0,j))){
                        return temp ;
                    }
                }
                temp =  strs[0].substring(0,j);
                j++;
            }

        }
    }
}
