package com.example.sortingvisualizer.animation;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class ElementAnimationData {
    public final int index;
    public final ArrayList<Pair<AnimationDirection, Integer>> instructions;

    public ElementAnimationData(int index, ArrayList<Pair<AnimationDirection, Integer>> instructions) {
        this.index = index;
        this.instructions = instructions;
    }

    @SafeVarargs
    public ElementAnimationData(int index, Pair<AnimationDirection, Integer> ... inst){
        this.index = index;
        this.instructions = new ArrayList<>();

        instructions.addAll(Arrays.asList(inst));
    }

    @SafeVarargs
    public final void add(Pair<AnimationDirection, Integer>... inst){
        instructions.addAll(Arrays.asList(inst));
    }


    @Override
    public String toString() {
        return index + "->" + instructions.toString();
    }

    public static ElementAnimationData reverse(ElementAnimationData normal){
        ArrayList<Pair<AnimationDirection, Integer>> instructions = new ArrayList<>();

        int index = normal.index;

        for(int i=normal.instructions.size()-1;i>=0;i--){
            Pair<AnimationDirection, Integer> pair = normal.instructions.get(i);
            switch (pair.first){
                case NULL:
                    instructions.add(pair);
                    break;
                case RIGHT:
                    instructions.add(new Pair<>(AnimationDirection.LEFT, pair.second));
                    break;
                case LEFT:
                    instructions.add(new Pair<>(AnimationDirection.RIGHT, pair.second));
                    break;
                default:
                    break;
            }
        }

        return new ElementAnimationData(index, instructions);
    }

}
