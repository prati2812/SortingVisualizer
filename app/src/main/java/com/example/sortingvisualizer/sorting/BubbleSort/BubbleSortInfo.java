package com.example.sortingvisualizer.sorting.BubbleSort;

import java.util.HashMap;

public class BubbleSortInfo {

    public static final String bs = "Bubble Sort";
    public static final String flag = "Flag";
    public static final String l_lessEqual_r = "Left <= Right";
    public static final String l_greater_r = "Left > Right";

    public static final HashMap<String, Integer[]> map = new HashMap<>();

    static {
        map.put(bs, new Integer[]{0});
        map.put(flag , new Integer[]{13,14});
        map.put(l_lessEqual_r , new Integer[]{7,8,9});
        map.put(l_greater_r, new Integer[]{10,11});
    }

    public static final int[] boldIndexes = new int[]{0};
    public static final String[] PsuedoCode = new String[]{
            "Bubble Sort(Data): ",
            "    length = Data.length",
            "",
            "    for(i = 0 to length)",
            "         flag = false",
            "",
            "       for(j = 0 to length-i-1)",
            "           if(Data[j] > Data[j+1])",
            "               swap(Data[j] , Data[j+1])",
            "               flag = true",
            "           else",
            "               continue",
            " ",
            "       if(flag == false)",
            "          return",
            ""

    };

    public static String getComparedString(int a, int b, int aIndex, int bIndex){
        if(a > b){
            return a + " > " + b + ", Swap Data[" + aIndex + "] with Data["+ bIndex + "]";

        }
        return a + " <= " + b + ", Continue";
    }

    public static String getBubbleSortString(){
        return "Bubble Sort(Data)";
    }

    public static String getFlagString(){
        return "Flag == false, Break outer for loop";
    }

}
