package javaproject;

import java.sql.ResultSet;
import java.util.*;

public class Main {
    static int createNewUser() throws Exception {
        int uid;
        DatabaseManager edb = new DatabaseManager();
        Scanner scan = new Scanner(System.in);

        System.out.println("To calculate your savings, we need your information. Please fill up the details.");
        System.out.println("Please enter your userid :");
        uid = scan.nextInt();
        scan.nextLine();

        if (edb.usercheck(uid) == true) {
            System.out.println("This userid is already taken!");
            do {
                System.out.println("Please enter a new userid, previous was already taken :");
                uid = scan.nextInt();
            } while (edb.usercheck(uid) == false);
            System.out.println("Please enter your name :");
            String uname = scan.nextLine();
            scan.nextLine();
            System.out.print("Please enter your income :");
            double income = scan.nextDouble();
            scan.nextLine();
            String sqlString = "insert into users values(" + uid + ",'" + uname + "'," + income + ");";
            edb.runsql(sqlString);

        } else {
            System.out.println("Please enter your name :");
            String uname = scan.nextLine();
            System.out.print("Please enter your income :");
            double income = scan.nextDouble();
            scan.nextLine();
            String sqlString = "insert into users values(" + uid + ",'" + uname + "'," + income + ");";
            edb.runsql(sqlString);
        }
        return uid;
    }

    public static void main(String[] args) throws Exception {
        int c, uid;
        char ch;
        String newuser,month,category;

        DatabaseManager edb = new DatabaseManager();

        Scanner scan = new Scanner(System.in);
        System.out.println("------------------");
        System.out.println("| EXPENSE TRACKER |");
        System.out.println("------------------");
        System.out.println("Hello! Are u a new user? (yes/no): ");
        newuser = scan.nextLine();

        if (newuser.equals("yes")) {
            uid = createNewUser();
        } else {
            // existing user
            System.out.println("Enter your userid :");
            uid = scan.nextInt();
            scan.nextLine();

            // validity
            String sql = "select * from users where uid = " + uid + ";";
            ResultSet resultSet = edb.display(sql);
            if (!resultSet.next()) {
                System.out.println("User doesn't exist. Please create new user.");
                uid = createNewUser();
            }
            ch = (char) System.in.read();
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
                    edb.addExpense(uid);
                    ch = (char) System.in.read();
                    break;

                case 2:
                    System.out.println("Enter a category: ");
                    category = scan.nextLine();
                    System.out.println("Enter a month: ");
                    month = scan.nextLine();
                    edb.deleteExpense(category, month);
                    ch = (char) System.in.read();
                    break;

                case 3:
                    System.out.println("Enter a month: ");
                    month = scan.nextLine();
                    edb.printExpensesByMonth(month, uid);
                    ch = (char) System.in.read();
                    break;

                case 4:
                    System.out.println("Enter a month: ");
                    month = scan.nextLine();
                    edb.calculateSavings(uid, month);
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
