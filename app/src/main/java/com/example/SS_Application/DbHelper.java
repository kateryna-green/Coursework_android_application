package com.example.SS_Application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.SS_Application.DbContract.*;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SS_database.db";
    public static final int DATABASE_VERSION = 1;

    private static DbHelper instance;

    private SQLiteDatabase db;

    private  DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_LANGUAGES_TABLE = "CREATE TABLE " +
                LanguagesTable.TABLE_NAME + "( " +
                LanguagesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LanguagesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER," +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_LANGUAGE_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_LANGUAGE_ID + ") REFERENCES " +
                LanguagesTable.TABLE_NAME + "(" + LanguagesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        final String SQL_CREATE_CARDS_TABLE = "CREATE TABLE " +
                CardsTable.TABLE_NAME + " ( " +
                CardsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CardsTable.COLUMN_TEXT_FRONT + " TEXT, " +
                CardsTable.COLUMN_TEXT_BACK + " TEXT, " +
                QuestionsTable.COLUMN_LANGUAGE_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_LANGUAGE_ID + ") REFERENCES " +
                LanguagesTable.TABLE_NAME + "(" + LanguagesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_LANGUAGES_TABLE);
        fillLanguagesTable();

        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionsTable();

        db.execSQL(SQL_CREATE_CARDS_TABLE);
        fillCardsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LanguagesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CardsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillLanguagesTable() {
        Language l1 = new Language("German");
        addLanguage(l1);
        Language l2 = new Language("Polish");
        addLanguage(l2);
    }

    private void addLanguage(Language language) {
        ContentValues cv = new ContentValues();
        cv.put(LanguagesTable.COLUMN_NAME, language.getName());
        db.insert(LanguagesTable.TABLE_NAME, null, cv);
    }

    private void  fillQuestionsTable(){
        Question q1 = new Question("___ Mädchen",
                "der", "die", "das", 3,
                Question.DIFFICULTY_EASY, Language.GERMAN);
        addQuestion(q1);
        Question q2 = new Question("Jak często ___ sport?",
                "Uprawiam", "Uprawiasz", "Ustawiacie", 2,
                Question.DIFFICULTY_EASY, Language.POLISH);
        addQuestion(q2);
        Question q3 = new Question("Es ___ mir gefallen.",
                "hat", "ist", "war", 1,
                Question.DIFFICULTY_MEDIUM, Language.GERMAN);
        addQuestion(q3);
        Question q4 = new Question("Jaki miesząc idzie po lipcu? ",
                "Sierpień", "Styczeń", "Wrzesień", 1,
                Question.DIFFICULTY_MEDIUM, Language.POLISH);
        addQuestion(q4);
        Question q5 = new Question("Kasia robiła prace roczną całą noc. Jak się czuję Kasia?",
                "Ona ma dużo sił", "Ona jest niewyspana", "Ona jest chora", 2,
                Question.DIFFICULTY_HARD, Language.POLISH);
        addQuestion(q5);
        Question q6 = new Question("Das Haus ist vom Vater gebaut ___",
                "würden", "werden", "worden", 3,
                Question.DIFFICULTY_HARD, Language.GERMAN);
        addQuestion(q6);
        Question q7 = new Question("___ Tastatur",
                "der", "die", "das", 2,
                Question.DIFFICULTY_EASY, Language.GERMAN);
        addQuestion(q7);
        Question q8 = new Question("Jakiego koloru jest krew?",
                "Granatowego", "Czerwonego", "Czarnego", 2,
                Question.DIFFICULTY_EASY, Language.POLISH);
        addQuestion(q8);
        Question q9 = new Question("Er ___ mich",
                "kümmert sich um", "kümmert auf", "kümmert für", 1,
                Question.DIFFICULTY_MEDIUM, Language.GERMAN);
        addQuestion(q9);
        Question q10 = new Question("Każdego ranka matka mówi mi ___.",
                "Obudź się!", "Śpi!", "Obudź!", 1,
                Question.DIFFICULTY_MEDIUM, Language.POLISH);
        addQuestion(q10);
        Question q11 = new Question("Po lewej stronie korytarza było ___ (2) drzwi  (tipa slożnyj) ",
                "Dwie", "Dwójka", "Dwoje", 3,
                Question.DIFFICULTY_HARD, Language.POLISH);
        addQuestion(q11);
        Question q12 = new Question("Ich scheine diesen Mann schon ___",
                "sah", "gesehen", "gesehen zu haben", 3,
                Question.DIFFICULTY_HARD, Language.GERMAN);
        addQuestion(q12);
        Question q13 = new Question("Der Lehrer ordnete an, ___",
                "ich habe mich an die Arbeit machen gesollt",
                "hätte ich an die Arbeit machen gesollt",
                "habe ich an die Arbeit machen gesollt", 1,
                Question.DIFFICULTY_HARD, Language.GERMAN);
        addQuestion(q13);
        Question q14 = new Question("W restauracji na mnie cały czas patrzył człowiek ___ (który pił) herbatę (tipa slożnyj)",
                "Pijący", "Piszący", "Pijąc", 1,
                Question.DIFFICULTY_HARD, Language.POLISH);
        addQuestion(q14);
    }

    private void  fillCardsTable(){
        Card c1 = new Card("Zapraszamy", "Please", Language.POLISH);
        addCard(c1);
        Card c2 = new Card("Nein","No", Language.GERMAN);
        addCard(c2);
        Card c3 = new Card("Być","Be", Language.POLISH);
        addCard(c3);
        Card c4 = new Card("Hässlich","Ugly", Language.GERMAN);
        addCard(c4);
    }

    private  void  addQuestion(Question question){
        ContentValues cv = new ContentValues();

        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());

        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());

        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());

        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());

        cv.put(QuestionsTable.COLUMN_LANGUAGE_ID, question.getLanguageID());

        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Language> getAllLanguages(){
        List<Language> languageList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + LanguagesTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Language language = new Language();
                language.setId(c.getInt(c.getColumnIndex(LanguagesTable._ID)));
                language.setName(c.getString(c.getColumnIndex(LanguagesTable.COLUMN_NAME)));
                languageList.add(language);
            } while (c.moveToNext());
        }
        c.close();
        return languageList;
    }


    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if(c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));

                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));

                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));

                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setLanguageID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_LANGUAGE_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }



    public ArrayList<Question> getQuestions(int languageID, String difficulty){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_LANGUAGE_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";

        String[] selectionArgs = new String[]{String.valueOf(languageID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(c.moveToFirst()) {
            do {
                Question question = new Question();

                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));

                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));

                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));

                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setLanguageID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_LANGUAGE_ID)));

                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }


    private void addCard(Card card){
        ContentValues cv = new ContentValues();

        cv.put(CardsTable.COLUMN_TEXT_FRONT, card.getTextFront());
        cv.put(CardsTable.COLUMN_TEXT_BACK, card.getTextBack());

        cv.put(QuestionsTable.COLUMN_LANGUAGE_ID, card.getLanguageID());

        db.insert(CardsTable.TABLE_NAME, null, cv);
    }


    public ArrayList<Card> getCards(int languageID) {
        ArrayList<Card> cardsList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_LANGUAGE_ID + " = ? " ;

        String[] selectionArgs = new String[]{String.valueOf(languageID)};

        Cursor c = db.query(
                CardsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Card card = new Card();

                card.setId(c.getInt(c.getColumnIndex(CardsTable._ID)));
                card.setTextFront(c.getString(c.getColumnIndex(CardsTable.COLUMN_TEXT_FRONT)));
                card.setTextBack(c.getString(c.getColumnIndex(CardsTable.COLUMN_TEXT_BACK)));
                card.setLanguageID(c.getInt(c.getColumnIndex(CardsTable.COLUMN_LANGUAGE_ID)));
                cardsList.add(card);
            } while (c.moveToNext());
        }
        c.close();
        return cardsList;
    }

}
