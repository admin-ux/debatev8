package com.example.debatev8;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
//Description       : This class is not used

public class review_stage {
    private int Debater1Review;
    private int Debater2Review;
    private int JudgeReview;
    private int Topic1Review;
    private int Topic2Review;

    public review_stage() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public review_stage(int debater1review, int debater2review, int judgereview, int topic1review, int topic2review) {
        Debater1Review = debater1review;
        Debater2Review = debater2review;
        JudgeReview = judgereview;
        Topic1Review = topic1review;
        Topic2Review = topic2review;

    }
    public int getDebater1Review() {
        return Debater1Review;
    }

    public void setDebater1Review(int debater1Review) {
        Debater1Review = debater1Review;
    }

    public int getDebater2Review() {
        return Debater2Review;
    }

    public void setDebater2Review(int debater2Review) {
        Debater2Review = debater2Review;
    }

    public int getJudgeReview() {
        return JudgeReview;
    }

    public void setJudgeReview(int judgeReview) {
        JudgeReview = judgeReview;
    }

    public int getTopic1Review() {
        return Topic1Review;
    }

    public void setTopic1Review(int topic1Review) {
        Topic1Review = topic1Review;
    }

    public int getTopic2Review() {
        return Topic2Review;
    }

    public void setTopic2Review(int topic2Review) {
        Topic2Review = topic2Review;
    }






}