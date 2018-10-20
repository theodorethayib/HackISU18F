package com.example.theodorethayib.hackisu18f;

import android.app.Application;
import android.widget.TextView;

public class Globals extends Application {
    private String playerName;
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String s) {
        this.playerName = s;
    }

    private String insultPlay3_1;
    public String getInsultPlay3_1() {return insultPlay3_1;}
    public void setInsultPlay3_1(String s) {this.insultPlay3_1 = s;}

    private boolean colorSwitch;
    public boolean getColorSwitch() {return colorSwitch;}
    public void setColorSwitch(boolean b) {this.colorSwitch =b;}
}
