import kFileUtils.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;

/**
 * Τάξη δημιουργίας του περιβάλλοντος της διαδικασίας τεμαχισμού
 * @author Πέτρος Φ. Κυλαδίτης
 * @version 1.0.3
 * @serial 040729
 * @since 031220
 */
class SplitPanel extends JPanel {

	private JLabel inputLabel ;				//Eτικέτα που εμφανίζει το αρχείου προς τεμαχισμό
	private JLabel outputLabel ;			//Ετικέτα που εμφανίζει το κοινό όνομα των αρχείων που θα παραχθούν
	private JTextField segmentSizeTxt ;		//Πεδίο εισόδου του μεγέθους του τεμαχίου
	private JButton inputButton ;			//Κουμπί αναζήτησης του αρχείου εισόδου
	private JButton outputButton ;			//Κουμπί αναζήτησης του αρχείου εξόδου
	private JButton startButton ;			//Κουμπί εκκίνισης του τεμαχισμού
	private JRadioButton bytesRadioButton ;	//Κουμπί επιλογής προσδιορισμού του μεγέθους σε Bytes
	private JRadioButton kbRadioButton ;	//Κουμπί επιλογής προσδιορισμού του μεγέθους σε KBytes
	private JRadioButton mbRadioButton ;	//Κουμπί επιλογής προσδιορισμού του μεγέθους σε MBytes
	private ButtonGroup mezurmentGroup ;	//Ομαδοποίηση των κουμπιών επιλογής
	private JComboBox segmentSizesList ;	//Λίστα επιλογής έτοιμων μεγεθών τεμαχίων
	private byte currentSizePower = 1 ;		//Η δύναμη στην οποία είναι υψωμένο το μέγεθος των κομματιών

	//Εσωτερική τάξη - ακροατής συμβάντων αλλαγής του επιλεγμένου κουμπιού
	private MezurmentsChangeListener mezurmentsChangeListener ;

	//Σταθερές που περιέχουν τα Strings του περιβάλλοντος
	private final String FIND_MSG = "<- Press button to select a file" ;
	private final String LABELS_TOOLTIP = "The selected file path" ;
	private final String[] SIZE_LIST_STRINGS = {"User selection", "Floppy 3,5\'", "CD-ROM 650MB", "CD-ROM 700MB", "mini CD-ROM 180MB", "mini CD-ROM 200MB", "Click! 40MB", "Zip 100ΜΒ", "Zip 250MB", "Zip 750MB"} ;

	public SplitPanel(){

		//Δημιουργία των στοιχείων του περιβάλλοντος

		//Δημιουργία ετικετών
		inputLabel = new JLabel(FIND_MSG) ;
		inputLabel.setToolTipText(LABELS_TOOLTIP) ;
		outputLabel = new JLabel(FIND_MSG) ;
		outputLabel.setToolTipText(LABELS_TOOLTIP) ;

		//Αρχικοποίηση του πεδίου εισαγωγής κειμένου
		segmentSizeTxt = new JTextField("0") ;
		segmentSizeTxt.setToolTipText("Give the size that you want the pieces to come up with") ;

		//Δημιουργία λίστας επιλογής έτοιμων μεγεθών τεμαχίων
		segmentSizesList = new JComboBox(SIZE_LIST_STRINGS) ;
		segmentSizesList.setToolTipText("Click here to select a preset size from the list") ;
		segmentSizesList.setEditable(false) ;
		segmentSizesList.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent e){
					//Με κάθε αλλαγή της επιλογής να έλεγχος για το νέο επιλεγμένο στοιχείο
					//καθορισμός της ανάλογης τιμής,
					//απενεργοποίηση της χειροκίνητης ρύθμισης,
					//ενεργοποίηση του πλήκτου σήμανσης των bytes,
					//καθορισμός της δύναμης αναπαράστασης των bytes σε 1
					switch(segmentSizesList.getSelectedIndex()){
						case 1 :
							segmentSizeTxt.setText("1457664") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						case 2 :
							segmentSizeTxt.setText("681574400") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						case 3 :
							segmentSizeTxt.setText("734003200") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						case 4 :
							segmentSizeTxt.setText("188743680") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						case 5 :
							segmentSizeTxt.setText("209715200") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						case 6 :
							segmentSizeTxt.setText("40257536") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						case 7 :
							segmentSizeTxt.setText("100348723") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						case 8 :
							segmentSizeTxt.setText("249561088") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						case 9 :
							segmentSizeTxt.setText("751828992") ;
							segmentSizeTxt.setEnabled(false) ;
							bytesRadioButton.setSelected(true) ;
							currentSizePower = 1 ;
							break ;
						default :
							//Ενεργοποίηση της χειροκίνητης εισαγωγής τιμών
							segmentSizeTxt.setEnabled(true) ;
					}
				}
			}
		) ;


		//Δημιουργία των κουμπιών του περιβάλλοντος

		//Δημιουργία του κουμπιού αναζήτησης του αρχείου εισόδου
		try{
			inputButton = new JButton("Search", new ImageIcon(this.getClass().getResource("/images/find.png"))) ;
		}catch(NullPointerException exception){
			inputButton = new JButton("Search") ;
		}
		inputButton.setToolTipText("Displays a file selection dialog") ;
		inputButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					PropertiesRW props = new PropertiesRW() ;
					JFileChooser inputFileChooser ;
					inputFileChooser = new JFileChooser() ;
					inputFileChooser.setDialogTitle("Select file to be split") ;
					inputFileChooser.setMultiSelectionEnabled(false) ;
					inputFileChooser.setDialogType(JFileChooser.FILES_ONLY) ;
					//Αν έχει επιλεχθεί κάποιο αρχείο εισόδου η αναζήτηση να αρχίσει απο το ίδιο σημείο
					if(!inputLabel.getText().equals(FIND_MSG))
						inputFileChooser.setCurrentDirectory(new File(inputLabel.getText())) ;
					//Αν έχει επιλεχθεί κάποιο αρχείο εξόδου να οριστεί αυτός ο τρέχων κατάλογος αναζήτησης
					else if(!outputLabel.getText().equals(FIND_MSG))
						inputFileChooser.setCurrentDirectory(new File(outputLabel.getText())) ;
					//Αλλιώς να ξεκινήσει από την διαδρομή που υπάρχει στο αρχείο ιδιοτήτων
					else
						inputFileChooser.setCurrentDirectory(new File(props.getInSplitFolder())) ;

					inputFileChooser.setLocale(java.util.Locale.getDefault()) ;
					inputFileChooser.showOpenDialog(getParent()) ;
					//Αν επιλέχθηκε κάποιο αρχείο & δεν πατήθηκε το άκυρο
					//έλεγχος αν το αρχείο υπάρχει, αλλιώς να εμφανιστεί μήνυμα
					if(inputFileChooser.getSelectedFile() != null)
						if(inputFileChooser.getSelectedFile().exists()) {
							inputLabel.setText(inputFileChooser.getSelectedFile().getAbsolutePath()) ;
							//Αν δεν έχει επιλεχθεί κάποιο αρχείο για έξοδο να οριστεί το ίδιο με αυτό της εισόδου
							if(outputLabel.getText().equals(FIND_MSG))
								outputLabel.setText(inputLabel.getText()) ;
							//Αποθήκευση της διαδρομής στο αρχείο ιδιοτήτων
							props.setInSplitFolder(inputFileChooser.getCurrentDirectory().getAbsolutePath()) ;
							props.storeProperties() ;
						}
						else
							ExceptionMessage.showExceptionMessage(getParent(), "The file you selected does not exist!\n\nChoose an existing file again.", "Error selecting a file", true) ;
				}
			}
		) ;

		//Δημιουργία του κουμπιού αναζήτησης του αρχείου εξόδου
		try{
			outputButton = new JButton("Search", new ImageIcon(this.getClass().getResource("/images/find.png"))) ;
		}catch(NullPointerException exception){
			outputButton = new JButton("Search") ;
		}
		outputButton.setToolTipText("Displays a file selection dialog") ;
		outputButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					PropertiesRW props = new PropertiesRW() ;
					JFileChooser outputFileChooser ;
					outputFileChooser = new JFileChooser() ;
					outputFileChooser.setDialogTitle("Choosing a common file name for parts") ;
					outputFileChooser.setMultiSelectionEnabled(false) ;
					outputFileChooser.setDialogType(JFileChooser.FILES_ONLY) ;
					//Aν έχει επιλεχθεί κάποιο αρχείο εξόδου η αναζήτηση να ξεκινήσει απ' αυτό το σημείο
					if(!outputLabel.getText().equals(FIND_MSG))
						outputFileChooser.setCurrentDirectory(new File(outputLabel.getText())) ;
					//Aν έχει επιλεχθεί κάποιο αρχείο εισόδου η αναζήτηση να αρχίσει από τον ίδιο κατάλογο
					else if(!inputLabel.getText().equals(FIND_MSG))
						outputFileChooser.setCurrentDirectory(new File(inputLabel.getText())) ;
					//Αλλιώς να ξεκινήσει από την διαδρομή που υπάρχει στο αρχείο ιδιοτήτων
					else
						outputFileChooser.setCurrentDirectory(new File(props.getOutSplitFolder())) ;

					outputFileChooser.setLocale(java.util.Locale.getDefault()) ;
					outputFileChooser.showSaveDialog(getParent()) ;
					if(outputFileChooser.getSelectedFile() != null){
						//Έλεγχος αν ο φάκελος που επέλεχθει να αποθηκευτούν τα αρχεία υπάρχει
						if(outputFileChooser.getSelectedFile().getParentFile().exists()){
							outputLabel.setText(outputFileChooser.getSelectedFile().getAbsolutePath()) ;
							//Αποθήκευση της διαδρομής στο αρχείο ιδιοτήτων
							props.setOutSplitFolder(outputFileChooser.getCurrentDirectory().getAbsolutePath()) ;
							props.storeProperties() ;
						}
						else ExceptionMessage.showExceptionMessage(getParent(), "The folder you chose to save the files that will be created does not exist!\n\nRe-select an existing folder or create a new\nfrom the appropriate file storage button.", "Error selecting a file", true) ;
					}
				}
			}
		) ;

		//Δημιουργία του κουμπιού έναρξης του τεμαχισμού
		try{
			startButton = new JButton("Start", new ImageIcon(this.getClass().getResource("/images/split.png"))) ;
		}catch(NullPointerException exception){
			startButton = new JButton("Start") ;
		}
		startButton.setToolTipText("Start the split process") ;
		startButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{

						//Καθορισμός των αρχείων Ε\Ε
						if(inputLabel.getText().equals(FIND_MSG) || inputLabel.getText().equals(FIND_MSG))
							throw new NoFilesSelectedException("You have not selected a file.\n\nTo continue, you must select the file to be splitted\nand the name of the resulting files (parts)\nTo do this, press the buttons \'" + inputButton.getText() + "\'") ;
						//Δημιουργία της τάξης για το αρχείο εισόδου & εξόδου
						File inputFile = new File(inputLabel.getText()) ;
						File outputFile = new File(outputLabel.getText()) ;

						//Ορισμός μεταβλητής που αναπαριστά το μέγεθος του τεμαχίου
						//Εκτέλεση μετατροπής στις μονάδες μέτρησης, στην πρώτη δύναμη δηλαδή σε bytes
						//εκτός και εάν έχουν ήδη επιλεχθεί σαν μονάδες τα bytes
						long segmentSize ;
						if(mbRadioButton.isSelected())
							segmentSize = ((long)(convertSegmentSize(Double.parseDouble(segmentSizeTxt.getText()), currentSizePower, (byte)1))) ;
						else if(kbRadioButton.isSelected())
							segmentSize = ((long)(convertSegmentSize(Double.parseDouble(segmentSizeTxt.getText()), currentSizePower, (byte)1))) ;
						else
							segmentSize = Long.parseLong(segmentSizeTxt.getText()) ;


						//Έλεγχος για το αν είναι δυνατή η ανάγνωση, η εγγραφή ή
						//αν το μέγεθος του τεμαχίου υπερβαίνει αυτό του αρχείου
						if(!inputFile.canRead())
							throw new IOException("Reading from file to split is impossible\n\nThe file does not exist or access to it is forbidden.") ;
//Απενεργοποιημένο διότι η Εικονική Μηχανή
// εμφανίζει σφάλματα όποτε της καπνίσει
//						if(!outputFile.canWrite())
//							throw new IOException("Η εγγραφή των αρχείων που θα προκείψουν είναι αδύνατη\n\nΕλέγξτε αν υπάρχει προστασία εγγραφής στον δίσκο\nή αν δεν έχετε δικαιώματα εγγραφής\nή εάν ο τόμος έχει καταργηθεί") ;
						if(segmentSize > inputFile.length())
							throw new BadSegmentSizeException("The size you specified for each piece exceeds the total size of the file.\nTo continue submit a smaller size for each piece.") ;
						if(segmentSize <= 0)
							throw new NumberFormatException() ;

						//Δημιουργία νέου νήματος για την επεξεργασία έτσι ώστε η υπόλοιπη εφαρμογή να λειτουργεί κανονικά
						Thread split = new SplitThread(inputFile, outputFile, segmentSize) ;
						split.start() ;


					}
					catch(NumberFormatException exception){
						ExceptionMessage.showExceptionMessage(getParent(), "The number you provided for the piece size is invalid.\nPlease provide a valid number to continue.", "Number conversion error", true ) ;
					}
					catch(NoFilesSelectedException exception){
						ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Initialization error", true) ;
					}
					catch(BadSegmentSizeException exception){
						ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Initialization error" , true) ;
					}
					catch(IOException exception){
						ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "File IO error", false) ;
					}
					catch(Exception exception){
							ExceptionMessage.showExceptionMessage(getParent(), exception.getClass().toString() + "/n" + exception.getLocalizedMessage() , "Error", false ) ;
					}
				}
			}
		) ;

		//Δημιουργία κουμπιών επιλογής μονάδων μέτρησης & των ακροατών τους
		//ο ακροατής είναι ίδιος για όλα, διαφέρει μονάχα η εντολή, η οποία
		//καθορίζει την δύναμη στην οποία θα μετατραπούν οι μονάδες
		mezurmentsChangeListener = new MezurmentsChangeListener() ;

		bytesRadioButton = new JRadioButton("Bytes") ;
		bytesRadioButton.setSelected(true) ;
		bytesRadioButton.setActionCommand("1") ;
		bytesRadioButton.addActionListener(mezurmentsChangeListener) ;

		kbRadioButton = new JRadioButton("KB") ;
		kbRadioButton.setToolTipText("1 Kilobyte = 1.024 Bytes") ;
		kbRadioButton.setActionCommand("10") ;
		kbRadioButton.addActionListener(mezurmentsChangeListener) ;

		mbRadioButton = new JRadioButton("MB") ;
		mbRadioButton.setToolTipText("1 Megabyte = 1.048.576 Bytes") ;
		mbRadioButton.setActionCommand("20") ;
		mbRadioButton.addActionListener(mezurmentsChangeListener) ;



		//Δημιουργία της ομάδας των κουμπιών επιλογής
		mezurmentGroup = new ButtonGroup() ;
		mezurmentGroup.add(bytesRadioButton) ;
		mezurmentGroup.add(kbRadioButton) ;
		mezurmentGroup.add(mbRadioButton) ;

		//Ταξιθέτηση των στοιχείων με τη χρήση Panels
		JPanel inPanel = new  JPanel() ;
		inPanel.setBorder(BorderFactory.createTitledBorder("File to split")) ;
		inPanel.setLayout(new FlowLayout()) ;
		inPanel.add(inputButton) ;
		inPanel.add(inputLabel) ;

		JPanel outPanel = new JPanel() ;
		outPanel.setBorder(BorderFactory.createTitledBorder("Pieces common filename")) ;
		outPanel.setLayout(new FlowLayout()) ;
		outPanel.add(outputButton) ;
		outPanel.add(outputLabel) ;

		JPanel mezurmentPanel = new JPanel() ;
		mezurmentPanel.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		mezurmentPanel.add(bytesRadioButton) ;
		mezurmentPanel.add(kbRadioButton) ;
		mezurmentPanel.add(mbRadioButton) ;

		JPanel sizeTopPanel = new JPanel() ;
		sizeTopPanel.setLayout(new GridLayout(1,2)) ;
		sizeTopPanel.add(segmentSizeTxt) ;
		sizeTopPanel.add(mezurmentPanel) ;

		JPanel segmentSizePanel = new JPanel() ;
		segmentSizePanel.setLayout(new BorderLayout()) ;
		segmentSizePanel.setBorder(BorderFactory.createTitledBorder("Size of each piece")) ;
		segmentSizePanel.add(sizeTopPanel, BorderLayout.NORTH) ;
		segmentSizePanel.add(segmentSizesList, BorderLayout.SOUTH) ;

		JPanel sizeAndStartPanel = new JPanel() ;
		sizeAndStartPanel.setLayout(new BorderLayout(2,1)) ;
		sizeAndStartPanel.add(segmentSizePanel, BorderLayout.WEST) ;
		sizeAndStartPanel.add(startButton, BorderLayout.EAST) ;

		setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		add(inPanel) ;
		add(outPanel) ;
		add(sizeAndStartPanel) ;

	}

	/**
	 *Εσωτερική τάξη - ακροατής συμβάτνων αλλαγής κατάστασης
	 *στα κουμπιά επιλογής των μονάδων μέτρησης.
	 *Μετατροπή του String της εντολής σε αριθμό & κλήση με αυτό
	 *της μεθόδου μετατροπής μονάδων. Μετά πέρασμα της επιστρεφόμενης
	 *τιμής της μεθόδου στην μέθοδο setText του πλαισίου εισαγωγής
	 */
	class MezurmentsChangeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			byte toPower = Byte.parseByte(e.getActionCommand())	;
			double currentSize = 0 ;
			try{
				//Δοκιμή για μετατροπή της τιμής, σε περίπτωση σφάλματος διατήρηση της μηδενικής τιμής
				currentSize = Double.parseDouble(segmentSizeTxt.getText()) ;
			}catch(NumberFormatException exception){}
			double newSegmentSize = convertSegmentSize(currentSize, currentSizePower , toPower) ;

			//Αν επιλεχθούν τα bytes, να γίνει μετατροπή από double σε long
			if(toPower == 1)
				segmentSizeTxt.setText(Long.toString((long)newSegmentSize)) ;
			else
				segmentSizeTxt.setText(Double.toString(newSegmentSize)) ;

			currentSizePower = toPower ;
		}
	}


	/**
	 * Μέθοδος μετατροπής μονάδων: Bytes - KBytes - MBytes.
	 * @param sizeToConver Το μέθεθος προς μετατροπή
	 * @param toPower Η δύναμη στην οποία θα γίνει η μετατροπή (1,10,20)
	 * @return Το νέο μέγεθος που προέκυψε από τη μετατροπή
	 */
	private double convertSegmentSize(double sizeToConver, byte currentPower, byte toPower){

		//To νέο μέγεθος που θα προκύψει από τη μετατροπή
		double newSize ;

		// Bytes -> KBytes : bytes / 2^10
		// Bytes -> MBytes : bytes / 2^20
		//KBytes -> MBytes : bytes / 2^10
		//KBytes -> Bytes  : bytes * 2^10
		//MBytes -> Bytes  : bytes * 2^20
		//MBytes -> KBytes : bytes * 2^10

		if(currentPower == 1)
			newSize = sizeToConver / Math.pow(2, toPower) ;
		else if(currentPower == 10){
			if(toPower > currentPower)
				newSize = sizeToConver / Math.pow(2, 10) ;
			else
				newSize = sizeToConver * Math.pow(2, 10) ;
		}
		else {
			if(toPower == 10)
				newSize = sizeToConver * Math.pow(2, 10) ;
			else
				newSize = sizeToConver * Math.pow(2, 20) ;
		}

		return newSize ;
	}

	/**
 	* Εσωρερική τάξη που εφαρμόζει πολυνηματικό προγραμματισμό στη τάξη Merge
 	* Όταν ξεκινάει το νήμα, αλλάζει τον τίτλο του κουμπιού σε π.χ.: "Περιμένετε"
 	* και κατόπιν ξεκινά η επεξεργασία στο τέλος της οποίας ή αν κατά τη διάρκεια
 	* αυτής εμφανίζονται τα ανάλογα μηνύματα & αλλάζει πάλι ο τίτλος του κουμπιού.
 	*/
	class SplitThread extends Thread {

 		private kFileUtils.Split split = new kFileUtils.Split() ;	//Αντικείμενο τηε τάξης συγχώνευσης αρχείων
 		private File inputFile ;		//Στιγμιότοιπο της τάξης File που αναπαριστά το αρχείο που θα τεμαχιστεί
 		private File outputFile ;		//Στιγμιότοιπο της τάξης File που αναπαριστά το κοινό όνομα των αρχείων που θα προκύψουν
 		private long segmentSize ;		//Το μέγεθος του κάθε τεμαχίου

 		private final String WAIT_MSG = "Wait..." ;			//Ο τίτλος του κουμπιού κατά τη διάρκεια της επεξεργασίας
 		private final String START_MSG = startButton.getText() ;	//Ο κανονικός τίτλος του κουμπιού

 		public SplitThread(File inputFile, File outputFile, long segmentSize ){
			//Αρχικοποίηση των μεταβλητών του νήματος
 			this.inputFile = inputFile ;
 			this.outputFile = outputFile ;
 			this.segmentSize = segmentSize ;
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

				//Αρχικοποίηση των απαραίτητων μεταβλητών της τάξης τεμαχισμού και έναρξη της διαδικασίας
				//& εμφάνιση του ανάλογου μηνύματος επι/αποτυχίας μετά το πέρας αυτής
				split.setInputFileName(inputFile) ;
 				split.setOutputFileName(outputFile) ;
 				split.setPartLength(segmentSize) ;

				if(split.startSplit()){
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
			catch(ArithmeticException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "Negative number or zero is not allowed as an argument for the size of the pieces", "Invalid value",  false) ;
			}
			catch(FileNotFoundException exception){
				ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "The file not found",  false) ;
			}
			catch(kFileUtils.AssemblyInfoCreationException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "The assembly information file can not be created./n/nCheck if the recording medium exists/nfree space, if removed/nor if it has ceased to work.", "File writing error",  false) ;
			}
			catch(IOException exception){
				ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "File IO error",  false) ;
			}
			//Σε περίπτωση αδυναμίας εγγραφής στο μέσο εμφανίζεται NullPointerException
			catch(NullPointerException exception){
				ExceptionMessage.showExceptionMessage(getParent(), "Can not write the files to the location you specified.\n\nCheck for write protection or if the media does not support recording.\nIf the media is removable, check if it is removed.", "File writing error",  false) ;
			}
			catch(Exception exception){
				if(exception.getLocalizedMessage() != null)
					ExceptionMessage.showExceptionMessage(getParent(), exception.getLocalizedMessage(), "Error", false) ;
				else
					ExceptionMessage.showExceptionMessage(getParent(), exception.toString(), "Error", false) ;
			}
			catch(OutOfMemoryError error){
				ExceptionMessage.showExceptionMessage(getParent(), "It is not possible to split the file\n\nThe Java Virtual Machine that you have installed\ncan not bind memory more than\n" + Runtime.getRuntime().totalMemory() + " Bytes.\n\nThe application will try to release resources\n, try again, or set a piece size smaller than the maximum memory.", "Out of memory", false) ;
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
