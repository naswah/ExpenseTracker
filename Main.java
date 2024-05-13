package javaproject;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int c;
        char ch;
        String newuser;
        int userid;

        DatabaseManager edb = new DatabaseManager();

        Scanner scan = new Scanner(System.in);

        System.out.println("EXPENSE TRACKER");
        System.out.println("Hello! Are u a new user? (yes/no): ");
        newuser = scan.nextLine();

        if (newuser.equals("yes")){
            System.out.println("Hello!!! To calculate your savings, we need your income amount. ");
            System.out.println("Please enter your userid :");
            int id = scan.nextInt();
            System.out.println("Please enter your name :");
            String name=scan.nextLine();
            System.out.print("Please enter your income :");
            double totalIncome = scan.nextDouble();
            scan.nextLine();
            String sqlString = "insert into users values("+ id +",'"+ name+"',"+ totalIncome +";)";
            edb.runsql(sqlString);
        }
        else {
            System.out.println("Enter your userid :");
            userid = scan.nextInt();
        }
        
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("1. Add Expense");
            System.out.println("2. Delete your expense");
            System.out.println("3. View your expenses according to month");
            System.out.println("4. Calculate savings according to month");
            System.out.println("5. Exit");
            System.out.print("Enter your choice:");

            c = scan.nextInt();
            scan.nextLine();

            switch (c) {
                case 1:
                    // add expense:
                    edb.addExpense();
                    ch = (char) System.in.read();
                    break;

                case 2:
                    ch = (char) System.in.read();
                    break;

                case 3:

                    System.out.println("Enter a month: ");
                    String month = scan.nextLine();
                    edb.printExpensesByMonth(month);
                    ch = (char) System.in.read();

                    break;

                case 4:
                    System.out.println("Enter a month: ");
                    String months = scan.nextLine();
                    edb.calculateSavings(months, totalIncome);  // cant pass totalIncome directly, have to extract from db
                    ch = (char) System.in.read();
                    break;

                case 5:
                    System.out.println("Quitting...");
                    break;
                default:
                    System.out.println("Invalid input");
                    ch = (char) System.in.read();
                    break;
            }

        } while (c != 5);

        scan.close();
    }
}
