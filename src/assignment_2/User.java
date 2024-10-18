package assignment_2;

import java.util.*;

abstract class User {
	protected String userID;
	protected String name;
	protected String email;
	protected ArrayList<Book> loanedBooks;
	protected double totalLoanFees;
	protected String phone;
	protected String address;

	public User(String userID, String name, String email, String phone, String address) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.loanedBooks = new ArrayList<>();
		this.totalLoanFees = 0;
	}

	public abstract boolean canBorrow();

	public void borrowBook(Book book) {
		loanedBooks.add(book);
	}

	public void removeBook(Book book) {
		loanedBooks.remove(book);
	}

	public ArrayList<Book> getLoanedBooks() {
		return loanedBooks;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTotalLoanFees() {
		return totalLoanFees;
	}

	public void setTotalLoanFees(float totalLoanFees) {
		this.totalLoanFees = totalLoanFees;
	}

	public void setLoanedBooks(ArrayList<Book> loanedBooks) {
		this.loanedBooks = loanedBooks;
	}

	public void addLoanFee(double fee) {
		totalLoanFees += fee;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phone;
	}

	public String getAddress() {
		return address;
	}
	
	public boolean hasLoanedBook()
	{
		if(loanedBooks.isEmpty())
			return false;
		else
			return true;
	}

}
