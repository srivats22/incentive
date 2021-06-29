package com.srivats.incentive.Helper;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class OnlineItems implements Serializable {
    @Exclude
    private String id;

    private String textTile;
    private String textDescription;
    private String textReward;

    public OnlineItems(){}

    public OnlineItems(String id, String textTile, String textDescription, String textReward) {
        this.id = id;
        this.textTile = textTile;
        this.textDescription = textDescription;
        this.textReward = textReward;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextTile() {
        return textTile;
    }

    public void setTextTile(String textTile) {
        this.textTile = textTile;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getTextReward() {
        return textReward;
    }

    public void setTextReward(String textReward) {
        this.textReward = textReward;
    }
}
