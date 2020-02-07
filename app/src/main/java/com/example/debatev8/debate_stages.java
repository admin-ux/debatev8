package com.example.debatev8;

import java.sql.Struct;
import java.io.Serializable;

//Description       : This class is an object used in Game craetion
//Inner Workings    :

public class debate_stages implements Serializable{
    private  String TopicTitle;
    private review_stage ReviewStage;
    private stage Stage1;
    private stage Stage2;
    private stage Stage3;

    public debate_stages() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public debate_stages(review_stage reviewstage, stage stage1, stage stage2, stage stage3, String topictitle) {
        ReviewStage=reviewstage;
        Stage1=stage1;
        Stage2=stage2;
        Stage3=stage3;
        TopicTitle=topictitle;

    }

    public review_stage getReviewStage() {
        return ReviewStage;
    }

    public void setReviewStage(review_stage reviewStage) {
        ReviewStage = reviewStage;
    }

    public stage getStage1() {
        return Stage1;
    }

    public void setStage1(stage stage1) {
        Stage1 = stage1;
    }

    public stage getStage2() {
        return Stage2;
    }

    public void setStage2(stage stage2) {
        Stage2 = stage2;
    }

    public stage getStage3() {
        return Stage3;
    }

    public void setStage3(stage stage3) {
        Stage3 = stage3;
    }

    public String getTopicTitle() {
        return TopicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        TopicTitle = topicTitle;
    }


}
