package com.barmej.culturalwords;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AnswerActivity extends AppCompatActivity {

    private TextView textView;
    private Button returnButton;

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set lang
        /*
        ملحوظة هنا بعيد ضبط اللغة لان عند دوران الجهاز اللغة تعود للغة الجهاز الافتراضية
         */
        Functions.getAndSetLang(this);

        setContentView(R.layout.activity_answer);

        textView = findViewById(R.id.text_view_answer);
        returnButton = findViewById(R.id.button_return);

        // Get current index from intent
        currentIndex = getIntent().getIntExtra(Constants.INDEX_IMAGE_EXTRA, 0);

        // get answer
        String answer = getAnswer();
        // Show answer
        showAnswer(answer);

        // Return button click listener
        returnButton.setOnClickListener(listener -> returnToMainActivity());

    }

    private String getAnswer(){
        String answer = getResources().getStringArray(R.array.answers)[currentIndex];
        String answerDetails = getResources().getStringArray(R.array.answer_description)[currentIndex];

        return (answer + "\n" + answerDetails);
    }

    private void showAnswer(String answer){
        textView.setText(answer);
    }

    private void returnToMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}