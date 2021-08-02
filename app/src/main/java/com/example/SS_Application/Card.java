package com.example.SS_Application;


import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {

    private int id;
    private String text_front;
    private String text_back;

    private int languageID;

    public Card() {
    }

    public Card(String text_front, String text_back, int languageID) {

        this.text_front = text_front;
        this.text_back = text_back;
        this.languageID = languageID;
    }

    protected Card(Parcel in){

        id = in.readInt();
        text_front = in.readString();
        text_back = in.readString();
        languageID = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(text_front);
        dest.writeString(text_back);
        dest.writeInt(languageID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextFront() {
        return text_front;
    }

    public void setTextFront(String question) {
        this.text_front = question;
    }

    public String getTextBack() {
        return text_back;
    }

    public void setTextBack(String option1) {
        this.text_back = option1;
    }

    public int getLanguageID() {
        return languageID;
    }

    public void setLanguageID(int languageID) {
        this.languageID = languageID;
    }

}
