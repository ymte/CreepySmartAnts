package MallVisualiser.Editor;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class BackToMenuDialog extends JDialog implements PropertyChangeListener {
	
	private EditorButton parent;

	private JOptionPane optionPane;

	private String btnString1 = "Save & Quit";
	private String btnString2 = "Discard & Quit";
	private String btnString3 = "Cancel";

	/** Creates the reusable dialog. */
	public BackToMenuDialog(Frame parentFrame, EditorButton parent) {
		super(parentFrame, true);
		this.parent = parent;
		
		setTitle("Quiz");

		//Create an array of the text and components to be displayed.
		String msgString1 = "Do you want to save first?";
		Object[] array = {msgString1};

		//Create an array specifying the number of dialog buttons
		//and their text.
		Object[] options = {btnString1, btnString2, btnString3};

		//Create the JOptionPane.
		optionPane = new JOptionPane(array,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_CANCEL_OPTION,
				null,
				options,
				options[0]);

		//Make this dialog display it.
		setContentPane(optionPane);
		this.setBounds(parentFrame.getWidth()/2+parentFrame.getX(), parentFrame.getHeight()/2+parentFrame.getY(), 100, 100);
		pack();

		//Handle window closing correctly.
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				/*
				 * Instead of directly closing the window,
				 * we're going to change the JOptionPane's
				 * value property.
				 */
				optionPane.setValue(new Integer(
						JOptionPane.CLOSED_OPTION));
			}
		});

		//Register an event handler that reacts to option pane state changes.
		optionPane.addPropertyChangeListener(this);
	}

	/** This method reacts to state changes in the option pane. */
	public void propertyChange(PropertyChangeEvent e) {		
		String prop = e.getPropertyName();

		if (isVisible()
				&& (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop) ||
						JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				//ignore reset
				return;
			}

			//Reset the JOptionPane's value.
			//If you don't do this, then if the user
			//presses the same button next time, no
			//property change event will be fired.
			optionPane.setValue(
					JOptionPane.UNINITIALIZED_VALUE);

			if (btnString1.equals(value)) { //Save & Quit pressed
				if(parent.save()){
					//If save succeeded
					parent.toMenu();				
				}
				clearAndHide();	
			} else if(btnString2.equals(value)) { //Discard & Quit pressed 
				parent.toMenu();
				clearAndHide();
			} else if(btnString3.equals(value)){ //Cancel pressed
				clearAndHide();				
			}
		}
		
	}

	/** This method clears the dialog and hides it. */
	public void clearAndHide() {
		setVisible(false);
	}
}
