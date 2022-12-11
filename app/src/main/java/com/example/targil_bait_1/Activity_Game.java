package com.example.targil_bait_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.material.textview.MaterialTextView;

public class Activity_Game extends AppCompatActivity {

    public static final int NUM_ROWS = 7;
    public static final int NUM_COLS = 5;
    private int seconds = 0;
    public final static String KEY_DELAY = "KEY_DELAY";
    public final static String KEY_MODE = "KEY_MODE";
    //private int delay;
    private boolean isSensorMode;
    private MaterialTextView score;
    private AppCompatImageView space_IMG_background;
    private ShapeableImageView[] game_IMG_hearts;
    private ShapeableImageView[][] game_IMG_matrix;
    private ShapeableImageView[] game_IMG_space;
    private int[][] imgIdsAst;
    String[] typeImage= new String[]{"asteroid","coin"};
    int astID;
    int coinID;


    private ExtendedFloatingActionButton game_FAB_left;
    private ExtendedFloatingActionButton game_FAB_right;

    GameManager gameManager;
    StepDetector stepDetector;
    CrashSound crashSound;
    CoinSound coinSound;

    Handler handlerAsteroidsDown;
    Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViews();
        initFabs();

        gameManager = new GameManager(game_IMG_hearts.length, this);
        stepDetector = new StepDetector(this, gameManager, game_IMG_space);
        astID = getResources().getIdentifier(typeImage[0], "drawable", getPackageName());
        coinID = getResources().getIdentifier(typeImage[1], "drawable", getPackageName());

        Intent previousIntent = getIntent();
        gameManager.setDelay(previousIntent.getExtras().getInt(KEY_DELAY));
        isSensorMode = previousIntent.getExtras().getBoolean(KEY_MODE);

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
        positionSpaceAtStart();
        setButtons(isSensorMode);

        handlerAsteroidsDown = new Handler();
        runnable = new Runnable() {
            public void run() {
                handlerAsteroidsDown.postDelayed(this, gameManager.getDelay());
                //  FOR EVERY SECOND THAT PASSES, CHECK IF I GOT HIT, AND MOVE ALL VISIBLE ROCKS DOWNWARDS
                refreshUI();
                final boolean twoSecond = seconds % 2 != 0;
                gameManager.moveVisibleAsteroids(game_IMG_matrix, coinID, astID);
                if (twoSecond) // EVERY TWO SECONDS, BRING A NEW ROCK OR A COIN
                {
                    int col = gameManager.pickARandomRockForStart();
                    int asteroidOrCoin = gameManager.randTypeImage();
                    setImage(0,col,asteroidOrCoin);
                    game_IMG_matrix[0][col].setVisibility(View.VISIBLE);
                }
                seconds ++;
                score.setText("" + seconds);
            }
        };


 //       handlerAsteroidsDown.postDelayed(runnable, gameManager.getDelay()); // THIS FUNCTION CALLS THE run() METHOD



    }

    private void setButtons(boolean isSensorMode)
    {
        game_FAB_left.setEnabled(!isSensorMode);
        game_FAB_right.setEnabled(!isSensorMode);
//        stepDetector.startY();
        if (isSensorMode)
        {
            stepDetector.startX();
            game_FAB_left.hide();
            game_FAB_right.hide();
        }
        else
        {
            stepDetector.stopX();
            game_FAB_left.show();
            game_FAB_right.show();
        }
    }

    private void positionSpaceAtStart()
    {
        game_IMG_space[0].setVisibility(View.INVISIBLE);
        game_IMG_space[1].setVisibility(View.INVISIBLE);
        game_IMG_space[2].setVisibility(View.VISIBLE);
        game_IMG_space[3].setVisibility(View.INVISIBLE);
        game_IMG_space[4].setVisibility(View.INVISIBLE);
    }

    public void setImage(int row, int col, int type){
        int imageID = getResources().getIdentifier(typeImage[type], "drawable", getPackageName());
        //gameManager.setMainTypeMatrix(row,col,type);// 0 == rock 1 == coins
        game_IMG_matrix[row][col].setImageResource(imageID);
        game_IMG_matrix[row][col].setTag(imageID);
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

        score = findViewById(R.id.game_LBL_score);

        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart4),
                findViewById(R.id.game_IMG_heart3),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart1),
        };


        game_IMG_space = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_spaceship60),
                findViewById(R.id.game_IMG_spaceship61),
                findViewById(R.id.game_IMG_spaceship62),
                findViewById(R.id.game_IMG_spaceship63),
                findViewById(R.id.game_IMG_spaceship64)
        };

        space_IMG_background = findViewById(R.id.space_IMG_background);

        imgIdsAst = new int[NUM_ROWS][NUM_COLS];
        imgIdsAst[0][0] = R.id.game_IMG_ast00;
        imgIdsAst[0][1] = R.id.game_IMG_ast01;
        imgIdsAst[0][2] = R.id.game_IMG_ast02;
        imgIdsAst[0][3] = R.id.game_IMG_ast03;
        imgIdsAst[0][4] = R.id.game_IMG_ast04;
        imgIdsAst[1][0] = R.id.game_IMG_ast10;
        imgIdsAst[1][1] = R.id.game_IMG_ast11;
        imgIdsAst[1][2] = R.id.game_IMG_ast12;
        imgIdsAst[1][3] = R.id.game_IMG_ast13;
        imgIdsAst[1][4] = R.id.game_IMG_ast14;
        imgIdsAst[2][0] = R.id.game_IMG_ast20;
        imgIdsAst[2][1] = R.id.game_IMG_ast21;
        imgIdsAst[2][2] = R.id.game_IMG_ast22;
        imgIdsAst[2][3] = R.id.game_IMG_ast23;
        imgIdsAst[2][4] = R.id.game_IMG_ast24;
        imgIdsAst[3][0] = R.id.game_IMG_ast30;
        imgIdsAst[3][1] = R.id.game_IMG_ast31;
        imgIdsAst[3][2] = R.id.game_IMG_ast32;
        imgIdsAst[3][3] = R.id.game_IMG_ast33;
        imgIdsAst[3][4] = R.id.game_IMG_ast34;
        imgIdsAst[4][0] = R.id.game_IMG_ast40;
        imgIdsAst[4][1] = R.id.game_IMG_ast41;
        imgIdsAst[4][2] = R.id.game_IMG_ast42;
        imgIdsAst[4][3] = R.id.game_IMG_ast43;
        imgIdsAst[4][4] = R.id.game_IMG_ast44;
        imgIdsAst[5][0] = R.id.game_IMG_ast50;
        imgIdsAst[5][1] = R.id.game_IMG_ast51;
        imgIdsAst[5][2] = R.id.game_IMG_ast52;
        imgIdsAst[5][3] = R.id.game_IMG_ast53;
        imgIdsAst[5][4] = R.id.game_IMG_ast54;
        imgIdsAst[6][0] = R.id.game_IMG_ast60;
        imgIdsAst[6][1] = R.id.game_IMG_ast61;
        imgIdsAst[6][2] = R.id.game_IMG_ast62;
        imgIdsAst[6][3] = R.id.game_IMG_ast63;
        imgIdsAst[6][4] = R.id.game_IMG_ast64;

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
        int res = gameManager.gotHit(game_IMG_space, game_IMG_matrix, astID, coinID);
        switch (res)
        {
            case 1:
            {
                crashSound = new CrashSound(this);
                crashSound.execute();
                vibrate();
            }
            break;
            case 2:
            {
                coinSound = new CoinSound(this);
                coinSound.execute();
                vibrate();
                seconds += 9;
                score.setText("" + seconds);
            }
            break;
            default: // if recieved 0 or something else
                break;
        }
        if (gameManager.isEndGame()) {
            //OPEN A TOAST MESSAGE AND RETURN TO MAIN MENU
            gameManager.displayToast("Game Over, returning to menu", Toast.LENGTH_SHORT);
            finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        handlerAsteroidsDown.postDelayed(runnable,gameManager.getDelay());
        stepDetector.startY();
        if (isSensorMode)
            stepDetector.startX();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

        handlerAsteroidsDown.removeCallbacks(runnable);
        stepDetector.stopX();
        stepDetector.stopY();
    }


}