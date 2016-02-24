package com.codepath.apps.mytweetbox.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mytweetbox.R;

public class ComposeFragment extends DialogFragment {
    private static final String TAG = "ComposeFragment";
    private EditText etBody;
    private TextView tvRemaining;
    private Button bSendTweet;

    public interface ComposeTweetListener {
        void onComposeTweet(String tweet);
    }


    public ComposeFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ComposeFragment newInstance(String title) {
        ComposeFragment frag = new ComposeFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvRemaining = (TextView) view.findViewById(R.id.tvRemaining);

        setupSendButton(view);
        setupTweetBody(view);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void setupSendButton(View view) {
        bSendTweet = (Button) view.findViewById(R.id.bSendTweet);

        bSendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return input text back to activity through the implemented listener
                ComposeTweetListener listener = (ComposeTweetListener) getActivity();
                listener.onComposeTweet(etBody.getText().toString());

                // Close the dialog and return back to the parent activity
                dismiss();

            }
        });
    }

    private void setupTweetBody(View view) {
        etBody = (EditText) view.findViewById(R.id.etTweetBody);

        etBody.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int tweetMaxSize = getResources().getInteger(R.integer.tweet_max_size);
                int curLength = tweetMaxSize - etBody.getText().toString().length();
                tvRemaining.setText(Integer.toString(curLength));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Request focus to the compose field.
        etBody.requestFocus();
    }
}
