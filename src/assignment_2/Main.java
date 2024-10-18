package assignment_2;

import java.util.*;
//import java.sql.*;
public class Main {

	public static void main(String args[]) {
		
		System.out.println("               --------------------- Welcome to Library Management System -------------------");
		System.out.println("Select Storage Method");
		
	    Scanner sc = new Scanner(System.in);
        int storageChoice = 0;
        
	    while (true) {
            System.out.println("1. Database");
            System.out.println("2. File");
            System.out.print("Choice: ");

            // Input validation
            if (sc.hasNextInt()) {
                storageChoice = sc.nextInt();
                sc.nextLine();  
                if (storageChoice == 1 || storageChoice == 2) {
                    break;  
                } 
                else {
                    System.out.println("Invalid choice. Please select 1 or 2.");
                }
            } 
            else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();  
            }
        } 
	    
	    PersistenceHandler persistenceHandler = null;
		if(storageChoice == 1)
		{
			persistenceHandler = new DatabaseHandler();
			
		}
		else if(storageChoice == 2)
		{
			persistenceHandler = new FileHandler();
		}
		
		LibrarySystem librarySystem = new LibrarySystem(persistenceHandler);
		librarySystem.populateExistingUsers();
		librarySystem.populateExistingBooks();
		boolean exit = false;
//		
		librarySystem.addUser(new Student("U001", "Alice", "alice@example.com", "123-456-7890", "123 Elm St."));
		librarySystem.addUser(new Faculty("U002", "Dr. Bob", "bob@example.com", "987-654-3210", "456 Maple St."));
		librarySystem.addUser(new Public("U003", "Charlie", "charlie@example.com", "555-555-5555", "789 Oak St."));

		librarySystem.addBook(new Textbook("B001", "Java Programming", "Author A", "ISBN001", 2020, "Computer Science", 20));
		librarySystem.addBook(new Novel("B002", "The Great Novel", "Author B", "ISBN002", 2018, "Fiction", 10));
		librarySystem.addBook(new ReferenceBook("B003", "Encyclopedia", "Author C", "ISBN003", 2015, "Reference", 15));

		int choice = -1;
		while (!exit) {
		    try {
		        System.out.println("\n");
		        System.out.println("                      ---------------------- Library Management System ---------------------");
		        System.out.println("Please choose an option by entering the corresponding number:");
		        System.out.println("1. Add User");
		        System.out.println("2. Remove User");
		        System.out.println("3. Update User");
		        System.out.println("4. Search User");
		        System.out.println("5. Retrieve User Details");
		        System.out.println("6. Retrieve All Users");
		        System.out.println("7. Add Book");
		        System.out.println("8. Remove Book");
		        System.out.println("9. Update Book");
		        System.out.println("10. Search Book");
		        System.out.println("11. Retrieve Book Details");
		        System.out.println("12. Retrieve All Books");
		        System.out.println("13. Loan Book");
		        System.out.println("14. Return Book");
		        System.out.println("15. Exit");
		        System.out.print("Your choice: ");

		        // Attempt to read user input as integer
		        choice = sc.nextInt();
		        sc.nextLine();
		        
		        if (choice >= 1 && choice <= 15) {
		            switch (choice) {
		                case 1:
		                    System.out.println("\n--- Add User ---");
		                    System.out.println("Select User Type:");
		                    System.out.println("(1 - Student, 2 - Faculty, 3 - Public Member):");
		                    int userType = sc.nextInt();
		                    sc.nextLine(); // Consume newline

		                    System.out.print("Enter User ID: ");
		                    String userID = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Name: ");
		                    String name = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Email: ");
		                    String email = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Phone Number: ");
		                    String phone = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Address: ");
		                    String address = sc.nextLine(); // Use nextLine() to allow spaces

		                    if (userType == 1) {
		                        librarySystem.addUser(new Student(userID, name, email, phone, address));
		                    } else if (userType == 2) {
		                        librarySystem.addUser(new Faculty(userID, name, email, phone, address));
		                    } else if (userType == 3) {
		                        librarySystem.addUser(new Public(userID, name, email, phone, address));
		                    } else {
		                        System.out.println("Invalid User Type");
		                    }
		                    break;

		                case 2:
		                    System.out.println("\n--- Remove User ---");
		                    System.out.print("Enter User ID: ");
		                    userID = sc.nextLine(); // Use nextLine() to allow spaces
		                    librarySystem.removeUser(userID);
		                    break;

		                case 3:
		                    System.out.println("\n--- Update User ---");
		                    System.out.print("Enter User ID: ");
		                    userID = sc.nextLine(); // Use nextLine() to allow spaces
		                    librarySystem.updateUser(userID);
		                    break;

		                case 4:
		                    System.out.println("\n--- Search User ---");
		                    System.out.println("Select the attribute to search the User by:");
		                    System.out.println("1 - UserID");
		                    System.out.println("2 - Name");
		                    System.out.println("3 - Type");

		                    int userAttribute = sc.nextInt();
		                    sc.nextLine(); // Consume newline

		                    System.out.print("Enter the value to search: ");
		                    String value = sc.nextLine(); // Use nextLine() to allow spaces

		                    librarySystem.searchUser(userAttribute, value);
		                    break;

		                case 5:
		                    System.out.println("\n--- Retrieve User Details ---");
		                    System.out.print("Enter User ID: ");
		                    userID = sc.nextLine(); // Use nextLine() to allow spaces
		                    librarySystem.displayUser(userID);
		                    break;

		                case 6:
		                    System.out.println("\n--- Retrieve All Users ---");
		                    librarySystem.displayAllUsers();
		                    break;

		                case 7:
		                    System.out.println("\n--- Add Book ---");
		                    System.out.println("Select Book Type:");
		                    System.out.println("(1 - Textbook, 2 - Novel, 3 - Reference Book)");
		                    int bookType = sc.nextInt();
		                    sc.nextLine(); // Consume newline

		                    System.out.print("Enter Book ID: ");
		                    String bookID = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Title: ");
		                    String title = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Author: ");
		                    String author = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter ISBN: ");
		                    String isbn = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Publication Year: ");
		                    int publicationYear = sc.nextInt();

		                    System.out.print("Enter Base Loan Fee: ");
		                    double baseLoanFee = sc.nextDouble();

		                    if (bookType == 1) {
		                        librarySystem.addBook(new Textbook(bookID, title, author, isbn, publicationYear, "Textbook", baseLoanFee));
		                    } else if (bookType == 2) {
		                        librarySystem.addBook(new Novel(bookID, title, author, isbn, publicationYear, "Novel", baseLoanFee));
		                    } else if (bookType == 3) {
		                        librarySystem.addBook(new ReferenceBook(bookID, title, author, isbn, publicationYear, "Reference", baseLoanFee));
		                    } else {
		                        System.out.println("Invalid book type.");
		                    }
		                    break;

		                case 8:
		                    System.out.println("\n--- Remove Book ---");
		                    System.out.print("Enter Book ID: ");
		                    bookID = sc.nextLine(); // Use nextLine() to allow spaces
		                    librarySystem.removeBook(bookID);
		                    break;

		                case 9:
		                    System.out.println("\n--- Update Book ---");
		                    System.out.print("Enter Book ID: ");
		                    bookID = sc.nextLine(); // Use nextLine() to allow spaces
		                    librarySystem.updateBook(bookID);
		                    break;

		                case 10:
		                    System.out.println("\n--- Search Book ---");
		                    System.out.println("Select the attribute to search the Book by:");
		                    System.out.println("1 - BookID");
		                    System.out.println("2 - Title");
		                    System.out.println("3 - Author");
		                    System.out.println("4 - ISBN");
		                    System.out.println("5 - Genre");
		                    System.out.println("6 - Type");
		                    int bookAttribute = sc.nextInt();
		                    sc.nextLine(); // Consume the newline character after nextInt()

		                    System.out.print("Enter the value to search: ");
		                    String value1 = sc.nextLine(); // Use nextLine() to allow spaces

		                    librarySystem.searchBook(bookAttribute, value1);
		                    break;

		                case 11:
		                    System.out.println("\n--- Retrieve Book Details ---");
		                    System.out.print("Enter Book ID: ");
		                    bookID = sc.nextLine(); // Use nextLine() to allow spaces
		                    librarySystem.displayBook(bookID);
		                    break;

		                case 12:
		                    
		                    librarySystem.displayAllBooks();
		                    break;

		                case 13:
		                    System.out.println("\n--- Loan Book ---");
		                    System.out.print("Enter User ID: ");
		                    userID = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Book ID: ");
		                    bookID = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Loan Duration (days): ");
		                    int days = sc.nextInt();
		                    sc.nextLine(); // Consume the newline left by nextInt()

		                    librarySystem.loanBook(userID, bookID, days);
		                    break;

		                case 14:
		                    System.out.println("\n--- Return Book ---");
		                    System.out.print("Enter User ID: ");
		                    userID = sc.nextLine(); // Use nextLine() to allow spaces

		                    System.out.print("Enter Book ID: ");
		                    bookID = sc.nextLine(); // Use nextLine() to allow spaces
		                    librarySystem.returnBook(userID, bookID);
		                    break;

		                case 15:
		                    exit = true;
		                    System.out.println("......Good Bye.....");
		                    break;

		                default:
		                    System.out.println("Invalid Option");
		                    break;
		            }
		        } else {
		            System.out.println("Invalid input. Please select a number between 1 and 15.");
		        }

		    } catch (InputMismatchException e) {
		        // If the user enters a non-integer value
		        System.out.println("Invalid input. Please enter a valid number.");
		        sc.next(); // Consume the invalid input to prevent an infinite loop
		    }
		}
		sc.close();
	}
}
