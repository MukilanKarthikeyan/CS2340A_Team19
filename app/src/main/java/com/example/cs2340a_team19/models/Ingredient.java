package com.example.cs2340a_team19.models;

public class Ingredient extends Aggregatable {
    private String name;
    private int calories;
    private int quantity;
    private String expirationDate;

    public Ingredient() {
        this("", 0, 1, null);
    }
    public Ingredient(String name, int calories, int quantity) {
        this(name, calories, quantity, null);
    }
    public Ingredient(String name, int calories, int quantity, String expirationDate) {
        this.setName(name);
        this.setCalories(calories);
        this.setQuantity(quantity);
        this.setExpirationDate(expirationDate);
    }

    public Ingredient(Ingredient other) {
        if (other == null) {
            return;
        }
        this.setName(other.name);
        this.setCalories(other.calories);
        this.setQuantity(other.quantity);
        this.setExpirationDate(other.expirationDate);
        super.setId(other.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Ingredient) {
            Ingredient curr = (Ingredient) o;
            if (getName() != null && curr.getName() != null && getName().trim().equalsIgnoreCase(
                    curr.getName().trim())) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
