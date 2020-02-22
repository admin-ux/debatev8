package com.game.thedebateapp;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
//Description       : This class is used to create the user object
//Inner Workings    :
public class user {
    private String Name;
    private String Email;
    private int DebateScore, JudgeScore;
    private int InGame;
    private String GameID;
    private String UserID;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public user(String userID,String username,int inGame, String email, String gameID) {
        Name = username;
        Email = email;
        DebateScore=0;
        JudgeScore=0;
        InGame=inGame;
        GameID=gameID;
        UserID=userID;

    }

    public int getDebateScore() {
        return DebateScore;
    }

    public void setDebateScore(int debateScore) {
        DebateScore = debateScore;
    }

    public int getJudgeScore() {
        return JudgeScore;
    }

    public void setJudgeScore(int judgeScore) {
        JudgeScore = judgeScore;
    }

    public String getGameID() {
        return GameID;
    }

    public void setGameID(String gameID) {
        GameID = gameID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public int getInGame() {
        return InGame;
    }

    public void setInGame(int inGame) {
        this.InGame = inGame;
    }
}
