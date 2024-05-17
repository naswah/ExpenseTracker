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

    public void addExpense(int uid) throws Exception{
        Scanner scan = new Scanner(System.in);
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

                String sqlString = "insert into record (category, amount, month) values('"+ category +"',  "+amount+" , '"+ month+"');";
                runsql(sqlString);
                
                //resultSet.next();

                String sql1 = "select count(*) from record as total;";
                runsql(sql1);

                String sql2 = "insert into combine values(total, uid)";
                runsql(sql2);

                System.out.println("Expense added successfully.");

                System.out.print("Add another expense? (yes/no): ");
                addAnother = scan.nextLine();
                System.out.println(addAnother);
            
        }while (addAnother.equals("yes"));
            
    }

    // public void deleteExpense(String category, String month) throws Exception {
    //     String sql = "DELETE FROM record WHERE category = '" + category + "' AND month = '" + month + "'";
    //     Statement statement = connection.createStatement();
    //     int rowsAffected = statement.executeUpdate(sql);
    //     System.out.println(rowsAffected > 0 ? "Expense deleted successfully." : "No expense found for the specified category and month.");
    //     statement.close();
    // }


        public void printExpensesByMonth(String month) throws Exception {
            String query = "SELECT * FROM record WHERE month = '" + month + "';";
            ResultSet resultSet = display(query);
            while (resultSet.next()) {
                System.out.println("Expense ID: " + resultSet.getInt("id"));
                System.out.println("Category: " + resultSet.getString("category"));
                System.out.println("Amount: " +  resultSet.getDouble("amount"));
                System.out.println("Month: " + resultSet.getString("month"));
                System.out.println("---------------------");
            }
        }    
   
        public double calculateSavings(int uid, String month) throws Exception {
            double totalExpenses = 0.0;
            double income = 0.0;
        
            String sql = "SELECT income FROM users WHERE uid = " + uid;
            ResultSet incomeResultSet = display(sql);
        
            if (incomeResultSet.next()) {
                income = incomeResultSet.getDouble("income");
            }
            incomeResultSet.close();
        
            String sql1 = "SELECT amount FROM record WHERE MONTH = '" + month + "'";
            ResultSet expensesResultSet = display(sql1);
        
            while (expensesResultSet.next()) {
                totalExpenses += expensesResultSet.getDouble("amount");
            }
            expensesResultSet.close();

            return income - totalExpenses;
        }
 } 
