package com.example.targil_bait_1.GameObjects;

import android.content.Context;
import android.view.View;

import com.example.targil_bait_1.Activity_Game;
import com.example.targil_bait_1.DataBase.DataBase;
import com.example.targil_bait_1.DataBase.User;
import com.example.targil_bait_1.utils.MySPV;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.Random;

public class GameManager {

    private SpaceShip ship;

    private int life;
    private int hits = 0;
    private int delay;
    Random rand;
    Context context;

    public GameManager(int life, Context context) {
        this.life = life;
        ship = new SpaceShip();
        rand = new Random();
        this.context = context;
    }

    public int getHits() {
        return hits;
    }

    public int gotHit(ShapeableImageView[] game_img_space, ShapeableImageView[][] game_img_matrix, final int astID, final int coinID) { // THIS FUNCTION CHECKS IF AN ASTEROID AND A SPACESHIP ARE VISIBLE ON THE SAME SQUARE
        int position = ship.getCurrentPos();
        Integer tag = (Integer)game_img_matrix[Activity_Game.NUM_ROWS-1][position].getTag();
        if (tag != null)
        {
            if (tag.intValue() == astID)
            {
                boolean isHitted = (game_img_space[position].getVisibility() == View.VISIBLE && game_img_matrix[Activity_Game.NUM_ROWS-1][position].getVisibility() == View.VISIBLE);
                if (isHitted)
                {
                    hits++;
                    return 1;
                }
            }
            else if (tag.intValue() == coinID)
            {
                boolean isHitted = (game_img_space[position].getVisibility() == View.VISIBLE && game_img_matrix[Activity_Game.NUM_ROWS-1][position].getVisibility() == View.VISIBLE);
                if (isHitted)
                {
                    return 2;
                }
            }
        }
        return 0;
    }

    public boolean isEndGame() {
        return hits == life;
    }

    public int getLife() {
        return life;
    }

    public void setHits() {
        this.hits = 0;
    }

    public void moveShip(int move, ShapeableImageView[] game_IMG_space) { // THIS FUNCTION MOVES THE SPACESHIP RIGHT/LEFT DEPENDS ON THE RESULT AND SETS THE IMG VISIBILITY ACCORDINGLY
        int result = ship.getCurrentPos() + move;
        if (result >= 0 && result < Activity_Game.NUM_COLS)
        {
            game_IMG_space[ship.getCurrentPos()].setVisibility(View.INVISIBLE);
            ship.setCurrentPos(result);
            game_IMG_space[ship.getCurrentPos()].setVisibility(View.VISIBLE);
        }
        else
            return;
    }

    public int pickARandomRockForStart() {
        return (int)(Math.random() * (Activity_Game.NUM_COLS));
    }

    public int randTypeImage() {
        int res = rand.nextInt(5);
        if( res > 3){
            return 1;//return viewType gold
        }
        return 0;//return viewType rock
    }

    public void moveVisibleAsteroids(ShapeableImageView[][] game_img_matrix, int coinID, int astID) {
        //gotHit(game_img_matrix);
        for (int i = Activity_Game.NUM_ROWS-1; i >= 0; i--)
        {
            for (int j = Activity_Game.NUM_COLS-1; j >= 0; j--)
            {
                if ((i == Activity_Game.NUM_ROWS-1) && game_img_matrix[i][j].getVisibility() == View.VISIBLE) // if we are at last line and there is a rock there
                {
                    // check collision
                    game_img_matrix[i][j].setVisibility(View.INVISIBLE);
                }
                if (game_img_matrix[i][j].getVisibility() == View.VISIBLE) // IF AN ASTEROID IS CURRENTLY ACTIVE, MOVE IT DOWN
                {
                    Integer tag = (Integer)game_img_matrix[i][j].getTag();
                    if (tag != null)
                    {
                        if (tag.intValue() == coinID)
                        {
                           game_img_matrix[i + 1][j].setImageResource(coinID);
                           game_img_matrix[i + 1][j].setTag(coinID);
                        }
                        else
                        {
                            game_img_matrix[i + 1][j].setImageResource(astID);
                            game_img_matrix[i + 1][j].setTag(astID);
                        }
                    }
                    game_img_matrix[i][j].setVisibility(View.INVISIBLE);
                    game_img_matrix[i + 1][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }


    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void registerUser(String userName, int seconds, double lat, double lon)
    {
        DataBase myDB;
        String json = MySPV.getInstance().getStrSP("records","");
        myDB = new Gson().fromJson(json, DataBase.class);
        if(myDB == null)
        {
            myDB = new DataBase();
        }
        User rec = new User(userName, seconds, lat, lon); //createRecord(lon,lat);
        myDB.getResults().add(rec);
        MySPV.getInstance().putString("records", new Gson().toJson(myDB));
    }
}
