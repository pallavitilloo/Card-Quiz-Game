package com.example.card_quiz_game_tilloop1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/************************************************************************************
 *
 *  The ShowResult activity shows the final score of the user after the quiz.
 * @author Pallavi Tilloo
 *
 *************************************************************************************/

public class ShowResult extends AppCompatActivity {

    int curr_score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        getSupportActionBar().hide();

        Button btnScore = (Button)findViewById(R.id.btnStat);
        Bundle bundle = getIntent().getExtras();

        // Get the current score from the bundle
        if(bundle != null){
            curr_score = bundle.getInt("current_score");
        }

        btnScore.setText(String.valueOf(curr_score));
    }

    /*********************************************************************************************
     * The function is called when user clicks on View Previous scores
     * @param view
     ********************************************************************************************/
    public void ViewPrevious(View view){
        Intent i = new Intent(this, ScoreStats.class);
        startActivity(i);
    }

    /*********************************************************************************************
     * The function is called when user clicks on Go To Home button
     * @param view
     ********************************************************************************************/
    public void GoBack(View view){
        Intent i = new Intent(this, GameRules.class);
        startActivity(i);
    }
}