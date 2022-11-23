package com.example.targil_bait_1;

import android.graphics.drawable.shapes.Shape;
import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;

public class GameManager {

    private SpaceShip ship;


    private int life;
    private int hits = 0;


    public GameManager(int life) {
        this.life = life;
        ship = new SpaceShip();
    }

    public int getHits() {
        return hits;
    }

    public boolean gotHit(ShapeableImageView[] game_img_space, ShapeableImageView[][] game_img_matrix) { // THIS FUNCTION CHECKS IF AN ASTEROID AND A SPACESHIP ARE VISIBLE ON THE SAME SQUARE
        int position = ship.getCurrentPos();
        boolean isHitted = (game_img_space[position].getVisibility() == View.VISIBLE && game_img_matrix[Activity_Game.NUM_ROWS-1][position].getVisibility() == View.VISIBLE);
        if (isHitted)
            hits++;
        return isHitted;
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
        if (result >=0 && result < Activity_Game.NUM_COLS)
        {
            ship.setCurrentPos(result);
            switch(result)
            {
                case 0:
                {
                    game_IMG_space[0].setVisibility(View.VISIBLE);
                    game_IMG_space[1].setVisibility(View.INVISIBLE);
                    game_IMG_space[2].setVisibility(View.INVISIBLE);
                }
                break;

                case 1:
                {
                    game_IMG_space[0].setVisibility(View.INVISIBLE);
                    game_IMG_space[1].setVisibility(View.VISIBLE);
                    game_IMG_space[2].setVisibility(View.INVISIBLE);
                }
                break;

                case 2:
                {
                    game_IMG_space[0].setVisibility(View.INVISIBLE);
                    game_IMG_space[1].setVisibility(View.INVISIBLE);
                    game_IMG_space[2].setVisibility(View.VISIBLE);
                }
                break;

                default:
                    break;
            }
        }
        else
            return;
    }

    public int pickARandomRockForStart() {
        return (int)(Math.random() * (Activity_Game.NUM_COLS));
    }

    public void moveVisibleAsteroids(ShapeableImageView[][] game_img_matrix, ShapeableImageView[] game_img_space) {
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
                    game_img_matrix[i][j].setVisibility(View.INVISIBLE);
                    game_img_matrix[i + 1][j].setVisibility(View.VISIBLE);
                }
            }
        }
    }




}
