/* Name: ChatFrameServer
 *  Author: Joe Janaskie 2020
 *  This classes creates the GUI for the server connection.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatFrameServer {
	//----------------------Properties----------------------
	private JFrame frame;
	private JPanel panel;
	private JTextArea chatArea;
	private JTextField sendField;
	private JButton connectButton;
	private JButton sendButton;
	private JTextField userName;
	private JTextField portNum;
	private JScrollPane pane;
	
	//----------------------Constructor----------------------
	public ChatFrameServer() {
		makeFrame();
		makePanel();
		makeTopPanel();
		makeTextAreaSendButton();
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	//----------------------Getters----------------------
	public JFrame getFrame() {
		return frame;
	}

	public JPanel getPanel() {
		return panel;
	}

	public JTextArea getChatArea() {
		return chatArea;
	}

	public JTextField getSendField() {
		return sendField;
	}

	public JButton getSendButton() {
		return sendButton;
	}
	
	public JButton getConnectButton() {
		return connectButton;
	}
	
	public JTextField getPortNum() {
		return portNum;
	}
	
	public JTextField getUser() {
		return userName;
	}
	
	//----------------------Methods----------------------
	private void makeFrame() {
		frame = new JFrame("Server Messenger");
		frame.setBounds(50, 50, 500, 700);
		frame.setAlwaysOnTop(true);
		frame.setResizable(true);
	}
	
	//main JPanel
	private void makePanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder(
		        BorderFactory.createEtchedBorder(), "Server Messenger"));
	}
	
	//Top JPanel (Connection panel)
	private void makeTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3, 2, 6, 6));
		
		topPanel.add(new JLabel("Type your Username"));
		userName = new JTextField(10);
		topPanel.add(userName);
		
		topPanel.add(new JLabel("Enter a port number"));
		portNum = new JTextField(10);
		topPanel.add(portNum);
		
		
		topPanel.add(new JLabel());
		connectButton = new JButton("Connect");
		topPanel.add(connectButton);
		
		panel.add(topPanel, BorderLayout.NORTH);
	}
	
	//Chat Area and Send Area
	private void makeTextAreaSendButton() {
		//Where messages are displayed
		chatArea = new JTextArea(30, 50);
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		pane = new JScrollPane(chatArea);
		panel.add(pane, BorderLayout.CENTER);
		
		//Where messages are sent
		sendField = new JTextField(25);
		sendField.setText("Enter message here");
		sendField.setFont(new Font(sendField.getFont().getName(), Font.BOLD, 12));
		sendField.setForeground(Color.LIGHT_GRAY);
		
		//Send Button
		JPanel bot = new JPanel();
		bot.setLayout(new FlowLayout());
		sendButton = new JButton("Send");
		sendButton.setSize(new Dimension(5,3));
		
		bot.add(sendField);
		bot.add(sendButton);
		panel.add(bot, BorderLayout.SOUTH);
	}

}
