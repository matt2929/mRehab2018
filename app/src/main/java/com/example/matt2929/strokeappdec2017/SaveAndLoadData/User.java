package com.example.matt2929.strokeappdec2017.SaveAndLoadData;

/**
 * Created by matt2929 on 12/18/17.
 */

public class User {
    String Name = "000";
    int Age = -1;
    int hand = -1;
    String Goals = "000";
    public User(){

    }
    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getGoals() {
        return Goals;
    }

    public void setGoals(String goals) {
        Goals = goals;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
