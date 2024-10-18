package assignment_2;

import java.util.*;
import java.io.*;
public class FileHandler implements PersistenceHandler {
	
	private static final String USER_FILE = "data/users.txt";
	private static final String BOOK_FILE = "data/books.txt";
	private static final String LOAN_BOOK_FILE = "data/loanBook.txt";
	@Override
	public boolean userExists(String userID) {
		 try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                // Splitting the line into parts (assuming CSV format)
	                String[] userDetails = line.split(",");
	                if (userDetails[0].equals(userID)) {
	                    return true; // User found
	                }
	            }
	        } 
		 catch (FileNotFoundException e) {
	            System.out.println("User file not found. It may not have been created yet.");
	        } 
		 catch (IOException e) {
	            e.printStackTrace();
	        }
	        return false; // User not found
	}

	@Override
	 public void insertUser(User user) {
        String userString = user.getUserID() + "," + user.getName() + "," + user.getEmail() + "," +
                            user.getPhoneNumber() + "," + user.getAddress() + "," + user.getClass().getSimpleName();
        
        // Ensure the directory exists
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(userString);
            writer.newLine();
            System.out.println("User added to the file " + USER_FILE); // Print file name directly
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override

	public void deleteUser(String userId) {
	    
	      ArrayList<String> userList = new ArrayList<>();

	        // Step 1: Read the users from the file
	        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] userDetails = line.split(",");
	                if (!userDetails[0].equalsIgnoreCase(userId)) {
	                    userList.add(line); // Add only users that are not the one to be deleted
	                }
	            }
	        } 
	        catch (IOException e) {
	            System.out.println("Error reading file: " + USER_FILE);
	            e.printStackTrace();
	            return;  // Return early in case of read error
	        }

	        // Step 2: Write the updated list of users back to the file
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
	            for (String user : userList) {
	                writer.write(user);
	                writer.newLine();
	            }
	            System.out.println("User with ID: " + userId + " deleted successfully.");
	        } 
	        catch (IOException e) {
	            System.out.println("Error writing to file: " + USER_FILE);
	            e.printStackTrace();
	        }
	}

	@Override
	public void updateUser(String userID, String name, String email, String phone, String address, String type) {
	    
	        ArrayList<String> userList = new ArrayList<>();

	        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] userDetails = line.split(",");
	                if (userDetails[0].equals(userID)) {
	                    // Update user details
	                    String updatedUser = userID + "," + name + "," + email + "," + phone + "," + address + "," + type;
	                    userList.add(updatedUser); // Add updated user details
	                } 
	                else 
	                {
	                    userList.add(line); // Add unmodified users
	                }
	            }
	        } 
	        catch (IOException e) {
	            System.out.println("Error reading file: " + USER_FILE);
	            e.printStackTrace();
	            return;  // Early return in case of error
	        }

	        // Step 2: Write the updated list of users back to the file
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
	            for (String user : userList) {
	                writer.write(user);
	                writer.newLine();
	            }
	            System.out.println("User with ID: " + userID + " updated successfully.");
	        } 
	        catch (IOException e) {
	            System.out.println("Error writing to file: " + USER_FILE);
	            e.printStackTrace();
	        }
	}

	

	@Override
	public void searchUser(int attribute, String value) {
	    File userFile = new File(USER_FILE);

	    if (userFile.exists() && userFile.length() > 0) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
	            String line;
	            boolean userFound = false;
	            String normalizedValue = normalize(value);
	            System.out.println(normalizedValue);
	            
	            while ((line = reader.readLine()) != null) {
	                String[] userDetails = line.split(",");
	                
	                String normalizedID = normalize(userDetails[0]);
	                String normalizedName = normalize(userDetails[1]);
	                String normalizedType = normalize(userDetails[5]);
	                
	                switch (attribute) {
	                    case 1: // Search by UserID 
	                        if (normalizedID.equalsIgnoreCase(normalizedValue)) {
	                        	 displayUserDetails(userDetails);
	                             userFound = true;
	                        }
	                        break;
	                    case 2: // Search by Name (assuming Name is the second element)
	                        if (normalizedName.equalsIgnoreCase(normalizedValue)) {
	                            displayUserDetails(userDetails);
	                            userFound = true;
	                        }
	                        break;
	                    case 3: // Search by Email (assuming Email is the third element)
	                        if (normalizedType.equalsIgnoreCase(normalizedValue)) {
	                            displayUserDetails(userDetails);
	                            userFound = true;
	                        }
	                        break;
	                    default:
	                        System.out.println("Invalid attribute for search.");
	                        return;
	                }
	            }
	            if (!userFound) {
	                System.out.println("No users found matching the search criteria.");
	            }
	        } 
	        catch (IOException e) {
	            System.out.println("Error reading file: " + USER_FILE);
	            e.printStackTrace();
	        }
	    } 
	    else {
	        System.out.println("No users found in the file.");
	    }
	}
	private void displayUserDetails(String[] userDetails) 
	{
		System.out.println("-------------------------------");
	    System.out.println("UserID: " + userDetails[0]);
	    System.out.println("Name: " + userDetails[1]);
	    System.out.println("Email: " + userDetails[2]);
	    System.out.println("Phone: " + userDetails[3]);
	    System.out.println("Address: " + userDetails[4]);
	    System.out.println("User Type: " + userDetails[5]);
	    System.out.println("-------------------------------");
	}

	@Override
	public void displayUser(String userID) {
	    if (userExists(userID)) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] userDetails = line.split(",");
	                if (userDetails[0].equals(userID)) {
	                	displayUserDetails(userDetails);
	                    return;  // Exit after displaying the user
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading file: " + USER_FILE);
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("User with ID: " + userID + " does not exist.");
	    }
	}

	@Override
	public void displayAllUsers() {
	    File userFile = new File(USER_FILE);

	    if (userFile.exists() && userFile.length() > 0) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
	            String line;
	            System.out.println("\nDisplaying all users in the File: ");
	            while ((line = reader.readLine()) != null) {
	                String[] userDetails = line.split(",");
	                displayUserDetails(userDetails);
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading file: " + USER_FILE);
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("No users found in the file.");
	    }
	}


	@Override
	public User getUser(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> fetchAllUsers() {
	    ArrayList<User> userList = new ArrayList<>();
	    File userFile = new File(USER_FILE);

	    if (userFile.exists() && userFile.length() > 0) {
	        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
	            String line;
	            User user = null;
	            while ((line = br.readLine()) != null) {
	                String[] userDetails = line.split(",");

	                String userID = userDetails[0];
	                String name = userDetails[1];
	                String email = userDetails[2];
	                String phone = userDetails[3];
	                String address = userDetails[4];
	                String type = userDetails[5];
	                
	                switch (type) {
	                    case "Student":
	                        user = new Student(userID, name, email, phone, address);
	                        break;
	                    case "Faculty":
	                        user = new Faculty(userID, name, email, phone, address);
	                        break;
	                    case "Public":
	                        user = new Public(userID, name, email, phone, address);
	                        break;
	                    default:
	                        System.out.println("Unknown user type: " + type);
	                }

	                if (user != null) {
	                    userList.add(user);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading file: " + USER_FILE);
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("No users found in the file.");
	    }

	    return userList;
	}

		
	
	
	
	

	@Override
	public boolean bookExists(String bookID) {
		 try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                
	                String[] bookDetails = line.split(",");
	                if (bookDetails[0].equals(bookID)) {
	                    return true; // Book found
	                }
	                
	            }
	        } 
		 catch (FileNotFoundException e) {
	            System.out.println("Book file not found. It may not have been created yet.");
	        } 
		 catch (IOException e) {
	            e.printStackTrace();
	        }
	        return false; 
	}

	@Override
	public void insertBook(Book book) {
	    String bookString = book.getBookID() + "," + book.getTitle() + "," + book.getAuthor() + "," +
	                        book.getIsbn() + "," + book.getPublicationYear() + "," + book.getGenre() + "," + book.isLoanStatus() + "," +
	                        book.getBaseLoanFee() + "," + book.getClass().getSimpleName();
	    
	    File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs();
        }
	    
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE, true))) {
	        writer.write(bookString);
	        writer.newLine();
	        System.out.println("Book added to the file " + BOOK_FILE);
	    } catch (IOException e) {
	        System.out.println("Error writing book to the file: " + BOOK_FILE);
	        e.printStackTrace();
	    }
	}


	@Override
	public void deleteBook(String bookID) {
		ArrayList<String> bookList = new ArrayList<>();

        // Step 1: Read the users from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (!userDetails[0].equalsIgnoreCase(bookID)) {
                    bookList.add(line); // Add only users that are not the one to be deleted
                }
            }
        } 
        catch (IOException e) {
            System.out.println("Error reading file: " + BOOK_FILE);
            e.printStackTrace();
            return;  // Return early in case of read error
        }

        // Step 2: Write the updated list of users back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            for (String book : bookList) {
                writer.write(book);
                writer.newLine();
            }
            System.out.println("Book with ID " + bookID + " deleted successfully.");
        } 
        catch (IOException e) {
            System.out.println("Error writing to file: " + BOOK_FILE);
            e.printStackTrace();
        }	
	}

	@Override
	public void updateBook(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee,boolean loanStatus,String type) {

	    ArrayList<String> bookList = new ArrayList<>();

	    // Step 1: Read all books from the file
	    try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] bookDetails = line.split(",");
	            if (bookDetails[0].equals(bookID)) {
	                // Update book details
	            	String updatedBook = bookID + "," + title + "," + author + "," + isbn + "," + publicationYear + ","  + genre + "," + loanStatus + "," + baseLoanFee + "," + type;
	                bookList.add(updatedBook); // Adding updated book details
	            } 
	            else {
	                bookList.add(line); // Adding unmodified books
	            }
	        }
	    } 
	    catch (IOException e) {
	        System.out.println("Error reading file " + BOOK_FILE);
	        e.printStackTrace();
	        return;  
	    }

	    // Step 2: Write the updated list of books back to the file
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE))) {
	        for (String book : bookList) {
	            writer.write(book);
	            writer.newLine();
	        }
	        System.out.println("Book with ID " + bookID + " updated successfully.");
	    } 
	    catch (IOException e) {
	        System.out.println("Error writing to file " + BOOK_FILE);
	        e.printStackTrace();
	    }
	}

	
	@Override
	public void searchBook(int attribute, String value) {
	    File bookFile = new File(BOOK_FILE);

	    if (bookFile.exists() && bookFile.length() > 0) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
	            String line;
	            boolean bookFound = false;
	            String normalizedValue = normalize(value);
	            System.out.println(normalizedValue);

	            while ((line = reader.readLine()) != null) {
	                String[] bookDetails = line.split(",");
	                String normalizedID = normalize(bookDetails[0]);
	                String normalizedTitle = normalize(bookDetails[1]);
	                String normalizedAuthor = normalize(bookDetails[2]);
	                String normalizedISBN = normalize(bookDetails[3]);
	                String normalizedGenre = normalize(bookDetails[5]);
	                String normalizedType = normalize(bookDetails[8]);
	                

	                // Ensure that the line has enough fields
	                if (bookDetails.length < 6) {
	                    System.out.println("Malformed line, skipping: " + line);
	                    continue;
	                }

	                switch (attribute) {
	                    case 1: // Search by bookID
	                        if (normalizedID.equalsIgnoreCase(normalizedValue)) {
	                            displayBookDetails(bookDetails);
	                            bookFound = true;
	                        }
	                        break;
	                    case 2: // Search by Title
	                        if (normalizedTitle.equalsIgnoreCase(normalizedValue)) {
	                            displayBookDetails(bookDetails);
	                            bookFound = true;
	                        }
	                        break;
	                    case 3: // Search by Author
	                        if (normalizedAuthor.equalsIgnoreCase(normalizedValue)) {
	                            displayBookDetails(bookDetails);
	                            bookFound = true;
	                        }
	                        break;
	                    case 4: // Search by ISBN
	                        if (normalizedISBN.equalsIgnoreCase(normalizedValue)) {
	                            displayBookDetails(bookDetails);
	                            bookFound = true;
	                        }
	                        break;
	                    case 5: // Search by Genre
	                        if (normalizedGenre.equalsIgnoreCase(normalizedValue)) {
	                            displayBookDetails(bookDetails);
	                            bookFound = true;
	                        }
	                        break;
	                        
	                    case 6: // Search by type
	                    	if (normalizedType.equalsIgnoreCase(normalizedValue)) {
	                            displayBookDetails(bookDetails);
	                            bookFound = true;
	                        }
	                        break;
	                    	
	                    default:
	                        System.out.println("Invalid attribute for search.");
	                        return;
	                }
	            }

	            if (!bookFound) {
	                System.out.println("No books found matching the search criteria.");
	            }

	        } catch (IOException e) {
	            System.out.println("Error reading file: " + BOOK_FILE);
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("No books found in the file.");
	    }
	}


	private void displayBookDetails(String[] bookDetails)
	{
		System.out.println("-----------------------------");
        System.out.println("Book ID: " + bookDetails[0]);
        System.out.println("Title: " + bookDetails[1]);
        System.out.println("Author: " + bookDetails[2]);
        System.out.println("ISBN: " + bookDetails[3]);
        System.out.println("Publication Year: " + bookDetails[4]);
        System.out.println("Genre: " + bookDetails[5]);
        System.out.println("Loan Status: " + bookDetails[6]);
        System.out.println("Base Loan Fee: " + bookDetails[7]);
        System.out.println("Book Type: " + bookDetails[8]);
        System.out.println("-----------------------------");
	}
	@Override
	public void displayBook(String bookID) {
		if (bookExists(bookID)) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] bookDetails = line.split(",");
	                if (bookDetails[0].equals(bookID)) {
	                	displayBookDetails(bookDetails);
	                    return;  // Exit after displaying the user
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading file " + USER_FILE);
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Book with ID " + bookID + " does not exist.");
	    }
		
	}

	@Override
	public void displayAllBooks() {
		File bookFile = new File(BOOK_FILE);

	    if (bookFile.exists() && bookFile.length() > 0) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
	            String line;
	            System.out.println("---- Books in the File ----");
	            while ((line = reader.readLine()) != null) {
	                String[] bookDetails = line.split(",");
	                displayBookDetails(bookDetails);
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading file: " + BOOK_FILE);
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("No books found in the file.");
	    }
	}
	@Override
	public void loanBook(String userID, String bookID, int days) {
	    // Step 2: Check if the book exists and is available
	    ArrayList<Book> bookList = fetchAllBooks();
	    Book loanedBook = null;
	    
	    for (Book book : bookList) {
	        if (book.getBookID().equals(bookID)) {
	            loanedBook = book;
	            if (book.isLoanStatus()) {
	                System.out.println("Book with ID " + bookID + " is currently unavailable for loan.");
	                return;
	            }
	            break;
	        }
	    }

	    if (loanedBook == null) {
	        System.out.println("Book with ID " + bookID + " does not exist.");
	        return;
	    }

	    // Step 3: Record the loan transaction in loanBook.txt
	    String loanRecord = userID + "," + bookID + "," + days;
	    
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOAN_BOOK_FILE, true))) {
	        writer.write(loanRecord);
	        writer.newLine();
	        System.out.println("Book " + loanedBook.getTitle() +" loaned to User with ID: " + userID + " for " + days + " days.");
	    } catch (IOException e) {
	        System.out.println("Error writing to loan file.");
	        e.printStackTrace();
	        return;
	    }

	    // Step 4: Update the book's availability (loan status = True) in book.txt
	    loanedBook.setLoanStatus(true);  // Mark the book as loaned
	    updateBook(loanedBook.bookID,loanedBook.getTitle(),loanedBook.getAuthor(),loanedBook.getIsbn(),loanedBook.getPublicationYear(),loanedBook.getGenre(),loanedBook.getBaseLoanFee(),true,loanedBook.getClass().getSimpleName()); 
	}

	@Override
	public void returnBook(String userID, String bookID) {
		ArrayList<Book> bookList = fetchAllBooks();
	    Book loanedBook = null;
	    
	    for (Book book : bookList) {
	        if (book.getBookID().equals(bookID)) {
	            loanedBook = book;
	            break;
	        }
	    }
	    
	    File loanFile = new File(LOAN_BOOK_FILE);
	    ArrayList<String> updatedLoans = new ArrayList<>();
	    boolean loanFound = false;
	    
	    if (loanFile.exists()) {
	        try (BufferedReader reader = new BufferedReader(new FileReader(loanFile))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                String[] loanDetails = line.split(",");
	                
	                // Assuming loan file format: userID,bookID,loanDays
	                if (loanDetails[0].equals(userID) && loanDetails[1].equals(bookID)) {
	                    loanFound = true;  // Loan entry found DONT ADD INTO UPDATED LIST
	                } else {
	                    updatedLoans.add(line);  // Add other loan entries to the list
	                }
	            }
	        } 
	        catch (IOException e) {
	            System.out.println("Error reading loan file.");
	            e.printStackTrace();
	            return;
	        }
	    } 
	    else {
	        System.out.println("No loans found. Loan book file does not exist.");
	        return;
	    }
	    
	    if (loanFound) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(loanFile))) {
	            for (String loan : updatedLoans) {
	                writer.write(loan);
	                writer.newLine();
	            }
	            System.out.println("Book with ID " + bookID + " has been returned by User " + userID);

	            // Step 3: Update the loan status of the book to 'false'
	            // Fetch current book details for the update
	            // Assuming this method retrieves the book based on bookID
	            if (loanedBook != null) {
	                updateBook(loanedBook.getBookID(), loanedBook.getTitle(), loanedBook.getAuthor(), loanedBook.getIsbn(), 
	                		loanedBook.getPublicationYear(), loanedBook.getGenre(), loanedBook.getBaseLoanFee(), 
	                           false, loanedBook.getClass().getSimpleName());
	            } 
	            else {
	                System.out.println("Book with ID " + bookID + " not found for updating loan status.");
	            }

	        } catch (IOException e) {
	            System.out.println("Error writing to loan file.");
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Loan not found for userID " + userID + " and bookID " + bookID);
	    }
		
	}

	public ArrayList<Book> fetchAllBooks() {
	    ArrayList<Book> bookList = new ArrayList<>();
	    File bookFile = new File(BOOK_FILE);

	    if (bookFile.exists() && bookFile.length() > 0) {
	        try (BufferedReader br = new BufferedReader(new FileReader(BOOK_FILE))) {
	            String line;
	            Book book = null;
	            while ((line = br.readLine()) != null) {
	                String[] bookDetails = line.split(",");

	                String bookID = bookDetails[0];
	                String title = bookDetails[1];
	                String author = bookDetails[2];
	                String isbn = bookDetails[3];
	                int publicationYear = Integer.parseInt(bookDetails[4]);
	                String genre = bookDetails[5];
	                boolean available = Boolean.parseBoolean(bookDetails[6]); // For availability or status
	                double baseLoanFee = Double.parseDouble(bookDetails[7]);
	                String bookType = bookDetails[8];

	                switch (bookType) {
	                    case "Textbook":
	                        book = new Textbook(bookID, title, author, isbn, publicationYear, genre, baseLoanFee,available);
	                        break;
	                    case "Novel":
	                        book = new Novel(bookID, title, author, isbn, publicationYear, genre, baseLoanFee,available);
	                        break;
	                    case "ReferenceBook":
	                        book = new ReferenceBook(bookID, title, author, isbn, publicationYear, genre, baseLoanFee,available);
	                        break;
	                    default:
	                        System.out.println("Unknown book type: " + bookType);
	                        break;
	                }

	                if (book != null) {
	                    bookList.add(book);
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading file: " + BOOK_FILE);
	            e.printStackTrace();
	        } catch (NumberFormatException e) {
	            System.out.println("Error parsing number from file.");
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("No books found in the file.");
	    }

	    return bookList;
	}
	
	
	private String normalize(String input) {
	    String normalized = input.replaceAll("\\s+", ""); // Remove all whitespace
	    //System.out.println("Normalized value: " + normalized);
	    return normalized;
	}

}
