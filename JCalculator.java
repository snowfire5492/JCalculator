/**
 * Name: 		Schenck, Eric
 * Project: 	# 1
 * Due:			Feb 20, 2018
 * Course:		cs24501-w18
 * 
 * Description:	
 * 			A simple calculator that only accepts integer values. 
 *
 */

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


public class JCalculator implements ActionListener{
		
		int tempInt = 0;									// used to store first value while entering a second
		String temp = "";
		char operator;										// used to store operator during operations
		boolean transButtonPressed = false;					// used to keep track of when a "/ - + * " is pressed
		boolean resetReq = false;							// used to keep track if reset is required to not allow input
		boolean equalPressed = false;
		boolean operatorChosen = false;						// used to make sure only one operator is chosen
		boolean operatorNeededNext = false;					// used to keep track of whether an operator is next or an integer
		boolean copyrightDisplayIsOn = false;				
		JTextField display;
		
	JCalculator(){
		
		JFrame frame = new JFrame("Calculator");			// creating JFrame to hold and display all components
		
		frame.setSize(250, 300);							// setting frame size
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon("C://Users//Eric//Desktop//Programs//CS245_JavaSwingGUI//JCalculator.png");
		
		frame.setIconImage(icon.getImage()); 				// setting new icon JCalculator.png to JFrame
		
		display = new JTextField(30);						       // creating display
		
		Font myFont = new Font("Courier", Font.BOLD , 12);			// changing font
		
		display.setFont(myFont);
		
		display.setHorizontalAlignment(SwingConstants.RIGHT); // input will appear on right side
		
		display.setText("0");	 							// initial display 
		
		JPanel textpan = new JPanel();						// JPanel textpan to hold display
		
		textpan.add(display);								// adding display to textpan
		
		frame.add(textpan,BorderLayout.PAGE_START);			// adding textpan to frame
		
		JPanel jpan = new JPanel();							// creating JPanel to hold 4x4 calculator buttons
		
		jpan.setLayout(new GridLayout(4,4));				// setting to gridlayout for 4x4 buttons
		
		JButton buttons[] = new JButton[16];				// creating 16 buttons for calculator
		
		for(int i = 0; i < buttons.length; ++i){
			buttons[i] = new JButton();
			jpan.add(buttons[i]);
		}
		
		buttons[0].setText("7");							// setting text for each button
		buttons[1].setText("8");
		buttons[2].setText("9");
		buttons[3].setText("/");
		buttons[4].setText("4");
		buttons[5].setText("5");
		buttons[6].setText("6");
		buttons[7].setText("*");
		buttons[8].setText("1");
		buttons[9].setText("2");
		buttons[10].setText("3");
		buttons[11].setText("-");
		buttons[12].setText("C");
		buttons[13].setText("0");
		buttons[14].setText("=");
		buttons[15].setText("+");
		
		for(int i = 0 ; i < buttons.length ; ++i){			// adding ActionListener to buttons
			buttons[i].addActionListener(this);
		}
		
		frame.getRootPane().setDefaultButton(buttons[14]);	// setting "=" to enter key 
		
		frame.add(jpan, BorderLayout.CENTER);				// adding JPanel to frame
		
		JLabel blank1 = new JLabel("    ");					// JLabels are used to format calculator look
		JLabel blank2 = new JLabel("    ");
		JLabel blank3 = new JLabel("  ");
		
		frame.add(blank1, BorderLayout.LINE_START);			// all used to create borders around keypad
		frame.add(blank2, BorderLayout.LINE_END);
		frame.add(blank3, BorderLayout.PAGE_END);
		
		frame.setLocationRelativeTo(null);					// starts window in center of screen
		
		frame.setVisible(true); 							// setting to visible	
	}
	
	@Override
	public void actionPerformed(ActionEvent ae){
		
		switch(ae.getActionCommand()){
			
			case "/":										// divide pressed
			case "*":										// multiply pressed
			case "-":										// subtraction pressed
			case "+":										// addition pressed
				
				if(resetReq)
					break;
				
				if(!operatorChosen){						
					operatorChosen = true;						// setting true to prevent double operator entry
					operator = ae.getActionCommand().charAt(0); // saving operator 
					transButtonPressed = true;					// transition button was pressed
					equalPressed = false;						// clearing equalPressed 
					operatorNeededNext = false;					// clearing boolean
				}else{
					display.setText("Wrong form");
					resetReq = true;
				}
				break;
				
			case "=":										// equals pressed
				
				temp = "";									// clearing out my temp string
				
				try{
					
					if(equalPressed || resetReq){			// user cant double tap equals operation
						break;								// reset is required before moving on
					}
					
					if(operator == '/'){ 
						if(display.getText().equals("0")){	// checking for division by 0
							temp = "Div by 0";
							resetReq = true;			
						}else{
							temp += tempInt / Integer.parseInt(display.getText());
						}
					}
					else if(operator == '*'){
						temp += tempInt *  Integer.parseInt(display.getText());	
					}
					else if(operator == '-'){
						temp += tempInt -  Integer.parseInt(display.getText());	
					}
					else if(operator == '+'){
						temp += tempInt +  Integer.parseInt(display.getText());	
					}
					
					operatorChosen = false;
				
					display.setText(temp);						// displaying answer
				}catch(Exception e){
					display.setText("Overflow");				// overflow error
					resetReq = true;
				}
				equalPressed = true;
				transButtonPressed = false;
				operatorNeededNext = true;						// next input accepted is an operator
				break;
				
			case "C":												// clear pressed
				
				if(copyrightDisplayIsOn){
					copyrightDisplayIsOn = false;					// reset boolean
					display.setText(temp);							// displaying original value
					resetReq = false;								// user can now continue calculation
					operatorNeededNext = true;						// user must now enter operator to continue
					break;
				}
				
				if(ae.getModifiers() == 19){						// Ctrl + Shift + C 
					
					temp = display.getText();						// saving previous display value
					display.setText("(c)2018 eric schenck");		// displaying copyright info
					copyrightDisplayIsOn = true;					// boolean to track display info
					resetReq = true;								// boolean to prevent unwanted input
					break;
				}
				
				operatorChosen = false;								// clearing out everything 
				display.setText("0");
				resetReq = false;
				operatorNeededNext = false;
				equalPressed = false;
				break;
				
			default:												// 0 through 9 key pressed
				
				try{
					
					if(resetReq)								// reset is required and user cannot enter until clear
						break;	
				
					if(equalPressed)							// user cannot add new integers onto a given solution
						operatorNeededNext = true;				// must use operator to proceed 
					
					if((display.getText().equals("0") || transButtonPressed ) && !operatorNeededNext){// clearing display if needed
						tempInt = Integer.parseInt(display.getText());	// saving original display before clearing
						display.setText("");
					}
				
					if(display.getText().length() < 10 && !operatorNeededNext){	// only allowing 10 input characters
						temp = display.getText() + ae.getActionCommand();
						display.setText(temp);							// setting new string text to display
					}
				
					transButtonPressed = false;							// transition button boolean cleared
					break;
				
				}catch(Exception e){
					display.setText("Overflow");
					resetReq = true;
					break;
				}
		}
	
	}
	
	public static void main(String[] args){
		new JCalculator();
	}

}
