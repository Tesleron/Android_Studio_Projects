package com.example.targil_bait_1.GameObjects;

public class SpaceShip {
    private int currentPos;

    public SpaceShip() {
        currentPos = 2;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }
}
