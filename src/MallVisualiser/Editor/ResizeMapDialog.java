package MallVisualiser.Editor;

import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ResizeMapDialog extends JDialog implements PropertyChangeListener {
	private JTextField textFieldWidth;
	private JTextField textFieldHeight;
	
	private WorldIO worldIO;

	private JOptionPane optionPane;

	private String btnString1 = "Enter";
	private String btnString2 = "Cancel";

	/** Creates the reusable dialog. */
	public ResizeMapDialog(Frame parentFrame, WorldIO worldIO) {
		super(parentFrame, true);
		this.worldIO = worldIO;
		
		setTitle("Resize current map");

		textFieldWidth = new JTextField(4);
		textFieldHeight = new JTextField(4);

		//Create an array of the text and components to be displayed.
		String msgString1 = "Please specify the map size:";
		String msgString2 = "Width (X): ";
		String msgString3 = "Height (Y): ";
		String msgString4 = "WARNING: Data will be lost when you reduce the map size!";
		Object[] array = {msgString1, msgString2, textFieldWidth, msgString3, textFieldHeight,msgString4};

		//Create an array specifying the number of dialog buttons
		//and their text.
		Object[] options = {btnString1, btnString2};

		//Create the JOptionPane.
		optionPane = new JOptionPane(array,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION,
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

		//Ensure the width text field always gets the first focus.
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textFieldWidth.requestFocusInWindow();
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

			if (btnString1.equals(value)) { //Enter pressed
				int width = -1;
				int height = -1;
				try{
					width = Integer.parseInt(textFieldWidth.getText());
				}catch(NumberFormatException exc){
					//width text was invalid
					textFieldWidth.selectAll();
					JOptionPane.showMessageDialog(
							ResizeMapDialog.this,
							"The value \"" + textFieldWidth.getText() + "\" is not an integer.",
									"Please enter an integer value.",
									JOptionPane.ERROR_MESSAGE);
					textFieldWidth.requestFocusInWindow();
					return;
				}
				try{
					height = Integer.parseInt(textFieldHeight.getText());
				}catch(NumberFormatException exc){
					//height text was invalid
					textFieldHeight.selectAll();
					JOptionPane.showMessageDialog(
							ResizeMapDialog.this,
							"The value \"" + textFieldHeight.getText() + "\" is not an integer.",
									"Please enter an integer value.",
									JOptionPane.ERROR_MESSAGE);
					textFieldHeight.requestFocusInWindow();
					return;
				}
				
				//Success:
				worldIO.resizeWorld(width, height);
				clearAndHide();
				
				
			} else if(btnString2.equals(value)) { //Cancel pressed
				clearAndHide();
			}
		}
		
	}

	/** This method clears the dialog and hides it. */
	public void clearAndHide() {
		textFieldWidth.setText(null);
		textFieldHeight.setText(null);
		setVisible(false);
	}
}
