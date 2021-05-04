package com.example.womensecurity.models;

import java.util.ArrayList;

public class User {

    String userId;
    String userName;
    String profile;
    ArrayList<Call> calls;
    ArrayList<Message> messages;
    ArrayList<Siren> sirens;
    ArrayList<Ratting> ratting = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public ArrayList<Call> getCalls() {
        return calls;
    }

    public void setCalls(ArrayList<Call> calls) {
        this.calls = calls;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void setRatting(ArrayList<Ratting> ratting) {
        this.ratting = ratting;
    }

    public ArrayList<Ratting> getRatting() {
        return ratting;
    }
}
