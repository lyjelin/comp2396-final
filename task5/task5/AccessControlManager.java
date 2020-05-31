package task5;

/**
 * 
 * @author Lee Yoon Jeong
 *
 */
public class AccessControlManager {
	User[] userlist = new User[1000];
	int n = 0;
	
	//Task 5.1
	//Password for “admin” and “kpchow” are “admin99” are "admin99" and "default"
	public AccessControlManager() {
		userlist[0] = new User("admin","admin99",true,true);
		userlist[1] = new User("kpchow","default",true,true);
		userlist[2] = new User("guest","guest",true,false);
		userlist[3] = new User("3035603930", "default", true, true);
		
		n = 4;
	}

	// checkUser: check if user �nm� already exists in the userlist
	//	if nm is found, returns the index to the name in userlist
	//	otherwise, returns �1
	public int checkUser(String nm)
	{
		for (int i=0; i<n; ++i)
		{
			if ( nm.equals(userlist[i].username) ) return i;
		}
		return -1;
	}

	// verifyPassword: verify the password of user �nm� is �pwd�
	public boolean verifyPassword(String nm, String pwd)
	{
		int idx = checkUser(nm);
		if ( idx >= 0 )
		{
			if ( pwd.equals(userlist[idx].password)) return true;
		}
		return false;
	}

	// checkReadPerm: check if user �nm� has the read permission,
	//			 i.e. read_perm is true
	public boolean checkReadPerm(String nm)
	{
		int idx = checkUser(nm);
		if ( idx >= 0 )
		{
			return (userlist[idx].read_perm);
		}
		return false;
	}

	// checkWritePerm: check if user �nm� has the write permission,
	//			 i.e. write_perm is true
	public boolean checkWritePerm(String nm)
	{
		int idx = checkUser(nm);
		if ( idx >= 0 )
		{
			return (userlist[idx].write_perm);
		}
		return false;
	}

}

