package com.example.targil_bait_1.DataBase;

public class User {
    private String name;
    private int score;
    private double lat;
    private double lon;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getLat() {
        return lat;
    }

    public User setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public User setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public User(String name, int score, double lat, double lon) {
        this.name = name;
        this.score = score;
        setLat(lat);
        setLon(lon);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("Player: ").append(name);
        sb.append("\nScore:").append(score);
        return sb.toString();
    }
}
