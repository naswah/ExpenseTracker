package javaproject;

public class Expense {
    int id;
    String category;
    double amount;
    String month;

    public Expense(){

    }

    public Expense(String category, double amount, String month) {
        this.category = category;
        this.amount = amount;
        this.month = month;
    }

}
