package com.example.card_quiz_game_tilloop1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/************************************************************************************
 *
 *  The Quiz activity shows the questions for the quiz to the user and captures the
 *  score as per whether the answer is right or wrong.
 * @author Pallavi Tilloo
 *
 *************************************************************************************/

public class Quiz extends AppCompatActivity implements SubmitConfirmationDialogFragment.ClickDialogListener {

    ArrayList<LinearLayout> ques_layouts;
    int score;
    int INVALID_SCORE = 100;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button submit;


    public Quiz(){
        super(R.layout.activity_quiz);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        //Create array list of layouts for easier handling of the question answer layouts
        ques_layouts = new ArrayList<LinearLayout>();
        ques_layouts.add((LinearLayout)findViewById(R.id.quesLayout1));
        ques_layouts.add((LinearLayout)findViewById(R.id.quesLayout2));
        ques_layouts.add((LinearLayout)findViewById(R.id.quesLayout3));
        ques_layouts.add((LinearLayout)findViewById(R.id.quesLayout4));
        ques_layouts.add((LinearLayout)findViewById(R.id.quesLayout5));

        //Submit button will not be visible until the last question
        submit = (Button)findViewById(R.id.btnSubmit);
        submit.setVisibility(View.INVISIBLE);

        //Initializing score as 0. This will keep increasing as the user gives correct answers
        score = 0;

        //Show the first question when the activity loads
        ShowQuestion(ques_layouts.get(0), false);
    }

    /**********************************************************************************************
     *  This method shows the question that is passed through the show_layout.
     *  It also hides other layouts so that only that question is shown.
     *  @param show_layout : The layout to be shown
     *  @param is_last_ques : If the question is the last question in the quiz
     *  ******************************************************************************************/

    private void ShowQuestion(LinearLayout show_layout, boolean is_last_ques){
        Iterator<LinearLayout> iterator = ques_layouts.iterator();
        while(iterator.hasNext()){
            LinearLayout temp = iterator.next();

            // All other layouts to be made INVISIBLE

            if(temp.getId() != show_layout.getId()){
                temp.setVisibility(View.INVISIBLE);
            }
            // Whichever layout matches the one passed to the function is made VISIBLE
            else{
                temp.setVisibility(View.VISIBLE);
            }
        }
        if(is_last_ques)
            submit.setVisibility(View.VISIBLE);
    }

    /**********************************************************************************************
     * The function to check if the answer contained in the view is correct or not.
     * @param view : The view whose answer needs to be checked
     ********************************************************************************************/
    private void CheckAnswer(View view){
        switch(view.getId()){
            case R.id.btnNext1:
                    // If the correct option is chosen for Question 1
                    RadioGroup rg1Options = (RadioGroup)findViewById(R.id.q1_rgOptions);
                    if(rg1Options.getCheckedRadioButtonId() == R.id.q1_rbOption1)
                        score = 10;
                    else
                        score = 0;
                    break;

            case R.id.btnNext2:
                    // If the correct options are chosen for Question 2
                    CheckBox cb2Option1 = (CheckBox) findViewById(R.id.q2_cbOption1);
                    CheckBox cb2Option2 = (CheckBox) findViewById(R.id.q2_cbOption2);
                    CheckBox cb2Option3 = (CheckBox) findViewById(R.id.q2_cbOption3);
                    CheckBox cb2Option4 = (CheckBox) findViewById(R.id.q2_cbOption4);

                    //Only the required options should be checked, and nothing else
                    if(cb2Option1.isChecked() && cb2Option2.isChecked() && cb2Option3.isChecked()
                        && !cb2Option4.isChecked())
                    score = score + 10;
                    break;

            case R.id.btnNext3:

                    // If the correct option is chosen for Question 3
                    RadioGroup rg3Options = (RadioGroup)findViewById(R.id.q3_rgOptions);
                    if(rg3Options.getCheckedRadioButtonId() == R.id.q3_rbOption3)
                    score = score + 10;
                    break;

            case R.id.btnNext4:

                    // If the correct answer is entered in the given text box for Question 4
                    EditText et = (EditText) findViewById(R.id.etq4);
                    String user_ans = et.getText().toString();
                    if(user_ans.toLowerCase().equals("layout"))
                        score = score + 10;
                    break;

            case R.id.btnSubmit:

                    // If the correct options are chosen in Question 5
                    CheckBox cb5Option1 = (CheckBox) findViewById(R.id.q5_cbOption1);
                    CheckBox cb5Option2 = (CheckBox) findViewById(R.id.q5_cbOption2);
                    CheckBox cb5Option3 = (CheckBox) findViewById(R.id.q5_cbOption3);
                    CheckBox cb5Option4 = (CheckBox) findViewById(R.id.q5_cbOption4);

                    //Only the required options should be chosen and nothing else
                    if(cb5Option1.isChecked() && cb5Option3.isChecked() && cb5Option4.isChecked() &&
                        !cb5Option2.isChecked())
                    score = score + 10;
                    break;

            default:break;
        }
    }

    /**********************************************************************************************
     * The function is called from every question view when the button NEXT is clicked from the
     * layout.
     * @param view : The view from which the NEXT button is clicked
     ********************************************************************************************/
    public void Next(View view){

        int ques_no = 1;
        switch(view.getId()){
            case R.id.btnNext2: ques_no = 2;
                                break;
            case R.id.btnNext3: ques_no = 3;
                                break;
            case R.id.btnNext4: ques_no = 4;
                                break;
            default: ques_no = 1;
        }

        // When the user clicks on Next, check answer and show confirmation dialog

        CheckAnswer(view);

        //The question number for which alert dialog has to be shown is passed as parameter
        showAlertDialog(ques_no);
    }

    /**********************************************************************************************
     * On click of the button Submit in the last question
     * @param view: The view from which it was called
     *********************************************************************************************/
    public void Submit(View view) {

        // When the user clicks on Submit, check answer and show confirmation dialog

        CheckAnswer(view);
        showAlertDialog(5);
    }

    /**********************************************************************************************
     *  The function that shows the alert dialog window when NEXT button is clicked.
     * @param ques_no : The question number from which the dialog is to be shown
     *********************************************************************************************/
    private void showAlertDialog(int ques_no) {
        FragmentManager fm = getSupportFragmentManager();
        SubmitConfirmationDialogFragment alertDialog = SubmitConfirmationDialogFragment.newInstance("Confirmation", ques_no);
        alertDialog.show(fm, "fragment_alert");
    }

    /*********************************************************************************************
     * The function that gets called when dialog is finished. Either on click of OK or Cancel.
     * @param inputText: The text from the dialog box. "OK" if the user clicks on OK
     * @param ques_no: The question number for which the dialog was shown.
     *********************************************************************************************/

    @Override
    public void onFinishDialog(String inputText, int ques_no) {

        // If user clicks OK, only then show next question, otherwise remain on the current one
        int next_ques = (inputText == "OK") ? ques_no : ques_no-1;

        // Check if the current question is the last one
        boolean is_last_ques = (next_ques == 4) ? true : false;

        // If the question is not last (ques_no=5) and the user has not clicked on OK
        if(ques_no < 5 || inputText != "OK")
        {
            ShowQuestion(ques_layouts.get(next_ques), is_last_ques);
        }
        // If the question is last (ques_no=5) and the user has clicked on OK

        else if(ques_no == 5 && inputText == "OK"){
            //Get the Shared Preferences
            pref = getApplicationContext().getSharedPreferences("CARD_QUIZ", 0); // 0 - for private mode
            editor = pref.edit();

            // The string will be used to get all the scores stored for the user
            String final_score_key;

            // The part of the key for which the scores are stored.
            // <UserEmail><Score><#>

            String score_key = pref.getString("Email", null)+"Score";

            int latest_score;
            int j=1;

            // This loop gets the latest score number which is stored for the user.
            // When the key is not present, it returns INVALID_SCORE for the score.
            do
            {
                final_score_key = score_key + String.valueOf(j++);
                latest_score=pref.getInt(final_score_key, INVALID_SCORE);

            }while(latest_score!=INVALID_SCORE);

            // Store SCORE in Shared Preferences with the key formed earlier
            editor.putInt(final_score_key, score);
            editor.commit();

            // Start activity show result after the quiz is done

            Intent i = new Intent(this, ShowResult.class);
            Bundle bundle = new Bundle();
            bundle.putInt("current_score",score);
            i.putExtras(bundle);
            startActivity(i);
        }
    }
}

/***************************************** End of Quiz Class *************************************/