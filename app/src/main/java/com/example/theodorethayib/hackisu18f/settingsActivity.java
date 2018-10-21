package com.example.theodorethayib.hackisu18f;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

public class settingsActivity extends AppCompatActivity {

    Globals g = (Globals)getApplication();

    public boolean hModeSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
    }

    protected void catFacts(View view){
        TextView catText = findViewById(R.id.catText);

//        boolean checked = (findViewById(R.id.checkBox) view).isChecked();
        boolean checked = ((CheckBox) view).isChecked();
        if(checked == true){
            catText.setText("To unsubscribe, enter 'Tyxt33358dggyf' into the text box below");
        }
        else{
            catText.setText("While not well known, the collective nouns used for cats and kittens are a clowder of cats and a kindle of kittens.");
            checked = true;
        }


    }
    protected void hardMode(View view){
        Globals g = (Globals)getApplication();
        Switch hardSwitch = (Switch) findViewById(R.id.hardSwitch);
        if(hardSwitch.isChecked()){
            g.setHardSwitch(true);
            hModeSwitch = true;
        }
        else{
            g.setHardSwitch(false);
            hModeSwitch = false;
        }


    }

}
