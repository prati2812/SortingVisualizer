package com.example.sortingvisualizer.animation;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class SortingAnimationState {
    public final String state;
    public String info;
    public ArrayList<ElementAnimationData> elementAnimationData;
    public ArrayList<Integer> highlightIndexes;
    public ArrayList<Pair<Integer, String>> pointers;

    public SortingAnimationState(String state, String info) {
        this.state = state;
        this.info = info;
        this.elementAnimationData = new ArrayList<>();
        this.highlightIndexes = new ArrayList<>();
        this.pointers = new ArrayList<>();
    }

    public void addElementAnimationData(ElementAnimationData... elementAnimationDatas) {
        this.elementAnimationData.addAll(Arrays.asList(elementAnimationDatas));
    }

    public void addHighlightIndexes(int... indexes) {
        for(int i : indexes)
            this.highlightIndexes.add(i);
    }


    @Override
    public String toString() {
        return state + "|" + elementAnimationData.toString();
    }

}
