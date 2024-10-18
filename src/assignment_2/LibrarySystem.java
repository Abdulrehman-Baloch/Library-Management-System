package assignment_2;

import java.util.*;

public class LibrarySystem {
	private Map<String, User> users;
	private Map<String, Book> books;
	private PersistenceHandler persistenceHandler;

	public LibrarySystem(PersistenceHandler persistenceHandler) {
		users = new HashMap<>();
		books = new HashMap<>();
		this.persistenceHandler = persistenceHandler;
	}

	public void populateExistingUsers() {
        ArrayList<User> userList = persistenceHandler.fetchAllUsers(); 
        if(!userList.isEmpty())
        {
	        for (User user : userList) {
	            users.put(user.getUserID(), user);  // Populating the HashMap
	        }
        System.out.println("System HashMap populated with existing data (Users).");
        }
    }
	
	public void addUser(User user) {
		if (users.containsKey(user.getUserID()) || persistenceHandler.userExists(user.getUserID())) {
			System.out.println("User with ID " + user.getUserID() + " already exists.");
		} 
		else {
			users.put(user.getUserID(), user);
			persistenceHandler.insertUser(user);
			//System.out.println("\n");
		}
	}
	
	public void removeUser(String userID) {
	   
	    User user = users.get(userID);
	    
	    if (!persistenceHandler.userExists(userID)) {
	            System.out.println("User with ID " + userID + " does not exist.");
	            return;
	    }
	    else if (user.hasLoanedBook() && user != null) {
	        System.out.println("User cannot be removed as they have loaned books.");
	        return;
	    } 
	    else {
	        // Removing user from the local system (if they exist in it)
	        if (users.containsKey(userID)) {
	            users.remove(userID);
	        }
	        persistenceHandler.deleteUser(userID);
	        //System.out.println("\n");
	    }
	}

	public void updateUser(String userID) {
	    User user = users.get(userID);

	    if (user == null || !persistenceHandler.userExists(userID)) {
	        System.out.println("User with ID " + userID + " not found.");
	        return;
	    }

	    Scanner sc = new Scanner(System.in);
	    
	    System.out.println("Updating user with ID: " + userID);
	    System.out.println("Current details:");
	    System.out.println("Name: " + user.getName());
	    System.out.println("Email: " + user.getEmail());
	    System.out.println("Phone: " + user.getPhoneNumber());
	    System.out.println("Address: " + user.getAddress());
	    System.out.println("User Type: " + user.getClass().getSimpleName());

	    System.out.println("\nEnter new values for the user. Leave blank to keep current value.");

	    System.out.print("New Name (current: " + user.getName() + "): ");
	    String newName = sc.nextLine();
	    if (newName.isEmpty()) {
	        newName = user.getName(); // Keeping current value if input is blank
	    }

	    System.out.print("New Email (current: " + user.getEmail() + "): ");
	    String newEmail = sc.nextLine();
	    if (newEmail.isEmpty()) {
	        newEmail = user.getEmail(); 
	    }

	    System.out.print("New Phone (current: " + user.getPhoneNumber() + "): ");
	    String newPhone = sc.nextLine();
	    if (newPhone.isEmpty()) {
	        newPhone = user.getPhoneNumber(); 
	    }

	    System.out.print("New Address (current: " + user.getAddress() + "): ");
	    String newAddress = sc.nextLine();
	    if (newAddress.isEmpty()) {
	        newAddress = user.getAddress(); 
	    }
	    System.out.print("New Type (current: " + user.getClass().getSimpleName()+ "): ");
	    String newType = sc.nextLine();
	    if (newType.isEmpty()) {
	        newType = user.getClass().getSimpleName(); 
	    }

	   
	    persistenceHandler.updateUser(userID, newName, newEmail, newPhone, newAddress,newType);
	    
	}
	
	public void searchUser(int userAttribute,String value)
	{
		 System.out.println("\nSearch Results: ");
		 persistenceHandler.searchUser(userAttribute, value);
	}

	public void displayUser(String userID)
	{
		persistenceHandler.displayUser(userID);
	}
	public void displayAllUsers() {
	   persistenceHandler.displayAllUsers();
	}
	
	
//	public void displayAllUsers() {
//  
//    if (users.isEmpty()) {
//        System.out.println("There are currently no users in the system.");
//    } 
//    else {
//        System.out.println("Displaying all users in the system:\n");
//        
//        
//        for (Map.Entry<String, User> entry : users.entrySet()) {
//            User user = entry.getValue();
//            
//            System.out.println("User ID: " + user.getUserID());
//            System.out.println("Name: " + user.getName());
//            System.out.println("Email: " + user.getEmail());
//            System.out.println("Phone: " + user.getPhoneNumber());
//            System.out.println("Address: " + user.getAddress());
//            System.out.println("User Type: " + user.getClass().getSimpleName()); 
//            System.out.println("-----------------------------");
//         }
//      }
//   }
	
	public void populateExistingBooks() {
        ArrayList<Book> bookList = persistenceHandler.fetchAllBooks();  
        if(!bookList.isEmpty())
        {
	        for (Book book : bookList) {
	            books.put(book.getBookID(), book);  // Populating the HashMap
	        }
        System.out.println("System HashMap populated with existing data (Books) .");
        }
    }
	
	
	public void addBook(Book book) {
		if (books.containsKey(book.getBookID()) || persistenceHandler.bookExists(book.getBookID())) {
			System.out.println("Book with ID " + book.getBookID() + " already exists.");
		} 
		else {
			books.put(book.getBookID(), book);
			persistenceHandler.insertBook(book);
			//System.out.println("\n");
		}
	}
	
	public void removeBook(String bookID) {
		Book book = books.get(bookID);

		if (!persistenceHandler.bookExists(bookID)) {
	            System.out.println("Book with ID " + bookID + " does not exist.");
	            return;
	    }
	    else if (book.isLoanStatus() && book != null) {
	        System.out.println("Book with ID " + bookID + " is currently loaned out and cannot be removed.");
	        return;
	    } 
	    else {
	        // Removing book from the local system (if they exist in it)
	        if (books.containsKey(bookID)) {
	            books.remove(bookID);
	        }
	        persistenceHandler.deleteBook(bookID);
	    }
	}
	
	public void updateBook(String bookID) {
	    Book book = books.get(bookID);

	    if (!persistenceHandler.bookExists(bookID)) {
	        System.out.println("Book with ID " + bookID + " not found.");
	        return;
	    }

	    Scanner sc = new Scanner(System.in);
	    
	    System.out.println("Updating Book with ID: " + bookID);
	    System.out.println("Current details:");
	    System.out.println("Title: " + book.getTitle());
	    System.out.println("Author: " + book.getAuthor());
	    System.out.println("ISBN: " + book.getIsbn());
	    System.out.println("Publication Year: " + book.getPublicationYear());
	    System.out.println("Genre: " + book.getGenre());
	    System.out.println("Loan Status: " + book.isLoanStatus());
	    System.out.println("Base Loan Fee: " + book.getBaseLoanFee());
	    System.out.println("Book Type: " + book.getClass().getSimpleName());

	    System.out.println("\nEnter new values for the Book. Leave blank to keep current value.");

	    System.out.print("New Title (current: " + book.getTitle() + "): ");
	    String newTitle = sc.nextLine();
	    if (newTitle.isEmpty()) {
	        newTitle = book.getTitle(); // Keeping current value if input is blank
	    }

	    System.out.print("New Author (current: " + book.getAuthor() + "): ");
	    String newAuthor = sc.nextLine();
	    if (newAuthor.isEmpty()) {
	        newAuthor = book.getAuthor(); 
	    }

	   
	    System.out.print("New ISBN (current: " + book.getIsbn() + "): ");
	    String newIsbn = sc.nextLine();
	    if (newIsbn.isEmpty()) {
	        newIsbn = book.getIsbn(); 
	    }

	    
	    System.out.print("New Publication Year (current: " + book.getPublicationYear() + "): ");
	    String pubYearInput = sc.nextLine();
	    Integer newPublicationYear = pubYearInput.isEmpty() ? book.getPublicationYear() : Integer.parseInt(pubYearInput);

	    
	    System.out.print("New Genre (current: " + book.getGenre() + "): ");
	    String newGenre = sc.nextLine();
	    if (newGenre.isEmpty()) {
	        newGenre = book.getGenre(); 
	    }
	    
	    System.out.print("New Base Loan Fee (current: " + book.getBaseLoanFee() + "): ");
	    String baseLoanFeeInput = sc.nextLine();
	    Double newBaseLoanFee = baseLoanFeeInput.isEmpty() ? book.getBaseLoanFee() : Double.parseDouble(baseLoanFeeInput);

	   
	    System.out.print("New Book Type (current: " + book.getClass().getSimpleName() + "): ");
	    String newBookType = sc.nextLine();
	    if (newBookType.isEmpty()) {
	        newBookType = book.getClass().getSimpleName(); 
	    }

	    
	    persistenceHandler.updateBook(bookID, newTitle,newAuthor, newIsbn, newPublicationYear, newGenre,newBaseLoanFee,book.isLoanStatus(),newBookType);
	}

	public void searchBook(int attribute,String value)
	{
		System.out.println("\nSearch Results: ");
		 persistenceHandler.searchBook(attribute, value);
	}

	public void displayBook(String bookID) {
		persistenceHandler.displayBook(bookID);
	}
	
	public void displayAllBooks()
	{
		persistenceHandler.displayAllBooks();
	}
//	public void displayAllBooks() {
//	    
//	    if (books.isEmpty()) {
//	        System.out.println("There are currently no books in the system.");
//	    } else {
//	        System.out.println("Displaying all books in the system:\n");
//	        
//	       
//	        for (Map.Entry<String, Book> entry : books.entrySet()) {
//	            Book book = entry.getValue();
//	            
//	            
//	            System.out.println("Book ID: " + book.getBookID());
//	            System.out.println("Title: " + book.getTitle());
//	            System.out.println("Author: " + book.getAuthor());
//	            System.out.println("ISBN: " + book.getIsbn());
//	            System.out.println("Publication Year: " + book.getPublicationYear());
//	            System.out.println("Genre: " + book.getGenre());
//	            System.out.println("Loan Status: " + (book.isLoanStatus() ? "Loaned" : "Available"));
//	            System.out.println("Base Loan Fee: " + book.getBaseLoanFee());
//	            System.out.println("Book Type: " + book.getClass().getSimpleName()); // Assuming book type based on class
//	            System.out.println("-----------------------------");
//	        }
//	    }
//	}


	public void loanBook(String userID, String bookID, int days) {
		User user = users.get(userID);
		Book book = books.get(bookID);

		if (!persistenceHandler.userExists(userID) ) {
			System.out.println("User with ID " + userID + " not found.");
			return;
		}

		if (!persistenceHandler.bookExists(bookID)) {
			System.out.println("Book with ID " + bookID + " not found.");
			return;
		}

		// Checking if user can borrow more books based on their type
		if (user.canBorrow()) 
		{
			persistenceHandler.loanBook(userID, bookID, days);
			user.borrowBook((Book) book);
		} 
		else {
			System.out.println("User " + user.getName() + " cannot borrow more books.");
		}
	}


	public void returnBook(String userID, String bookID) {
		User user = users.get(userID);
		Book book = books.get(bookID);

		if (!persistenceHandler.userExists(userID) ) {
			System.out.println("User with ID " + userID + " not found.");
			return;
		}

		if (!persistenceHandler.bookExists(bookID)) {
			System.out.println("Book with ID " + bookID + " not found.");
			return;
		}
		
		persistenceHandler.returnBook(userID, bookID);
		user.removeBook(book);
		book.returnBook();

	}
	
	
}
