package com.example.sortingvisualizer.sorting.BubbleSort;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sortingvisualizer.R;
import com.example.sortingvisualizer.activity.BaseActivity;
import com.example.sortingvisualizer.sorting.InsertionSort.InsertionSortActivity;
import com.example.sortingvisualizer.sorting.SelectionSort.SelectionSortActivity;

import java.util.Timer;
import java.util.TimerTask;

public class BubbleSortActivity extends BaseActivity {

    LinearLayout ll_anim;
    CardView cardView_psuedocode;

    ConstraintLayout constL_BubbleSort;
    ConstraintLayout constl_selectionSort;
    ConstraintLayout constl_insertionSort;
    ConstraintLayout constL_navigation_home;

    ImageButton button_play;
    ImageButton button_navigation;
    ImageButton button_menu;
    ImageButton button_code;
    ImageButton button_info;
    ImageButton button_backward;
    ImageButton button_forward;
    ImageButton button_close_menu;
    ImageButton button_help_menu;
    ImageButton button_close_navigation;



    Button button_generate;
    Button button_clear;

    SeekBar seekBarAnimationSpeed;
    SeekBar seekBarArraySize;

    Switch switchRandomArray;

    ScrollView psuedocode_scrollView;

    TextView textView_sequence_no;
    TextView textView_info;
    TextView textViewArraySize;
    TextView textArraysize_no;
    TextView[] textViews;

    BubbleSort bubbleSort;

    LinearLayout pseudocode_linearLayout;

    EditText editTextCustomArray;

    Timer timer = new Timer();
    boolean isAutoPlay = false;
    boolean isRandomArray = true;

    int autoAnimationSpeed = 1500;
    final int Layout_main = R.layout.activity_sorting;
    final int Layout_left = R.layout.navigation_sorting;
    final int Layout_right = R.layout.controls_of_sorting;

    final String OnBoardingKey = "Sorting";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configure(Layout_main,Layout_left,Layout_right);
        super.onCreate(savedInstanceState);

        view_main = viewStub_main.inflate();
        view_menu_left = viewStub_menu_left.inflate();
        view_menu_right = viewStub_menu_right.inflate();

        seekBarAnimationSpeed= view_main.findViewById(R.id.seekbar_animation_speed);
        button_play = view_main.findViewById(R.id.button_play);
        button_menu = view_main.findViewById(R.id.button_menu);
        button_code = view_main.findViewById(R.id.button_code);
        button_info = view_main.findViewById(R.id.button_info);
        button_navigation = view_main.findViewById(R.id.button_navigation);
        button_backward = view_main.findViewById(R.id.button_backward);
        button_forward = view_main.findViewById(R.id.button_forward);
        textView_sequence_no = view_main.findViewById(R.id.textview_sequence_no);
        textView_info = view_main.findViewById(R.id.textview_info);
        cardView_psuedocode = view_main.findViewById(R.id.psuedocode_cardView);
        psuedocode_scrollView = view_main.findViewById(R.id.psuedocode_scrollView_cardView);
        pseudocode_linearLayout = view_main.findViewById(R.id.pseudocode_linearL_cardView);

        ll_anim = view_main.findViewById(R.id.ll_anim);

        button_close_menu = view_menu_right.findViewById(R.id.button_closemenu);
        button_help_menu = view_menu_right.findViewById(R.id.button_helpmenu);
        seekBarArraySize = view_menu_right.findViewById(R.id.seekbar_arraysize);
        textViewArraySize = view_menu_right.findViewById(R.id.txtArraySize_text);
        textArraysize_no = view_menu_right.findViewById(R.id.text_arraysize_no);
        button_generate = view_menu_right.findViewById(R.id.button_generate);
        button_clear = view_menu_right.findViewById(R.id.button_clear);
        switchRandomArray = view_menu_right.findViewById(R.id.switch_random_array);
        editTextCustomArray = view_menu_right.findViewById(R.id.edit_text_customarray);

        button_close_navigation = view_menu_left.findViewById(R.id.close_navigation_btn);
        constL_navigation_home = view_menu_left.findViewById(R.id.navigation_home);
        constL_BubbleSort = view_menu_left.findViewById(R.id.navigation_bubblesort);
        constl_selectionSort = view_menu_left.findViewById(R.id.navigation_selectionsort);
        constl_insertionSort = view_menu_left.findViewById(R.id.navigation_insertionsort);
        textView_sequence_no.setText(String.valueOf(seekBarArraySize.getProgress()+1));

        initPseudoCode();
        initViews();
        initNavigation();
        initToolTipTexts();

        switchRandomArray.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    isRandomArray=true;
                    editTextCustomArray.setError(null);
                    switchRandomArray.setText(switchRandomArray.getTextOn());
                    textArraysize_no.setText(seekBarArraySize.getProgress() + 1);
                }
                else{
                    isRandomArray = false;
                    switchRandomArray.setText(switchRandomArray.getTextOn());
                    String customArray = editTextCustomArray.getText().toString();
                    if(customArray != null){
                        String []customInput = customArray.split(",");
                        int length = customInput.length;
                        if(length > 16){
                            editTextCustomArray.setError("Decrease Elements");
                            textArraysize_no.setText("0");
                        }
                        else{
                            int []data = new int[length];
                            try{
                                for(int i=0; i < data.length; i++){
                                    data[i] = Integer.parseInt(customInput[i]);
                                }
                                textArraysize_no.setText(String.valueOf(customInput.length));
                            }
                            catch (NumberFormatException e){
                                editTextCustomArray.setError("Invalid Input");
                                textArraysize_no.setText("0");
                            }
                        }

                    }
                }
            }
        });

        editTextCustomArray.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isRandomArray){
                    if(!editable.toString().isEmpty()){
                        String []customInput = editable.toString().split(",");
                        int length = customInput.length;
                        if(length > 16){
                            editTextCustomArray.setError("Decrease Elements");
                            textArraysize_no.setText("0");
                        }
                        else{
                            int []data = new int[length];
                            try{
                                for(int i=0; i < data.length; i++){
                                    data[i] = Integer.parseInt(customInput[i]);
                                }
                                textArraysize_no.setText(String.valueOf(customInput.length));
                            }
                            catch (NumberFormatException e){
                                editTextCustomArray.setError("Invalid Input");
                                textArraysize_no.setText("0");
                            }
                        }
                    }
                }

            }
        });

        seekBarAnimationSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                autoAnimationSpeed = (2000 - seekBar.getProgress() * 20) + 500;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                  if(bubbleSort != null && isAutoPlay){
                      timer.cancel();
                      timer = new Timer();
                      timer.schedule(new TimerTask() {
                          @Override
                          public void run() {
                              if(bubbleSort.bubbleSortSortingSequence.currentSequenceNo < bubbleSort.bubbleSortSortingSequence.size){
                                  onForwardClick();
                              }
                              else{
                                  isAutoPlay = false;
                                  button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_play));
                                  timer.cancel();
                              }
                          }
                      },0,autoAnimationSpeed);
                  }
            }
        });

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bubbleSort != null){
                    if(isAutoPlay){
                        isAutoPlay = false;
                        button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_play));
                        timer.cancel();
                    }
                    else{
                        isAutoPlay = true;
                        button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_pause));
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if(bubbleSort.bubbleSortSortingSequence.currentSequenceNo < bubbleSort.bubbleSortSortingSequence.size){
                                    onForwardClick();
                                }
                                else{
                                    isAutoPlay = false;
                                    button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_play));
                                    timer.cancel();
                                }
                            }
                        } , 0, autoAnimationSpeed);
                    }
                }
            }
        });

        button_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAutoPlay = false;
                button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_play));
                timer.cancel();
                onForwardClick();
            }
        });

        button_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAutoPlay = false;
                button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_play));
                timer.cancel();
                onBackwardClick();
            }
        });

        button_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAutoPlay = false;
                button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_play));
                timer.cancel();

                openDrawer(1);
            }
        });

        button_close_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(1);
            }
        });

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAutoPlay = false;
                button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_play));
                timer.cancel();

                openDrawer(2);
            }
        });

        button_close_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(2);
            }
        });

        drawerLayout_main.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if(slideOffset >= 0.35){
                    isAutoPlay = false;
                    button_play.setImageDrawable(ContextCompat.getDrawable(context , R.drawable.ic_play));
                    timer.cancel();
                }
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        seekBarArraySize.setOnTouchListener(new SeekBar.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekBarArraySize.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        seekBarArraySize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(isRandomArray)
                    textArraysize_no.setText(String.valueOf(i+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int arraySize = seekBarArraySize.getProgress() + 1;
                if(bubbleSort != null){
                    bubbleSort.linearLayout.removeAllViews();
                    bubbleSort = null;
                }

                if(isRandomArray){
                    bubbleSort = new BubbleSort(context , ll_anim , arraySize);
                }
                else{
                    String customArray = editTextCustomArray.getText().toString();
                    if(customArray != null || !customArray.isEmpty()){
                        String []customInput = customArray.split(",");
                        int length = customInput.length;
                        if(length > 16){
                            editTextCustomArray.setError("Decrease Elements");
                            textViewArraySize.setText("0");
                        }
                        else{
                            int[] data = new int[customInput.length];
                            try {
                                for (int i = 0; i < data.length; i++) {
                                    data[i] = Integer.parseInt(customInput[i]);
                                }
                                bubbleSort = new BubbleSort(context, ll_anim, data);
                            }
                            catch (NumberFormatException e){
                                editTextCustomArray.setError("Invalid Input");
                                bubbleSort = null;
                            }
                        }
                    }
                }

                closeDrawer(0);
                initViews();
            }
        });

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bubbleSort != null){
                    bubbleSort.linearLayout.removeAllViews();
                    bubbleSort = null;
                }
                initViews();
            }
        });

        // Generate the random for examples
        ll_anim.post(new Runnable() {
            @Override
            public void run() {
                button_generate.performClick();
            }
        });

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = getLayoutInflater().inflate(R.layout.layout_sorting_info,null);
                TextView text_name = view1.findViewById(R.id.tv_name);
                TextView txt_avg = view1.findViewById(R.id.tv_avg);
                TextView txt_worst = view1.findViewById(R.id.tv_worst);
                TextView txt_best = view1.findViewById(R.id.tv_best);
                TextView txt_space = view1.findViewById(R.id.tv_space);
                TextView txt_stable = view1.findViewById(R.id.tv_stable);
                TextView txt_compare = view1.findViewById(R.id.tv_comparisons);
                ImageButton btn_close = view1.findViewById(R.id.btn_close);

                String compare = "-";
                if(bubbleSort != null){
                    isAutoPlay = false;
                    button_play.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_play));
                    timer.cancel();
                    compare = String.valueOf(bubbleSort.comparisons);
                }

                text_name.setText("Bubble Sort");
                txt_avg.setText("O(n*n)");
                txt_worst.setText("O(n*n)");
                txt_best.setText("O(n)");
                txt_space.setText("O(1)");
                txt_stable.setText("YES");
                txt_compare.setText(compare);

                final Dialog  dialog = new Dialog(context);
                dialog.setContentView(view1);
                dialog.show();

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


    }

    private void onForwardClick(){
        if(bubbleSort != null){
            bubbleSort.forward();
            final int currentSequenceNo = bubbleSort.bubbleSortSortingSequence.currentSequenceNo;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView_sequence_no.setText(currentSequenceNo + "/" + bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.size());
                    if(currentSequenceNo < bubbleSort.bubbleSortSortingSequence.size){
                        textView_info.setText(bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(currentSequenceNo).info);

                        if(currentSequenceNo == -1){
                            for(View view : bubbleSort.views){
                                view.findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_done));
                            }
                            return;
                        }

                        for(View view : bubbleSort.views){
                            view.findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_normal));
                        }

                        if(bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(currentSequenceNo).highlightIndexes != null){
                            for(int i : bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(currentSequenceNo).highlightIndexes){
                                bubbleSort.views[i].findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_highlighted));
                            }
                        }

                        for(Pair<Integer , Integer> pair : bubbleSort.sortedIndexes){
                            if(currentSequenceNo >= pair.first){
                                bubbleSort.views[pair.second].findViewById(R.id.tv_elementvalue).
                                        setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_done));
                            }
                        }

                    }
                    else{
                        textView_info.setText("Array is Sorted");
                        if(-1 == -1){
                            for(View view : bubbleSort.views){
                                view.findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_done));
                            }
                            return;
                        }

                        for(View view : bubbleSort.views){
                            view.findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_normal));
                        }

                        if(null != null){
                            for(int i : bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(currentSequenceNo).highlightIndexes){
                                bubbleSort.views[i].findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_highlighted));
                            }
                        }

                        for(Pair<Integer , Integer> pair : bubbleSort.sortedIndexes){
                            if(currentSequenceNo >= pair.first){
                                bubbleSort.views[pair.second].findViewById(R.id.tv_elementvalue).
                                        setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_done));
                            }
                        }

                    }

                }
            });
        }
    }

    private void onBackwardClick(){
        if(bubbleSort!=null){
            bubbleSort.backward();
            final int currentSequenceNo = bubbleSort.bubbleSortSortingSequence.currentSequenceNo;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView_sequence_no.setText(currentSequenceNo+" / "+bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.size());
                    textView_info.setText(bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(currentSequenceNo).info);

                    if(currentSequenceNo == -1){
                        for(View view : bubbleSort.views){
                            view.findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_done));
                        }
                        return;
                    }

                    for(View view : bubbleSort.views){
                        view.findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_normal));
                    }

                    if(bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(currentSequenceNo).highlightIndexes != null){
                        for(int i : bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(currentSequenceNo).highlightIndexes){
                            bubbleSort.views[i].findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_highlighted));
                        }
                    }

                    for(Pair<Integer , Integer> pair : bubbleSort.sortedIndexes){
                        if(currentSequenceNo >= pair.first){
                            bubbleSort.views[pair.second].findViewById(R.id.tv_elementvalue).
                                    setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_done));
                        }
                    }


                }
            });
        }
    }

    @Override
    protected void initPseudoCode() {
        int size = BubbleSortInfo.PsuedoCode.length;
        textViews = new TextView[size];
        for(int i=0; i<size; i++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT
            );
            TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setText(BubbleSortInfo.PsuedoCode[i]);
            textView.setPadding(10,1,5,0);
            textViews[i] = textView;
            pseudocode_linearLayout.addView(textView);
        }
        for(int i: BubbleSortInfo.boldIndexes){
            textViews[i].setTypeface(textViews[i].getTypeface() , Typeface.BOLD);
        }

    }

    @Override
    protected void initViews() {
         if(bubbleSort != null){
             textView_sequence_no.setText("0 / " + bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.size());
             textView_info.setText(bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(0).info);
             for(View view : bubbleSort.bubbleSortSortingSequence.views){
                 view.findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context , R.drawable.rounded_rect_sorting_element_normal));
             }
             if(bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(0).highlightIndexes != null){
                 for(int i : bubbleSort.bubbleSortSortingSequence.sortingAnimationStates.get(0).highlightIndexes){
                     bubbleSort.bubbleSortSortingSequence.views[i].findViewById(R.id.tv_elementvalue).setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_rect_sorting_element_normal));
                 }
             }
         }
         else{
             textView_sequence_no.setText("0 / 0");
             textView_info.setText("-");
         }
    }


    @Override
    protected void initNavigation() {
        constL_navigation_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
            }
        });

        constL_BubbleSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer(1);
            }
        });

        constl_selectionSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
                Intent intent = new Intent(BubbleSortActivity.this , SelectionSortActivity.class);
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(BubbleSortActivity.this).toBundle();
                BubbleSortActivity.this.startActivity(intent, bundle);
            }
        });

        constl_insertionSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAfterTransition();
                Intent intent = new Intent(BubbleSortActivity.this, InsertionSortActivity.class);
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(BubbleSortActivity.this).toBundle();
                BubbleSortActivity.this.startActivity(intent,bundle);
            }
        });
    }

    @Override
    protected void initToolTipTexts() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}