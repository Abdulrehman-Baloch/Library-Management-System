package assignment_2;

public interface Loan 
{
	public void loanBook();
	public double calculateLoanFee(int d);
	public void returnBook();
	public boolean isExtendable();
	
}
