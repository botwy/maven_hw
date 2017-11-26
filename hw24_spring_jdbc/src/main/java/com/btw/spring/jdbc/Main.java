package com.btw.spring.jdbc;

import com.btw.spring.jdbc.pojo.Ingredient;
import com.btw.spring.jdbc.pojo.Recipe;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main (String...args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppSpringConfig.class);
        AppRecipe app = context.getBean(AppRecipe.class);

        Scanner scanner = new Scanner(System.in);



        while (true) {
            System.out.println("ввести новый рецепт (1), или найти рецепт по имени (2)" +
                    ", или удалить рецепт по имени (3)");
        int command = Integer.valueOf(scanner.nextLine());

            if (command == 1) {

                System.out.println("ввести имя нового рецепта");
                String nameRecipe = scanner.nextLine();
                System.out.println("ввести число ингридиентов");
                int rowNumber = Integer.valueOf(scanner.nextLine());

                List<Ingredient> ingredientList = new ArrayList<>();
                for (int i = 0; i < rowNumber; i++) {
                    Ingredient ingredient = context.getBean(Ingredient.class);
                    System.out.println("ввести название ингридиента");
                    String nameIng = scanner.nextLine();
                    System.out.println("ввести количество ингридиента");
                    double qtyIng = Double.valueOf(scanner.nextLine());
                    System.out.println("ввести меру ингридиента");
                    String measureIng = scanner.nextLine();

                    ingredient.setName(nameIng);
                    ingredient.setQty(qtyIng);
                    ingredient.setMeasure(measureIng);
                    ingredientList.add(ingredient);
                }

                app.insertRecipe(nameRecipe, ingredientList);
            }
            if (command == 2) {
                System.out.println("ввести имя рецепта, который ищите");
                String reqName = scanner.nextLine();

                Recipe recipe = app.getRecipesByName(reqName);
                if(recipe==null) continue;
                System.out.println("Рецепт: " + recipe);
                System.out.println("Ингридиенты: ");
                for (Ingredient ing : recipe.getIngredientList()
                        ) {
                    System.out.println(ing);
                }
            }

            if (command == 3) {
                System.out.println("ввести имя рецепта, который хотите удалить");
                String reqName = scanner.nextLine();

                Recipe recipe = app.getRecipesByName(reqName);
                if(recipe==null) {
                    System.out.println("Рецепт не найден");
                    continue;
                }
                System.out.println("Рецепт удален: " + recipe);

            }
        }
    }


}
