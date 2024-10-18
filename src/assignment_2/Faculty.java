package assignment_2;

public class Faculty extends User 
{
	private static final int MAX_BORROW = 10;
	
	public Faculty(String userID,String name,String email,String phone,String address)
	{
		super(userID, name, email, phone, address);
	}

	@Override
	public boolean canBorrow() 
	{
		return loanedBooks.size() < MAX_BORROW;
	}
}
