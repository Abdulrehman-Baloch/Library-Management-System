package assignment_2;

public class Novel extends Book
{
	private final static double FLAT_RATE = 5.0;
	
	public Novel(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee) 
	{
		super(bookID, title, author, isbn, publicationYear, genre, baseLoanFee);
	}
	public Novel(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee,boolean loanStatus) 
	{
		super(bookID, title, author, isbn, publicationYear, genre, baseLoanFee,loanStatus);
	}
	
	@Override
	public boolean isExtendable() 
	{
		return false; // novel can not be extended
	}
	@Override
	public double calculateLoanFee(int d) 
	{
		return FLAT_RATE;
	}
	

}
