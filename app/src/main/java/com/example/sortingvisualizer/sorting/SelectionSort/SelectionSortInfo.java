package com.example.sortingvisualizer.sorting.SelectionSort;

import java.util.HashMap;

public class SelectionSortInfo {
    public static final String selection_sort = "Selection Sort";
    public static final String Val = "I";
    public static final String Swap = "SWAP";
    public static final String L_lessEqual_R = "LEFT <= RIGHT";
    public static final String L_greaterEqual_R = "LEFT > RIGHT";

    public static final HashMap<String , Integer[]> map = new HashMap<>();

    static {
        map.put(selection_sort , new Integer[]{0});
        map.put(Val , new Integer[]{3,4});
        map.put(Swap , new Integer[]{12,13});
        map.put(L_lessEqual_R , new Integer[]{7,8});
        map.put(L_greaterEqual_R , new Integer[]{9,10});
    }

    public static final int[] boldIndex = new int[]{0};

    public static final String[] psuedocode = new String[]{
          "Selection_Sort(Data):",
          "    length = Data.length",
          "",
          "    for(i = 0 to length - 1)",
          "        min_index = i",
          "        for(j = i+1 to length)",
          "            if(Data[j] < Data[min_index])",
          "               min_index = j",
          "            else",
          "               continue",
          "",
          "        if(min_index !=i)",
          "           swap(Data[i],Data[min_index])",
          "",
    };

    public static String getComparedString(int a, int b, int index){
        if(a < b){
            return a + " < " + b + ", min_index =" + index;
        }
        return  a + " >= " + b + ", continue";
    }

    public static String getSelectionSortString(){
        return  "SelectionSort(Data)";
    }

    public static String getValueString(int index){
        return "Min_Index" +index;
    }

    public static String getSwapString(int i, int min_index){
        return "SWAP Data [" + i + "] and Data [" + min_index + "]";
    }

}
