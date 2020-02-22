package com.game.thedebateapp;
import java.io.Serializable;


//Description       : This class is the game object that structures debates
//Inner Workings    :
public class game implements Serializable{

    private String GameID;
    private boolean GameFull = false;
    private boolean BeenJudged = false;
    private  boolean GameFinished = false;
    private debate_stages Stages;
    private String Player1;
    private String Player2;
//    private topic TopicChosen;
    private String Player1Topics;
    private String Player2Topics;

    private Integer TotalScore;
    private Integer Wins;
    private Integer A_avg;
    private Integer R_avg;
    private Integer NumGamesPlayed;

    public game() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public game(String gameID, boolean gameFull, boolean beenJudged, boolean gameFinished, debate_stages stages, String player1, String player2, String player1Topics, String player2Topics, Integer totalScore, Integer wins, Integer a_avg, Integer r_avg, Integer numGamesPlayed) {
        GameID = gameID;
        GameFull = gameFull;
        BeenJudged = beenJudged;
        GameFinished = gameFinished;
        Stages = stages;
        Player1 = player1;
        Player2 = player2;
//        TopicChosen = topicChosen;
        Player1Topics = player1Topics;
        Player2Topics = player2Topics;
        TotalScore = totalScore;
        Wins = wins;
        A_avg = a_avg;
        R_avg = r_avg;
        NumGamesPlayed = numGamesPlayed;
    }


    public String getGameID() {
        return GameID;
    }

    public void setGameID(String gameID) {
        GameID = gameID;
    }

    public boolean isGameFull() {
        return GameFull;
    }

    public void setGameFull(boolean gameFull) {
        GameFull = gameFull;
    }

    public boolean isBeenJudged() {
        return BeenJudged;
    }

    public void setBeenJudged(boolean beenJudged) {
        BeenJudged = beenJudged;
    }

    public boolean isGameFinished() {
        return GameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        GameFinished = gameFinished;
    }

    public debate_stages getStages() {
        return Stages;
    }

    public void setStages(debate_stages stages) {
        Stages = stages;
    }

    public String getPlayer1() {
        return Player1;
    }

    public void setPlayer1(String player1) {
        Player1 = player1;
    }

    public String getPlayer2() {
        return Player2;
    }

    public void setPlayer2(String player2) {
        Player2 = player2;
    }

//    public topic getTopicChoosen() {
//        return TopicChosen;
//    }
//
//    public void setTopicChoosen(topic topicChoosen) {
//        TopicChosen = topicChoosen;
//    }

    public String getPlayer1Topics ()
    {
        return Player1Topics;
    }

    public void setPlayer1Topics (String player1topics)
    {
        Player1Topics = player1topics;
    }
    public String getPlayer2Topics ()
    {
        return Player2Topics;
    }

    public void setPlayer2Topics (String player2topics)
    {
        Player2Topics = player2topics;
    }

//    public topic getTopicChosen() {
//        return TopicChosen;
//    }
//
//    public void setTopicChosen(topic topicChosen) {
//        TopicChosen = topicChosen;
//    }

    public Integer getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(Integer totalScore) {
        TotalScore = totalScore;
    }

    public Integer getWins() {
        return Wins;
    }

    public void setWins(Integer wins) {
        Wins = wins;
    }

    public Integer getA_avg() {
        return A_avg;
    }

    public void setA_avg(Integer a_avg) {
        A_avg = a_avg;
    }

    public Integer getR_avg() {
        return R_avg;
    }

    public void setR_avg(Integer r_avg) {
        R_avg = r_avg;
    }

    public Integer getNumGamesPlayed() {
        return NumGamesPlayed;
    }

    public void setNumGamesPlayed(Integer numGamesPlayed) {
        NumGamesPlayed = numGamesPlayed;
    }
}
