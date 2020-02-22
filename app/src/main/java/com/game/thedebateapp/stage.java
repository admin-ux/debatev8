package com.game.thedebateapp;
import java.io.Serializable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
//Description       : This class is for the stage object
//Inner Workings    :
public class stage implements Serializable{
    private String TopicHeader;
    private String Arg;
    private String Resp;
    private int JudgeScoreA;
    private int JudgeScoreR;

    public stage() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public stage(String arg, String resp, int judgeScoreA,int judgeScoreR, String topicheader ) {
        Arg = arg;
        Resp = resp;
        JudgeScoreA = judgeScoreA;
        JudgeScoreR= judgeScoreR;
        TopicHeader = topicheader;

    }

    public String getArg() {
        return Arg;
    }

    public void setArg(String arg) {
        Arg = arg;
    }

    public String getResp() {
        return Resp;
    }

    public void setResp(String resp) {
        Resp = resp;
    }

    public int getJudgeScoreA() {
        return JudgeScoreA;
    }

    public void setJudgeScoreA(int judgeScoreA) {
        JudgeScoreA = judgeScoreA;
    }
    public int getJudgeScoreR() {
        return JudgeScoreR;
    }

    public void setJudgeScoreR(int judgeScoreR) {
        JudgeScoreR = judgeScoreR;
    }

    public String getTopicHeader() {
        return TopicHeader;
    }

    public void setTopicHeader(String topicHeader) {
        TopicHeader = topicHeader;
    }


}