/* Name: ChatBoxClient
 * Author: Joe Janaskie 2020
 * Description: Responsible for connection to server, sending msgs to server,
 * and recieving msgs from server
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ChatBoxClient implements ActionListener {
	
	//----------------------Properties----------------------
	private Socket s;
	private ChatFrameClient chat = new ChatFrameClient(); 
	private JButton send = chat.getSendButton();
	private JButton connect = chat.getConnect();;
	private String user;
	private String hostName;
	private int port;
	private BufferedWriter bw;
	private BufferedReader br;

	//----------------------Constructor----------------------
	public ChatBoxClient() {
		send.addActionListener(this);
		connect.addActionListener(this);
	}

	//----------------------Methods----------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		//if user presses send button
		if (e.getActionCommand().equals("Send")) {
			String outgoingMsg=""; 
			
			try {
				outgoingMsg = "<" + user + ">:  " + chat.getSendField().getText() + "\n";  
				chat.getChatArea().append(outgoingMsg);
				bw.write(outgoingMsg);
				bw.flush(); 

			} catch (IOException  e1) {
				e1.printStackTrace();
			}  
			//if user types quit, close socket and end program
			if (chat.getSendField().getText().equalsIgnoreCase("quit")) {

				try {
					br.close();
					bw.close();
					s.close();
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		//If user presses Connect button
		if (e.getActionCommand().equals("Connect")) {
			connect.setEnabled(false); //Can only use connect button once.
			user = chat.getUserName().getText();
			hostName = chat.getHostName().getText();
			port = Integer.parseInt(chat.getPortNum().getText());
			//Now attempt connection
			connection();

		}
	}

	//Create client socket
	public void connection() {

		try {
			s=new Socket(hostName, port);  
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(chat.getFrame(), "Cannot connect due to incorrect port or host number", "Connection Status", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

		if (s.isConnected())
			chat.getChatArea().append("You are now connected!\tType quit to end chat.\n");

	}
	//Reads message from server
	private String readMessage() {

		String incomingMsg = "";

		try {
			incomingMsg = br.readLine();
		} catch (Exception e) {}
		
		if (!incomingMsg.equals(""))
			chat.getChatArea().append(incomingMsg + "\n");

		return incomingMsg;

	}


	public static void main(String[] args) throws IOException {
		ChatBoxClient cbc = new ChatBoxClient();
		//continue reading from client until client writes quit
		while (!cbc.readMessage().equalsIgnoreCase("quit")) {}
	}
}  



