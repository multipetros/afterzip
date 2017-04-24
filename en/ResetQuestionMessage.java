import javax.swing.*;

/**
 * Τάξη που εμφανίζει στο χρήστη μια ερώτηση, για το αν επιθυμεί
 * επανεκκίνηση της εφαρμογής και επιστρέφει την απάντηση.
 * Η τάξη λόγω της μεγάλης σημασίας της καθώς και για λόγους ασφαλείας
 * είναι δηλωμένη ως final, έτσι ώστε να μην μπορεί να κληρονομηθεί.
 * @author Πέτρος Κυλαδίτης
 * @verion 1.2.0
 * @serial 040804
 * @since  040115
 */
final class ResetQuestionMessage extends JOptionPane{

	private final static String QUESTION_IMAGE_RELATIVE_PATH = new String("/images/question.png") ;
	//private final static ImageIcon QUESTION_IMAGE_ICON = new ImageIcon(new ResourceReferenceFromStaticMembers().getResource(QUESTION_IMAGE_RELATIVE_PATH)) ;
	private final static String QUESTION_TITLE = new String("Question to the user") ;
	private final static String QUESTION_MESSAGE = new String("Do you want to restart this program now?") ;

	/**
	 * Μέθοδος που εμφανίζει ένα πλαίσιο διαλόγου και ρωτάει το χρήστη αν επιθυμεί να γίνει επανεκκίνηση
	 * @param parentComponent Το γονικό πλαίσιο του παραθύρου διαλόγου που θα εμφανιστεί
	 * @param otherMessage Κείμενο που θα εμφανίζεται μαζί με την ερώτηση προς το χρήστη.<br>
	 *		Η ερώτηση εμφανίζεται πάντα επειδή είναι υψηλής σημασίας.
	 * @param title Ο τίτλος του παραθύρου που θα εμφανιστεί
	 * @return 1 αν ο χρήστης απαντήσει 'ΝΑΙ' θετικά και 0 αν απαντήσει 'ΟΧΙ'
	 */
	public static int showConfirmDialog(java.awt.Component parentComponent, String otherMessage, String title){
		if(otherMessage != null)
			otherMessage += "\n\n" ;
		if(title == null)
			title = QUESTION_TITLE ;
		int answer = 0 ;
		try{
			answer = showConfirmDialog(parentComponent, otherMessage + QUESTION_MESSAGE, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(new ResourceReferenceFromStaticMembers().getResource(QUESTION_IMAGE_RELATIVE_PATH))) ;
		}catch(NullPointerException exception){
			answer = showConfirmDialog(parentComponent, otherMessage + QUESTION_MESSAGE, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ;
		}
		return answer ;
	}


	/* Όλες οι παρακάτω μέθοδοι είναι υπερφορτωμένες εκδόσεις της ανωτέρω
	   Οι πληροφορίες των παραμέτρων που απουσιάζουν αντικαθήστανται από τις
	   αντίστοιχες προεπιλεγμένες 'static final' τιμές της τάξης. */
	public static int showConfirmDialog(String otherMessage, String title){
		return showConfirmDialog(null, otherMessage, title) ;
	}

	public static int showConfirmDialog(String otherMessage){
		return showConfirmDialog(null, otherMessage, null) ;
	}

	public static int showConfirmDialog(){
		return showConfirmDialog(null, null, null) ;
	}
}
