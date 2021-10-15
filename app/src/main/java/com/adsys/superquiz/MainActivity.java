package com.adsys.superquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Quiz quiz;
    private LinearLayout questionLayout;
    private ImageView questionImageView;
    private TextView questionTextView;
    private LinearLayout resultLayout;
    private ImageView resultImageView;
    private TextView resultTextView;
    private TextView resultAnswerTextView;
    private LinearLayout answerButtonsLayout;
    private TextView questionReference;
    private boolean isInTransition = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quiz = new Quiz(this);
        setContentView(R.layout.activity_main);
        questionImageView = findViewById(R.id.questionImageView);
        questionTextView = findViewById(R.id.questionTextView);
        resultLayout = findViewById(R.id.resultLayout);
        resultImageView = findViewById(R.id.resultImageView);
        resultTextView = findViewById(R.id.resultTextView);
        resultAnswerTextView = findViewById(R.id.resultAnswerTextView);
        answerButtonsLayout = findViewById(R.id.answerButtonsLayout);
        questionLayout = findViewById(R.id.questionLayout);
        questionReference = findViewById(R.id.questionReference);
        LinearLayout contentLayout = findViewById(R.id.contentLayout);

        contentLayout.setOnTouchListener(new OnSwipeListener(this){
            @Override
            public void onSwipeRight() {
                if (quiz.getQuestionNumber() > 0 && !isInTransition){
                    makeTransitionToPrevious();
                }
            }

            @Override
            public void onSwipeLeft() {
                if (quiz.getQuestionNumber() < quiz.length()-1 && !isInTransition){
                    makeTransitionToNext();
                }            }
        });

        ImageButton previousButton = findViewById(R.id.previousButton);
        previousButton.setOnClickListener(view -> {
            if (quiz.getQuestionNumber() > 0 && !isInTransition){
                makeTransitionToPrevious();
            }
        });

        ImageButton nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(view -> {
            if (quiz.getQuestionNumber() < quiz.length()-1 && !isInTransition){
                makeTransitionToNext();
            }
        });

        Button trueButton = findViewById(R.id.trueButton);
        trueButton.setOnClickListener(view -> {
            quiz.answerQuestion(true);
            updateQuestionAnswered();
        });

        Button falseButton = findViewById(R.id.falseButton);
        falseButton.setOnClickListener(view -> {
            quiz.answerQuestion(false);
            updateQuestionAnswered();
        });

        updateQuestion();
    }

    private void updateQuestion(){
        questionImageView.setImageResource(quiz.getCurrentQuestion().image);
        questionTextView.setText(quiz.getCurrentQuestion().question);
        resultLayout.setVisibility(View.GONE);
        answerButtonsLayout.setVisibility(View.VISIBLE);
        String reference = (quiz.getQuestionNumber()+1)+"/"+(quiz.length());
        questionReference.setText(reference);
    }

    @SuppressLint("SetTextI18n")
    private void updateQuestionAnswered(){
        questionImageView.setImageResource(quiz.getCurrentQuestion().image);
        questionTextView.setText(quiz.getCurrentQuestion().question);
        answerButtonsLayout.setVisibility(View.GONE);
        String reference = (quiz.getQuestionNumber()+1)+"/"+(quiz.length());
        questionReference.setText(reference);
        if (quiz.getCurrentQuestion().isCorrect()){
            resultImageView.setImageResource(R.drawable.tick_icon);
            resultTextView.setText("You nailed it!\n\nThe correct answer was");
            resultAnswerTextView.setTextColor(getResources().getColor(R.color.green));
        }else{
            resultImageView.setImageResource(R.drawable.cross_icon);
            resultTextView.setText("You failed it\n\nThe correct answer was");
            resultAnswerTextView.setTextColor(getResources().getColor(R.color.red));
        }
        if (quiz.getCurrentQuestion().correctAnswer){
            resultAnswerTextView.setText("TRUE");
        }else{
            resultAnswerTextView.setText("FALSE");
        }
        resultLayout.setVisibility(View.VISIBLE);
    }

    private void makeTransitionToPrevious(){
        isInTransition = true;
        Animation animationOut = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        animationOut.setDuration(300);  //milliseconds;
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                //Setting previous question
                quiz.setPreviousQuestion();
                if (quiz.getCurrentQuestion().isAnswered){
                    updateQuestionAnswered();
                }else{
                    updateQuestion();
                }
                Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
                animationIn.setDuration(300);  //milliseconds;
                animationIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationRepeat(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isInTransition = false;
                    }
                });
                questionLayout.startAnimation(animationIn);
            }
        });

        questionLayout.startAnimation(animationOut);
    }

    private void makeTransitionToNext(){
        isInTransition = true;
        Animation animationOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        animationOut.setDuration(300);  //milliseconds;
        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                //Setting next question
                quiz.setNextQuestion();
                if (quiz.getCurrentQuestion().isAnswered){
                    updateQuestionAnswered();
                }else{
                    updateQuestion();
                }
                Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left);
                animationIn.setDuration(300);  //milliseconds;
                animationIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationRepeat(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isInTransition = false;
                    }
                });
                questionLayout.startAnimation(animationIn);
            }
        });

        questionLayout.startAnimation(animationOut);
    }


}