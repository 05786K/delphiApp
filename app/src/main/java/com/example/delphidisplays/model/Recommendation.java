package com.example.delphidisplays.model;

import com.google.gson.annotations.SerializedName;

public class Recommendation {
    private Integer rank;
    private Integer itemId;
    private String itemName;

    // Constructor
    public Recommendation(Integer rank, Integer item_id, String itemName) {
        this.rank = rank;
        this.itemId = item_id;
        this.itemName = itemName;
    }

    // Getters and setters

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer item_id) {
        this.itemId = item_id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String item_name) {
        this.itemName = item_name;
    }
}