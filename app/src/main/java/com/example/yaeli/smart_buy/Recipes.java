package com.example.yaeli.smart_buy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaeli on 06/01/2018.
 */

public class Recipes {
    ArrayList<Recipe> recipes;

    Recipes(){
        recipes=new ArrayList<Recipe>();
    }


    public void addRecipe(Recipe rec){
        recipes.add(rec);
    }


}

