package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the shoppingList class. We can set the budget for this shopping. And the toBuy list stores
 * all the things that we added which needed to buy. When the items are bought (added to the cart), the
 * things are removed from the toBuy list and goes into the bought list.
 * Shoppling list extends CategoryList used for categorize the items into proper category
 */

public class ShoppingList extends CategoryList implements Writable {
    private double budget;
    protected List<Item> toBuy;
    protected List<Item> bought;
    private Spending spending;


    // EFFECTS: create a shopping list with initial budget set to 0.0,
    //          with empty to-buy list, bought list
    public ShoppingList() {
        budget = 0.0;
        toBuy = new ArrayList<>();
        bought = new ArrayList<>();
        spending = new Spending();
    }

    // Getter and Setter
    public double getBudget() {
        return budget;
    }

    public List<Item> getToBuy() {
        return toBuy;
    }

    public List<Item> getBought() {
        return bought;
    }

    public Spending getSpending() {
        return spending;
    }

    // MODIFIES: this
    // EFFECTS: set the budget for this shopping
    public void setBudget(double budget) {
        this.budget = budget;
    }

    // EFFECTS: return the number of items needed to buy in the list
    public int totalItem() {
        return toBuy.size();
    }


    // REQUIRES: item != null, item not contain in the shopping list already
    // MODIFIES: this
    // EFFECTS: add the given Item (item) into the toBuy list
    public void addItem(Item item) {
        if (!isContained(item.getName())) {
            toBuy.add(item);
        }
    }

    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: delete the items from the toBuy list
    public void deleteItem(Item item) {
        toBuy.remove(item);
    }


    // REQUIRES: items not null, item is in the toBuy list
    // MODIFIES: this
    // EFFECTS: for the already-added-to-cart items, stores the transaction to home
    // create a new transaction for the item.
    public void markItem(Item item) {
        if (isContained(item.getName())) {
            deleteItem(item);
            bought.add(item);
            addToHome(item);
            addTransaction();
        }
    }


    // REQUIRES: item != null
    // MODIFIES: this
    // EFFECTS: stores the given Item (it) into the appropriate categories within this class
    private void addToHome(Item item) {
        Categories categories = item.getCategories();
        super.addToList(item, categories);
    }


    // EFFECTS: check if the item is already in the list with given item name, if it is in the list,
    // return true, if not return false
    public Boolean isContained(String name) {
        for (Item i : toBuy) {
            if (i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: create a new transaction for each bought items,
    //          After the transaction for item is tracked, remove the item from the bought list
    public void addTransaction() {
        for (Item i : bought) {
            Transaction t = new Transaction(i, 0.0);
            spending.getTransactions().add(t);
        }
        bought.removeAll(getBought());
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("budget", budget);
        json.put("Needs to buy", addToJson(toBuy));
        json.put("Have bought", addToJson(bought));
//        json.put("Transactions", addToJson(spending.getTransactions()))
        return json;
    }

    // EFFECTS: returns items in this shopping list as a JSON array
    private JSONArray addToJson(List<Item> list) {
        JSONArray jsonArray = new JSONArray();

        for (Item i : list) {
            jsonArray.put(i.toJson());
        }

        return jsonArray;
    }
}








