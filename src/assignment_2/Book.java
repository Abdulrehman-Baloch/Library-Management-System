package assignment_2;
import java.util.*;
public abstract class Book implements Loan
{
	protected String bookID;
	protected String title;
	protected String author;
	protected String isbn;
	protected int publicationYear;
	protected String genre;
	protected boolean loanStatus;
	protected double baseLoanFee;
	
	public Book(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee) 
	{
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.baseLoanFee = baseLoanFee;
        this.loanStatus = false;
    }
	
	public Book(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee,boolean loanStatus) 
	{
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.baseLoanFee = baseLoanFee;
        this.loanStatus = loanStatus;
    }
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getBaseLoanFee() {
		return baseLoanFee;
	}

	public void setBaseLoanFee(double baseLoanFee) {
		this.baseLoanFee = baseLoanFee;
	}

	@Override
	public abstract boolean isExtendable();
	@Override
	public abstract double calculateLoanFee(int d);


	@Override
	public void loanBook() 
	{
		this.loanStatus = true;
	}
	
	@Override
	public void returnBook() 
	{
		this.loanStatus = false;
	}

	public String getBookID() 
	{
		return bookID;
	}

	public void setBookID(String bookID) 
	{
		this.bookID = bookID;
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public boolean isLoanStatus() 
	{
		return loanStatus;
	}

	public void setLoanStatus(boolean loanStatus) 
	{
		this.loanStatus = loanStatus;
	}
	
}
