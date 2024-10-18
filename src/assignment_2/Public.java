package assignment_2;

public class Public extends User
{
	private static final int MAX_BORROW = 3;
	
	public Public(String userID,String name,String email,String phone,String address) 
	{
		super(userID, name, email, phone, address);
	}
	@Override
	public boolean canBorrow() 
	{
		return loanedBooks.size() < MAX_BORROW ;
	}

}
