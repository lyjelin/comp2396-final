package task5;

import java.io.Serializable;

public class PhonebookEntry implements Serializable {
	String name;
	String phone_no;
	String address;
	boolean deleted;
	
	public PhonebookEntry()  { }

	public PhonebookEntry(String nm, String ph, String addr)
	{
		name = nm;
		phone_no = ph;
		address = addr;
		deleted = false;
	}

}
