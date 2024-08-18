package net.ddns.superrobs.warpmap;

import java.util.*;
import java.util.stream.Collectors;

public class CategoryManager {

    private static List<Category> categories = FileHandler.load();

    static List<Category> getCategories(){
        return categories;
    }

    static void setCategories(List<Category> categories){
        CategoryManager.categories = categories;
    }

    public static void addCategory(String name, CategoryAvailability availability){
        //category names must be unique
        if(categoryExists(name)) return;
        categories.add(new Category(name, availability));
    }

    public static void removeCategory(String categoryName) {
        categories.removeIf(c -> c.getName().equals(categoryName));
    }

    public static Set<String> getCategoryNames(){
        return categories.stream().map(Category::getName).collect(Collectors.toSet());
    }

    public static Set<String> getCategoryNames(CategoryAvailability availability){
        return categories.stream().filter(c -> c.getAvailability().equals(availability)).map(Category::getName).collect(Collectors.toSet());
    }

    public static void changeCategoryAvailability(String name, CategoryAvailability availability){
        getCategory(name).ifPresent(c -> c.setAvailability(availability));
    }

    public static Optional<CategoryAvailability> getCategoryAvailability(String categoryName){
        return categories.stream()
                .filter(c -> c.getName().equals(categoryName))
                .findFirst()
                .map(Category::getAvailability);
    }

    public static boolean categoryExists(String name){
        return categories.stream().anyMatch(c -> c.getName().equals(name));
    }

    static Optional<Category> getCategory(String categoryName){
        return categories.stream().filter(c -> c.getName().equals(categoryName)).findFirst();
    }
}

