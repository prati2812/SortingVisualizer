package com.example.sortingvisualizer.sorting.BubbleSort;

import android.util.Pair;

import com.example.sortingvisualizer.animation.AnimationDirection;
import com.example.sortingvisualizer.animation.ElementAnimationData;
import com.example.sortingvisualizer.animation.SortingAnimationState;
import com.example.sortingvisualizer.animation.SortingSequence;

public class BubbleSortSortingSequence extends SortingSequence {
    @Override
    public boolean backward() {
        if(size <= 0)
           return false;
        if(currentSequenceNo == 0)
           return false;

        SortingAnimationState oldSortingAnimation = sortingAnimationStates.get(currentSequenceNo - 1);

        for(ElementAnimationData elementAnimationData : oldSortingAnimation.elementAnimationData){
            ElementAnimationData inverse = ElementAnimationData.reverse(elementAnimationData);
            for(Pair<AnimationDirection, Integer> in : inverse.instructions){
                int index = inverse.index;
                if(in.first == AnimationDirection.LEFT){
                    positions[index] -= in.second;
                }
                else if(in.first == AnimationDirection.RIGHT){
                    positions[index] += in.second;
                }
                animateViews.animateDirection(views[index] , in.second , in.first);
            }
        }
        currentSequenceNo--;
        return true;
    }

    @Override
    public boolean forward() {
        if(size <= 0)
           return false;

        if(currentSequenceNo == size)
            return false;

        SortingAnimationState newSortingAnimationState = sortingAnimationStates.get(currentSequenceNo);

        for(ElementAnimationData elementAnimationDatas : newSortingAnimationState.elementAnimationData){
            for(Pair<AnimationDirection , Integer> in : elementAnimationDatas.instructions){
                int index = elementAnimationDatas.index;
                if(in.first == AnimationDirection.LEFT){
                    positions[index] -= in.second;
                }
                else if(in.first == AnimationDirection.RIGHT){
                    positions[index] += in.second;
                }
                animateViews.animateDirection(views[index],in.second,in.first);
            }
        }
        currentSequenceNo++;
        return true;
    }
}
