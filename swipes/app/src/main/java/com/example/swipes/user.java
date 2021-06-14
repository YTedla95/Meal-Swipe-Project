package com.example.swipes;

public class user {
    public String name, email,swipes_have,swipes_giving;

    public user(){

    }

    public user(String name, String email, String swipes_have, String swipes_giving) {
        this.name = name;
        this.email = email;

        this.swipes_have = swipes_have;
        this.swipes_giving=swipes_giving;
    }
}