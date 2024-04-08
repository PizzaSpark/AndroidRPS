package com.example.cc10624rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView lblScoreYou, lblScoreCom;
    private ImageView imgYou, imgCom;
    private RadioButton rbRock, rbPaper, rbScissors;
    private Button btnReset, btnBet, btnClose;

    private int[] scores = {0, 0};
    int you = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblScoreYou = findViewById(R.id.lblScoreYou);
        lblScoreCom = findViewById(R.id.lblScoreCom);
        imgYou = findViewById(R.id.imgYou);
        imgCom = findViewById(R.id.imgCom);
        rbRock = findViewById(R.id.rbRock);
        rbPaper = findViewById(R.id.rbPaper);
        rbScissors = findViewById(R.id.rbScissors);
        btnReset = findViewById(R.id.btnReset);
        btnBet = findViewById(R.id.btnBet);
        btnClose = findViewById(R.id.btnClose);

        imgYou.setImageResource(R.drawable.rock);
        imgCom.setImageResource(R.drawable.rock);

        // Set the initial score
        lblScoreYou.setText("You: " + scores[0]);
        lblScoreCom.setText("Com: " + scores[1]);

        // Set the event listener for the reset button
        btnReset.setOnClickListener(v -> {
            scores[0] = 0;
            scores[1] = 0;
            lblScoreYou.setText("You: " + scores[0]);
            lblScoreCom.setText("Com: " + scores[1]);
        });

        btnBet.setOnClickListener(v -> {
            if (rbRock.isChecked()) {
                you = 0;
                imgYou.setImageResource(R.drawable.rock);
            } else if (rbPaper.isChecked()) {
                you = 1;
                imgYou.setImageResource(R.drawable.paper);
            } else if (rbScissors.isChecked()) {
                you = 2;
                imgYou.setImageResource(R.drawable.scissors);
            }

            final Handler handler = new Handler();

            final int[] count = {0};
            final int[] images = {R.drawable.rock, R.drawable.paper, R.drawable.scissors};

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (count[0] < 25) {
                        int imageId = images[new Random().nextInt(images.length)];
                        imgCom.setImageResource(imageId);
                        count[0]++;
                        handler.postDelayed(this, 100);
                    } else {
                        // After cycling 10 times, make the final choice for the computer
                        int com = (int) (Math.random() * 3);
                        if (com == 0) {
                            imgCom.setImageResource(R.drawable.rock);
                        } else if (com == 1) {
                            imgCom.setImageResource(R.drawable.paper);
                        } else if (com == 2) {
                            imgCom.setImageResource(R.drawable.scissors);
                        }

                        // Continue with the rest of the game logic
                        if (you == com) {
                            // Draw
                            showToast("Draw");
                        } else if (you == 0 && com == 1) {
                            scores[1]++;
                            showToast("You lose");
                        } else if (you == 0 && com == 2) {
                            scores[0]++;
                            showToast("You win");
                        } else if (you == 1 && com == 0) {
                            scores[0]++;
                            showToast("You win");
                        } else if (you == 1 && com == 2) {
                            scores[1]++;
                            showToast("You lose");
                        } else if (you == 2 && com == 0) {
                            scores[1]++;
                            showToast("You lose");
                        } else if (you == 2 && com == 1) {
                            scores[0]++;
                            showToast("You win");
                        }
                        lblScoreYou.setText("Score: " + scores[0]);
                        lblScoreCom.setText("Score: " + scores[1]);
                    }
                }
            };
            handler.postDelayed(runnable, 100);
        });

        // Set the event listener for the close button
        btnClose.setOnClickListener(v -> {
            finish();
        });
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}