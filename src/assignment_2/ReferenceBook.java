package assignment_2;

public class ReferenceBook extends Book 
{

	public ReferenceBook(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee) 
	{
		super(bookID, title, author, isbn, publicationYear, genre, baseLoanFee);
	}
	
	public ReferenceBook(String bookID, String title, String author, String isbn, int publicationYear, String genre, double baseLoanFee, boolean loanStatus) 
	{
		super(bookID, title, author, isbn, publicationYear, genre, baseLoanFee, loanStatus);
	}

	@Override
	public boolean isExtendable() 
	{
		return false; // reference book can not loaned hence can not be extended
	}

	@Override
	public double calculateLoanFee(int d) {
		System.out.println("Reference books cannot be loaned");
		return 0;
	}

}
