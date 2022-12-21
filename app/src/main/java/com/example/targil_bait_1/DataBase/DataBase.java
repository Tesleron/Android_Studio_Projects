package com.example.targil_bait_1.DataBase;

import java.util.ArrayList;

public class DataBase
{
    private ArrayList<User> results;
    private final int LIMIT_TOPTEN = 10;

    public DataBase() {
        this.results = new ArrayList<>();
    }

    public ArrayList<User> getResults()
    {
        results.sort((r1, r2) -> r2.getScore() - r1.getScore());
        if (results.size() == LIMIT_TOPTEN)
        {
            results.remove(LIMIT_TOPTEN - 1);
        }

        return results;
    }

    public DataBase setResults(ArrayList<User> results)
    {
        this.results = results;
        return this;
    }
}
