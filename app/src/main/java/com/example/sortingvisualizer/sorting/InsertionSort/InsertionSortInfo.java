package com.example.sortingvisualizer.sorting.InsertionSort;

import java.util.HashMap;

public class InsertionSortInfo {
   public static final String is = "Insertion Sort";

   public static final String l_lessEqual_r = "Left <= Right";
   public static final String l_greater_r = "Left > Right";

   public static final HashMap<String,Integer[]> map = new HashMap<>();

   static {
       map.put(is, new Integer[]{0});
       map.put(l_lessEqual_r, new Integer[]{7,8,9});
       map.put(l_greater_r, new Integer[]{10,11});
   }

   public static final int[] boldIndexes = new int[]{0};

   public static final String [] psuedoCode = new String[]{
           "Insertion Sort(Data)",
           "    length = Data.length",
           "",
           "    for(i=1 to length)",
           "        val = Data[i]",
           "        j = i - 1",
           "        while(j >= 0)",
           "           if(Data[j] > val)",
           "              Swap(Data[j] , val)",
           "              j--",
           "           else",
           "              break",
           "",
           "        continue"
   };

   public static String getComparedString(int a, int b , int aIndex, int bIndex){
       if(a > b){
           return a + " > " + b + ", Swap Data[" + aIndex + "] with Data [" + bIndex +"]";
       }
       return a + " <= " + b + ", Continue";
   }

   public static String getInsertionSortString(){
       return "Insertion Sort(Data)";
   }

   public static String getFlagString(){
      return "Flag == false , Break outer for loop";
   }

}
