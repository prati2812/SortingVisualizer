package com.example.sortingvisualizer.sorting.InsertionSort;

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
import com.example.sortingvisualizer.sorting.BubbleSort.BubbleSortData;
import com.example.sortingvisualizer.sorting.BubbleSort.BubbleSortSortingSequence;

import java.util.ArrayList;
import java.util.Random;

public class InsertionSort {
    final Context context;
    final int arraySize;
    int[] data;
    InsertionSortData[] insertionSortData;
    View[] views;
    int[] positions;
    final LinearLayout linearLayout;

    InsertionSortSequence insertionSortSequence;

    final Random random;
    float width;
    float height;
    int textSize;
    final boolean isRandom;
    final int[] rawInput;
    int comparisons;
    final ArrayList<Pair<Integer, Integer>> sortedIndexes;

    public InsertionSort(Context context , LinearLayout linearLayout, int arraySize){
        this.context = context;
        this.random = new Random();
        this.arraySize = arraySize;
        this.isRandom = true;
        this.linearLayout = linearLayout;
        this.comparisons = 0;
        this.sortedIndexes = new ArrayList<>();
        rawInput = null;

        init();
    }

    public InsertionSort(Context context , LinearLayout linearLayout , int []rawInput){
        this.context = context;
        this.random  = new Random();
        this.arraySize = rawInput.length;
        this.isRandom = false;
        this.linearLayout = linearLayout;
        this.comparisons = 0;
        this.sortedIndexes = new ArrayList<>();
        this.rawInput = rawInput;

        init();
    }

    private void init(){
        if(arraySize > 8){
            textSize = 12;
        }
        else{
            textSize = 14;
        }

        int totalWidth = linearLayout.getWidth();
        int totalHeight = linearLayout.getHeight();
        this.width = (float) totalWidth / arraySize;
        this.height = (float) totalHeight;

        int Max = 0;

        this.data = new int[arraySize];
        this.insertionSortData = new InsertionSortData[arraySize];
        this.views = new View[arraySize];
        this.positions = new int[arraySize];
        if(isRandom){
            for(int i = 0; i < data.length; i++){
                data[i] = random.nextInt(20)+1;
                Max = Math.max(data[i], Max);
            }
        }else{
            for(int i = 0; i < data.length; i++){
                data[i] = rawInput[i];
                Max = Math.max(data[i],Max);
            }
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,(int)height,1);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0; i < data.length; i++){
            float x = (float)data[i]/Max;
            float h = (x * .75f) + .20f;

            View myView = layoutInflater.inflate(R.layout.elements_of_sorting,null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView textView = myView.findViewById(R.id.tv_elementvalue);
            textView.setText(String.valueOf(data[i]));
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(textSize);
            textView.getLayoutParams().height = (int) (height * h);
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_normal));
            linearLayout.addView(myView);

            InsertionSortData insertionSortData1 = new InsertionSortData();
            insertionSortData1.data = data[i];
            insertionSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            insertionSortData[i] = insertionSortData1;
        }

        this.insertionSortSequence = new InsertionSortSequence();
        this.insertionSortSequence.setViews(views);
        this.insertionSortSequence.setPositions(positions);
        this.insertionSortSequence.setAnimateViews(height,width,context);

        this.insertionSort();
    }

    public void forward(){
        insertionSortSequence.forward();
    }

    public void backward(){
        insertionSortSequence.backward();
    }

    private void insertionSort(){
        SortingAnimationState sortingAnimationState = new SortingAnimationState(InsertionSortInfo.is , InsertionSortInfo.getInsertionSortString());
        for(int i = 0; i < insertionSortData.length; i++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(insertionSortData[i].index , new Pair<>(AnimationDirection.NULL , 1)));
            sortingAnimationState.addHighlightIndexes(insertionSortData[i].index);
        }
        insertionSortSequence.addAnimationSequences(sortingAnimationState);
        insertion(insertionSortData);
    }

    private void insertion(InsertionSortData []a){
        int length = a.length;
        sortedIndexes.add(new Pair<>(insertionSortSequence.sortingAnimationStates.size() , a[0].index));

        for(int i = 1; i < length; i++){
            InsertionSortData val = a[i];
            int j = i - 1;

            SortingAnimationState sortingAnimationState1 = new SortingAnimationState(InsertionSortInfo.is , InsertionSortInfo.getInsertionSortString());
            sortingAnimationState1.addHighlightIndexes(val.index);
            sortingAnimationState1.addElementAnimationData(new ElementAnimationData(val.index, new Pair<>(AnimationDirection.NULL,1)));
            insertionSortSequence.addAnimationSequences(sortingAnimationState1);

            while(j >= 0){
                comparisons++;
                if(a[j].data > val.data){
                    SortingAnimationState sortingAnimationState = new SortingAnimationState(InsertionSortInfo.l_greater_r , InsertionSortInfo.getComparedString(a[j].data,val.data,j,j+1));
                    sortingAnimationState.addHighlightIndexes(val.index , a[j].index);
                    sortingAnimationState.addElementAnimationData(new ElementAnimationData(a[j].index, new Pair<>(AnimationDirection.RIGHT , 1)));
                    sortingAnimationState.addElementAnimationData(new ElementAnimationData(val.index , new Pair<>(AnimationDirection.LEFT, 1)));
                    insertionSortSequence.addAnimationSequences(sortingAnimationState);

                    a[j+1] = a[j];
                    j--;
                }
                else{
                    SortingAnimationState sortingAnimationState = new SortingAnimationState(InsertionSortInfo.l_lessEqual_r , InsertionSortInfo.getComparedString(a[j].data ,val.data , j , val.index));
                    sortingAnimationState.addHighlightIndexes(val.index , a[j].index);
                    insertionSortSequence.addAnimationSequences(sortingAnimationState);
                    break;
                }
            }
            SortingAnimationState sortingAnimationState2 = new SortingAnimationState("","");
            sortingAnimationState2.addElementAnimationData(new ElementAnimationData(val.index , new Pair<>(AnimationDirection.NULL,1)));
            insertionSortSequence.addAnimationSequences(sortingAnimationState2);
            sortedIndexes.add(new Pair<>(insertionSortSequence.sortingAnimationStates.size(), val.index));

            a[j+1] = val;
        }

    }

}
