package com.example.debatev8;
import java.io.Serializable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class stage implements Serializable{
    private String TopicHeader;
    private String Arg;
    private String Resp;
    private int JudgeScore;

    public stage() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public stage(String arg, String resp, int judgescore, String topicheader ) {
        Arg = arg;
        Resp = resp;
        JudgeScore = judgescore;
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

    public int getJudgeScore() {
        return JudgeScore;
    }

    public void setJudgeScore(int judgeScore) {
        JudgeScore = judgeScore;
    }

    public String getTopicHeader() {
        return TopicHeader;
    }

    public void setTopicHeader(String topicHeader) {
        TopicHeader = topicHeader;
    }


}