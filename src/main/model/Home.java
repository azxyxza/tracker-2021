package model;

import java.util.ArrayList;
import java.util.List;

public class Home extends Categorize {
    private List<Item> all;
    private List<Item> favorite;

    public Home() {
        all = new ArrayList<>();
        favorite = new ArrayList<>();
        addToFavorite();
    }

    public List<Item> getFood() {
        return food;
    }

    public List<Item> getFruitAndVeg() {
        return fruitAndVeg;
    }

    public List<Item> getDrinks() {
        return drinks;
    }

    public List<Item> getNecessities() {
        return necessities;
    }

    public List<Item> getOthers() {
        return others;
    }

    public List<Item> getAll() {
        return all;
    }

    public List<Item> getFavorite() {
        return favorite;
    }


    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: stores the given Item (it) into the appropriate categories within this class
    public void addItem(Item it) {
        Categories categories = it.getCategories();
        super.addToList(it, categories);
        all.add(it);
    }

    // EFFECTS: get total amount of items at home
    public int totalItem() {
        int sum = 0;
        for (Item item : all) {
            sum++;
        }
        return sum;
    }

    // REQUIRES: the item is in the list
    // MODIFIES: this
    // EFFECTS: remove the certain item from the whole list and the given categories
    public void deleteItem(Item it) {
        if (all.contains(it)) {
            all.remove(it);
            switch (it.getCategories()) {
                case Food:
                    food.remove(it);
                case Drinks:
                    drinks.remove(it);
                case FruitAndVegetables:
                    fruitAndVeg.remove(it);
                case Necessities:
                    necessities.remove(it);
                case Others:
                    others.remove(it);
            }
        }
    }

    // EFFECTS: get amount of items in specific categories
    public int getAmountType(Categories categories) {
        switch (categories) {
            case Food:
                return food.size();
            case Drinks:
                return drinks.size();
            case FruitAndVegetables:
                return fruitAndVeg.size();
            case Necessities:
                return necessities.size();
            default:
                return others.size();
        }
    }


    // REQUIRES: item != null
    // EFFECTS: add the item to the favorite
    public void addToFavorite() {
        for (Item i : all) {
            if (i.isFavorite()) {
                favorite.add(i);
            }
        }
    }


    // REQUIRES: the item is in one of the categories, or it is in favorite
    // EFFECTS: delete the item from previous categories and move an item to the new categories
    public void moveItem(Item item, Categories categories) {
        if (all.contains(item)) {
            deleteItem(item);
            super.addToList(item, categories);
            all.add(item);
        }
    }
}
