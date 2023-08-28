package com.example.sortingvisualizer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sortingvisualizer.R;
import com.example.sortingvisualizer.others.ActivityItemData;
import com.example.sortingvisualizer.sorting.BubbleSort.BubbleSortActivity;
import com.example.sortingvisualizer.sorting.InsertionSort.InsertionSortActivity;
import com.example.sortingvisualizer.sorting.SelectionSort.SelectionSortActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout_sorting;

    Context context;

    ActivityItemData []activityItemData = new ActivityItemData[]{
            new ActivityItemData(BubbleSortActivity.class.getName(), "Bubble Sort"),
            new ActivityItemData(SelectionSortActivity.class.getName() , "Selection Sort"),
            new ActivityItemData(InsertionSortActivity.class.getName() , "Insertion Sort")
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        linearLayout_sorting = (LinearLayout) findViewById(R.id.linearLayout_Sorting);

        int width =  (int) dpToPx(context, 250);
        int height = (int) dpToPx(context, 200);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);

        for(final ActivityItemData activityItemData1 : activityItemData){
            View view = getLayoutInflater().inflate(R.layout.layout_item,null);
            TextView textView = view.findViewById(R.id.textView_Sorting_name);
            textView.setText(activityItemData1.sortingName);

            layoutParams.setMargins(15, 15, 15, 5);
            view.setLayoutParams(layoutParams);

            linearLayout_sorting.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Class<?> className = Class.forName(activityItemData1.className);
                        Intent i = new Intent(MainActivity.this , className);
                        startActivity(i);
                    }
                    catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }
            });

        }

    }


    public static float dpToPx(Context context, int dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}