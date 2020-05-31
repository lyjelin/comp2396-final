package task5;

import java.io.Serializable;

public class SPBMessage implements Serializable {
	
	String command;
	String username;
	String password;
	PhonebookEntry entry;
	String reply_msg;
	Integer recno;
		
	public SPBMessage(String cmd, String nm, String pwd, PhonebookEntry e) {
		command = cmd;
		username = nm;
		password = pwd;
		entry = e;
	}
	
	public SPBMessage(String cmd, String nm, String pwd, PhonebookEntry e, String r) {
		command = cmd;
		username = nm;
		password = pwd;
		entry = e;
		reply_msg = r;
	}
	
	public PhonebookEntry getPBEntry() { return entry; }
	public String getCommand() { return command; }
	
	public void setPBEntry(PhonebookEntry pe) { entry = pe; }
	public void setCommand(String c) { command = c; }
	public void setReplyMsg(String s) { reply_msg = s; }
	
	public String toString() {
		return ( command + ":" + username + ":" + reply_msg );
	}

}
