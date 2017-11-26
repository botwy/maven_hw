package com.btw.spring.jdbc.pojo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Recipe {

    private Integer id;
    private String name;
    private List<Ingredient> ingredientList;

    public Recipe() {
        this.ingredientList=new ArrayList<>();
    }

    public Recipe(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.ingredientList=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                ", name='" + name + '\'' +
                '}';
    }
}
