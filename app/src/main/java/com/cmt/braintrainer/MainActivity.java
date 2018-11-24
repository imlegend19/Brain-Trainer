package com.cmt.braintrainer;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView textView;
    ArrayList<Integer> answers = new ArrayList<>(6);
    int locationOfCorrectAnswer;
    TextView review;
    int score = 0;
    int numberOfQuestions = 0;
    Button playAgainButton;
    TextView scoreTextView;
    Button button0, button1, button2, button3, button4, button5;
    TextView sumTextView, timerTextView;
    android.support.v7.widget.GridLayout grid;
    RelativeLayout gameRelativeLayout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.score);

        review = findViewById(R.id.reviewText);
        review.setText("Let's Play!");

        playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.GONE);

        timerTextView = findViewById(R.id.counter);

        gameRelativeLayout = findViewById(R.id.gameRelativeLayout);

        button0 = findViewById(R.id.button1);
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);
        button4 = findViewById(R.id.button5);
        button5 = findViewById(R.id.button6);

        startButton = findViewById(R.id.startButton);
        textView = findViewById(R.id.textView);

        sumTextView = findViewById(R.id.question);

        Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_LONG).show();

        grid = findViewById(R.id.gridLayout);
    }

    public void start(View view) {
        textView.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);

        playAgain(findViewById(R.id.playAgainButton));
    }

    @SuppressLint("SetTextI18n")
    public void generateQuestion() {
        Random rand = new Random();

        int a = rand.nextInt(21) + 5;
        int b = rand.nextInt(21) + 5;
        int c = a + b;

        answers.clear();

        sumTextView.setText(String.format("%s + %s", Integer.toString(a), Integer.toString(b)));

        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;

        for (int i=0; i<6; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(c);
            }
            else {
                incorrectAnswer = rand.nextInt(41);
                while (incorrectAnswer == a+b) {
                    incorrectAnswer = rand.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
        button4.setText(Integer.toString(answers.get(4)));
        button5.setText(Integer.toString(answers.get(5)));
    }

    @SuppressLint("SetTextI18n")
    public void chooseAnswer(View view) {
        Log.i("Tag", (String) view.getTag());

        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            review.setText("Correct");
            score++;
        }

        else {
            review.setText("Wrong");
        }

        numberOfQuestions++;
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    @SuppressLint("SetTextI18n")
    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        generateQuestion();

        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        review.setText("Let's Play!");

        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                grid.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.INVISIBLE);
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                grid.setVisibility(View.INVISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                review.setText("Your Score: " + Integer.toString(score) + "/" +
                        Integer.toString(numberOfQuestions));
            }
        }.start();

    }
}
