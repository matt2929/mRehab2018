package com.example.matt2929.strokeappdec2017;

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

    public String getGoals() {
        return Goals;
    }

    public int getHand() {
        return hand;
    }

    public String getName() {
        return Name;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setGoals(String goals) {
        Goals = goals;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public void setName(String name) {
        Name = name;
    }
}
