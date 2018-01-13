package com.example.yaeli.smart_buy;

import java.util.ArrayList;

class Recipe {

    private final ArrayList<String> recipe;
    private final String name;

    Recipe(String name) {
        recipe = new ArrayList<>();
        this.name = name;
    }

    public void addIngredient(String ing) {
        recipe.add(ing);
    }

}
