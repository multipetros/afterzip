import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import java.util.Properties ;
import java.net.URL ;

/**
 * Τάξη που δημιουργεί το γονικό γραφικό περιβάλλον, στο οποίο
 * προστίθενται σε μια καρτέλα όλες οι υπόλοιπες τάξεις-γραφικά
 * περιβάλλοντα που απαρτίζουν την εφαρμογή
 * @author Πέτρος Φ. Κυλαδίτης
 * @version 1.0.7
 * @serial 040804
 * @since  031210
 */
class AfterzipFrame extends Object {

	private static JFrame frame ;
	private JTabbedPane tabbs ;
	private PropertiesRW props ;

	//Σταθερές που περιέχουν τα Strings του περιβάλλοντος
	private final String SPLIT_MSG 		= new String("Τεμαχισμός") ;
	private final String MERGE_MSG 		= new String("Συγχώνευση") ;
	private final String OPTIONS_MSG 	= new String("Ρυθμίσεις") ;
	private final String HELP_MSG 		= new String("Βοήθεια") ;
	private final String ABOUT_MSG 		= new String("Πληροφορίες") ;
	private final String TITLE_MSG 		= new String("Afterzip") ;

	//Σταθερές για τα σφάλματα που προκύπτουν
	//Η δήλωσή τους ως στατικές είναι απαραίτητη για την χρήση τους από τις στατικές μεθόδους εμφάνισης των προειδοποιητικών μηνυμάτων
	private static final String UNSUPPORTED_LAF_EXCEPTION_MSG 	= new String("Tο θέμα που επιλέξατε δεν υποστιρίζεται από το σύστημά σας.\nΣτην εφαρμογή θα εφαρμοστεί το προεπιλεγμένο θέμα της πλατφόρμας σας.\n\nΤάξη για την οποία εμφανίστηκε η εξαίρεση:\n") ;
	private static final String INSTANTIATION_EXCEPTION_MSG 	= new String("Σφάλμα κατά την προσπάθεια εφαρμογής του θέματος.\nΣτην εφαρμογή θα εφαρμοστεί το προεπιλεγμένο θέμα της πλατφόρμας σας.") ;
	private static final String CLASS_NOT_FOUND_EXCEPTION_MSG 	= new String("H τάξη του θέματος δεν βρέθηκε.\nΤο αρχείο δεν υπάρχει ή η διαδρομή CLASSPATH είναι εσφαλμένη.\nΣτην εφαρμογή θα εφαρμοστεί το προεπιλεγμένο θέμα της πλατφόρμας σας.\n\nΤάξη για την οποία εμφανίστηκε η εξαίρεση:\n") ;
	private static final String ILLEGAL_ACCESS_EXCEPTION_MSG 	= new String("Η πρόσβαση στην τάξη του θέματος δεν επιτράπηκε.\nΣτην εφαρμογή θα εφαρμοστεί το προεπιλεγμένο θέμα της πλατφόρμας σας.") ;
	private static final String GENERAL_UI_EXCEPTION_MSG 		= new String("Error at UI loadig!\n") ;

	private static final String EXCEPTION_TITLE = new String("Εμφανίστηκε εξαίρεση") ;
	private static final String EXCEPTION_CLASS_TITLE = new String("Εμφανίστηκε εξαίρεση.\n\nΤάξη για την οποία εμφανίστηκε η εξαίρεση:\n") ;

	//Η τοποθεσία του εικονιδίου του παραθύρου
	private final String IMAGE_ICON_PATH = new String("/images/icon.png") ;

	public AfterzipFrame(){

		//Ανάγνωση από το αρχείο πληροφοριών για την αρχικοποίηση των στοιχείων του παραθύρου.
		props = new PropertiesRW() ;

		//Καθορισμός του θέματος με κλήση της δημόσια στατικής μεθόδου της τάξης
		this.setTheme(props.getTheme(), props.getThemedBorder())  ;

		//Δημιουργία των καρτελών
		tabbs = new JTabbedPane(JTabbedPane.BOTTOM) ;
		tabbs.addTab(SPLIT_MSG, new SplitPanel()) ;
		tabbs.addTab(MERGE_MSG, new MergePanel()) ;
		tabbs.addTab(OPTIONS_MSG, new OptionsPanel()) ;
		tabbs.addTab(HELP_MSG , new  HelpPanel()) ;
		tabbs.addTab(ABOUT_MSG, new AboutPanel()) ;

		/* Δεν θα εφαρμοστούν διότι εμποδίζουν την θέαση των καρτελών
		tabbs.setToolTipTextAt(0, "Από αυτή την καρτέλα μπορείτε να εκκινήσετε μια διαδικασία συγχώνευσης αρχείων") ;
		tabbs.setToolTipTextAt(1, "Από εδώ μπορείτε να εκκινήσετε μια διαδικασία συγχώνευσης αρχείων") ;
		tabbs.setToolTipTextAt(2, "Ρθμίσεις και επιλογές του περιβάλλοντος εργασίας") ;
		tabbs.setToolTipTextAt(3, "Λήψη βοήθειας για τις λειτουργίες της εφαρμογής") ;
		tabbs.setToolTipTextAt(4, "Πληροφορίες για την εφαρμογή, άδεια χρήσης, απόδοση πνευματικών δικαιωμάτων κ.α.") ;
		*/

		tabbs.setSelectedIndex(props.getInitTab()) ;



		//Καθορισμός του εικονιδίου του παραθύρου,
		//αν δεν βρεθεί προκαλείται εξαίρεση και θέτεται η τιμή null στο Image,
		//όταν η μέθοδος του JFrame λάβει το null ορίζεται από το σύστημα το προκσθορισμένο Image.
		Image windowIcon ;
		try{
			URL iconImageURL = this.getClass().getResource(IMAGE_ICON_PATH) ;
			windowIcon = new ImageIcon(iconImageURL).getImage() ;

		}catch(NullPointerException exception){
			windowIcon = null ;
		}


		frame = new JFrame() ;
		frame.setTitle(TITLE_MSG) ;
		frame.setIconImage(windowIcon) ;
		frame.setSize(props.getFrameWidth(), props.getFrameHeight()) ;
		frame.setLocation(props.getFrameX(), props.getFrameY()) ;
		frame.getContentPane().add(tabbs) ;
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE) ;	//Toν τερματισμό τον χειρίζεται η ανώνυμη τάξη-ακροατής αυτού του συμβάντος
		frame.setVisible(true) ;
		frame.addWindowListener(
			new WindowAdapter(){
				//Κατά το κλείσιμο του παραθύρου-τερματισμός εφαρμογής
				//η ανώνυμη αυτή τάξη αποθηκεύει τις ιδιότητες μεγέθους-θέσης
				public void windowClosing(WindowEvent e){
					PropertiesRW properties = new PropertiesRW() ;
					boolean changeMade = false ;

					if(properties.getSaveNewSize()){
						properties.setFrameWidth(frame.getWidth()) ;
						properties.setFrameHeight(frame.getHeight()) ;
						changeMade = true ;
					}

					if(properties.getSaveNewLocation()){
						properties.setFrameX(frame.getX()) ;
						properties.setFrameY(frame.getY()) ;
						changeMade = true ;
					}

					if(properties.getInitTab() != tabbs.getSelectedIndex()){
						properties.setInitTab(tabbs.getSelectedIndex()) ;
						changeMade = true ;
					}

					if(changeMade) properties.storeProperties() ;

					System.exit(0) ;
				}
			}
		) ;

		/* Ακροατής αλλαγών στο διαχειριστή θεμάτων  *
		 * με τη χρήση ανώνυμης τάξης του java.beans */
		UIManager.addPropertyChangeListener(
			new java.beans.PropertyChangeListener(){
				//Αντικείμενο πάνω στο οποίο θα γίνουν οι αλλαγές
    			private JComponent component = (JComponent) frame.getRootPane() ;

				public void propertyChange(java.beans.PropertyChangeEvent e) {
					if (e.getPropertyName().equals("lookAndFeel")) {
						SwingUtilities.updateComponentTreeUI(component) ;
						component.invalidate() ;
						component.validate() ;
						component.repaint() ;
					}
    			}
			}
		);
	}

	/**
	 * Μέθοδος ορισμού του θέματος (Look&Feel) της εφαρμογής (χωρίς εφαρμογή του θέματος στο περίγραμμα)
	 * @param theme Ένα αντικείμενο String που περιέχει την τάξη του θέματος
	 */
	public static void setTheme(String theme){
		setTheme(theme, false) ;
	}

	/**
	 * Μέθοδος ορισμού του θέματος (Look&Feel) της εφαρμογής
	 * @param theme Ένα αντικείμενο String που περιέχει την τάξη του θέματος
	 * @param themedBorder True/False για την εμφάνιση ή όχι του περιγράμματος του παραθύρου όπως και το θέμα (εφαρμόζεται μόνο στην τάξη Metal)
	 */
	public static void setTheme(String theme, boolean themedBorder){
		try{
			if(theme != null)
				UIManager.setLookAndFeel(theme) ;
			else
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ;

			if(themedBorder && UIManager.getLookAndFeel().getSupportsWindowDecorations()){
				JDialog.setDefaultLookAndFeelDecorated(true);
				JFrame.setDefaultLookAndFeelDecorated(true);
			}
			else{
				JDialog.setDefaultLookAndFeelDecorated(false);
				JFrame.setDefaultLookAndFeelDecorated(false);
			}

			Toolkit.getDefaultToolkit().setDynamicLayout(true);
			System.setProperty("sun.awt.noerasebackground","true");
		}
		catch(UnsupportedLookAndFeelException exception){
			ExceptionMessage.showExceptionMessage(frame, UNSUPPORTED_LAF_EXCEPTION_MSG.concat(theme), EXCEPTION_TITLE, false) ;
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ;
				PropertiesRW props = new PropertiesRW() ;
				props.setTheme(UIManager.getSystemLookAndFeelClassName()) ;
				props.setThemedBorder(false) ;
				props.storeProperties() ;
			}
			catch(Exception error){
				System.out.println(GENERAL_UI_EXCEPTION_MSG) ;
				System.out.println(error.getMessage()) ;
				System.out.println(error.getStackTrace()) ;
			}
		}
		catch(ClassNotFoundException exception){
			ExceptionMessage.showExceptionMessage(frame, CLASS_NOT_FOUND_EXCEPTION_MSG.concat(theme), EXCEPTION_TITLE, false) ;
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ;
				PropertiesRW props = new PropertiesRW() ;
				props.setTheme(UIManager.getSystemLookAndFeelClassName()) ;
				props.setThemedBorder(false) ;
				props.storeProperties() ;
			}
			catch(Exception error){
				System.out.println(GENERAL_UI_EXCEPTION_MSG) ;
				System.out.println(error.getMessage()) ;
				System.out.println(error.getStackTrace()) ;
			}
		}
		catch(InstantiationException exception){
			ExceptionMessage.showExceptionMessage(frame, INSTANTIATION_EXCEPTION_MSG, EXCEPTION_CLASS_TITLE.concat(theme), false) ;
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ;
				PropertiesRW props = new PropertiesRW() ;
				props.setTheme(UIManager.getSystemLookAndFeelClassName()) ;
				props.setThemedBorder(false) ;
				props.storeProperties() ;
			}
			catch(Exception error){
				System.out.println(GENERAL_UI_EXCEPTION_MSG) ;
				System.out.println(error.getMessage()) ;
				System.out.println(error.getStackTrace()) ;
			}
		}
		catch(IllegalAccessException exception){
			ExceptionMessage.showExceptionMessage(frame, ILLEGAL_ACCESS_EXCEPTION_MSG, EXCEPTION_CLASS_TITLE.concat(theme), false) ;
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()) ;
				PropertiesRW props = new PropertiesRW() ;
				props.setTheme(UIManager.getSystemLookAndFeelClassName()) ;
				props.setThemedBorder(false) ;
				props.storeProperties() ;
			}
			catch(Exception error){
				System.out.println(GENERAL_UI_EXCEPTION_MSG) ;
				System.out.println(error.getMessage()) ;
				System.out.println(error.getStackTrace()) ;
			}
		}
	}

	/**
	 * Μέθοδος αλλαγής των διαστάσεων του παραθύρου
	 * @param width Νέο πλάτος
	 * @param height Νέο ύψος
	 */
	public static void setSize(int width, int height){
		frame.setSize(width, height) ;
	}


	/**
	 * Μέθοδος αλλαγής της θέσης του παραθύρου στην οθόνη
	 * @param x Νέο σημείο (αριθμιμένο pixel) του πλάτους της οθόνης
	 * @param y Νέο σημείο (αριθμιμένο pixel) του μήκους της οθόνης
	 */
	public static void setLocation(int x, int y){
		frame.setLocation(x, y) ;
	}

	/**
	 * Μέθοδος που εμφανίζει ένα πλαίσιο επιβεβαίωσης επανεκκίνησης της εφαρμγής
	 * Αν ο χρήστης απαντήσει αρνητικά τότε η μέθοδος επιστρέφει αμέσως
	 * Αλλιώς καλείται η μέθοδος απόκρυψης του αντικειμένου του παραθύρου της εφαρμογής
	 * η αναφορά δεν δείχνει πια προς το αντικείμενο, αναδομείται η τάξη
	 * και καλείται η συλλογή άχρηστης μνήμης.
	 * @param confirmDialogMessage Κείμενο ειδοποίησης, εμφανίζεται μαζί με το προκαθορισμένο κείμενο
	 * @param confirmDialogTitle Τίτλος του παραθύρου
	 */
	public static void reset(String confirmDialogMessage, String confirmDialogTitle){
		//Εμφάνιση παραθύρου ερώτησης προς τον χρήση, για επανεκκίνηση
		//Αν απαντήσει αρνητικά η μέθοδος να επιστρέψει αμέσως.
		//int answer = ResetQuestionMessage.showConfirmDialog(frame, confirmDialogMessage, confirmDialogTitle) ;
		//if(answer == JOptionPane.NO_OPTION)	return ;

		// Έλεγχος αν έχουν αλλάξει οι διαστάσει ή η θέση του παραθύρου
		// Αν ναι, να αποθηκευτούν οι καινούριες πριν από την καταστροφή του αντικειμένου
		PropertiesRW properties = new PropertiesRW() ;
		boolean changeMade = false ;

		if(properties.getSaveNewSize()){
			properties.setFrameWidth(frame.getWidth()) ;
			properties.setFrameHeight(frame.getHeight()) ;
			changeMade = true ;
		}

		if(properties.getSaveNewLocation()){
			properties.setFrameX(frame.getX()) ;
			properties.setFrameY(frame.getY()) ;
			changeMade = true ;
		}

		if(changeMade) properties.storeProperties() ;

		//Επανεκκίνηση...
		frame.setVisible(false) ;
		frame = null ;
		new AfterzipFrame() ;
		System.gc() ;
	}

	/* Υπερφορτωμένες μέθοδοι της προηγούμενης μεθόδου για πιο απλές κλήσεις, δίχως ορίσματα */
	public static void reset(String confirmDialogMessage){
		reset(confirmDialogMessage, null) ;
	}

	public static void reset(){
		reset(null, null) ;
	}

	public static void main(String[] args){
		new AfterzipFrame() ;
	}
}
