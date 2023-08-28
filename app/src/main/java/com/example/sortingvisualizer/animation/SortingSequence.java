package com.example.sortingvisualizer.animation;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

public abstract class SortingSequence {
    public int size;
    public int currentSequenceNo;
    public View[] views;
    public int[] positions;
    public AnimateViews animateViews;
    public ArrayList<SortingAnimationState> sortingAnimationStates;

    public abstract boolean backward();
    public abstract boolean forward();

    public SortingSequence() {
        this.currentSequenceNo = 0;
        this.size = 0;
        this.sortingAnimationStates = new ArrayList<>();
    }

    public void setAnimateViews(float height, float width, Context context) {
        this.animateViews = new AnimateViews(height, width, context);
    }

    public void addAnimationSequences(SortingAnimationState sortingAnimationState){
        sortingAnimationStates.add(sortingAnimationState);
        size++;
    }

    public void setViews(View[] views) {
        this.views = views;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public String printAnimationStates() {
        StringBuilder stringBuilder = new StringBuilder();
        for(SortingAnimationState sortingAnimationState : sortingAnimationStates){
            stringBuilder.append(sortingAnimationState.toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "size = " + size + ", curSeqNo = " + currentSequenceNo + "\n" + printAnimationStates();
    }

}
