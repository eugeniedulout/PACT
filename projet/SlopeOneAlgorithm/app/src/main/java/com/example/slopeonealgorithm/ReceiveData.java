package com.example.slopeonealgorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/*
 * Method ReceiveData - not written yet-
 *
 */
public class ReceiveData {

    protected static List<Item> items = Arrays.asList(new Item("Halal"), new Item("Bio"), new Item("Vegetarien"), new Item("qualite"));

    public static Map<User, HashMap<Item, Double>> initializeData(int numberOfUsers) {
        Map<User, HashMap<Item, Double>> data = new HashMap<>();
        HashMap<Item, Double> newUser;
        Set<Item> newRecommendationSet;
        for (int i = 0; i < numberOfUsers; i++) {
            newUser = new HashMap<Item, Double>();
            newRecommendationSet = new HashSet<>();
            for (int j = 0; j < 3; j++) {
                newRecommendationSet.add(items.get((int) (Math.random() * 4)));
            }
            for (Item item : newRecommendationSet) {
                newUser.put(item, Math.random());
            }
            data.put(new User("User " + i), newUser);
        }
        return data;
    }
}
