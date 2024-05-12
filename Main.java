package javaproject;

import java.util.*;
public class Main {
        public static void main(String[] args) throws Exception{
            int c;

           DatabaseManager edb = new DatabaseManager();
           
            Scanner scan = new Scanner(System.in);
            System.out.println("EXPENSE TRACKER");
           
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println(" Hello! Welcome Back!! To calculate your savings, we need your income amount. Please enter your income: ");
            double totalIncome = scan.nextDouble(); //problem- entering income eveytime u open the program
          //solution- store total income in db
            scan.nextLine(); 

            do{

            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("1. Add Expense");
            System.out.println("2. Delete your expense");
            System.out.println("3. View your expenses according to month");
            System.out.println("4. Calculate savings according to month");
            System.out.println("5. Exit");
            System.out.println("Enter your choice:");

            c = scan.nextInt();
            scan.nextLine();

            switch(c){
                case 1:
                    //add expense:
                    edb.addExpense();
                break;

                case 2:


                break;

                case 3:
                    
                    System.out.println("Enter a month: ");
                    String month = scan.nextLine();
                    edb.printExpensesByMonth(month);

                break;

                case 4:
                    System.out.println("Enter a month: ");
                    String months = scan.nextLine();
                    edb.calculateSavings(months, totalIncome);
                break;

                case 5:
                    System.out.println("Quitting..."); 
                break;
                default:
                    System.out.println("Invalid input");
                break;
            }

        }while(c!=4);

            scan.close();
        }
}
