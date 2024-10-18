package assignment_2;
import java.util.*;
public interface PersistenceHandler {
	
	boolean userExists(String userID);
	void insertUser(User user);
	void deleteUser(String userID);
	void updateUser(String userID,String name, String email, String phone, String address,String type);
	void displayUser(String userID);
	void searchUser(int  attribute,String value);
	void displayAllUsers();
	User getUser(String userID);
	ArrayList<User> fetchAllUsers();
	
	boolean bookExists(String bookID);
	void insertBook(Book book);
	void deleteBook(String bookID);
	void updateBook(String bookID,String title, String author, String isbn,int publication_year, String genre,double base_loan_fee,boolean loanStatus, String type);
	void searchBook(int attribute,String value);
	void displayBook(String bookID);
	void displayAllBooks();
	void loanBook(String userID,String bookID,int days);
	void returnBook(String userID,String bookID);
	ArrayList<Book> fetchAllBooks();
	
	
	
}
