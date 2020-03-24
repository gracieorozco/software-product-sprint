package com.google.sps.data;

public final class SentimentScore {

    private final float score;

    public SentimentScore(float score) {
        this.score = score;
    }

    public float GetScore() {
        return this.score;
    }

    public String CreateMessage() {
        return "This comment has a sentiment score of " + this.GetScore() + ". ";
    }

}