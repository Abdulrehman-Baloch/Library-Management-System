package assignment_2;
import java.util.*;
import java.sql.*;
public class DatabaseHandler implements PersistenceHandler {
	
	private final String url = "jdbc:postgresql://localhost:5432/LMS_DB"; 
    private final String user = "postgres"; 
    private final String password = "baloch222"; 
    private Connection connection;
    
    public DatabaseHandler() {
        connect();
    }
    
    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the LMS database.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
    
    @Override
	public boolean userExists(String userID) {
		String query = "SELECT 1 FROM users WHERE user_id = ?";
		try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, userID);
            ResultSet rs = stmnt.executeQuery();
            return rs.next();
        } 
		catch (SQLException e) {
            System.out.println("Failed to retrieve user: " + e.getMessage());
            return false;
        }
	}
    
	@Override
	public void insertUser(User user) {
		String query = "INSERT INTO users (user_id, name, email, phone, address, user_type) VALUES (?, ?, ?, ?, ?, ?)";
		 try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	            stmnt.setString(1, user.getUserID());
	            stmnt.setString(2, user.getName());
	            stmnt.setString(3, user.getEmail());
	            stmnt.setString(4, user.getPhoneNumber());
	            stmnt.setString(5, user.getAddress());
	            //stmnt.setDouble(6, user.getTotalLoanFees());
	            stmnt.setString(6, user.getClass().getSimpleName()); // Assuming user_type based on class name

	            int affectedRows = stmnt.executeUpdate();
	            if(affectedRows > 0){
	            	System.out.println("User " + user.getName() + " with ID " + user.getUserID() + " added to the database.");
	            }
	            else{
	            	System.out.println("User can not be added due to some error");
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed to insert user: " + e.getMessage());
	        }
	}

	@Override
	public void deleteUser(String userID) {
		String query = "DELETE FROM users WHERE user_id = ?";
		 try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	            stmnt.setString(1, userID);
	            int affectedRows = stmnt.executeUpdate();
	            if(affectedRows > 0) {
	            	System.out.println("User with ID " + userID + " deleted from the database.");
	            }
	            else {
	            	System.out.println("User can not be deleted due to some error");
	            }
	           
	        } 
		 catch (SQLException e) {
	            System.out.println("Failed to delete user: " + e.getMessage());
	        }
	}
	


	@Override
	public void updateUser(String userID, String name, String email, String phone, String address,String type) {
		 String query = "UPDATE users SET name = ?, email = ?, phone = ?, address = ?,user_type = ? WHERE user_id = ?";

	        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	           
	            stmnt.setString(1, name);
	            stmnt.setString(2, email);
	            stmnt.setString(3, phone);
	            stmnt.setString(4, address);
	            stmnt.setString(5, type);
	            stmnt.setString(6, userID);

	            int rowsUpdated = stmnt.executeUpdate(); // Execute the update
	            if (rowsUpdated > 0) {
	                System.out.println("User with ID " + userID + " updated successfully.\n");
	            } 
	            
	        } catch (SQLException e) {
	            System.out.println("Failed to update user: " + e.getMessage());
	        }
	}
	
	

	
	@Override
	public void searchUser(int attribute, String value) {
	    String query = null;
	    
	    // Define the query based on the attribute chosen
	    switch (attribute) {
	        case 1: // Search by User ID
	            query = "SELECT * FROM users WHERE LOWER(user_id) = LOWER(?)";
	            break;
	        case 2: // Search by Name
	            query = "SELECT * FROM users WHERE LOWER(name) LIKE ?";
	            value = "%" + value.toLowerCase() + "%"; // Use LIKE for partial match
	            break;
	        case 3: // Search by Type
	            query = "SELECT * FROM users WHERE lower(user_type) = LOWER(?)";
	            break;
	        default:
	            System.out.println("Invalid attribute for search.");
	            return;
	    }
	    
	    // Execute the query and display the results
	    try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	        stmnt.setString(1, normalize(value));
	        ResultSet rs = stmnt.executeQuery();
	        
	        if (!rs.isBeforeFirst()) {
	            System.out.println("No users found matching the search criteria.");
	        } 
	        else {
	            while (rs.next()) {
	                String userID = rs.getString("user_id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String phone = rs.getString("phone");
	                String address = rs.getString("address");
	                String userType = rs.getString("user_type");
	                
	                System.out.println("----------------------------");
	                System.out.println("User ID: " + userID);
	                System.out.println("Name: " + name);
	                System.out.println("Email: " + email);
	                System.out.println("Phone: " + phone);
	                System.out.println("Address: " + address);
	                System.out.println("Type: " + userType);
	                System.out.println("----------------------------");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to search for user: " + e.getMessage());
	    }
	}


	@Override
	public void displayUser(String userID) {
		 String query = "SELECT * FROM users WHERE user_id = ?";
		    
		    try (PreparedStatement stmnt = connection.prepareStatement(query)) {
		        stmnt.setString(1, userID);  // Set the user ID in the query

		        ResultSet rs = stmnt.executeQuery();  // Execute the query

		        if (rs.next()) {
		            // Extract user details from the result set
		            String name = rs.getString("name");
		            String email = rs.getString("email");
		            String phone = rs.getString("phone");
		            String address = rs.getString("address");
		            String userType = rs.getString("user_type");
		            
		            System.out.println("-----------------------------");
		            System.out.println("User ID: " + userID);
		            System.out.println("Name: " + name);
		            System.out.println("Email: " + email);
		            System.out.println("Phone: " + phone);
		            System.out.println("Address: " + address);
		            System.out.println("User Type: " + userType);
		            System.out.println("-----------------------------");
		        }
		        else {
		        	System.out.println("User with ID " + userID + " not found in the database.");
		        }
		    } catch (SQLException e) {
		        System.out.println("Failed to retrieve user: " + e.getMessage());
		    }
	}
	
	

	@Override
	public void displayAllUsers() {
	    String query = "SELECT * FROM users"; 

	    try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	        ResultSet rs = stmnt.executeQuery();

	        if (!rs.isBeforeFirst()) {
	            
	            System.out.println("There are currently no users in the database");
	        } 
	        else {
	            
	            while (rs.next()) {
	                // Extracting user details from the result set
	                String userID = rs.getString("user_id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String phone = rs.getString("phone");
	                String address = rs.getString("address");
	                String userType = rs.getString("user_type");
	                
	                System.out.println("-----------------------------");
	                System.out.println("User ID: " + userID);
	                System.out.println("Name: " + name);
	                System.out.println("Email: " + email);
	                System.out.println("Phone: " + phone);
	                System.out.println("Address: " + address);
	                System.out.println("User Type: " + userType);
	                System.out.println("-----------------------------");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to fetch users from database: " + e.getMessage());
	    }
	}
	
	@Override
	public User getUser(String userID) {
	    String query = "SELECT * FROM users WHERE user_id = ?";
	    User user = null;  // To store the retrieved user
	    try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	        stmnt.setString(1, userID);  // Set the user ID in the query

	        ResultSet rs = stmnt.executeQuery();  // Execute the query

	        if (rs.next()) {
	            // Extract user details from the result set
	            String name = rs.getString("name");
	            String email = rs.getString("email");
	            String phone = rs.getString("phone");
	            String address = rs.getString("address");
	            String userType = rs.getString("user_type");
	            
	            // Create the appropriate User object based on the userType
	            if (userType.equals("Student")) {
	                user = new Student(userID, name, email, phone, address);
	            } else if (userType.equals("Faculty")) {
	                user = new Faculty(userID, name, email, phone, address);
	            } else if (userType.equals("Public")) {
	                user = new Public(userID, name, email, phone, address);
	            }
	        }
	    } 
	    catch (SQLException e) {
	        System.out.println("Failed to retrieve user: " + e.getMessage());
	    }

	    return user;  // Return the retrieved user (or null if not found)
	}

	public ArrayList<User> fetchAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM users"; 
        
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            ResultSet rs = stmnt.executeQuery(); 
            
            if (!rs.isBeforeFirst()) {
	            System.out.println("There are currently no users in the database");
	        } 
            else
            {
	            while (rs.next()) {
	                
	                String userID = rs.getString("user_id");
	                if (userID == null || userID.isEmpty()) {
	                    System.out.println("Invalid user ID found, skipping entry");
	                    continue;
	                }
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String phone = rs.getString("phone");
	                String address = rs.getString("address");
	                String userType = rs.getString("user_type");
	
	                // Create the appropriate User object based on the userType
	                User user = null;
	                if (userType.equals("Student")) {
	                    user = new Student(userID, name, email, phone, address);
	                } 
	                else if (userType.equals("Faculty")) {
	                    user = new Faculty(userID, name, email, phone, address);
	                }
	                else if (userType.equals("Public")) {
	                    user = new Public(userID, name, email, phone, address);
	                }
	                // Add the user to the list
	                if (user != null) {
	                    userList.add(user);
	                }
                }
            }
        } 
        catch (SQLException e) {
            System.out.println("Failed to fetch users from database: " + e.getMessage());
        }

        return userList;
    }
	
	
	
	
	
	@Override
	public boolean bookExists(String bookID)
	{
		String query = "SELECT 1 FROM BOOK WHERE book_id = ?";
		try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, bookID);
            ResultSet rs = stmnt.executeQuery();
            return rs.next();
        } 
		catch (SQLException e) {
            System.out.println("Failed to retrieve book" + e.getMessage());
            return false;
        }
	}
	
	@Override
	public void insertBook(Book book) {
		String query = "INSERT INTO BOOK (book_id, title, author, isbn, publication_year, genre, loan_status, base_loan_fee, book_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		 try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	            stmnt.setString(1, book.getBookID());
	            stmnt.setString(2, book.getTitle());
	            stmnt.setString(3, book.getAuthor());
	            stmnt.setString(4, book.getIsbn());
	            stmnt.setInt(5, book.getPublicationYear());
	            stmnt.setString(6, book.getGenre());
	            stmnt.setBoolean(7, false); 
	            stmnt.setDouble(8, book.getBaseLoanFee());
	            stmnt.setString(9, book.getClass().getSimpleName());
	           
	            int affectedRows = stmnt.executeUpdate();
	            if(affectedRows > 0){
	            	System.out.println("Book " + book.getTitle() + " with ID " + book.getBookID() + " added to the database.");
	            }
	            else{
	            	System.out.println("Book can not be added due to some error");
	            }
	        } 
		 catch (SQLException e) {
	            System.out.println("Failed to insert book: " + e.getMessage());
		 }
	}
	
	@Override
	public void deleteBook(String bookID) {
		String query = "DELETE FROM BOOK WHERE book_id = ?";
		 try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	            stmnt.setString(1, bookID);
	            int affectedRows = stmnt.executeUpdate();
	            if(affectedRows > 0) {
	            	System.out.println("Book with ID " + bookID + " deleted from the database.");
	            }
	            else {
	            	System.out.println("Book can not be deleted due to some error");
	            }
	        } 
		 catch (SQLException e) {
	            System.out.println("Failed to delete user: " + e.getMessage());
	        }
		
	}

	@Override
	public void updateBook(String bookID, String title, String author, String isbn, int publication_year, String genre,
			double base_loan_fee, boolean loanStatus,String type) {
		String query = "UPDATE BOOK SET title = ?, author = ?, isbn = ?, publication_year = ?,genre = ?, base_loan_fee = ?, book_type = ?  WHERE book_id = ?";

        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
           
            stmnt.setString(1, title);
            stmnt.setString(2, author);
            stmnt.setString(3, isbn);
            stmnt.setInt(4, publication_year);
            stmnt.setString(5, genre);
            stmnt.setDouble(6, base_loan_fee);
            stmnt.setString(7, type);
            stmnt.setString(8, bookID);

            int rowsUpdated = stmnt.executeUpdate(); // Execute the update
            if (rowsUpdated > 0) {
                System.out.println("Book with ID " + bookID + " updated successfully.\n");
            } 
            
        } catch (SQLException e) {
            System.out.println("Failed to update book: " + e.getMessage());
        }
	}
	
	

	@Override
	public void searchBook(int attribute, String value) {
	    
	    String query = null;

	    
	    switch (attribute) {
	        case 1: // Search by BookID
	            query = "SELECT * FROM BOOK WHERE LOWER(book_id) = LOWER(?)";
	            break;
	            
	        case 2: // Search by Title
	            query = "SELECT * FROM BOOK WHERE LOWER(title) = LOWER(?)" ;  // Use LIKE for partial match, case-insensitive
	            break;
	        case 3: // Search by Author
	            query = "SELECT * FROM BOOK WHERE LOWER(author) = LOWER(?)";
	           
	            break;
	        case 4: // Search by ISBN
	            query = "SELECT * FROM BOOK WHERE isbn = ?";
	            break;
	        case 5: // Search by Genre
	            query = "SELECT * FROM BOOK WHERE LOWER(genre) = LOWER(?)";
	            break;
	        case 6: // Search by Type
	            query = "SELECT * FROM BOOK WHERE LOWER(book_type) = LOWER(?)";
	            break;
	        default:
	            System.out.println("Invalid attribute for search.");
	            return;
	    }

	    // Execute the query and display the results
	    try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	        stmnt.setString(1, value);  // Set the value with normalized spaces
	        ResultSet rs = stmnt.executeQuery();

	        if (!rs.isBeforeFirst()) {
	            System.out.println("No books found matching the search criteria.");
	        } else {
	            while (rs.next()) {
	                String bookID = rs.getString("book_id");
	                String title = rs.getString("title");
	                String author = rs.getString("author");
	                String isbn = rs.getString("isbn");
	                int publicationYear = rs.getInt("publication_year");
	                String genre = rs.getString("genre");
	                boolean loanStatus = rs.getBoolean("loan_status");
	                double baseLoanFee = rs.getDouble("base_loan_fee");
	                String bookType = rs.getString("book_type");

	                System.out.println("----------------------------");
	                System.out.println("Book ID: " + bookID);
	                System.out.println("Title: " + title);
	                System.out.println("Author: " + author);
	                System.out.println("ISBN: " + isbn);
	                System.out.println("Publication Year: " + publicationYear);
	                System.out.println("Genre: " + genre);
	                System.out.println("Loan Status: " + (loanStatus ? "Loaned" : "Available"));
	                System.out.println("Base Loan Fee: $" + baseLoanFee);
	                System.out.println("Book Type: " + bookType);
	                System.out.println("----------------------------");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to search for book: " + e.getMessage());
	    }
	}




	@Override
	public void displayBook(String bookID) {
	    String query = "SELECT * FROM BOOK WHERE book_id = ?";  

	    try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	        stmnt.setString(1, bookID);  

	        ResultSet rs = stmnt.executeQuery();  

	        if (rs.next()) {
	            
	            String title = rs.getString("title");
	            String author = rs.getString("author");
	            String isbn = rs.getString("isbn");
	            int publicationYear = rs.getInt("publication_year");
	            String genre = rs.getString("genre");
	            boolean loanStatus = rs.getBoolean("loan_status");
	            double baseLoanFee = rs.getDouble("base_loan_fee");
	            String bookType = rs.getString("book_type");

	            System.out.println("-----------------------------");
	            System.out.println("Book ID: " + bookID);
	            System.out.println("Title: " + title);
	            System.out.println("Author: " + author);
	            System.out.println("ISBN: " + isbn);
	            System.out.println("Publication Year: " + publicationYear);
	            System.out.println("Genre: " + genre);
	            System.out.println("Loan Status: " + (loanStatus ? "Loaned" : "Available"));
	            System.out.println("Base Loan Fee: $" + baseLoanFee);
	            System.out.println("Book Type: " + bookType);
	            System.out.println("-----------------------------");
	        } 
	        else {
	            System.out.println("Book with ID " + bookID + " not found in the database.");
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to retrieve book: " + e.getMessage());
	    }
	}


	@Override
	public void displayAllBooks() {
		String query = "SELECT * FROM BOOK"; 

	    try (PreparedStatement stmnt = connection.prepareStatement(query)) {
	        ResultSet rs = stmnt.executeQuery();

	        if (!rs.isBeforeFirst()) {
	            // If no books are found
	            System.out.println("There are currently no books in the database.");
	        } else {
	            // Loop through each book record
	            while (rs.next()) {
	                // Extract book details from the result set
	                String bookID = rs.getString("book_id");
	                String title = rs.getString("title");
	                String author = rs.getString("author");
	                String isbn = rs.getString("isbn");
	                int publicationYear = rs.getInt("publication_year");
	                String genre = rs.getString("genre");
	                boolean loanStatus = rs.getBoolean("loan_status");
	                double baseLoanFee = rs.getDouble("base_loan_fee");
	                String bookType = rs.getString("book_type");

	                // Display book information
	                System.out.println("-----------------------------");
	                System.out.println("Book ID: " + bookID);
	                System.out.println("Title: " + title);
	                System.out.println("Author: " + author);
	                System.out.println("ISBN: " + isbn);
	                System.out.println("Publication Year: " + publicationYear);
	                System.out.println("Genre: " + genre);
	                System.out.println("Loan Status: " + (loanStatus ? "Loaned" : "Available"));
	                System.out.println("Base Loan Fee: " + baseLoanFee);
	                System.out.println("Book Type: " + bookType);
	                System.out.println("-----------------------------");
	                 
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to fetch books from the database: " + e.getMessage());
	    }
		
	}
	
	

	@Override
	public void loanBook(String userID, String bookID, int days) {
	    
	    String checkLoanStatusQuery = "SELECT loan_status FROM BOOK WHERE book_id = ?";
	    String loanBookQuery = "INSERT INTO LoanedBook (user_id, book_id, duration) VALUES (?, ?, ?)";
	    String updateLoanStatusQuery = "UPDATE BOOK SET loan_status = ? WHERE book_id = ?";

	    try (PreparedStatement checkStmnt = connection.prepareStatement(checkLoanStatusQuery)) {
	        checkStmnt.setString(1, bookID);
	        ResultSet rs = checkStmnt.executeQuery();

	        if (rs.next()) {
	            boolean isLoanedOut = rs.getBoolean("loan_status");
	            if (isLoanedOut) {
	                System.out.println("Book with ID " + bookID + " is already loaned out and cannot be loaned again.");
	                return;
	            }
	        }
	        // If the book is not loaned out, proceed with loaning it
	        try (PreparedStatement loanStmnt = connection.prepareStatement(loanBookQuery);
	             PreparedStatement updateStmnt = connection.prepareStatement(updateLoanStatusQuery)) {

	            // Insert loan record
	            loanStmnt.setString(1, userID);
	            loanStmnt.setString(2, bookID);
	            loanStmnt.setInt(3, days);

	            int affectedRows = loanStmnt.executeUpdate();
	            if (affectedRows > 0) {
	                // Update the loan status of the book to true (loaned out)
	                updateStmnt.setBoolean(1, true);
	                updateStmnt.setString(2, bookID);
	                updateStmnt.executeUpdate();

	                System.out.println("User with ID " + userID + " has successfully loaned out book with ID " + bookID);
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("Failed to loan book: " + e.getMessage());
	    }
	}
	
	@Override
	public void returnBook(String userID, String bookID) {
	  
	    String deleteLoanQuery = "DELETE FROM LoanedBook WHERE user_id = ? AND book_id = ?";
	    
	    
	    String updateLoanStatusQuery = "UPDATE BOOK SET loan_status = ? WHERE book_id = ?";

	    try (PreparedStatement deleteStmnt = connection.prepareStatement(deleteLoanQuery);
	         PreparedStatement updateStmnt = connection.prepareStatement(updateLoanStatusQuery)) {

	        // Set parameters for deleting loan record
	        deleteStmnt.setString(1, userID);
	        deleteStmnt.setString(2, bookID);

	        
	        int affectedRows = deleteStmnt.executeUpdate();
	        
	        if (affectedRows > 0) {
	            // Setting parameters to update loan status of the book
	            updateStmnt.setBoolean(1, false);
	            updateStmnt.setString(2, bookID);
	            
	            updateStmnt.executeUpdate();

	            System.out.println("Book with ID " + bookID + " has been returned successfully by user with ID " + userID);
	        } 
	        else {
	            System.out.println("No loan record found for userID " + userID + " and bookID " + bookID);
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to return book: " + e.getMessage());
	    }
	}



	@Override
	public ArrayList<Book> fetchAllBooks() {
	    String query = "SELECT * FROM BOOK";  // SQL query to fetch all books
	    ArrayList<Book> bookList = new ArrayList<>();  // List to store the fetched books

	    try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            ResultSet rs = stmnt.executeQuery(); 
            
            if (!rs.isBeforeFirst()) {
	            System.out.println("There are currently no books in the database");
            }
            else
            {
            	while (rs.next()) {
		            // Extract book details from the result set
		            String bookID = rs.getString("book_id");
		            String title = rs.getString("title");
		            String author = rs.getString("author");
		            String isbn = rs.getString("isbn");
		            int publicationYear = rs.getInt("publication_year");
		            String genre = rs.getString("genre");
		            double baseLoanFee = rs.getDouble("base_loan_fee");
		            String bookType = rs.getString("book_type");
	
		            Book book = null;
		            
		            if (bookType.equals("Novel")) {
	                    book = new Novel(bookID, title, author, isbn, publicationYear,genre,baseLoanFee);
	                } 
	                else if (bookType.equals("Textbook")) {
	                    book = new Textbook(bookID, title, author, isbn, publicationYear,genre,baseLoanFee);
	                }
	                else if (bookType.equals("ReferenceBook")) {
	                    book = new ReferenceBook(bookID, title, author, isbn, publicationYear,genre,baseLoanFee);
	                }
	                // Add the user to the list
	                if (book != null) {
	                    bookList.add(book);
	                }
		        }
		    } 
	    }
	    catch (SQLException e) {
	        System.out.println("Failed to fetch books: " + e.getMessage());
	    }
	    return bookList;  
	}

	
	private String normalize(String input) {
	    String normalized = input.replaceAll("\\s+", ""); // Remove all whitespace
	    //System.out.println("Normalized value: " + normalized);
	    return normalized;
	}
	
	
	
}
