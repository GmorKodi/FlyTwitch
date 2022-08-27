package com.example.enums;

public enum Timeout {
    CONNECT(60000),
    READ(60000);

    public int time;    //Timeout time in milliseconds.
    Timeout(int m){
        time=m;
    }
}
