package javaproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;

public class DatabaseManager {

    public void runsql(String sql) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root",
                "nash123");
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public ResultSet display(String sql) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root",
                "nash123");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public boolean usercheck(int uid) throws Exception {
        String sql = "select * from record where id=" + uid + ";";
        ResultSet resultSet = display(sql);
        while (resultSet.next()) {
            if (uid == resultSet.getInt("id")) {
                return true;
            }
        }
        return false;
    }

    public void addExpense(int uid) throws Exception {
        Scanner scan = new Scanner(System.in);
        String addAnother;

        do {

            System.out.println("Enter category of expense:");
            String category = scan.nextLine();

            System.out.print("Enter amount: ");
            double amount = scan.nextDouble();
            scan.nextLine();

            System.out.print("Enter month: ");
            String month = scan.nextLine();

            Expense expense = new Expense(category, amount, month);

            String sqlString = "insert into record (category, amount, month) values('" + category + "',  " + amount
                    + " , '" + month + "');";
            runsql(sqlString);

            String sql1 = "select count(*) as total from record;";
            ResultSet resultSet=display(sql1);
            resultSet.next();
            int total = resultSet.getInt("total");

            String sql2 = "insert into combine values("+total+", "+uid+");";
            runsql(sql2);

            System.out.println("Expense added successfully.");

            System.out.print("Add another expense? (yes/no): ");
            addAnother = scan.nextLine();
            System.out.println(addAnother);

        } while (addAnother.equals("yes"));
    }

    public void deleteExpense(String category, String month) throws Exception {
        String query="delete from record where category='"+category+"' and month = '"+month+"';";
        runsql(query);
        String sql = "select * from record where category='" + category + "';";
        ResultSet resultSet = display(sql);
            System.out.println("Deleted successfully!");
            
    }

    public void printExpensesByMonth(String month, int uid) throws Exception {
        String query = "SELECT * FROM record NATURAL JOIN combine NATURAL JOIN users WHERE month = '" + month
                + "' AND uid = " + uid + ";";
        ResultSet resultSet = display(query);
        while (resultSet.next()) {
            System.out.println("---------------------");
            System.out.println("Expense ID: " + resultSet.getInt("id"));
            System.out.println("Category: " + resultSet.getString("category"));
            System.out.println("Amount: " + resultSet.getDouble("amount"));
            System.out.println("Month: " + resultSet.getString("month"));
            
        }
    }

    public void calculateSavings(int uid, String month) throws Exception {
        String sql = "SELECT income FROM users WHERE uid = " + uid + ";";
        ResultSet incomeResultSet = display(sql);
        incomeResultSet.next();
        double income = incomeResultSet.getDouble("income");

        double totalExpenses = 0.0;
        String query = "SELECT * FROM record NATURAL JOIN combine NATURAL JOIN users WHERE month = '" + month
                + "' AND uid = " + uid + ";";
        ResultSet resultSet = display(query);
        while (resultSet.next()) {
            totalExpenses += resultSet.getDouble("amount");
        }

        // display user ko current income and expense

        double incomeExpenseDifference;
        if (income > totalExpenses) {
            incomeExpenseDifference = income - totalExpenses;
            System.out.println("Savings = " + incomeExpenseDifference);
        } else if (income < totalExpenses) {
            incomeExpenseDifference = totalExpenses - income;
            System.out.println("Loss = " + incomeExpenseDifference);
        } else {
            System.out.println("You have no excess saving or expenditure");
        }
    }
}
