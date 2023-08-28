package com.example.sortingvisualizer.sorting.SelectionSort;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.sortingvisualizer.R;
import com.example.sortingvisualizer.animation.AnimationDirection;
import com.example.sortingvisualizer.animation.ElementAnimationData;
import com.example.sortingvisualizer.animation.SortingAnimationState;

import java.util.ArrayList;
import java.util.Random;

public class SelectionSort {

    final Context context;
    final int arraySize;
    int[] data;
    SelectionSortData[] selectionSortData;
    View[] views;
    int[] positions;
    final LinearLayout linearLayout;

    SelectionSortSortingSequence selectionSortSortingSequence;

    final Random random;
    float width;
    float height;
    int textSize;
    final boolean isRandom;
    final int[] rawInput;
    int comparisons;
    final ArrayList<Pair<Integer, Integer>> sortedIndexes;


    public SelectionSort(Context context, int arraySize, LinearLayout linearLayout) {
        this.context = context;
        this.arraySize = arraySize;
        this.linearLayout = linearLayout;
        this.random = new Random();
        this.isRandom = true;
        this.comparisons = 0;
        this.sortedIndexes = new ArrayList<>();
        rawInput = null;

        init();
    }

    public SelectionSort(Context context , LinearLayout linearLayout , int []rawInput){
        this.context = context;
        this.linearLayout = linearLayout;
        this.rawInput = rawInput;
        this.random = new Random();
        this.isRandom = false;
        this.comparisons = 0;
        this.sortedIndexes = new ArrayList<>();
        this.arraySize = rawInput.length;

        init();
    }

    private void init(){
        if (arraySize > 8){
            textSize = 12;
        }
        else {
            textSize =14;
        }
        int totalWidth = linearLayout.getWidth();
        int totalHeight = linearLayout.getHeight();
        this.width = (float) totalWidth / arraySize;
        this.height = (float) totalHeight;

        int Max = 0;

        this.data = new int[arraySize];
        this.selectionSortData = new SelectionSortData[arraySize];
        this.views = new View[arraySize];
        this.positions = new int[arraySize];
        if(isRandom){
            for(int i = 0; i < data.length; i++){
                data[i] = random.nextInt(20)+1;
                Max = Math.max(data[i], Max);
            }
        }
        else{
            for(int i = 0; i < data.length; i++){
                data[i] = rawInput[i];
                Max = Math.max(data[i], Max);
            }
        }

        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(0,(int)height , 1);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);

        for(int i = 0; i < data.length; i++){
            float x = (float)data[i] / Max;
            float h = (x * .75f) + .20f;

            View myView = layoutInflater.inflate(R.layout.elements_of_sorting , null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView textView = myView.findViewById(R.id.tv_elementvalue);
            textView.setText(String.valueOf(data[i]));
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(textSize);
            textView.getLayoutParams().height = (int) (height * h);
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_normal));
            linearLayout.addView(myView);

            SelectionSortData selectionSortData1 = new SelectionSortData();
            selectionSortData1.data = data[i];
            selectionSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            selectionSortData[i] = selectionSortData1;

        }

        this.selectionSortSortingSequence = new SelectionSortSortingSequence();
        this.selectionSortSortingSequence.setViews(views);
        this.selectionSortSortingSequence.setPositions(positions);
        this.selectionSortSortingSequence.setAnimateViews(height,width,context);

        this.selectionSort();
    }

    public void forward(){
        selectionSortSortingSequence.forward();
    }

    public void backward(){
        selectionSortSortingSequence.backward();
    }

    public void selectionSort(){

        SortingAnimationState sortingAnimationState = new SortingAnimationState(SelectionSortInfo.selection_sort ,
                SelectionSortInfo.getSelectionSortString());
        for(int i = 0; i < selectionSortData.length; i++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(selectionSortData[i].index , new Pair<>(AnimationDirection.NULL,1)));
            sortingAnimationState.addHighlightIndexes(selectionSortData[i].index);
        }

        selectionSortSortingSequence.addAnimationSequences(sortingAnimationState);
        selection(selectionSortData);

    }

    public void selection(SelectionSortData[] a){
       int length = a.length;
       for(int i =0; i < length-1; i++){
           int min_index = i;

           SortingAnimationState sortingAnimationState = new SortingAnimationState(SelectionSortInfo.Val , SelectionSortInfo.getValueString(min_index));
           sortingAnimationState.addHighlightIndexes(a[min_index].index);
           selectionSortSortingSequence.addAnimationSequences(sortingAnimationState);

           for(int j = i + 1; j < length; j++){
               comparisons++;
               if(a[j].data < a[min_index].data){
                   SortingAnimationState sortingAnimationState1 = new SortingAnimationState(SelectionSortInfo.L_lessEqual_R , SelectionSortInfo.getComparedString(a[j].data , a[min_index].data , j));
                   sortingAnimationState1.addHighlightIndexes(a[j].index);
                   selectionSortSortingSequence.addAnimationSequences(sortingAnimationState1);
                   min_index = j;
               }
               else{
                   SortingAnimationState sortingAnimationState1 = new SortingAnimationState(SelectionSortInfo.L_greaterEqual_R , SelectionSortInfo.getComparedString(a[j].data , a[min_index].data , j));
                   sortingAnimationState1.addHighlightIndexes(a[min_index].index);
                   sortingAnimationState1.addHighlightIndexes(a[j].index);
                   selectionSortSortingSequence.addAnimationSequences(sortingAnimationState1);
               }
           }

           if(min_index != i){
               int difference = Math.abs(i - min_index);
               SortingAnimationState animationState1 = new SortingAnimationState(SelectionSortInfo.Swap , SelectionSortInfo.getSwapString(i,min_index));
               animationState1.addHighlightIndexes(a[min_index].index , a[i].index);
               animationState1.addElementAnimationData(new ElementAnimationData(a[min_index].index
                  , new Pair<>(AnimationDirection.LEFT , difference)));
               animationState1.addElementAnimationData(new ElementAnimationData(a[i].index
                  , new Pair<>(AnimationDirection.RIGHT , difference)));

               selectionSortSortingSequence.addAnimationSequences(animationState1);
           }

           sortedIndexes.add(new Pair<>(selectionSortSortingSequence.sortingAnimationStates.size(),a[min_index].index));
           int tempIndex = a[min_index].index;
           int  tempData = a[min_index].data;

           a[min_index].index = a[i].index;
           a[min_index].data = a[i].data;

           a[i].index = tempIndex;
           a[i].data = tempData;

       }

    }

}
