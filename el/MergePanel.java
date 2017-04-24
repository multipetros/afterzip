import kFileUtils.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;

/**
 * Τάξη δημιουργίας του γραφικού περιβάλλοντος για την συγχώνευση των τεμαχίων
 * @version 1.0.7
 * @serial 040804
 * @since  031210
 */
class MergePanel extends JPanel {

	private JButton inputButton ;	//Κουμπί αναζήτησης του αρχείου εισόδου
	private JButton outputButton ;	//Κουμπί αναζήτησης του αρχείου εξόδου
	private JButton startButton ;	//Κουμπί έναρξης της συγχώνευσης
	private JLabel inputLabel ;		//Eτικέτα που εμφανίζει το αρχείου πληροφοριών συγχώνευσης μιας ομάδας τεμαχίων
	private JLabel outputLabel ;	//Eτικέτα που εμφανίζει τον κατάλογο αποθήκευσης του αρχείου που θα δημιουργηθεί

	//Σταθερές που περιέχουν τα Strings του περιβάλλοντος
	private final String FIND_TIP_MSG = "<- Για να επιλέξετε κάποιο αρχείο κάντε πατήστε το κουμπί" ;
	private final String LABELS_TOOLTIP = "Εδώ φαίνεται το όνομα του αρχείου που έχετε επιλέξει" ;
	private final String START_MSG = "Έναρξη" ;
	private final String FIND_MSG = "Αναζήτηση" ;

	public MergePanel(){

		//Δημιουργία των στοιχείων

		//Δημιουργία του κουμπιού αναζήτησης του αρχείου εισόδου
		//και ακροατή ενέργειας
		try{
			inputButton = new JButton(FIND_MSG, new ImageIcon(this.getClass().getResource("/images/find.png"))) ;
		}
		catch(NullPointerException excetption){
			inputButton = new JButton(FIND_MSG) ;
		}
		inputButton.setToolTipText("Εμφανίζει ένα πλαίσιο επιλογής αρχείων") ;
		inputButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					PropertiesRW props = new PropertiesRW() ;
					JFileChooser inputFileChooser ;
					inputFileChooser = new JFileChooser() ;
					inputFileChooser.setDialogTitle("ʼνοιγμα αρχείου πληροφοριών συγχώνευσης") ;
					inputFileChooser.setMultiSelectionEnabled(false) ;
					inputFileChooser.setFileFilter(new AssemblyInfoFileFilter()) ;
					//Αν έχει επιλεχθεί από πριν το αρχείο εισόδου η αναζήτηση να ξεκινίσει απ' αυτό το σημείο
					if(!inputLabel.getText().equals(FIND_TIP_MSG))
						inputFileChooser.setCurrentDirectory(new File(inputLabel.getText())) ;
					//Αν έχει επιλεχθεί κάποιο αρχείο εξόδου ο τρέχων κατάλογος να είναι ίδιος μ' αυτόν
					else if(!outputLabel.getText().equals(FIND_TIP_MSG))
						inputFileChooser.setCurrentDirectory(new File(outputLabel.getText())) ;
					//Αλλιώς να ξεκινήσει από την διαδρομή που υπάρχει στο αρχείο ιδιοτήτων
					else
						inputFileChooser.setCurrentDirectory(new File(props.getInMergeFolder())) ;

					inputFileChooser.setLocale(java.util.Locale.getDefault()) ;
					inputFileChooser.showOpenDialog(getParent()) ;
					if(inputFileChooser.getSelectedFile() != null)
						if(inputFileChooser.getSelectedFile().exists()) {
							inputLabel.setText(inputFileChooser.getSelectedFile().getAbsolutePath()) ;
							//Αν δεν έχει επιλεχθεί ή εξοδος να οριστεί ίδια με την είσοδο
							if(outputLabel.getText().equals(FIND_TIP_MSG))
								outputLabel.setText(inputFileChooser.getCurrentDirectory().getAbsolutePath()) ;
							//Αποθήευση της διαδρομής στο αρχείο ιδιοτήτων
							props.setInMergeFolder(inputFileChooser.getCurrentDirectory().getAbsolutePath()) ;
							props.storeProperties() ;
						}
						else
							ExceptionMessage.showExceptionMessage(getParent(), "Το αρχείο που επιλέξατε δεν υφίσταται!\n\nΕπιλέξτε ξανά ένα υπαρκτό αρχείο.", "Σφάλμα επιλογής αρχείου", true) ;
				}
			}
		) ;

		try{
			outputButton = new JButton(FIND_MSG, new ImageIcon(this.getClass().getResource("/images/find.png"))) ;
		}catch(NullPointerException exception){
			outputButton = new JButton(FIND_MSG) ;
		}
		outputButton.setToolTipText("Εμφανίζει ένα πλαίσιο επιλογής αρχείων") ;
		outputButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					PropertiesRW props = new PropertiesRW() ;
					JFileChooser outputFileChooser ;
					outputFileChooser = new JFileChooser() ;
					outputFileChooser.setDialogTitle("Επιλογή φακέλου αποθήκευσης του αρχείου") ;
					outputFileChooser.setMultiSelectionEnabled(false) ;
					outputFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY) ;
					//Αν έχει επιλεχθεί από πριν ο φάκελος εξόδου η αναζήτηση να ξεκινίσει απ' αυτόν
					if(!outputLabel.getText().equals(FIND_TIP_MSG))
						outputFileChooser.setCurrentDirectory(new File(outputLabel.getText())) ;
					//Αν έχει επιλεχθεί κάποιο αρχείο εισόδου ο τρέχων κατάλογος να είναι ίδιος μ' αυτόν
					else if(!inputLabel.getText().equals(FIND_TIP_MSG))
						outputFileChooser.setCurrentDirectory(new File(inputLabel.getText())) ;
					//Αλλιώς να ξεκινήσει από την διαδρομή που υπάρχει στο αρχείο ιδιοτήτων
					else
						outputFileChooser.setCurrentDirectory(new File(props.getOutMergeFolder())) ;

					outputFileChooser.setLocale(java.util.Locale.getDefault()) ;
					outputFileChooser.showSaveDialog(getParent()) ;
					if(outputFileChooser.getSelectedFile() != null){
						if(outputFileChooser.getSelectedFile().exists()){
							outputLabel.setText(outputFileChooser.getSelectedFile().getAbsolutePath()) ;
							//Αποθήευση της διαδρομής στο αρχείο ιδιοτήτων
							props.setOutMergeFolder(outputFileChooser.getCurrentDirectory().getAbsolutePath()) ;
							props.storeProperties() ;
						}
						else
							ExceptionMessage.showExceptionMessage(getParent(), "O φάκελος που επιλέξατε δεν υφίσταται!\n\nΕπιλέξτε ξανά έναν υπαρκτό φάκελο ή δημιουργείστε έναν νέο\nαπό το κατάλληλο κουμπί του πλαισίου επιλογής φακέλου.", "Σφάλμα επιλογής φακέλου", true) ;
					}
				}
			}
		) ;

		try{
			startButton = new JButton(START_MSG, new ImageIcon(this.getClass().getResource("/images/merge.png"))) ;
		}catch(NullPointerException exception){
			startButton = new JButton(START_MSG) ;
		}
		startButton.setToolTipText("Εκκίνηση της διαδικασίας συγχώνευσης") ;
		startButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try {

						//Καθορισμός των αρχείων Ε\Ε
						if(inputLabel.getText().equals(FIND_TIP_MSG) || inputLabel.getText().equals(FIND_MSG))
							throw new NoFilesSelectedException("Δεν έχετε επιλέξει κάποιο αρχείο.\n\nΓια να συνεχίσετε πρέπει να επιλέξετε το αρχείο πληροφοριών συνένωσης\nκαι ο φάκελος του αρχείου που θα προκύψει\nΓια να το κάνενε αυτό πατήστε τα κουμπιά \'" + inputButton.getText() + "\'") ;
						//Δημιουργία της τάξης για το αρχείο εισόδου & εξόδου
						File inputFile = new File(inputLabel.getText()) ;
						File outputFile = new File(outputLabel.getText()) ;

						//Έλεγχος για το αν είναι δυνατή η ανάγνωση, η εγγραφή ή
						//αν το μέγεθος του τεμαχίου υπερβαίνει αυτό του αρχείου
						if(!inputFile.canRead())
							throw new IOException("Η ανάγνωση από το αρχείο πληροφοριών συνένωσης είναι αδύνατη\n\nTo αρχείο δεν υπάρχει, έχει καταστραφεί\nή η πρόσβαση σ' αυτό είναι απαγορευμένη.") ;
						if(!outputFile.canWrite())
							throw new IOException("Η εγγραφή του αρχείου που θα προκείψει είναι αδύνατη\n\nΕλέγξτε αν υπάρχει προστασία εγγραφής στον δίσκο\nή αν δεν έχετε δικαιώματα εγγραφής\nή εάν ο τόμος έχει καταργηθεί") ;

						//Δημιουργία νέου νήματος για την επεξεργασία έτσι ώστε η υπόλοιπη εφαρμογή να λειτουργεί κανονικά
						Thread	merge = new MergeThread(inputFile, outputFile) ;
						merge.start() ;

					}
					catch(NoFilesSelectedException exception){
						ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Σφάλμα αρχικοποίησης" , true) ;
					}
					catch(FileNotFoundException exception){
						ExceptionMessage.showExceptionMessage(getParent(), "Δεν είναι δυνατή η Συγχώνευση των αρχείων\nδιότι ένα τεμμάχιο λείπει ή είναι κατεστραμμένο.\nΕλέξτε αν έχετε τοποθετήσει όλα τα τεμμάχια\nστον ίδιο φάκελο.", "Το αρχείο δεν βρέθηκε",  false) ;
					}
					catch(IOException exception){
						ExceptionMessage.showExceptionMessage(getParent(), "Δεν είναι δυνατή η Συγχώνευση των αρχείων\nδιότι ένα τεμμάχιο λείπει ή είναι κατεστραμμένο.\nΕλέξτε αν έχετε τοποθετήσει όλα τα τεμμάχια\nστον ίδιο φάκελο.", "Σφάλμα εισόδου-εξόδου αρχείων",  false) ;
					}
					catch(Exception exception){
						ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Σφάλμα " + exception.getClass().toString(), false) ;
					}
				}
			}
		) ;

		inputLabel = new JLabel(FIND_TIP_MSG) ;
		inputLabel.setToolTipText(LABELS_TOOLTIP) ;
		outputLabel = new JLabel(FIND_TIP_MSG) ;
		outputLabel.setToolTipText(LABELS_TOOLTIP) ;

		JPanel inPanel = new JPanel() ;
		inPanel.setLayout(new BorderLayout()) ;
		inPanel.setBorder(BorderFactory.createTitledBorder("Αρχείο πληροφοριών συγχώνευσης")) ;
		inPanel.add(inputButton, BorderLayout.WEST) ;
		inPanel.add(inputLabel, BorderLayout.EAST) ;

		JPanel outPanel = new JPanel() ;
		outPanel.setLayout(new BorderLayout()) ;
		outPanel.setBorder(BorderFactory.createTitledBorder("Φάκελος αποθήκευσης του αρχείου")) ;
		outPanel.add(outputButton, BorderLayout.WEST) ;
		outPanel.add(outputLabel, BorderLayout.EAST) ;

		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)) ;
		add(inPanel) ;
		add(outPanel);
		add(startButton) ;

	}

	/**
 	* Εσωρερική τάξη που εφαρμόζει πολυνηματικό προγραμματισμό στη τάξη Merge
 	* Όταν ξεκινάει το νήμα, αλλάζει τον τίτλο του κουμπιού σε π.χ.: "Περιμένετε"
 	* και κατόπιν ξεκινά η επεξεργασία στο τέλος της οποίας ή αν κατά τη διάρκεια
 	* αυτής εμφανίζονται τα ανάλογα μηνύματα & αλλάζει πάλι ο τίτλος του κουμπιού.
 	*/
	class MergeThread extends Thread {

 		private kFileUtils.Merge merge = new kFileUtils.Merge() ;	//Αντικείμενο τηε τάξης συγχώνευσης αρχείων
 		private File assemblyInfo ;		//Στιγμιότοιπο της τάξης File που αναπαριστά το αρχείο πληροφοριών συγχώνευσης
 		private File outputDir ;		//Στιγμιότοιπο της τάξης File που αναπαριστά τον φάκελο που θα τοποθετηθεί το αρχείο που θα προκύψει

 		private final String WAIT_MSG = "Περιμένετε..." ;			//Ο τίτλος του κουμπιού κατά τη διάρκεια της επεξεργασίας
 		private final String START_MSG = startButton.getText() ;	//Ο κανονικός τίτλος του κουμπιού

 		public MergeThread(File assemblyInfo, File outputDir){
 			//Aρχικοποίηση των μεταβλτών του νήματος
			this.assemblyInfo = assemblyInfo ;
			this.outputDir = outputDir ;
 		}

 		/* Μέθοδος που καλείται από την VM *
 		 * κατά την εκκίνηση του νήματος   */
	 	public void run(){
	 		//Μεταβλητές αποθήκευσης χρονομέτρων
	 		long startTime = System.currentTimeMillis() ;	//Ο χρόνος κατά την έναρξη
	 		long totalTime ;	//Ο χρόνος κατά τον τερματισμός
	 		long totalSec ;		//Τα δευτερόλεπτα της διάρκειας
	 		long totalMSec ;	//Τα χιλιοστά της διάρκειας

	 		//Έναρξη της διαδικασίας συγχώνευσης και εμφάνιση του ανάλογου
	 		//μηνύματος ή της εξαίρεσης που συνελήθφει
 			try{
 				//Aλλαγή του τίτλου του κουμπιού & απενεργοποίησή του ώστε να μην μπορεί ο χρήστης να ξεκινήσει άλλη διαδικασία
	 			startButton.setText(WAIT_MSG) ;
	 			startButton.setEnabled(false) ;

				//Αρχικοποίηση των απαραίτητων μεταβλητών της τάξης συγχόνευσης και έναρξη της διαδικασίας
				//& εμφάνιση του ανάλογου μηνύματος επι/αποτυχίας μετά το πέρας αυτής
				merge.setAssemblyInfo(assemblyInfo) ;
 				merge.setOutputDirName(outputDir) ;

				if(merge.startMerge()){
					//Αφαίρεση του χρόνου έναρξης με τον τρέχοντα για εύρεση της διάρκειας
					//Αποκοπή των δευτερολέπτων και των χιλιοστών
					totalTime = System.currentTimeMillis() - startTime ;
					totalSec = totalTime / 1000 ;
					totalMSec = totalTime - (totalSec * 1000) ;
					SuccessOrFailureMessage.showSuccessMessage(getParent(), SuccessOrFailureMessage.SUCCESS_MESSAGE + "\nΔιάρκεια: " + totalSec + " δευτ. & " + totalMSec + " χιλ.", SuccessOrFailureMessage.TITLE ) ;
				}
				else
					SuccessOrFailureMessage.showFailureMessage(getParent()) ;
			}
			catch(FileNotFoundException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "Δεν είναι δυνατή η συγχώνευση των τεμμαχίων\nδιότι, ένα από τα αρχεία-τεμμάχια δεν βρέθηκε.\n\nΕλέξτε αν έχετε τοποθετήσει όλα τα τεμμάχια\n στον ίδιο φάκελο με το αρχείο πληροφοριών\nσυγχώνευσης " + assemblyInfo.getName() , "Το αρχείο δεν βρέθηκε",  false) ;
			}
			catch(kFileUtils.DamagedAssemblyInfoException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "Δεν είναι δυνατή η συγχώνευση των τεμμαχίων\nδιότι, το αρχείο που περιέχει τις πληροφορίες\n συγχώνευσης είναι κατεστραμμένο.", "Σφάλμα ανάγνωσης πληροφοριών συγχώνευσης",  false) ;
			}
			catch(IOException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "Δεν είναι δυνατή η συγχώνευση των τεμμαχίων\nδιότι, ένα από τα αρχεία-τεμμάχια είναι κατεστραμμένο.", "Σφάλμα εισόδου-εξόδου αρχείων",  false) ;
			}
			catch(Exception exception){
				if(exception.getLocalizedMessage() != null)
					ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Σφάλμα", false) ;
				else
					ExceptionMessage.showExceptionMessage(getParent(), exception.toString(), "Σφάλμα", false) ;
			}
			catch(OutOfMemoryError error){
				ExceptionMessage.showExceptionMessage(getParent(), "Δεν είναι δυνατή η συγχώνευση του αρχείου\n\nΗ εικονική μηχανή Java που έχετε εγκαταστήσει\nδεν μπορεί να δεσμεύσει μνήμη περισσότερη από\n" + Runtime.getRuntime().totalMemory() + " Bytes.\n\nΗ εφαρμογή θα προσπαθήσει να αποδεσμεύσει\nπόρους, δοκιμάστε ξανά ή ορίστε ένα μέγεθος\nκομματικού μικρότερο από το μέγιστο της μνήμης.", "Εκτός μνήμης", false) ;
				//Καθαρισμός της μνήμης από άχρηστα αντικείμενα
				System.gc() ;
			}
			finally{
				//Επαναφορά του τίτλου του κουμπιού & ενεργοποίησή του
				startButton.setText(START_MSG) ;
				startButton.setEnabled(true) ;
			}
 		}
	}
}
