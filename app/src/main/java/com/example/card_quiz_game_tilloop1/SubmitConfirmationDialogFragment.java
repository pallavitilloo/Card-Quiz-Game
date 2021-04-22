package com.example.card_quiz_game_tilloop1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

/***************************************************************************************************
 *
 *  The SubmitConfirmationDialog Fragment shows the confirmation dialog before submitting the answer
 *  @author Pallavi Tilloo
 *
 **************************************************************************************************/

public class SubmitConfirmationDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    static int ques_no;

    // Defines the listener interface with a method passing back data result.
    public interface ClickDialogListener {
        void onFinishDialog(String clickedButton, int ques_no);
    }

    public SubmitConfirmationDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    // Constructor to initialize the question no for the fragment
    public static SubmitConfirmationDialogFragment newInstance(String title, int ques) {
        SubmitConfirmationDialogFragment frag = new SubmitConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        ques_no = ques;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create the dialog fragment with the supplied title, message and buttons

        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(R.string.confirmation_text);
        alertDialogBuilder.setPositiveButton(R.string.ok, this::onClick);
        alertDialogBuilder.setNegativeButton(R.string.cancel, this::onClick);
        return alertDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        // Return input text back to activity through the implemented listener
        ClickDialogListener listener = (ClickDialogListener) getActivity();
        String returnVal;
        returnVal = (which ==-1)?"OK":"Cancel";
        listener.onFinishDialog(returnVal, ques_no);
        dismiss();
    }
}

/*************************************** End of class *******************************************/
