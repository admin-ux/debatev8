package com.example.debatev8;
import java.io.Serializable;




public class game implements Serializable{

    private String GameID;
    private boolean GameFull = false;
    private boolean BeenJudged = false;
    private  boolean GameFinished = false;
    private debate_stages Stages;
    private String Player1;
    private String Player2;
    private topic TopicChosen;
    private String Player1Topics;
    private String Player2Topics;

    public game() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public game(String gameID, boolean gameFull, boolean beenJudged, boolean gameFinished, debate_stages stages, String player1, String player2, topic topicChosen, String player1Topics, String player2Topics) {
        GameID = gameID;
        GameFull = gameFull;
        BeenJudged = beenJudged;
        GameFinished = gameFinished;
        Stages = stages;
        Player1 = player1;
        Player2 = player2;
        TopicChosen = topicChosen;
        Player1Topics = player1Topics;
        Player2Topics = player2Topics;
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

    public topic getTopicChoosen() {
        return TopicChosen;
    }

    public void setTopicChoosen(topic topicChoosen) {
        TopicChosen = topicChoosen;
    }

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

}
