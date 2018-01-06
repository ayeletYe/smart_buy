package com.example.yaeli.smart_buy;

import java.util.ArrayList;

/**
 * Created by yaeli on 06/01/2018.
 */

public class Recipe {

        ArrayList<String> recipe;
        String name;

        Recipe(String name){
            recipe=new ArrayList<>();
            this.name=name;
        }

        public void addIngredient(String ing){
            recipe.add(ing);
        }

}
