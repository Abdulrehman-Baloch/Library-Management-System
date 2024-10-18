package assignment_2;

public class Textbook extends Book 
{
    private final double DAILY_FEE = 2.0;
    private boolean extendable = false;

    public Textbook(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee) 
    {
        super(bookID, title, author, isbn, publicationYear, genre, baseLoanFee);
    }
    
    public Textbook(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee,boolean loanStatus) 
    {
        super(bookID, title, author, isbn, publicationYear, genre, baseLoanFee, loanStatus);
    }

	@Override
	public boolean isExtendable() {
		return !extendable;
	}

	@Override
	public double calculateLoanFee(int d) 
	{
		return baseLoanFee + d * DAILY_FEE;
	}
	


}
