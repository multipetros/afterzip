import javax.swing.*;

/**
 * Τάξη που εμφανίζει ένα μήνυμα επιτυχίας ή αποτυχίας εκτέλεσης μιας διαδικασίας
 * @author Πέτρος Φ. Κυλαδίτης
 * @version 1.1.0
 * @serial 04072
 * @since 040105
 */
class SuccessOrFailureMessage extends JOptionPane{

	private final static String SUCCESS_IMAGE_RELATIVE_PATH = new String("/images/ok.png") ;
	private final static String FAILURE_IMAGE_RELATIVE_PATH = new String("/images/stop.png") ;
	public final static String SUCCESS_MESSAGE = new String("The process has been successfully completed") ;
	public final static String FAILURE_MESSAGE = new String("Attention!\nExecution failed!") ;
	public final static String TITLE = new String("Result of execution") ;


	/* Μηνύματα ΕΠΙΤΥΧΟΥΣ εκτέλεσης της διαδικασίας */

	public static void showSuccessMessage(java.awt.Component parentComponet, String message, String title){
		try{
			showMessageDialog(parentComponet, message, title, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(new ResourceReferenceFromStaticMembers().getResource(SUCCESS_IMAGE_RELATIVE_PATH))) ;
		}catch(NullPointerException exception){
			showMessageDialog(parentComponet, message, title, JOptionPane.INFORMATION_MESSAGE) ;
		}
	}

	//Υπερφορτωμένες εκδόσεις με λιγότερα ορίσματα
	public static void showSuccessMessage(String message, String title){
		showSuccessMessage(null, message, title) ;
	}

	public static void showSuccessMessage(java.awt.Component parentComponent){
		showSuccessMessage(parentComponent, SUCCESS_MESSAGE, TITLE) ;
	}

	public static void showSuccessMessage(){
		showSuccessMessage(null, SUCCESS_MESSAGE, TITLE) ;
	}


	/* Μηνύματα ΑΠΟΤΥΧΗΜΈΝΗΣ εκτέλεσης της διαδικασίας */

	public static void showFailureMessage(java.awt.Component parentComponet, String message, String title){
		try{
			showMessageDialog(parentComponet, message, title, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(new ResourceReferenceFromStaticMembers().getResource(FAILURE_IMAGE_RELATIVE_PATH))) ;
		}catch(NullPointerException exception){
			showMessageDialog(parentComponet, message, title, JOptionPane.INFORMATION_MESSAGE) ;
		}
	}

	//Υπερφορτωμένες εκδόσεις με λιγότερα ορίσματα
	public static void showFailureMessage(String message, String title){
		showFailureMessage(null, message, title) ;
	}

	public static void showFailureMessage(java.awt.Component parentComponent){
		showFailureMessage(parentComponent, FAILURE_MESSAGE, TITLE) ;
	}

	public static void showFailureMessage(){
		showFailureMessage(null, FAILURE_MESSAGE, TITLE) ;
	}
}
