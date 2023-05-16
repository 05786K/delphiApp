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

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getTotal_fat() {
        return total_fat;
    }

    public void setTotal_fat(int total_fat) {
        this.total_fat = total_fat;
    }

    public int getSaturated_fat() {
        return saturated_fat;
    }

    public void setSaturated_fat(int saturated_fat) {
        this.saturated_fat = saturated_fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getSugars() {
        return sugars;
    }

    public void setSugars(int sugars) {
        this.sugars = sugars;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }
}
