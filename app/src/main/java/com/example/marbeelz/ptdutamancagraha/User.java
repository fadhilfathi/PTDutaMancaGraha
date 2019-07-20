package com.example.marbeelz.ptdutamancagraha;

public class User {
    public String username;
    public String password;

    public User(){

    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}

