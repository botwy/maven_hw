package com.btw.spring_jdbc;

import com.btw.spring_jdbc.pojo.Ingredient;
import com.btw.spring_jdbc.pojo.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class AppRecipe {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public AppRecipe(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        //  jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS APP_RECIPE");
        //  jdbcTemplate.execute("USE APP_RECIPE");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS RECIPE (" +
                " id NUMBER(18) AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(100)," +
                ")");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS INGREDIENT (" +
                " id NUMBER(18) AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(100)," +
                " qty DECIMAL(5)," +
                " measure VARCHAR(10)," +
                " id_recipe NUMBER(18)," +
                " CONSTRAINT IF NOT EXISTS FK_ID_RECIPE FOREIGN KEY(id_recipe) REFERENCES RECIPE(id)" +
                ")");

    }

    //нужен preparestatement

    public void insertRecipe(String recipeName, List<Ingredient> ingredientList) {

        int id_recipe = jdbcTemplate.update("INSERT INTO RECIPE (name) values ('" + recipeName + "')");

        SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(ingredientList.toArray());

        namedParameterJdbcTemplate.batchUpdate("INSERT INTO INGREDIENT (name,qty,measure,id_recipe) " +
                "values (:name,:qty,:measure," + id_recipe + ")", batchParams);
    }

    /**
     * @param reqName
     * @return null если рецепт по имени не найден
     */

    public Recipe getRecipesByName(String reqName) {
        List<Recipe> recipeList = namedParameterJdbcTemplate.query("SELECT * FROM RECIPE where name like :reqName",
                new MapSqlParameterSource("reqName", reqName+"%"),
                new RowMapper<Recipe>() {

                    @Override
                    public Recipe mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Recipe(resultSet.getInt(1), resultSet.getString(2));
                    }
                });

        if (recipeList.isEmpty()) return null;

        Recipe recipe = recipeList.get(0);

        List<Ingredient> ingredientList = namedParameterJdbcTemplate.query("SELECT * FROM INGREDIENT where id_recipe=:idRecipe",
                new MapSqlParameterSource("idRecipe", recipe.getId()),
                new RowMapper<Ingredient>() {

                    @Override
                    public Ingredient mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Ingredient(resultSet.getInt(1)
                                , resultSet.getString(2)
                                , resultSet.getDouble(3)
                                , resultSet.getString(4)
                                , resultSet.getInt(5)
                        );
                    }
                });
        recipe.setIngredientList(ingredientList);
        return recipe;

    }


    public Recipe deleteRecipe(String reqName) {

        List<Recipe> recipeList = namedParameterJdbcTemplate.query("SELECT * FROM RECIPE where name = :reqName",
                new MapSqlParameterSource("reqName", reqName),
                new RowMapper<Recipe>() {

                    @Override
                    public Recipe mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Recipe(resultSet.getInt(1), resultSet.getString(2));
                    }
                });

        if (recipeList.isEmpty()) return null;

        Recipe recipe = recipeList.get(0);

        namedParameterJdbcTemplate.update("DELETE FROM INGREDIENT where id_recipe=:idRecipe"
                , new MapSqlParameterSource("idRecipe", recipe.getId()));

        namedParameterJdbcTemplate.update("DELETE FROM RECIPE where id=:idRecipe"
                , new MapSqlParameterSource("idRecipe", recipe.getId()));
        return recipe;
    }

}
