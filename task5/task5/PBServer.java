package task5;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PBServer {

	AccessControlManager ac_store;
	Phonebook pb;
	boolean autosave = false;

	public static void main(String[] args) {

		PBServer cs = new PBServer();
		cs.go();
	}

	public void go() {		

		ac_store = new AccessControlManager();
		pb = new Phonebook(autosave);
		
		Thread saveInfo = new Thread(new AutoSaveHandler(pb.autosave));
		saveInfo.start();
		
		try {
			System.out.println("Server listening");
			ServerSocket ss = new ServerSocket(5000);

			while (true) {
				Socket s = ss.accept();

				System.out.println("Accepting connection");
				Thread c = new Thread(new ClientHandler(s));
				c.start();

			}
		} catch (Exception e) { e.printStackTrace(); }

	}

	//Task 5.6
	class AutoSaveHandler implements Runnable {
		Boolean autosaver;

		AutoSaveHandler (Boolean autosaver) {
			this.autosaver = autosaver;
		}

		//Task 5.7
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!autosaver) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				pb.updatePBFile();
				System.out.println("Autosaved");
			}

		}
	}

	//Task 5.6
	class ClientHandler implements Runnable {

		OutputStream os;
		ObjectOutputStream oos;
		InputStream is;
		ObjectInputStream ois;

		public ClientHandler(Socket s) {		
			try {
				os = s.getOutputStream();
				oos = new ObjectOutputStream(os);
				is = s.getInputStream();
				ois = new ObjectInputStream(is);
			} catch (IOException e) {
				System.out.println("ClientHandler error");
				e.printStackTrace();
			}
		}


		public void ch_go() {	

			boolean more_msg = true;

			while ( more_msg ) {

				try {

					SPBMessage m = (SPBMessage) ois.readObject();
					System.out.println("Received msg:" + m);
					System.out.println("Recieved command: " + m.command);

					if ( m.command.equals("LOGIN") ) {
						if ( ac_store.verifyPassword(m.username, m.password) ) {
							m.reply_msg = "LOGIN success";
						}
						else 
							m.reply_msg = "LOGIN fail";

						oos.writeObject(m);
						continue;
					}

					//Integer entry_recno = m.recno;

					if ( ! ac_store.verifyPassword(m.username,m.password) ){
						m.reply_msg = "Incorrect password";
					}
					//Task 5.4
					else if ( m.command.equals("GETBYNAME") ){
						String entry_name = m.entry.name;
						Integer entry_recno = m.recno;
						System.out.println("GETBYNAME: " + entry_name);
						m.entry = null;
						if ( ac_store.checkReadPerm(m.username) ){
							boolean entry_exist = false;
							entry_name = entry_name.toLowerCase();

							for (int i = 0; i < pb.entry_count; i++) {
								String nameX = pb.getEntry(i).name;
								nameX = nameX.toLowerCase();

								if (nameX.indexOf(entry_name) >= 0 && entry_exist == false) {
									m.entry = pb.getEntry(i);
									m.recno = i;
									m.reply_msg = "GETBYNAME success: " + i + ",";
									entry_exist = true;
								}

								else if (nameX.indexOf(entry_name) >= 0 && entry_exist == true) {
									m.reply_msg += " " + i + ",";
								}
							}

							if (entry_exist == false)
								m.reply_msg = "Phonebook entry does not exist";
							else
								m.reply_msg = m.reply_msg.substring(0, m.reply_msg.length() - 1);
						}
						else {
							m.reply_msg = "Invalid permission";
						}
					} //Task 5.3
					else if ( m.command.equals("GETBYRECNO")){
						Integer entry_recno = m.recno;
						System.out.println("GETBYRECNO: " + entry_recno);
						m.entry = null;
						if ( ac_store.checkReadPerm(m.username) ){
							int i = entry_recno;
							if ( i >=0 && i < pb.entry_count){
								m.entry = pb.getEntry(entry_recno);
								m.recno = i;
								m.reply_msg = "GETBYRECNO success";
							}
							else
								m.reply_msg = "Phonebook entry does not exist";
						} else {
							m.reply_msg = "Invalid permission";
						} 
					}			
					//Task 5.3
					else if ( m.command.equals("PREV") ) {
						Integer entry_recno = m.recno;
						// code to view previous entry
						int recno_prey = entry_recno;
						System.out.println("PREV: " + recno_prey);
						m.entry = null;
						if ( ac_store.checkReadPerm(m.username) ){
							int i = recno_prey;
							if ( (i >=0) && (i < pb.entry_count)){
								m.entry = pb.getEntry(recno_prey);
								m.recno = i;
								m.reply_msg = "PREV success";
							}

							else
								m.reply_msg = "Phonebook entry does not exist";

						} 
						else {
							m.reply_msg = "Invalid permission";
						} 
					}
					//Task 5.5
					else if ( m.command.equals("NEXT")){
						Integer entry_recno = m.recno;
						// code to view next entry
						int recno_next = entry_recno;
						System.out.println("NEXT: " + recno_next);

						m.entry = null;
						if ( ac_store.checkReadPerm(m.username) ){
							int i = recno_next;
							if ( (i >=0) && (i < pb.entry_count) && i!=-1) {
								m.entry = pb.getEntry(recno_next);
								m.recno = i;
								m.reply_msg = "NEXT success";
							}
							else
								m.reply_msg = "Phonebook entry does not exist";
						} 
						else {
							m.reply_msg = "Invalid permission";
						} 
					}
					else  if ( m.command.equals("ADD") ){
						if ( ac_store.checkWritePerm(m.username) ){
							int i = pb.entryExist(m.entry.name);
							if ( i < 0 ) {
								pb.addEntry(m.entry.name, m.entry);
								int recno = pb.entryExist(m.entry.name);
								m.reply_msg = "ADD success";
								m.recno = recno;
							}
							else
								m.reply_msg = "Phonebook entry exists";
						} else {
							m.reply_msg = "Invalid permission";
						}
					}
					//Task 5.3
					else if ( m.command.equals("UPDATE") ){
						// code to update the Phonebook
						if ( ac_store.checkWritePerm(m.username) ){
							int i = pb.entryExist(m.entry.name);
							if ( i >= 0 ) {
								pb.updateFile(m.entry.name, m.entry);
								int recno = pb.entryExist(m.entry.name);
								m.reply_msg = "UPDATE success";
								m.recno = recno;
							}
							else
								m.reply_msg = "Phonebook entry does not exist";
						} else {
							m.reply_msg = "Invalid permission";
						}
					}
					//Task 5.3
					else if ( m.command.equals("DELETE") ){
						
						// code to delete the Phonebook entry
						System.out.println("DELETE: " + m.entry.name);
						if ( ac_store.checkWritePerm(m.username) ){
							int i = pb.entryExist(m.entry.name);
							int recno = pb.entryExist(m.entry.name);
							if ( i >=0 ) {
								pb.entry_list.remove(i);
								pb.entry_count = pb.entry_count - 1;
								pb.updatePBFile();
								
								
								m.entry = new PhonebookEntry();
								m.recno = -1;
								m.reply_msg = "DELETE success";
							}
							else
								m.reply_msg = "Phonebook entry does not exist";
						} 
						else {
							m.reply_msg = "Invalid permission";
						}
					}
					//Task 5.3
					else if ( m.command.equals("LOGOFF") ){
						more_msg = false;
						oos.close();
						ois.close();
						return;
					}

					oos.writeObject(m);



				} catch ( Exception e ) {
					System.out.println("Exception in command processing");
					e.printStackTrace();
					return;
				}

			}		

		}
		
		//Task 5.6
		@Override
		public void run() {
			this.ch_go();

		}

	}

}