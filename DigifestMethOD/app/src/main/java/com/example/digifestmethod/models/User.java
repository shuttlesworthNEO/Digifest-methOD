package com.example.digifestmethod.models;

import java.util.ArrayList;

/**
 * Created by piyush0 on 18/08/17.
 */

public class User {
    String username;
    String password;
    ArrayList<Query> queriesResolved;
    String score;

    public String getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Query> getQueriesResolved() {
        return queriesResolved;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
