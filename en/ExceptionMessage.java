import javax.swing.ImageIcon ;
import javax.swing.JOptionPane ;

/**
 * Tάξη που εμφανίζει μήνυμα από την πρόκληση μιας εξαίρεσης
 * @author Πέτρος Φ .Κυλαδίτης
 * @version 1.2.0
 * @serial 040804
 * @since  040110
 */
class ExceptionMessage extends JOptionPane{

	private static final String EXCEPTION_IMAGE_RELATIVE_PATH = new String("/images/exception.png") ;
	private static final String STOP_IMAGE_RELATIVE_PATH = new String("/images/stop.png") ;
	private static final String DIALOG_TITLE = new String("Exception Thrown ") ;


	//Μέθοδοι εμφάνισης των παραθύρων μηνυμάτων κάνοντας κλήση της showAboutBox() που κληρονομείται από την γονική τάξη

	/**
	 * Εμφανίζει το παράθυρο με τα μηνύματα (Strings) που δέχεται στα ορίσματα
	 * @param parentComponent Το γονικό στοιχείο στο οποίο θα εμφανιστεί το παράθυρο
	 * @param message Το μύνημα που θα εμφανιστεί στο παράθυρο
	 * @param title Ο τίτλος του παραθύρου που θα εμφανιστεί
	 * @param isUserFault Εάν ο χρήστης έκανε κάποιο λάθος χειρισμό ή το σφάλμα είναι του συστήματος (επιρεάζει το εικονίδο που εμφανίζεται)
	 */
	public static void showExceptionMessage(java.awt.Component parentComponent, String message, String title, boolean isUserFault){
		if(isUserFault){
			try{
				showMessageDialog(parentComponent, message, title, JOptionPane.WARNING_MESSAGE, new ImageIcon(new ResourceReferenceFromStaticMembers().getResource(STOP_IMAGE_RELATIVE_PATH))) ;
			}catch(NullPointerException exception){
				//Αν δεν υπάρχει το αρχείο της εικόνας
				showMessageDialog(parentComponent, message, title, JOptionPane.ERROR_MESSAGE) ;
			}
		}
		else{
			try{
				showMessageDialog(parentComponent, message, title, JOptionPane.WARNING_MESSAGE, new ImageIcon(new ResourceReferenceFromStaticMembers().getResource(EXCEPTION_IMAGE_RELATIVE_PATH))) ;
			}catch(NullPointerException exception){
				//Αν δεν υπάρχει το αρχείο της εικόνας
				showMessageDialog(parentComponent, message, title, JOptionPane.WARNING_MESSAGE) ;
			}
		}
	}

	//Κάλεί την παραπάνω μέθοδο χρησιμοποιώντας ένα αντικείμενο της τάξης Exception στο οποίο αναλύει και εμφανίζει τα ανάλογα μηνύματα

	public static void showExceptionMessage(java.awt.Component parentComponent, Exception exception,  boolean isUserFault){
			showExceptionMessage(parentComponent, exception.getLocalizedMessage(), DIALOG_TITLE + exception.getClass().toString(), isUserFault) ;
	}

	public static void showExceptionMessage(Exception exception,  boolean isUserFault){
		showExceptionMessage(null, exception, isUserFault) ;
	}

	public static void showExceptionMessage(String message, String title,  boolean isUserFault){
		showExceptionMessage(null, message, title, isUserFault) ;
	}

}
