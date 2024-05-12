package javaproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;

public class DatabaseManager {
    
    public void runsql(String sql) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root", "nash123");
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
      }
    
    public ResultSet display(String sql) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root", "nash123");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }

    public void addExpense() throws Exception{
        Scanner scan = new Scanner(System.in);
        //Expense expense= new Expense();
        String addAnother ;
            do{

                System.out.println("Enter category of expense:");
                String category = scan.nextLine();

                System.out.print("Enter amount: ");
                double amount = scan.nextDouble();
                scan.nextLine();

                System.out.print("Enter month: ");
                String month = scan.nextLine();

                Expense expense = new Expense(category, amount, month);

                String sqlString = "insert into record (category, amount, month) values('"+ expense.category +"',  "+expense.amount+" , '"+ expense.month+"');";
                runsql(sqlString);

                System.out.println("Expense added successfully.");

                System.out.print("Add another expense? (yes/no): ");
                addAnother = scan.nextLine();
                System.out.println(addAnother);

                // if (!addAnother.equals("yes")) {
                //     break;  // Exit the loop if user does not want to add another expense
                // }
            
        }while (!addAnother.equals("no"));
            scan.close(); 
    }

    // public void deleteExpense(String category, String month) throws Exception {
    //     String sql = "DELETE FROM record WHERE category = '" + category + "' AND month = '" + month + "'";
    //     Statement statement = connection.createStatement();
    //     int rowsAffected = statement.executeUpdate(sql);
    //     System.out.println(rowsAffected > 0 ? "Expense deleted successfully." : "No expense found for the specified category and month.");
    //     statement.close();
    // }

    
    public void printExpensesByMonth(String month) throws Exception {
       
            String query = "SELECT * FROM record WHERE month = " + month;
            ResultSet resultSet = display(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                int expenseMonth = resultSet.getInt("month");

                System.out.println("Expense ID: " + id);
                System.out.println("Category: " + category);
                System.out.println("Amount: " + amount);
                System.out.println("Month: " + expenseMonth);
                System.out.println("---------------------");
            }
        }
   
        public double calculateSavings(String month, double totalIncome) throws Exception {
            double totalExpenses = 0.0;
        
            String sql = "SELECT amount FROM record WHERE MONTH = '" + month + "'";
            
            runsql(sql);
        
            ResultSet resultSet = display(sql);
        
            while (resultSet.next()) {
                totalExpenses += resultSet.getDouble("amount");
            }
            resultSet.close();
        
            return totalIncome - totalExpenses;
        }
 } 
