package com.example.theodorethayib.hackisu18f;

import android.app.Application;
import android.widget.TextView;

public class Globals extends Application {
    private String playerName;

    private TextView catText = null;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String s) {
        this.playerName = s;
    }
}
