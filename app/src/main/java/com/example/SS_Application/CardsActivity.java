package com.example.SS_Application;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Collections;

public class CardsActivity extends AppCompatActivity {

    public static final String KEY_CARD_LIST = "keyCardList";
    public static final String KEY_CARD_COUNT = "keyCardCount";

    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;

    private boolean mIsBackVisible = false;

    private View mCardFrontLayout;
    private View mCardBackLayout;

    private TextView textFront;
    private TextView textBack;

    private Button buttonNext;
    private ArrayList<Card> cardsList;
    private int cardsCounter;
    private int cardsCountTotal;
    private Card currentCard;

    private TextView textViewLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);


        findViews();
        loadAnimations();
        changeCameraDistance();

        textFront = findViewById(R.id.text_front);
        textBack = findViewById(R.id.text_back);
        buttonNext = findViewById(R.id.button_next);
        textViewLanguage = findViewById(R.id.text_view_language_card);


        Intent intent = getIntent();

        int languageID = intent.getIntExtra(StartingScreenActivity.EXTRA_LANGUAGE_ID, 0);
        String languageName = intent.getStringExtra(StartingScreenActivity.EXTRA_LANGUAGE_NAME);

        textViewLanguage.setText("Language: " + languageName);

        if(savedInstanceState == null) {
            DbHelper dbHelper = DbHelper.getInstance(this);

            cardsList = dbHelper.getCards(languageID);
            cardsCountTotal = cardsList.size();
            Collections.shuffle(cardsList);
            showNextCard();

        } else {
            cardsList = savedInstanceState.getParcelableArrayList(KEY_CARD_LIST);
            cardsCountTotal = cardsList.size();
            cardsCounter = savedInstanceState.getInt(KEY_CARD_COUNT);
            currentCard = cardsList.get(cardsCounter - 1);
        }


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showNextCard();}
        });

    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    private void showNextCard(){

        if(cardsCounter < cardsCountTotal) {

            currentCard = cardsList.get(cardsCounter);
            textFront.setText(currentCard.getTextFront());
            textBack.setText(currentCard.getTextBack());
            cardsCounter++;

        } else {
            finishCards();
        }
    }
    private void finishCards(){
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CARD_COUNT, cardsCounter);
        outState.putParcelableArrayList(KEY_CARD_LIST, cardsList);
    }

}