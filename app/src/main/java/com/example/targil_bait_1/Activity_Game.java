package com.example.targil_bait_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.os.Handler;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class Activity_Game extends AppCompatActivity {

    public static final int NUM_ROWS = 7;
    public static final int NUM_COLS = 3;
    private int seconds = 0;
    private final int DELAY_DOWN = 1000;
    private AppCompatImageView space_IMG_background;
    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[][] game_IMG_matrix;
    private ShapeableImageView[] game_IMG_space;
    private int[][] imgIdsAst;

    private ExtendedFloatingActionButton game_FAB_left;
    private ExtendedFloatingActionButton game_FAB_right;

    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViews();
        initFabs();
        gameManager = new GameManager(game_IMG_hearts.length);

        Glide
                .with(this)
                .load(R.drawable.starfield6)
                .into(space_IMG_background);

        startGame(); //THIS METHOD WILL MAKE EVERYTHING INVISIBLE AND POSITION SPACESHIP AT THE START, AND WILL START TIMING THE METEORS AND SHIT


    }

    private void startGame() {
        turnAllInvisible();
        int col = gameManager.pickARandomRockForStart();
        game_IMG_matrix[0][col].setVisibility(View.VISIBLE);


        // POSITION SPACESHIP AT THE START
        game_IMG_space[0].setVisibility(View.INVISIBLE);
        game_IMG_space[1].setVisibility(View.VISIBLE);
        game_IMG_space[2].setVisibility(View.INVISIBLE);

        final Handler handlerAsteroidsDown = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                handlerAsteroidsDown.postDelayed(this, DELAY_DOWN);
                //  FOR EVERY SECOND THAT PASSES, CHECK IF I GOT HIT, AND MOVE ALL VISIBLE ROCKS DOWNWARDS
                refreshUI();
                final boolean twoSecond = seconds % 2 != 0;
                gameManager.moveVisibleAsteroids(game_IMG_matrix, game_IMG_space);
                if (twoSecond) // EVERY TWO SECONDS, BRING A NEW ROCK
                {
                    int col = gameManager.pickARandomRockForStart();
                    game_IMG_matrix[0][col].setVisibility(View.VISIBLE);
                }
                seconds ++;
            }
        };


        handlerAsteroidsDown.postDelayed(runnable, DELAY_DOWN);


    }

    private void turnAllInvisible() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                game_IMG_matrix[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }


    private void findViews() {
        //football_IMG_background = findViewById(R.id.football_IMG_background);
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart4),
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };

        game_IMG_space = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_spaceship60),
                findViewById(R.id.game_IMG_spaceship61),
                findViewById(R.id.game_IMG_spaceship62)
        };

        space_IMG_background = findViewById(R.id.space_IMG_background);

        imgIdsAst = new int[NUM_ROWS][NUM_COLS];
        imgIdsAst[0][0] = R.id.game_IMG_ast00;
        imgIdsAst[0][1] = R.id.game_IMG_ast01;
        imgIdsAst[0][2] = R.id.game_IMG_ast02;
        imgIdsAst[1][0] = R.id.game_IMG_ast10;
        imgIdsAst[1][1] = R.id.game_IMG_ast11;
        imgIdsAst[1][2] = R.id.game_IMG_ast12;
        imgIdsAst[2][0] = R.id.game_IMG_ast20;
        imgIdsAst[2][1] = R.id.game_IMG_ast21;
        imgIdsAst[2][2] = R.id.game_IMG_ast22;
        imgIdsAst[3][0] = R.id.game_IMG_ast30;
        imgIdsAst[3][1] = R.id.game_IMG_ast31;
        imgIdsAst[3][2] = R.id.game_IMG_ast32;
        imgIdsAst[4][0] = R.id.game_IMG_ast40;
        imgIdsAst[4][1] = R.id.game_IMG_ast41;
        imgIdsAst[4][2] = R.id.game_IMG_ast42;
        imgIdsAst[5][0] = R.id.game_IMG_ast50;
        imgIdsAst[5][1] = R.id.game_IMG_ast51;
        imgIdsAst[5][2] = R.id.game_IMG_ast52;
        imgIdsAst[6][0] = R.id.game_IMG_ast60;
        imgIdsAst[6][1] = R.id.game_IMG_ast61;
        imgIdsAst[6][2] = R.id.game_IMG_ast62;


        game_IMG_matrix = new ShapeableImageView[NUM_ROWS][NUM_COLS];
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                game_IMG_matrix[i][j] = findViewById(imgIdsAst[i][j]);

            }
        }
        for (int j = 0; j < NUM_COLS; j++) {
            game_IMG_matrix[NUM_ROWS - 1][j] = findViewById(imgIdsAst[NUM_ROWS - 1][j]);

        }
        game_FAB_left = findViewById(R.id.game_FAB_left);
        game_FAB_right = findViewById(R.id.game_FAB_right);
    }


    private void refreshUI() {
        if (gameManager.gotHit(game_IMG_space, game_IMG_matrix))
        {
            vibrate();
        }
        if (gameManager.isEndGame()) {
            //OPEN A TOAST MESSAGE AND RESTART THE LIVES
            Toast
                    .makeText(this, "Game Over, restarting lives", Toast.LENGTH_LONG)
                    .show();
            gameManager.setHits();
            for (int i = 0; i < gameManager.getLife(); i++) {
                game_IMG_hearts[i].setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 0; i < gameManager.getHits(); i++) {
                game_IMG_hearts[i].setVisibility(View.INVISIBLE);
            }
        }
    }


    private void initFabs() {
        game_FAB_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.moveShip(-1, game_IMG_space);
            }
        });

        game_FAB_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.moveShip(1, game_IMG_space);
            }
        });
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
}