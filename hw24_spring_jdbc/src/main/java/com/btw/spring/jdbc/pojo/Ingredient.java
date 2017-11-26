package com.btw.spring.jdbc.pojo;

import org.springframework.stereotype.Component;

@Component
public class Ingredient {
    private Integer id;
    private String name;
    private Number qty;
    private String measure;
    private Integer recipe_id;

    public Ingredient() {
    }

    public Ingredient(Integer id, String name, Number qty, String measure, Integer recipe_id) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.measure = measure;
        this.recipe_id = recipe_id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Number getQty() {
        return qty;
    }

    public String getMeasure() {
        return measure;
    }

    public Integer getRecipe_id() {
        return recipe_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQty(Number qty) {
        this.qty = qty;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setRecipe_id(Integer recipe_id) {
        this.recipe_id = recipe_id;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                ", name='" + name + '\'' +
                ", qty=" + qty +
                ", measure='" + measure + '\'' +
                '}';
    }
}
