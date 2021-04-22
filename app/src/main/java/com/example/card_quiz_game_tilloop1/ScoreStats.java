package com.example.card_quiz_game_tilloop1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/************************************************************************************
 *
 *  The Score Stats activity shows the past scores of the user
 * @author Pallavi Tilloo
 *
 *************************************************************************************/

public class ScoreStats extends AppCompatActivity {

    SharedPreferences pref;
    LinearLayout statLayout;
    final int INVALID_SCORE = 100;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_stats);
        getSupportActionBar().hide();

        statLayout = (LinearLayout) findViewById(R.id.statLayout);

        // Form the table for displaying the scores
        TableLayout TL = new TableLayout(this);

        // Form the table header row
        TableRow tr_head = new TableRow(this);
        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT));

        // Add column for S.No.
        TextView tvSNO_head = new TextView(this);
        tvSNO_head.setTextColor(R.color.black); // set the color
        tvSNO_head.setPadding(5, 5, 5, 5);
        tvSNO_head.setText("S. No.");
        tr_head.addView(tvSNO_head);

        // Add column for User Email field
        TextView tvUserName_head = new TextView(this);
        tvUserName_head.setTextColor(R.color.black); // set the color
        tvUserName_head.setPadding(5, 5, 5, 5);
        tvUserName_head.setText("USER");
        tr_head.addView(tvUserName_head);

        // Add column for User Score field
        TextView tvNewScore_head = new TextView(this);
        tvNewScore_head.setTextColor(R.color.black); // set the color
        tvNewScore_head.setPadding(5, 5, 5, 5);
        tvNewScore_head.setText("SCORE");
        tr_head.addView(tvNewScore_head);

        // Add Header row to table
        TL.addView(tr_head);

        // Get the Shared Preferences data
        pref = getApplicationContext().getSharedPreferences("CARD_QUIZ", 0);
        String curr_user_email = pref.getString("Email", null);
        String score_key = pref.getString("Email", null);
        String final_score_key;

        if(!score_key.isEmpty())
        {
            // To form the key to retrieve all the scores
            final_score_key = score_key = score_key+"Score";
            int i = 1;
            int score;
            do
            {
                final_score_key = score_key + String.valueOf(i);
                score = pref.getInt(final_score_key,100);

                // Until the score is valid, add rows of the details to the table
                if(score != INVALID_SCORE) {

                    // Form a new Table Row
                    TableRow row = new TableRow(this);

                    // Fill the field for S.No. and add it to the row
                    TextView tvSNO = new TextView(this);
                    tvSNO.setTextColor(R.color.fgMaroon);
                    tvSNO.setPadding(5, 5, 5, 5);
                    tvSNO.setText(String.valueOf(i++));
                    row.addView(tvSNO);

                    // Fill in the field for User Name (email) and add it to the row
                    TextView tvUserName = new TextView(this);
                    tvUserName.setTextColor(R.color.fgMaroon);
                    tvUserName.setPadding(5, 5, 5, 5);
                    tvUserName.setText(curr_user_email);
                    row.addView(tvUserName);

                    // Fill in the field for Score and add it to the row
                    TextView tvNewScore = new TextView(this);
                    tvNewScore.setTextColor(R.color.fgMaroon);
                    tvNewScore.setPadding(5, 5, 5, 5);
                    tvNewScore.setText(String.valueOf(score));
                    row.addView(tvNewScore);

                    // Add the complete row to the table
                    TL.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                }

            }while(score != INVALID_SCORE);

            //Once the table is ready, add it to the parent layout
            statLayout.addView(TL);
        }
    }

    /**********************************************************************************************
     * This function is called when the user clicks on the Go To Home button
     * @param view
     */
    public void GoBack(View view){
        Intent i = new Intent(this, GameRules.class);
        startActivity(i);
    }
}

/*********************************** End of ScoreStats class  *************************************/