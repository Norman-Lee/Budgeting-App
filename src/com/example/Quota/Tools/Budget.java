package com.example.Quota.Tools;

import java.util.ArrayList;

public class Budget {

    private String name;

    private ArrayList<Budget> subBudgets;
    private ArrayList<Item> items;

    private double total;
    private double remaining;

    public Budget(String name){
        this.name = name;
        total = 0;

        items = new ArrayList<Item>();
        subBudgets = new ArrayList<Budget>();
    }

    public Budget(String name, double total){
        this.name = name;
        this.total = total;
        this.remaining = total;
        items = new ArrayList<Item>();
        subBudgets = new ArrayList<Budget>();
    }

    public boolean isOverBudget(){
        if(remaining <= 0)
            return true;
        else
            return false;
    }

    public void addSubBudget(Budget budget){
        subBudgets.add(budget);
        remaining = calculateRemaining();
    }

    public void removeSubBudget(String name){
        for(int budgetIndex = 0; budgetIndex < subBudgets.size(); budgetIndex++){

            if(subBudgets.get(budgetIndex).name ==  name)
                subBudgets.remove(budgetIndex);
        }
    }

    public void addItem(Item item){
        if(item != null) {

        items.add(item);
        remaining = calculateRemaining();

        }
    }

    public void removeItem(String name){
        for(int itemIndex = 0; itemIndex < items.size(); itemIndex++){

            if(items.get(itemIndex).name() ==  name)
                items.remove(itemIndex);
        }
    }

    public ArrayList<Item> getBudgetItems(){
        return items;
    }

    public ArrayList<Budget> getSubBudgets(){
        return subBudgets;
    }
    public double getRemaining(){
        return remaining;
    }

    public double getTotal(){
        return total;
    }

    public String name(){
        return name;
    }

    private double calculateRemaining(){
        double sum = 0;

        if(total == 0) {
            for (int budgetIndex = 0; budgetIndex < subBudgets.size(); budgetIndex++) {
                sum += subBudgets.get(budgetIndex).getRemaining();
            }

            for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
                sum += items.get(itemIndex).cost();
            }
        }
        else{
            for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
                sum += items.get(itemIndex).cost();
            }
            sum = total + sum;
        }
        return sum;
    }

}
