package com.example.yaeli.smart_buy;

import java.util.ArrayList;

class Recipes {
    private final ArrayList<Recipe> recipes;

    Recipes(){
        recipes= new ArrayList<>();
    }


    public void addRecipe(Recipe rec){
        recipes.add(rec);
    }

}

