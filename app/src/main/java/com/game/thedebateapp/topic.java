package com.game.thedebateapp;
import java.io.Serializable;

//Description       : This class is for the topic object creation
//Inner Workings    :
public class topic implements Serializable{
    private String TopicTitle;
    private String TopicHeader1;
    private String TopicHeader2;
    private String TopicHeader3;
    private int TopicRating;

    public topic() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public topic(String topicTitle, String topicHeader1, String topicHeader2, String topicHeader3, int topicRating) {
        TopicTitle =topicTitle;
        TopicHeader1 =topicHeader1;
        TopicHeader2 =topicHeader2;
        TopicHeader3 =topicHeader3;
        TopicRating =topicRating;


    }
    public String getTopicTitle() {
        return TopicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        TopicTitle = topicTitle;
    }

    public String getTopicHeader1() {
        return TopicHeader1;
    }

    public void setTopicHeader1(String topicHeader1) {
        TopicHeader1 = topicHeader1;
    }

    public String getTopicHeader2() {
        return TopicHeader2;
    }

    public void setTopicHeader2(String topicHeader2) {
        TopicHeader2 = topicHeader2;
    }

    public String getTopicHeader3() {
        return TopicHeader3;
    }

    public void setTopicHeader3(String topicHeader3) {
        TopicHeader3 = topicHeader3;
    }

    public int getTopicRating() {
        return TopicRating;
    }

    public void setTopicRating(int topicRating) {
        TopicRating = topicRating;
    }



}