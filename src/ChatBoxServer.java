/* Name: ChatBoxServer
 * Author: Joe Janaskie 2020
 * Description: Responsible for creating server socket, sending msgs to client, 
 * and receiving messages from client
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;


public class ChatBoxServer implements ActionListener {

	//----------------------Properties----------------------
	private ServerSocket ss;
	private Socket s;
	private ChatFrameServer chat = new ChatFrameServer();
	private JButton connect = chat.getConnectButton();;
	private JButton send = chat.getSendButton();
	private String user;
	private int port;
	private BufferedReader br;
	private BufferedWriter bw;

	//----------------------Constructor----------------------
	public ChatBoxServer() {
		send.addActionListener(this);
		connect.addActionListener(this);
	}


	//If a button is pressed:
	@Override
	public void actionPerformed(ActionEvent e) {
		//If send button is pressed
		if (e.getActionCommand().equals("Send")) {

			String outgoingMsg = "";

			try {
				outgoingMsg= "<" + user + ">:  " + chat.getSendField().getText() + "\n"; 
				chat.getChatArea().append(outgoingMsg);
				bw.write(outgoingMsg);  
				bw.flush();  
			} catch (IOException e1) {
				e1.printStackTrace();
			}  

			//if user types quit, socket is closed
			if (chat.getSendField().getText().equals("quit")) {
				try {
					s.close();
					br.close(); 
					bw.close();
					System.exit(0);
				} catch (Exception error) {
					chat.getChatArea().append("Server Error");
				}
			}
		} 
		//If user presses connect button
		if (e.getActionCommand().equals("Connect")) {
			connect.setEnabled(false); //Connect button can only be pressed once.
			try {
				port = Integer.parseInt(chat.getPortNum().getText());
			} catch (Exception error) {
				chat.getChatArea().append("That port is not valid");
			}

			user = chat.getUser().getText();
			createServer(port);
		}
	}


	//Create server socket
	private void createServer(int port){

		try {
			ss=new ServerSocket(port); 
			s=ss.accept(); 
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream())); 
		} catch (IOException e) {
			System.exit(0);
		}

		if (s.isConnected())
			chat.getChatArea().append("You are now connected!\tType quit to end chat.\n");

	}

	//Reads message from client
	private String readMessage() {

		String incomingMsg = "";
		try {
			incomingMsg = br.readLine();
		} catch (Exception e) {}
		if (!incomingMsg.equals(""))
			chat.getChatArea().append(incomingMsg + "\n");

		return incomingMsg;
	}
	//main method
	public static void main(String[] args) throws IOException {
		ChatBoxServer cbs = new ChatBoxServer();
		//continue reading from client until client writes quit
		while (!cbs.readMessage().equalsIgnoreCase("quit")) {} 
	}


}

