package main.java.logic.entities;

import main.java.animation.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.java.logic.algorithms.Algorithm;
import main.java.logic.algorithms.DefaultAlgorithm;

import java.awt.geom.Point2D;
import java.util.*;


public abstract class Location extends GameObject {
    public Point point;
    static double DIAMETER = 30;
    Circle circle;
    Algorithm algorithm = new DefaultAlgorithm();

    List<Order> orders = new ArrayList<>();
    List<Recipe> recipes = new ArrayList<>();

    Map< String,Integer> resourceMap = new HashMap<>();
    Map< String,Integer> resourceProductionMap = new HashMap<>();


    public Location(Point point, String name) {
        super(name);
        this.point = point;
        circle.setText(name,200);
        circle = new Circle(point,DIAMETER, AnimationHelper.getRandomColor());
    }

    @Override
    public DrawableObject getDrawable() {
        return circle;
    }

    @Override
    public void update(double delta){
        if(delta>1000){
            delta=0;
            Set< Map.Entry< String,Integer> > st = resourceProductionMap.entrySet();
            for (Map.Entry< String,Integer> me:st) {
                addResources(me.getKey(), me.getValue());
            }

        }
        for (Recipe recipe: recipes) {
            recipe.checkRecipe(this, delta);
        }

        algorithm.handleOrders(orders,this);
        circle.setDescription(mapToString(resourceMap),13);
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}

