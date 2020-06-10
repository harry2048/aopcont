package com.baidu.aopcont.test;

public class CharCome {
    /**
     * 判断字符串中某个字符出现的次数
     * 使用字符串替换的方式，写法简单，但是效率较低 3-5倍
     * @param args
     */

    public static void main(String[] args) {
        String str = "asdfaAG;GALFHGhfadhgHADGghadljFGHgfhgfjghjghhhjdhkjkfhjlklkjjhjlk;'kljkatywtytjhkjhhhatysuykjkl;;kjklgkj;l;./kjjkhhshaahdjhk86543456546455464ghlk1676461656798484984aegoajgag65a46g7adgad6g4a6g4g7g46j84g6f4h89S4G6G4SG46SF4H会使法国四会方";
        String des = "a";
        for (int i = 0; i < 200; i++) {
            testReplace(str, des);
            //testFor(str, des);
        }
    }

    public static void testReplace(String srcStr, String d){
        long start = System.nanoTime();
        String replace = srcStr.replace(d, "");
        int length = replace.length();

        System.out.println("replace 方式查询的数量为：" + (srcStr.length()-length) +"  所需时间: "+(System.nanoTime()-start));
    }

    public static void testFor(String srcStr, String d){
        char c = d.charAt(0);
        long start = System.nanoTime();

        int count = 0;
        char[] charArray = srcStr.toCharArray();
        for (char item : charArray) {
            if (item == c) {
                count++;
            }
        }

        System.out.println("replace 方式查询的数量为：" + count +"  所需时间: "+(System.nanoTime()-start));
    }
}
