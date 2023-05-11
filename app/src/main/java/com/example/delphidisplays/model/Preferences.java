package com.example.delphidisplays.model;



public class Preferences {
    private int calories, total_fat, saturated_fat, sodium, carbohydrates, sugars, protein;

    public Preferences(int calories, int total_fat, int saturated_fat, int sodium, int carbohydrates, int sugars, int protein) {
        this.calories = calories;
        this.total_fat = total_fat;
        this.saturated_fat = saturated_fat;
        this.sodium = sodium;
        this.carbohydrates = carbohydrates;
        this.sugars = sugars;
        this.protein = protein;
    }
}
