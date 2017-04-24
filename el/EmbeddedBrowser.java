import java.util.LinkedList ;
import java.net.URL ;
import java.net.UnknownHostException ;
import java.net.MalformedURLException ;
import java.io.IOException ;
import javax.swing.ImageIcon ;
import javax.swing.JEditorPane ;
import javax.swing.JPopupMenu ;
import javax.swing.JMenuItem ;
import javax.swing.JOptionPane ;
import javax.swing.event.HyperlinkEvent ;
import javax.swing.event.HyperlinkListener ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseEvent ;

/**
 * Τάξη που δημιουργεί έναν ενσωματομένο φυλλομετρητή.
 * Επεκτείνει την ήδη υπάρχουσα τάξη javax.swing.JEditorPane
 * @version 0.8.4
 * @serial 040803
 * @since  040801
 */
class EmbeddedBrowser extends JEditorPane{

	//Σταθερά που ορίζει τον τύπο του περιεχομένου του Pane
	private final String HTML_MIME   = new String("text/html") ;

	//Σταθερές συμβολοσειρών των στοιχείων του μενού
	private final String GO_HOME_STR = new String("Κεντρική σελίδα") ;
	private final String GO_BACK_STR = new String("Προηγούμενη σελίδα") ;
	private final String GO_NEXT_STR = new String("Επόμενη σελίδα") ;
	private final String REFRESH_STR = new String("Ανανέωση") ;
	private final String GOTO_STR    = new String("Μετάβαση σε...") ;
	private final String INFO_STR    = new String("Πληροφορίες") ;

	//Σταθερές που περιγράφουν τη θέση των εικόνων των στοιχείων του μενού
	private final String HOME_IMAGE_PATH 	= new String("/images/home.png") ;
	private final String BACK_IMAGE_PATH 	= new String("/images/back.png") ;
	private final String NEXT_IMAGE_PATH 	= new String("/images/next.png") ;
	private final String REFRESH_IMAGE_PATH = new String("/images/refresh.png") ;
	private final String GOTO_IMAGE_PATH	= new String("/images/goto.png") ;

	//Αντικείμενο που περιγράφει το URL της αρχικής σελίδας
	private URL homePage ;

	//Μεταβλητές που χρειάζονται για το χειρισμό του ιστορικού.
	// !! περισσότερες λεπτομέρειες στο τέλος του αρχείου !!
	private LinkedList history = new LinkedList() ;
	private int historyIndex = 1 ;

	//Στοιχεία του αναδυόμενου μενού επιλογών
	private JMenuItem goHomeMenuItem  = new JMenuItem() ;
	private JMenuItem goBackMenuItem  = new JMenuItem() ;
	private JMenuItem goNextMenuItem  = new JMenuItem() ;
	private JMenuItem refreshMenuItem = new JMenuItem() ;
	private JMenuItem gotoMenuItem    = new JMenuItem() ;

	//Αναδυόμενο μενού επιλογών
	private JPopupMenu popupMenu = new JPopupMenu(this.getClass().getName()) ;


	/**
	 * Προεπιλεγμένη μέθοδος κατασκευής δίχως ορισμό κεντρικής σελίδας.
	 */
	public EmbeddedBrowser(){
		startEngine() ;
	}

	/**
	 * @param homePage Ένα αντικείμενο URL, για τον ορισμό της κεντρικής σελίδας.
	 */
	public EmbeddedBrowser(URL homePage){
		if(homePage != null)
			this.homePage = homePage ;
		else
			this.homePage = null ;
		startEngine() ;
	}

	/**
	 * @param homePage Ένα αντικείμενο String που μπορεί να μετατραπεί σε έγκυρο URL, για τον ορισμό της κεντρικής σελίδας.
	 */
	public EmbeddedBrowser(String homePage){
		if(homePage != null){
			try{
				this.homePage = new URL(homePage) ;
			}catch(MalformedURLException exception){
				this.homePage = null ;
			}
		}
		else
			this.homePage = null ;

		startEngine() ;
	}

	/**
	 * Ξεκινάει την αρχικοποίηση του αντικειμένου (των μεθόδων της γονική τάξης).
	 */
	private void startEngine(){

		makePopupMenu() ;				//Δημιουργία το μενού χειρισμού του φυλλομετρητή

		setContentType(HTML_MIME) ;		//Ορισμός του τύπου του περιεχομένου
		setEditable(false) ;			//Απαγόρευση επεξεργασίας του περιεχομένου
		goHome() ;						//Μετάβαση στην κεντρική σελίδα

		addHyperlinkListener(			//Προσθήκη ακροατή και χειριστή συμβάντος
			new HyperlinkListener(){
				public void hyperlinkUpdate(HyperlinkEvent event){
					if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
						gotoURL(event.getURL()) ;
					}
				}
			}
		) ;
	}

	private void makePopupMenu(){

		//Αρχικοποίηση των στοιχείων του μενού
		//και προσθήκη ακροατών συμβάντων.
		goHomeMenuItem.setText(GO_HOME_STR) ;
		goHomeMenuItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					goHome() ;
				}
			}
		) ;
		try{ goHomeMenuItem.setIcon(new ImageIcon(this.getClass().getResource(this.HOME_IMAGE_PATH))) ; }
		catch(NullPointerException exception){}

		goNextMenuItem.setText(GO_NEXT_STR) ;
		goNextMenuItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					goNext() ;
				}
			}
		) ;
		try{ goNextMenuItem.setIcon(new ImageIcon(this.getClass().getResource(this.NEXT_IMAGE_PATH))) ;	}
		catch(NullPointerException exception){}

		goBackMenuItem.setText(GO_BACK_STR) ;
		goBackMenuItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					goBack() ;
				}
			}
		) ;
		try{ goBackMenuItem.setIcon(new ImageIcon(this.getClass().getResource(this.BACK_IMAGE_PATH))) ; }
		catch(NullPointerException exception){}

		refreshMenuItem.setText(REFRESH_STR) ;
		refreshMenuItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					refresh() ;
				}
			}
		) ;
		try{ refreshMenuItem.setIcon(new ImageIcon(this.getClass().getResource(this.REFRESH_IMAGE_PATH))) ; }
		catch(NullPointerException exception){}

		gotoMenuItem.setText(GOTO_STR) ;
		gotoMenuItem.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					URL userURL = showGotoPanel() ;
					if(userURL != null)	gotoURL(userURL) ;
				}
			}
		) ;
		try{ gotoMenuItem.setIcon(new ImageIcon(this.getClass().getResource(this.GOTO_IMAGE_PATH))) ; }
		catch(NullPointerException exception){}


		//Προσθήκη των στοιχείων στο αναδυόμενο μενού
		popupMenu.add(goHomeMenuItem) ;
		popupMenu.add(goBackMenuItem) ;
		popupMenu.add(goNextMenuItem) ;
		popupMenu.add(refreshMenuItem) ;
		popupMenu.add(gotoMenuItem) ;

		//Προσθήκη του αναδυόμενου μενού πάνω στο στοιχείο της τάξης
		//και προσθήκη του κατάλληλου ακροατή συμβάντος.
		add(popupMenu) ;
		addMouseListener(
			new MouseAdapter(){
				public void mouseReleased(MouseEvent e){
					if(e.isPopupTrigger()){

						//Έλεγχοι της κατάστασης του Pane και των στοιχείων του,
						//για καθορισμό της απ/ενεργοποίησης των κουμπιών λειτουργιών.
						if(historyIndex <= 1 || getPage() == null)
							goNextMenuItem.setEnabled(false) ;
						else
							goNextMenuItem.setEnabled(true) ;

						if(historyIndex >= history.size() )
							goBackMenuItem.setEnabled(false) ;
						else
							goBackMenuItem.setEnabled(true) ;

						if(getPage() == null)
							refreshMenuItem.setEnabled(false) ;
						else
							refreshMenuItem.setEnabled(true) ;


						popupMenu.show(e.getComponent(), e.getX(), e.getY()) ;
					}
				}
			}
		) ;

	}


	/**
	 * Εμφανίζει το περιεχόμενο της δοθέντας σελίδας.
	 * @param url Το URL της σελίδας προς φόρτωση.
	 */
	private void gotoURL(URL url){
		try{
			setPage(url) ;
			for(int i=1; i<historyIndex; i++)	//Αφαίρεση των καταχωρήσεων στο ιστορικό, έπειτα από την τρέχουσα σελίδα
				history.removeLast() ;
			history.add(getPage()) ;			//Προσθήκη της σελίδας στο ιστορικό
			historyIndex = 1 ;					//Ορισμός του επιπέδου της τρέχουσας θέσης μέσα στο ιστορικό
		}
		catch(UnknownHostException exception){  //Αν δεν υπάρχει σύνδεση με το διαδίκτυο.
			//Επειδή η μέθοδος setText είναι violated και μπορεί να δημιουργήσει πρόβλημα στο χειρισμό του νέου υπερκειμένου
			//ορίζεται ένα νέο Document με βάση το DefaultDocument του EditorKit της τάξης.
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText(unknownHost(url, ((URL) history.get(history.size() - historyIndex)))) ;
			historyIndex -- ;
		}catch(IOException exception){  //Αν ο σύνδεσμος είναι νεκρός ή το αρχείο δεν μπορεί να προβληθεί.
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText(fileNotFound(url, ((URL) history.get(history.size() - historyIndex /*- 1*/)))) ;
			historyIndex -- ;
		}
	}


	//Εμφανίζει την κετνρική σελίδα
	private void goHome(){
		try{
			if(homePage != null)
				setPage(homePage) ;
			else{
				setDocument(getEditorKit().createDefaultDocument()) ;
				setText("") ;
			}

			for(int i=1; i<historyIndex; i++)
				history.removeLast() ;
			history.add(getPage()) ;
			historyIndex = 1 ;
		}catch(IOException exception){
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText(homePageNotFound()) ;
			historyIndex -- ;
		}
	}

	//Εμφανίζει την προηγούμενη σελίδα
	private void goBack(){
		try{
			setPage((URL) history.get(history.size() - historyIndex - 1)) ;
		}catch(UnknownHostException exception){  //Αν δεν υπάρχει σύνδεση με το διαδίκτυο.
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText(unknownHost(((URL) history.get(history.size() - historyIndex - 1)), ((URL) history.get(history.size() - historyIndex)))) ;
		}catch(IOException exception){  //Αν ο σύνδεσμος είναι νεκρός ή το αρχείο δεν μπορεί να προβληθεί.
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText(fileNotFound((URL) history.get(history.size() - historyIndex - 1), (URL) history.get(history.size() - historyIndex + 1))) ;
		}catch(NullPointerException exception){
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText("") ;
		}catch(IndexOutOfBoundsException exception){
			/* Αν βγεί εκτός ορίων να μην γίνει τίποτα, ο κώδικας
			   στο finally θα επαναφέρει την ορθή κατάσταση       */
		}
		finally{
			historyIndex ++ ;
		}
	}

	//Εμφανίζει την επόμενη σελίδα
	private void goNext(){
		try{
			setPage((URL) history.get(history.size() - historyIndex + 1)) ;
		}
		catch(UnknownHostException exception){  //Αν δεν υπάρχει σύνδεση με το διαδίκτυο.
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText(unknownHost(((URL) history.get(history.size() - historyIndex +1)), ((URL) history.get(history.size() - historyIndex)))) ;
		}catch(IOException exception){  //Αν ο σύνδεσμος είναι νεκρός ή το αρχείο δεν μπορεί να προβληθεί.
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText(fileNotFound((URL) history.get(history.size() - historyIndex + 1), (URL) history.get(history.size() - historyIndex ))) ;
		}catch(NullPointerException exception){
			setDocument(getEditorKit().createDefaultDocument()) ;
			setText("") ;
		}catch(IndexOutOfBoundsException exception){
			/* Αν βγεί εκτός ορίων να μην γίνει τίποτα, ο κώδικας
			   στο finally θα επαναφέρει την ορθή κατάσταση       */
		}
		finally{
			historyIndex -- ;
		}
	}

	//Ανανεώνει την τρέχουσα σελίδα
	private void refresh(){
		if(getPage() != null){
			try{
				setPage(getPage()) ;
			}
			catch(UnknownHostException exception){  //Αν δεν υπάρχει σύνδεση με το διαδίκτυο.
				setDocument(getEditorKit().createDefaultDocument()) ;
				setText(unknownHost(getPage(), ((URL) history.get(history.size() - historyIndex)))) ;
			}catch(IOException exception){  //Αν ο σύνδεσμος είναι νεκρός ή το αρχείο δεν μπορεί να προβληθεί.
				setDocument(getEditorKit().createDefaultDocument()) ;
				setText(fileNotFound(getPage(), (URL) history.get(history.size() - historyIndex - 1))) ;
			}
		}
	}

	/**
	 * Εμφανίζει ένα πλαίσιο διαλόγου όπου ζητάει στο χρήστη να εισάγει το URL της αρεσκείας του.
	 * @return Το URL που έδωσε ο χρήστης.<br>
	 *		<em>Εαν εμφανιστεί εξαίρεση επιστρέφεται null</em>
	 */
	private URL showGotoPanel(){

		//Σταθερές των μυνημάτων που εμφανίζονται στα παράθυρα διαλόγου
		final String GOTO_MSG    = new String("Δώστε το URL, στο οποίο θέλετε να μεταβείτε: ") ;
		final String GOTO_TITLE  = new String("Μετάβαση σε...") ;

		//Η τοποθεσία που βρίσκονται οι εικόνες που εμφανίζονται στα παράθυρα διαλόγου
		final String GOTO_ICON_PATH  = new String("/images/question.png") ;

		//Σταθερές που περιγράφουν το πρωτόκολλο σε ένα URL
		final String HTTP_PROTOCOL = "http://" ;

		JOptionPane gotoPane = new JOptionPane() ;
		String userInput ;

		try{
			ImageIcon questionIcon = new ImageIcon(this.getClass().getResource(GOTO_ICON_PATH)) ;
			userInput = (String) gotoPane.showInputDialog(this.getParent(), GOTO_MSG, GOTO_TITLE, gotoPane.QUESTION_MESSAGE, questionIcon, null, null) ;
		}catch(NullPointerException exception){
			//Εάν δεν υπάρχει η εικόνα εμφανίζεται το προεπιλεγμένο παράθυρο.
			userInput = gotoPane.showInputDialog(this.getParent(), GOTO_MSG, GOTO_TITLE, gotoPane.QUESTION_MESSAGE) ;
		}


		//Αν δωθεί κενή συμβολοσειρά ή null επιστρέφει αμέσως null
		//σαν να πατήθηκε άκυρο.
		if(userInput.length() == 0 || userInput == null)
			return null ;

		//Αν η είσοδος ξεκινάει με κενό, αριθμό ή με σύμβολο
		//εμφανίζει μήνυμα λανθασμένης URL επιστρέφει null.
		Character firstChar = new Character(userInput.charAt(0)) ;
		if(firstChar.isWhitespace(userInput.charAt(0)) || firstChar.isISOControl(userInput.charAt(0))){
			showBadURLMessageDialog() ;
			return null ;
		}

		try{
			return new URL(userInput) ;
		}catch(MalformedURLException exception){
			//Προσπάθεια αυτόματης διόρθωσης μιας ημιτελούς URL
			userInput = new String(HTTP_PROTOCOL.concat(userInput)) ;

			try {
				return new URL(userInput) ;
			}catch(MalformedURLException exception1){
				//Αν η διεύθυνση έχει εσφαλμένη μορφή επιστρέφει null.
				//Αφού πρώτα εμφανιστεί ένα προειδοποιητικό παράθυρο.
				showBadURLMessageDialog() ;
				return null ;
			}
		}
	}

	/**
	 * Εμφανίζει ένα προειδοποιητικό παράθυρο, που επισημαίνει
	 * στο χρήστη την είσοδο εσφαλμένης μορφής URL.
	 */
	private void showBadURLMessageDialog(){

		//Σταθερές μυνημάτων που εμφανίζονται στο παράθυρο διαλόγου
		final String ERROR_MSG   = new String("Εσφαλμένo URL") ;
		final String ERROR_TITLE = new String("To URL που δώσατε έχει εσφαλμένη μορφή!") ;

		//Η τοποθεσία που βρίσκονται οι εικόνες που εμφανίζονται στα παράθυρα διαλόγου
		final String ERROR_ICON_PATH = new String("/images/stop.png") ;

		JOptionPane errorPane = new JOptionPane() ;

		try{
			ImageIcon errorIcon = new ImageIcon(this.getClass().getResource(ERROR_ICON_PATH)) ;
			errorPane.showMessageDialog(this.getParent(), ERROR_TITLE, ERROR_MSG, errorPane.WARNING_MESSAGE, errorIcon) ;
		}catch(NullPointerException exception2){
			//Εάν δεν υπάρχει η εικόνα εμφανίζεται το προεπιλεγμένο παράθυρο.
			errorPane.showMessageDialog(this.getParent(), ERROR_TITLE, ERROR_MSG, errorPane.WARNING_MESSAGE) ;
		}
	}

	/**
	 * @param fileNotFound Το URL για το οποίο εμφανίστηκε το σφάλμα.
	 * @param previusPage Το URL της προηγούμενης σελίδας.
	 * @return Κώδικας HTML που περιγράφει το σφάλμα και προτρέπει
	 * 			το χρήστη να επιστρέψει στην προηγούμενη σελίδα.
	 */
	private String fileNotFound(URL fileNotFound, URL previusPage){
		return new String("<html></body><font size=\"5\" color=\"#ff0000\">Σφάλμα 404 - </font><font size=\"4\" color=\"#ff0000\">Το αρχείο δεν βρέθηκε!</font><p>Το αρχείο <b>" + fileNotFound.toExternalForm() + "</b> δεν βρέθηκε, ή η μορφή του είναι τέτοια που δεν μπορεί να προβληθεί.<a href=\"" + previusPage.toExternalForm() + "\"> Επιστοφή στην προηγούμενη σελίδα...</a></body></html>") ;
	}

	/**
	 * @return Κώδικας HTML που περιγράφει το σφάλμα.
	 */
	private String homePageNotFound(){
		return new String("<html><body><font size=\"5\" color=\"#ff0000\">Σφάλμα 404 - </font><font size=\"4\" color=\"#ff0000\">Η κεντρική σελίδα δεν βρέθηκε!</font><p>Το αρχείο <font color=\"#0000ff\">" + homePage.toExternalForm() + "</font> που περιέχει τις πληροφορίες που θα εμφανιζόταν σε αυτό το πλαίσιο δεν βρέθηκε ή έχει καταστραφεί.</p><p>Για να λυθεί το πρόβλημα παρακαλώ εγκαταστήστε ξανά την εφαρμογή.</p></body></html>") ;
	}

	/**
	 * @param targetURL Η διεύθυνση του διακομιστή για τον οποίο εμφανίστηκε το σφάλμα.
	 * @param previusPage Η διεύθυνση της προηγούμενης σελίδας, για προτροπή του χρήστη επιστροφής σε αυτήν.
	 * @return Κώδικας HTML που περιγράφει το σφάλμα και προτρέπει το
	 *			το χρήστη να επιστρέψει στην αρχική σελίδα.
	 */
	private String unknownHost(URL targetURL, URL previusPage){
		return new String("<html></body><font size=\"5\" color=\"#ff0000\">Σφάλμα 503 - </font><font size=\"4\" color=\"#ff0000\">ʼγνωστος διακομιστής</font><p>Ο διακομιστής <b>" + targetURL.getHost() + "</b> δεν βρέθηκε. Πιθανόν το URL που δώσατε δεν είναι σωστό ή ο υπολογιστής σας <b>δεν</b> είναι συνδεδεμένος με το διαδίκτυο.<p><a href=\"" + previusPage + "\">Επιστροφή στην προηγούμενη σελίδα...</a></p></body></html>") ;
	}
}




/*                  ΤΡΟΠΟΣ ΛΕΙΤΟΥΡΓΙΑ ΤΟΥ «ΙΣΤΟΡΙΚΟΥ»
 *                  =================================
 * 	Το ιστορικό είναι μια συνδεδεμένη λίστα που περιέχει URLs και η οποία σε
 * συνδιασμό με έναν δείκτη της τρέχουσας θέσης από το τέλος της λίστας,
 * επιτρέπει την πλοήγηση σ' αυτήν.
 * 	Η λίστα έχει τη μορφή της δομής δεδομένων της στίβας LIFO, με τη διαφορά
 * ότι κατά τη μετάβαση από την ουρά στην κορυφή της λίστας δεν αφαιρούνται
 * τα στοιχεία που λαμβάνονται, αλλά παραμένουν στην θέση τους.
 *  Στοιχεία αφαιρούνται μονάχα όταν προστεθεί στοιχείο στη λίστα (προστίθενται)
 * πάντα στο τέλος της, και η τρέχουσα θέση μας δεν είναι στο τέλος τις λίστας.
 * Σ' αυτή την περίτπωση αφαιρούνται όσα στοιχεία βρίσκονται μετά το δείκτη.
 *
 *	Σε αυτή την υλοποίηση η λίστα είναι ένα αντικείμενο java.util.LinkedList
 * το οποίο περιέχει αντικείμενα της τάξης java.net.URL
 * Ο δείκτης είναι ένας προσημασμένος ακαίρεος εύρους 32 δυφίων (τύπος int).
 * Αρχικά ο δείκτης έχει την τιμή, που σημαίνει ότι βρισκόμαστε στην πρώτη από
 * το τέλος θέση της λίστας (δηλαδή στο τέλος της).
 *
 *	Για την μετάβαση στο προηγούμενο URL, γίνεται κλήση της μεθόδου get(i), όπου
 * i είναι η θέση του προηγούμενου από το τρέχον στοιχείο και υπολογίζεται αν
 * από το μέγεθος της λίστας αφαιρεθεί ο δείκτης της τρέχουσας θέσης και μια
 * ακόμα μονάδα (διότι η αρίθμιση των στοιχείων της λίστας ξεκινάει από το 0).
 * Έπειτα ο δείκτης θα υποστεί μοναδιαία αύξηση, για μετέπειτα χρήση.
 *
 *	Για την μετάβαση στο επόμενο URL, γίνεται κλήση της μεθόδου get(i), όπου
 * i είναι η θέση του επόμενου από το τρέχον στοιχείο και υπολογίζεται αν από
 * το μέγεθος της λίστας αφαιρεθεί ο δείκτης της τρέχουσας θέσης και προστεθεί
 * μια μονάδα (διότι η αρίθμιση των στοιχείων της λίστας ξεκινάει από το 0).
 * Έπειτα ο δείκτης θα υποστεί μοναδιαία μείωση, για μετέπειτα χρήση.
 *
 *	Με κάθε επίσκεψη σε ένα URL, γίνεται έλεγχος αν η τρέχουσα θέση είναι στο
 * τέλος της λίστας (ο δείκτης έχει την τιμή 1). Αν όχι, και για να λειτουργήσει
 * σωστά και όπως αναμένεται το ιστορικό, αφαιρούνται όσα στοιχεία βρίσκοτναι
 * μετά από την τέχουσα θέση και μέχρι το τέλος της λίστας.
 * 	Αυτό γίνεται με τη χρήση της μεθόδου removeLast() η οποία αφαιρεί το τελευταίο
 * στοιχείο της λίστας. Η μέθοδος θα πρέπει να κληθεί τόσες φορές όση είναι και
 * η τιμή του δείκτη μείον 1.
 * 	Τέλος το URL στο οποίο έγινε η μετάβαση τοπόθετείται στο τέλος της λίστας,
 * με την χρήση της μεθόδου add(URL) και ο δείκτης να λάβει την τιμή 1.
 *
 * Σημείωση:
 * - Πριν από κάθε προσπάθεια μετάβασης προστά στο Ιστορικό πρέπει να ελέγχεται
 *  αν ο δείκτης είναι μιρκότερος ή ίσος με 1
 * - Πριν από κάθε προσπάθεια μετάβασης πίσω στο Ιστορικό πρέπει να ελέγχεται αν
 *  ο δείκτης είναι μεγαλύτερος ή ίσος από το μέγεθος της λίστας.
 * Σε κάθε αληθή περίπτωση θα πρέπει να αποτρέπεται η δυνατότητα εκτέλεσης της
 * συγκεκριμένης εντολής διότι θα ξεπεραστούν τα όρια της λίστας.
 */