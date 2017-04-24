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
	private final String FIND_TIP_MSG = "<- Press button to select a file" ;
	private final String LABELS_TOOLTIP = "The selected file path" ;
	private final String START_MSG = "Start" ;
	private final String FIND_MSG = "Search" ;

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
		inputButton.setToolTipText("Displays a file selection dialog") ;
		inputButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					PropertiesRW props = new PropertiesRW() ;
					JFileChooser inputFileChooser ;
					inputFileChooser = new JFileChooser() ;
					inputFileChooser.setDialogTitle("Open assembly information file") ;
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
							ExceptionMessage.showExceptionMessage(getParent(), "The file you selected does not exist!\n\nRe-select an existing file.", "Error selecting a file", true) ;
				}
			}
		) ;

		try{
			outputButton = new JButton(FIND_MSG, new ImageIcon(this.getClass().getResource("/images/find.png"))) ;
		}catch(NullPointerException exception){
			outputButton = new JButton(FIND_MSG) ;
		}
		outputButton.setToolTipText("Displays a file selection dialog") ;
		outputButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					PropertiesRW props = new PropertiesRW() ;
					JFileChooser outputFileChooser ;
					outputFileChooser = new JFileChooser() ;
					outputFileChooser.setDialogTitle("Choose a file to store the file") ;
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
							ExceptionMessage.showExceptionMessage(getParent(), "The folder you chose does not exist!\n\nRe-select an existing folder or create a new folder from\nthe appropriate folder selection box.", "Folder Selection Error", true) ;
					}
				}
			}
		) ;

		try{
			startButton = new JButton(START_MSG, new ImageIcon(this.getClass().getResource("/images/merge.png"))) ;
		}catch(NullPointerException exception){
			startButton = new JButton(START_MSG) ;
		}
		startButton.setToolTipText("Start the merge process") ;
		startButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try {

						//Καθορισμός των αρχείων Ε\Ε
						if(inputLabel.getText().equals(FIND_TIP_MSG) || inputLabel.getText().equals(FIND_MSG))
							throw new NoFilesSelectedException("You have not selected a file.\n\nTo continue, you must select the assembly information file\nand the resulting file folder\nTo do this, press the buttons \'" + inputButton.getText() + "\'") ;
						//Δημιουργία της τάξης για το αρχείο εισόδου & εξόδου
						File inputFile = new File(inputLabel.getText()) ;
						File outputFile = new File(outputLabel.getText()) ;

						//Έλεγχος για το αν είναι δυνατή η ανάγνωση, η εγγραφή ή
						//αν το μέγεθος του τεμαχίου υπερβαίνει αυτό του αρχείου
						if(!inputFile.canRead())
							throw new IOException("It is impossible to read from the assembly information file\n\nThe file does not exist, it has been corrupted\nor access to it is prohibited.") ;
						if(!outputFile.canWrite())
							throw new IOException("It is impossible to write the file that is going to merge\n\nCheck for write protection on the disc\nor if you do not have writing rights\nor if the volume has been removed") ;

						//Δημιουργία νέου νήματος για την επεξεργασία έτσι ώστε η υπόλοιπη εφαρμογή να λειτουργεί κανονικά
						Thread	merge = new MergeThread(inputFile, outputFile) ;
						merge.start() ;

					}
					catch(NoFilesSelectedException exception){
						ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Initialization error" , true) ;
					}
					catch(FileNotFoundException exception){
						ExceptionMessage.showExceptionMessage(getParent(), "Can not Merge Files\nbecause a part is missing or damaged.\nCheck if all the parts are in the\nsame folder.", "The file not found",  false) ;
					}
					catch(IOException exception){
						ExceptionMessage.showExceptionMessage(getParent(), "Can not Merge Files\nbecause a part is missing or damaged.\nCheck if all the parts are in the\nsame folder.", "Files IO error",  false) ;
					}
					catch(Exception exception){
						ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Error " + exception.getClass().toString(), false) ;
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
		inPanel.setBorder(BorderFactory.createTitledBorder("Assembly Info File")) ;
		inPanel.add(inputButton, BorderLayout.WEST) ;
		inPanel.add(inputLabel, BorderLayout.EAST) ;

		JPanel outPanel = new JPanel() ;
		outPanel.setLayout(new BorderLayout()) ;
		outPanel.setBorder(BorderFactory.createTitledBorder("Folder to merge the file")) ;
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

 		private final String WAIT_MSG = "Wait..." ;			//Ο τίτλος του κουμπιού κατά τη διάρκεια της επεξεργασίας
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
					SuccessOrFailureMessage.showSuccessMessage(getParent(), SuccessOrFailureMessage.SUCCESS_MESSAGE + "\nDuration: " + totalSec + " sec & " + totalMSec + " ms", SuccessOrFailureMessage.TITLE ) ;
				}
				else
					SuccessOrFailureMessage.showFailureMessage(getParent()) ;
			}
			catch(FileNotFoundException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "The parts can not be merged\nbecause one of the files-parts was not found.\n\nCheck to see if all the parts have been inserted\n in the same folder as the assembly information \nfile " + assemblyInfo.getName() , "Το αρχείο δεν βρέθηκε",  false) ;
			}
			catch(kFileUtils.DamagedAssemblyInfoException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "The parts can not be merged\nbecause, the file that contains the information\n for the merge is corrupted.", "Assembly Info Reading Error",  false) ;
			}
			catch(IOException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "Can not Merge Files\nbecause a part is missing or damaged.", "Files IO error",  false) ;
			}
			catch(Exception exception){
				if(exception.getLocalizedMessage() != null)
					ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Error", false) ;
				else
					ExceptionMessage.showExceptionMessage(getParent(), exception.toString(), "Error", false) ;
			}
			catch(OutOfMemoryError error){
				ExceptionMessage.showExceptionMessage(getParent(), "The file can not be merged\n\nThe Java Virtual Machine that you have installed\ncan not bind memory more than\n" + Runtime.getRuntime().totalMemory() + " Bytes.\n\nThe application will try to release resources\ntry again or tell the file sender to set parts size smaller\nthan the maximum memory..", "Out of memory", false) ;
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
