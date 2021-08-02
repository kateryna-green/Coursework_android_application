package com.example.SS_Application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class StartingScreenActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_QUIZ = 1;
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    public static final String EXTRA_LANGUAGE_ID = "extraLanguageID";
    public static final String EXTRA_LANGUAGE_NAME = "extraLanguageName";

    public static final String SHARE_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    private TextView textViewHighscore;

    private Spinner spinnerLanguage;
    private Spinner spinnerDifficulty;

    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_screen);

        textViewHighscore = findViewById(R.id.text_view_highscore);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);
        spinnerLanguage = findViewById(R.id.spinner_language);

        loadLanguages();
        loadDifficultyLevels();
        loadHighscore();

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });

        Button buttonStartCards = findViewById(R.id.button_start_cards);
        buttonStartCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCards();
            }
        });

    }

    private void startQuiz(){

        Language selectedLanguage = (Language) spinnerLanguage.getSelectedItem();

        int categoryID = selectedLanguage.getId();

        String languageName = selectedLanguage.getName();
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent intent = new Intent(StartingScreenActivity.this, QuizActivity.class);

        intent.putExtra(EXTRA_LANGUAGE_ID, categoryID);
        intent.putExtra(EXTRA_LANGUAGE_NAME, languageName);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);

        startActivityForResult(intent, REQUEST_CODE_QUIZ);

    }


    private void startCards() {

        Language selectedLanguage = (Language) spinnerLanguage.getSelectedItem();

        int categoryID = selectedLanguage.getId();
        String languageName = selectedLanguage.getName();
        Intent intent = new Intent(StartingScreenActivity.this, CardsActivity.class);

        intent.putExtra(EXTRA_LANGUAGE_ID, categoryID);
        intent.putExtra(EXTRA_LANGUAGE_NAME, languageName);

        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == REQUEST_CODE_QUIZ){ {
                if(resultCode == RESULT_OK) {
                    int score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                    if (score > highscore){
                        updateHighscore(score);
                    }
                }
            }
        }
    }

    private void loadLanguages() {

        DbHelper dbHelper = DbHelper.getInstance(this);
        List<Language> categories = dbHelper.getAllLanguages();

        ArrayAdapter<Language> adapterLanguages = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);

        adapterLanguages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterLanguages);
    }

    private void loadDifficultyLevels() {

        String[] difficultyLevels = Question.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    }

    private void loadHighscore(){
        SharedPreferences preferences = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
        highscore = preferences.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences preferences = getSharedPreferences(SHARE_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}