package task5;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import javax.swing.*;

/**
 * 
 * @author Lee Yoon Jeong
 *
 */
public class PBClient {	

	JFrame jf;
	JFrame jf_main;
	Container c;
	
	// Phonebook GUI components	
	String pb_name, pb_phone, pb_addr;
	JTextField name_field, phone_field, recno_field, msg_field;
	JTextArea addr_field;
	JButton jb_getbyname, jb_getbyno, jb_add, jb_update, jb_delete, jb_logoff;
	//Task 5.5
	JButton jb_prey, jb_next;
	JPanel mp1, mp2, mp3, mp, south_p, north_p;
	JLabel j_name, j_phone, j_addr, j_pbtitle, title, j_recno, j_msg;
	JTextField j_pbuser;
	
	// Login GUI components
	String user, pwd;
	JTextField user_field;
	JPasswordField pwd_field;
	JPanel jp1, jp2, jp;
	JLabel j_user, j_pwd, j_title;
	JButton j_login;
	
	//  Networking 
	InputStream is;
	ObjectInputStream ois;
	OutputStream os;
	ObjectOutputStream oos;
	Socket sock;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new PBClient().go();

	}
	
	public void go() {
		
		setupNetworking();
		
		System.out.print("Connection established");
		
		jf = new JFrame("Simple Phonebook Client");
		jf.setLocation(300,300);
        jf.setSize(250,150);
        
        create_login_gui();
		
		jf_main = new JFrame("Simple Phonebook Client");
		jf_main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf_main.setBounds(300,90,600,500);
        // jf_main.setResizable(false);
        
        create_main_gui();    
      
        jf.setVisible(true);        
        		
	}
	
	void create_main_gui() {
		
		jb_getbyname = new JButton("Get by name");
		jb_getbyname.addActionListener(new GetbynameButtonListener());
		
		//Task 5.3
		jb_getbyno = new JButton("Get by record no.");
		jb_getbyno.addActionListener(new GetbyrecnoButtonListener());
		
		jb_add = new JButton("Add");
		jb_add.addActionListener(new AddButtonListener());
		
		//Task 5.3
		jb_update = new JButton("Update");
		jb_update.addActionListener(new UpdateButtonListener());
		
		//Task 5.3
		jb_delete = new JButton("Delete");
		jb_delete.addActionListener(new DeleteButtonListener());
		
		//Task 5.3
		jb_logoff = new JButton("Logoff");
		jb_logoff.addActionListener(new LogoffButtonListener());
		
		//Task 5.4
		jb_prey = new JButton("Prev");
		jb_prey.addActionListener(new PrevButtonListener());
		
		//Task 5.4
		jb_next = new JButton("Next");
		jb_next.addActionListener(new NextButtonListener());
		
		south_p = new JPanel();
		GridLayout g = new GridLayout(2, 4);
		south_p.add(jb_getbyname);
		south_p.add(jb_getbyno);
		south_p.add(jb_prey);
		south_p.add(jb_next);
		south_p.add(jb_add);
		south_p.add(jb_update);
		south_p.add(jb_delete);
		south_p.add(jb_logoff);	
		south_p.setLayout(g);
		
		mp1 = new JPanel();
		mp2 = new JPanel();
		mp3 = new JPanel();
		mp = new JPanel();
		
		mp.setLayout(null);
		mp.setBounds(300,90,600,500);
        
		
		title = new JLabel("Phone Entry");
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		title.setSize(300,30);
		title.setLocation(200,30);
		mp.add(title);
		
		j_name = new JLabel("Name");
		j_name.setFont(new Font("Arial", Font.PLAIN, 20));
		j_name.setSize(100,20);
		j_name.setLocation(100,100);
		mp.add(j_name);
		
		name_field = new JTextField();
		name_field.setFont(new Font("Arial", Font.PLAIN, 15));
		name_field.setSize(190,20);
		name_field.setLocation(200,100);
		mp.add(name_field);
				
		j_phone = new JLabel("Phone no.");
		j_phone.setFont(new Font("Arial", Font.PLAIN, 20));
		j_phone.setSize(100,20);
		j_phone.setLocation(100,150);
		mp.add(j_phone);
		
		phone_field = new JTextField();
		phone_field.setFont(new Font("Arial", Font.PLAIN, 15));
		phone_field.setSize(150,20);
		phone_field.setLocation(200,150);
		mp.add(phone_field);
		
		j_addr = new JLabel("Address");
		j_addr.setFont(new Font("Arial", Font.PLAIN, 20));
		j_addr.setSize(100,20);
		j_addr.setLocation(100,200);
		mp.add(j_addr);
		
		addr_field = new JTextArea();
		addr_field.setFont(new Font("Arial", Font.PLAIN, 15));
		addr_field.setSize(200,75);
		addr_field.setLocation(200,200);		
		mp.add(addr_field);
		
		j_recno = new JLabel("Rec No:");
		j_recno.setFont(new Font("Arial", Font.PLAIN, 15));
		j_recno.setSize(100,20);
		j_recno.setLocation(100,300);
		mp.add(j_recno);
		
		recno_field = new JTextField();
		recno_field.setFont(new Font("Arial", Font.PLAIN, 15));
		recno_field.setSize(150,20);
		recno_field.setLocation(200,300);
		mp.add(recno_field);
		
		j_msg = new JLabel("Server msg:");
		j_msg.setFont(new Font("Arial", Font.PLAIN, 15));
		j_msg.setSize(100,20);
		j_msg.setLocation(100,350);
		mp.add(j_msg);
		
		msg_field = new JTextField();
		msg_field.setFont(new Font("Arial", Font.PLAIN, 15));
		msg_field.setSize(150,20);
		msg_field.setLocation(200,350);
		mp.add(msg_field);
		
		j_pbtitle = new JLabel("Phonebook by     ");
		j_pbuser = new JTextField(15);
		j_pbuser.setText(user);

		
		north_p = new JPanel();
		north_p.add(j_pbtitle);
		north_p.add(j_pbuser);
		
		c = jf_main.getContentPane();
				
		c.add(BorderLayout.CENTER,mp);
		c.add(BorderLayout.SOUTH,south_p);
		c.add(BorderLayout.NORTH,north_p);
		
	}
	
	void create_login_gui() {

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp = new JPanel();
		
		j_user = new JLabel("Username");
		j_pwd = new JLabel("Password");
		j_title = new JLabel("Phonebook System");

		user_field = new JTextField(10);
		pwd_field = new JPasswordField(10);
		
		j_login = new JButton("Login");
		j_login.addActionListener(new LoginButtonListener());
		
		jp1.add(user_field);
		jp2.add(pwd_field);
		
		jp.setLayout(new GridLayout(2,2,5,5));
		jp.add(j_user);
		jp.add(jp1);
		jp.add(j_pwd);
		jp.add(jp2);
		
		jf.getContentPane().add(BorderLayout.CENTER,jp);
		jf.getContentPane().add(BorderLayout.SOUTH,j_login);
		jf.getContentPane().add(BorderLayout.NORTH,j_title);
		
	}

	class LoginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			SPBMessage mesg, reply_m;
			
			try {
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
								
				System.out.println("User: " + user);
				System.out.println("Password: " + pwd);
			
				mesg = new SPBMessage("LOGIN",user,pwd,null);
				oos.writeObject(mesg);
				reply_m = (SPBMessage) ois.readObject();
				String m = reply_m.reply_msg;
			
				if ( m.equals("LOGIN success") ) {
					jf.setVisible(false);
					jf_main.setVisible(true);
				}
				else {
					System.out.println("LOGIN FAIL");
				}
				
				if ( j_pbuser != null ) {
					j_pbuser.setText(user);
				}
			
			} catch (Exception e) {				
				System.out.print("Network exception");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	//Task 5.3
	class LogoffButtonListener implements ActionListener {

			@Override
		public void actionPerformed(ActionEvent e) {
			SPBMessage mesg, reply_m;
			
			//breakNetworking();
			try {
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
				
				System.out.println("User: " + user);
				System.out.println("Password: " + pwd);
				
				mesg = new SPBMessage("LOGOFF",user,pwd,null,null);
				oos.writeObject(mesg);
				
				oos.close();
				os.close();
				ois.close();
				is.close();
				
				System.exit(0);
				
			} catch (Exception e1) {				
				System.out.print("Network exception");
				e1.printStackTrace();
				System.exit(0);
			}

				
				
				
		}	
	}
		
	
	class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			SPBMessage mesg, reply_m;
			PhonebookEntry pbe = new PhonebookEntry();
			
			try {				
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
				
				System.out.println("User: " + user);
				System.out.println("Password: " + pwd);
				
				pbe.name = name_field.getText();
				pbe.phone_no = phone_field.getText();
				pbe.address = addr_field.getText();											
							
				mesg = new SPBMessage("ADD",user,pwd,pbe,null);
				oos.writeObject(mesg);
				reply_m = (SPBMessage) ois.readObject();
				String m = reply_m.reply_msg;
				
				if ( ! m.equals("ADD success") ) {
					msg_field.setText(m);
				}
				else {
					msg_field.setText(m);
					recno_field.setText(reply_m.recno.toString());
				}
								
			} catch (Exception e) {
				System.out.print("Network exception");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	//Task 5.3
	class DeleteButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SPBMessage mesg, reply_m;
			PhonebookEntry pbefd = new PhonebookEntry();
			
			try {				
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
				
				System.out.println("User: " + user);
				System.out.println("Password: " + pwd);
				
				pbefd.name = name_field.getText();
				pbefd.phone_no = phone_field.getText();
				pbefd.address = addr_field.getText();											
							
				mesg = new SPBMessage("DELETE",user,pwd,pbefd,null);
				oos.writeObject(mesg);
				reply_m = (SPBMessage) ois.readObject();
				String m = reply_m.reply_msg;
				PhonebookEntry pbr = reply_m.entry;
				
				if ( ! m.equals("DELETE success") ) {
					msg_field.setText(m);
				}
				else {
					name_field.setText(pbr.name);
					phone_field.setText(pbr.name);
					addr_field.setText(pbr.name);
					recno_field.setText(reply_m.recno.toString());
					msg_field.setText(m);
				}
								
			} catch (Exception e1) {
				System.out.print("Network exception");
				e1.printStackTrace();
				System.exit(0);
			}

			
		}
		
	}
	
	//Task 5.4
	class GetbynameButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			SPBMessage mesg, reply_m;
			PhonebookEntry pbe = new PhonebookEntry();
			
			try {				
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
				
				System.out.println("User: " + user);
				System.out.println("Password: " + pwd);
				
				pbe.name = name_field.getText();
				pbe.phone_no = phone_field.getText();
				pbe.address = addr_field.getText();											
							
				mesg = new SPBMessage("GETBYNAME",user,pwd,pbe,null);
				oos.writeObject(mesg);
				reply_m = (SPBMessage) ois.readObject();
				String m = reply_m.reply_msg;
				PhonebookEntry pbe1 = reply_m.entry;
				
				if ( !m.contains("GETBYNAME success")) {
					msg_field.setText(m);
				}
				else {
					name_field.setText(pbe1.name);
					phone_field.setText(pbe1.phone_no);
					addr_field.setText(pbe1.address);
					recno_field.setText(reply_m.recno.toString());
					msg_field.setText(m);
				}
								
			} catch (Exception e) {
				System.out.print("Network exception");
				e.printStackTrace();
				System.exit(0);
			}

		}
	}
	
	//Task 5.3
	class GetbyrecnoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SPBMessage mesg, reply_m;
			
			PhonebookEntry pbern = new PhonebookEntry();
			
			try {
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
				
				System.out.println("User: " + user);
				System.out.println("Password: "+ pwd);
				
				Integer recno = Integer.parseInt(recno_field.getText());
				
				mesg = new SPBMessage("GETBYRECNO", user, pwd, pbern, null);
				mesg.recno = recno;
				
				oos.writeObject(mesg);
				reply_m = (SPBMessage) ois.readObject();
				
				String m = reply_m.reply_msg;
				PhonebookEntry pbe1 = reply_m.entry;
				
				if (!m.equals("GETBYRECNO success")) {
					msg_field.setText(m);
					
				}
				else {
					name_field.setText(pbe1.name);
					phone_field.setText(pbe1.phone_no);
					addr_field.setText(pbe1.address);
					recno_field.setText(reply_m.recno.toString());
					msg_field.setText(m);
				}
				
			} catch (Exception ie) {
				System.out.println("Nectwork exception");
				ie.printStackTrace();
				System.exit(0);
			}
			
		}
		
	}
	
	//Task 5.3
	class UpdateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			SPBMessage mesg, reply_m;
			PhonebookEntry pbeu = new PhonebookEntry();
			
			try {				
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
				
				System.out.println("User: " + user);
				System.out.println("Password: " + pwd);
				
				pbeu.name = name_field.getText();
				pbeu.phone_no = phone_field.getText();
				pbeu.address = addr_field.getText();	
							
				mesg = new SPBMessage("UPDATE",user,pwd,pbeu,null);
				oos.writeObject(mesg);
				reply_m = (SPBMessage) ois.readObject();
				String m = reply_m.reply_msg;
				
				if ( ! m.equals("UPDATE success") ) {
					msg_field.setText(m);
				}
				else {
					msg_field.setText(m);
					recno_field.setText(reply_m.recno.toString());
				}
								
			} catch (Exception e) {
				System.out.print("Network exception");
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	//Task 5.5
	class PrevButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			SPBMessage mesg, reply_m;
			
			PhonebookEntry pbep = new PhonebookEntry();
			
			try {
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
				
				System.out.println("User: " + user);
				System.out.println("Password: "+ pwd);
				
				Integer recno = Integer.parseInt(recno_field.getText());
				
				mesg = new SPBMessage("PREV", user, pwd, pbep, null);
				mesg.recno = recno-1;
				
				oos.writeObject(mesg);
				reply_m = (SPBMessage) ois.readObject();
				
				String m = reply_m.reply_msg;
				PhonebookEntry pbe1 = reply_m.entry;
				
				if (!m.equals("PREV success")) {
					msg_field.setText(m);
					
				}
				else {
					name_field.setText(pbe1.name);
					phone_field.setText(pbe1.phone_no);
					addr_field.setText(pbe1.address);
					recno_field.setText(reply_m.recno.toString());
					msg_field.setText(m);
				}
				
			} catch (Exception ie) {
				System.out.println("Network exception");
				ie.printStackTrace();
				System.exit(0);
			}
			
		}
			
		
	}
	
	//Task 5.5
	class NextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			SPBMessage mesg, reply_m;
			
			PhonebookEntry pber = new PhonebookEntry();
			
			try {
				user = user_field.getText();
				char[] inpwd = pwd_field.getPassword();
				pwd = new String(inpwd);
				
				System.out.println("User: " + user);
				System.out.println("Password: "+ pwd);
				
				Integer recno = Integer.parseInt(recno_field.getText());
				
				mesg = new SPBMessage("NEXT", user, pwd, pber, null);
				mesg.recno = recno+1;
				
				oos.writeObject(mesg);
				reply_m = (SPBMessage) ois.readObject();
				
				String m = reply_m.reply_msg;
				PhonebookEntry pbe1 = reply_m.entry;
				
				if (!m.equals("NEXT success")) {
					msg_field.setText(m);
					
				}
				else {
					name_field.setText(pbe1.name);
					phone_field.setText(pbe1.phone_no);
					addr_field.setText(pbe1.address);
					recno_field.setText(reply_m.recno.toString());
					msg_field.setText(m);
				}
				
			} catch (Exception ie) {
				System.out.println("Nectwork exception");
				ie.printStackTrace();
				System.exit(0);
			}
			
			
		}
		
	}
	
	void setupNetworking() {
		try {
			
			sock = new Socket("127.0.0.1",5000);
			
			is = sock.getInputStream();
			ois = new ObjectInputStream(is);
			os = sock.getOutputStream();
			oos = new ObjectOutputStream(os);
			
			System.out.println("Network connection established.");
			
		} catch (IOException e) { e.printStackTrace(); }
	}

	/*void breakNetworking() {
		SPBMessage mesg;
		try {
			
			
		} catch (IOException e) {
			
		}
	}*/
	


}




