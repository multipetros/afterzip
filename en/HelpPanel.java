import java.io.File ;
import java.io.IOException ;
import java.net.URL ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import java.awt.BorderLayout ;
import javax.swing.JPanel ;
import javax.swing.JEditorPane ;
import javax.swing.JScrollPane ;
import javax.swing.JToolBar ;
import javax.swing.JButton ;
import javax.swing.ImageIcon ;


/**
 * Τάξη που δημιουργεί το πλαίσιο βοήθειας της εφαρμογής
 * @author Πέτρος Φ. Κυλαδίτης
 * @version 1.2.0
 * @serial 040807
 * @since  031225
 */
class HelpPanel extends JPanel{

	private JToolBar toolbar ;			//Εργαλειοθήκη για την τοποθέτηση των κουμπιών
	private JButton introButton ;		//Προβάλλει την εισαγωγή στην βοήθεια
	private JButton splitHelpButton ;	//Προβάλλει βοήθεια για τον τεμαχισμό των αρχείων
	private JButton mergeHelpButton ;	//Προβάλλει βοήθεια για την συγχώνευση των αρχείων
	private JButton optionsHelpButton ;	//Προβάλλει βοήθεια για τις επιλογές προσαρμογής της εφαρμογής
	private JEditorPane browserPanel ;	//Το πλαίσιο που θα προβληθούν οι πληροφορίες
	private JScrollPane scrollPanel ;	//Το πλαίσιο κύλισης που θα εμφανίζεται τπ browserPanel

	//Οι διαδρομές των αρχείων βοήθειας
	private final String INTRO_FILEPATH = new String("/docs/intro.html") ;
	private final String SPLIT_HELP_FILEPATH = new String("/docs/split.html") ;
	private final String MERGE_HELP_FILEPATH = new String("/docs/merge.html") ;
	private final String OPTIONS_HELP_FILEPATH = new String("/docs/options.html") ;

	//Η διαδρομή του εικονιδίου
	private final String HELP_ICON_PATH = new String("/images/intro.png") ;

	//Σταθερές συμβολοσειρές των στοιχείων
	private final String INTRO_BUTTON_TEXT 		= new String("Intro") ;
	private final String INTRO_BUTTON_TOOLTIP	= new String("Displays the introductory help for the application") ;
	private final String SPLIT_BUTTON_TEXT 		= new String("Split") ;
	private final String SPLIT_BUTTON_TOOLTIP 	= new String("Displays help for the splitting process") ;
	private final String MERGE_BUTTON_TEXT 		= new String("Merge") ;
	private final String MERGE_BUTTON_TOOLTIP 	= new String("Displays help for the merging process") ;
	private final String OPTIONS_BUTTON_TEXT 	= new String("Options") ;
	private final String OPTIONS_BUTTON_TOOLTIP = new String("Displays help for the user options") ;
	private final String TOOLBAR_TEXT 			= new String("Contents") ;

	private final String HTML_MIME = new String("text\\html") ;


	public HelpPanel(){

		//Αρχικοποίηση του φυλλομετρητή
		//Αν το αρχείο δεν υπάρχει να εμφανιστεί το μήνυμα 404
		//που επιστρέφει η μέθοδος printError404(File fileNotFound)
		browserPanel = new JEditorPane() ;
		browserPanel.setEditable(false) ;
		try{
	 		browserPanel.setPage(this.getClass().getResource(INTRO_FILEPATH)) ;
	 	}
	 	catch(NullPointerException exception){
	 		new IOException() ;
	 	}
	 	catch(IOException exception){
	 		browserPanel.setContentType(HTML_MIME) ;
			browserPanel.setText(printError404(INTRO_FILEPATH)) ;
		}

		//Αρχικοποίηση των κουμπιών και δημιουργία ακροατών συμβάντων
		try{
			introButton = new JButton(INTRO_BUTTON_TEXT, new ImageIcon(this.getClass().getResource(HELP_ICON_PATH))) ;
		}catch(NullPointerException exception){
			introButton = new JButton(INTRO_BUTTON_TEXT) ;
		}
		introButton.setToolTipText(INTRO_BUTTON_TOOLTIP) ;
		introButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
				 		browserPanel.setPage(this.getClass().getResource(INTRO_FILEPATH)) ;
				 	}
				 	catch(NullPointerException exception){
				 		new IOException() ;
				 	}
				 	catch(IOException exception){
				 		browserPanel.setContentType(HTML_MIME) ;
						browserPanel.setText(printError404(INTRO_FILEPATH)) ;
					}
				}
			}
		) ;

		try{
			splitHelpButton = new JButton(SPLIT_BUTTON_TEXT, new ImageIcon(this.getClass().getResource(HELP_ICON_PATH))) ;
		}catch(NullPointerException exception){
			splitHelpButton = new JButton(SPLIT_BUTTON_TEXT) ;
		}
		splitHelpButton.setToolTipText(SPLIT_BUTTON_TOOLTIP) ;
		splitHelpButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
				 		browserPanel.setPage(this.getClass().getResource(SPLIT_HELP_FILEPATH)) ;
				 	}
				 	catch(NullPointerException exception){
				 		new IOException() ;
				 	}
				 	catch(IOException exception){
				 		browserPanel.setContentType(HTML_MIME) ;
						browserPanel.setText(printError404(SPLIT_HELP_FILEPATH)) ;
					}
				}
			}
		) ;

		try{
			mergeHelpButton = new JButton(MERGE_BUTTON_TEXT, new ImageIcon(this.getClass().getResource(HELP_ICON_PATH))) ;
		}catch(NullPointerException exception){
			mergeHelpButton = new JButton(MERGE_BUTTON_TEXT) ;
		}
		mergeHelpButton.setToolTipText(MERGE_BUTTON_TOOLTIP) ;
		mergeHelpButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
				 		browserPanel.setPage(this.getClass().getResource(MERGE_HELP_FILEPATH)) ;
				 	}
				 	catch(NullPointerException exception){
				 		new IOException() ;
				 	}
				 	catch(IOException exception){
				 		browserPanel.setContentType(HTML_MIME) ;
						browserPanel.setText(printError404(MERGE_HELP_FILEPATH)) ;
					}
				}
			}
		) ;

		try{
			optionsHelpButton = new JButton(OPTIONS_BUTTON_TEXT, new ImageIcon(this.getClass().getResource(HELP_ICON_PATH))) ;
		}catch(NullPointerException exception){
			optionsHelpButton = new JButton(OPTIONS_BUTTON_TEXT) ;
		}
		optionsHelpButton.setToolTipText(OPTIONS_BUTTON_TOOLTIP) ;
		optionsHelpButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
				 		browserPanel.setPage(this.getClass().getResource(OPTIONS_HELP_FILEPATH)) ;
				 	}
				 	catch(NullPointerException exception){
				 		new IOException() ;
				 	}
				 	catch(IOException exception){
				 		browserPanel.setContentType(HTML_MIME) ;
						browserPanel.setText(printError404(OPTIONS_HELP_FILEPATH)) ;
					}
				}
			}
		) ;

		//Αρχικοποίηση της εργαλειοθήκης & προσθήκη των κουμπιών σ' αυτήν
		toolbar = new JToolBar(TOOLBAR_TEXT) ;
		toolbar.add(introButton)     ;
		toolbar.add(splitHelpButton) ;
		toolbar.add(mergeHelpButton) ;
		toolbar.add(optionsHelpButton) ;

		//Aρχικοποίηση του πλαισίου κύλισης στο οποίο θα εφαρμοστεί το πλαίσιο
		//φυλλομέτρησης, έτσι ώστε να κυλάνε τα περιεχόμενά του
		scrollPanel = new JScrollPane(browserPanel) ;

		//Καθορισμός του διαχειριστή διάταξης και τοποθέτηση των στοιχείων
		setLayout(new BorderLayout()) ;
		add(toolbar, BorderLayout.NORTH) ;
		add(scrollPanel) ;


	}

	/**
	 * @return Επιστρέφει ένα html έγγραφο που προειδοποιεί για ένα σφάλμα 404 για το αρχείο που είναι στο όρισμα
	 * @param fileNotFound Αντικείμενο της τάξης java.io.File που περιγράφει το αρχείο για το οποίο δημιουργήθηκε το σφάλμα.
	 */

	private String printError404(File fileNotFound){
		return new String("<html><body><font size=\"5\" color=\"#ff0000\">Error 404</font><br><font size=\"4\" color=\"#ff0000\">The file not found!</font><p>File <font color=\"#0000ff\">" + fileNotFound.toString() + "</font> witch contains the information that would appear in this box was not found or destroyed.</p><p>To resolve the issue, please install the program again.</p></body></html>") ;
	}

	/**
	 * Υπερφορτωμένη έκδοση της μεθόδου  printError404(File)
	 * @return Επιστρέφει ένα html έγγραφο που προειδοποιεί για ένα σφάλμα 404 για το αρχείο που είναι στο όρισμα
	 * @param fileNotFound String που περιέχει τη δαδρομή του αρχείο για το οποίο δημιουργήθηκε το σφάλμα.
	 */
	private String printError404(String fileNotFoundPath){
		return printError404(new File(fileNotFoundPath)) ;
	}

}