package com.example.sortingvisualizer.sorting.BubbleSort;

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

public class BubbleSort {
    final Context context;
    final int arraySize;
    int[] data;
    BubbleSortData[] bubbleSortData;
    View[] views;
    int[] positions;
    final LinearLayout linearLayout;

    BubbleSortSortingSequence bubbleSortSortingSequence;

    final Random random;
    float width;
    float height;
    int textSize;
    final boolean isRandom;
    final int[] rawInput;
    int comparisons;
    final ArrayList<Pair<Integer, Integer>> sortedIndexes;


    public BubbleSort(Context context, LinearLayout linearLayout, int arraySize) {
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

    public BubbleSort(Context context, LinearLayout linearLayout, int[] rawInput) {
        this.context = context;
        this.random = new Random();
        this.arraySize = rawInput.length;
        this.isRandom = false;
        this.linearLayout = linearLayout;
        this.comparisons = 0;
        this.sortedIndexes = new ArrayList<>();
        this.rawInput = rawInput;

        init();
    }

    private void init() {
        if(arraySize > 8){
            textSize = 12;
        }
        else{
            textSize = 14;
        }

        int totalWidth = linearLayout.getWidth();
        int totalHeight = linearLayout.getHeight();
        this.width = (float) totalWidth / arraySize;
        this.height =  (float) totalHeight;

        int MAX = 0;

        this.data = new int[arraySize];
        this.bubbleSortData = new BubbleSortData[arraySize];
        this.views = new View[arraySize];
        this.positions = new int[arraySize];
        if(isRandom){
            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextInt(20) + 1;
                MAX = Math.max(data[i], MAX);
            }
        }
        else{
            for (int i = 0; i < data.length; i++) {
                data[i] = rawInput[i];
                MAX = Math.max(data[i], MAX);
            }
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, (int) height, 1);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0;i<data.length;i++){
            float x = (float)data[i] / (float)MAX;
            float h = (x * .75f) + .20f;

            View myView = vi.inflate(R.layout.elements_of_sorting, null);
            myView.setLayoutParams(layoutParams);
            myView.setPadding(5,5,5,5);
            TextView tv = myView.findViewById(R.id.tv_elementvalue);
            tv.setText(String.valueOf(data[i]));
            tv.setTextColor(Color.WHITE);
            tv.setTextSize(textSize);
            tv.getLayoutParams().height = (int) (height * h);
            tv.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_normal));
            linearLayout.addView(myView);

            BubbleSortData mergeSortData1 = new BubbleSortData();
            mergeSortData1.data = data[i];
            mergeSortData1.index = i;
            views[i] = myView;
            positions[i] = i;
            bubbleSortData[i] = mergeSortData1;
        }

        this.bubbleSortSortingSequence = new BubbleSortSortingSequence();
        this.bubbleSortSortingSequence.setViews(views);
        this.bubbleSortSortingSequence.setPositions(positions);
        this.bubbleSortSortingSequence.setAnimateViews(height,width,context);

        this.bubblesort();
    }

    public void forward(){
        bubbleSortSortingSequence.forward();
    }

    public void backward(){
        bubbleSortSortingSequence.backward();
    }

    private void bubblesort(){
        SortingAnimationState sortingAnimationState = new SortingAnimationState(BubbleSortInfo.bs , BubbleSortInfo.getBubbleSortString());
        for(int i=0; i < bubbleSortData.length; i++){
            sortingAnimationState.addElementAnimationData(new ElementAnimationData(bubbleSortData[i].index , new Pair<>(AnimationDirection.NULL , 1)));
            sortingAnimationState.addHighlightIndexes(bubbleSortData[i].index);
        }
        bubbleSortSortingSequence.addAnimationSequences(sortingAnimationState);
        bubble(bubbleSortData);
    }

    private void bubble(BubbleSortData []a){
        int length = a.length;
        boolean flag = false;

        for(int i=0; i < length; i++){
            flag = false;

            for(int j = 0; j < length - i - 1; j++){
                comparisons++;
                if(a[j].data > a[j+1].data){
                    SortingAnimationState sortingAnimationState = new SortingAnimationState(BubbleSortInfo.l_greater_r,BubbleSortInfo.getComparedString(a[j].data,a[j+1].data,j,j+1));
                    sortingAnimationState.addHighlightIndexes(bubbleSortData[j].index,bubbleSortData[j+1].index);
                    sortingAnimationState.addElementAnimationData(new ElementAnimationData(bubbleSortData[j].index,new Pair<>(AnimationDirection.RIGHT, 1)));
                    sortingAnimationState.addElementAnimationData(new ElementAnimationData(bubbleSortData[j+1].index , new Pair<>(AnimationDirection.LEFT,1)));
                    bubbleSortSortingSequence.addAnimationSequences(sortingAnimationState);

                    int tempIndex = a[j].index;
                    int  tempData = a[j].data;

                    a[j].index = a[j+1].index;
                    a[j].data = a[j+1].data;

                    a[j+1].index = tempIndex;
                    a[j+1].data = tempData;

                    flag = true;
                }
            }
            if(!flag){
                SortingAnimationState sortingAnimationState = new SortingAnimationState(BubbleSortInfo.bs , BubbleSortInfo.getFlagString());
                bubbleSortSortingSequence.addAnimationSequences(sortingAnimationState);
                for(int k = 0; k < length-i-1; k++){
                    sortedIndexes.add(new Pair<>(bubbleSortSortingSequence.sortingAnimationStates.size() , a[k].index));
                }
                return;
            }

            sortedIndexes.add(new Pair<>(bubbleSortSortingSequence.sortingAnimationStates.size() , a[length-i-1].index));
        }
    }


}





