import java.io.*;
import java.util.Properties;

/**
 * Τάξη (PropertiesReaderWRiter) ανάγνωσης & εγγραφής από αρχείο ιδοτήτων
 * @version 1.1.0
 * @serial 040730
 * @since 040105
 */
class PropertiesRW extends Object {

	//Σταθερές που απεικονίζουν τις προεπιλεγμένς τιμές των ιδιοτήτων
	public static final int DEFAULT_FRAME_WIDTH = 530 ;
	public static final int DEFAULT_FRAME_HEIGHT = 320 ;
	public static final int DEFAULT_FRAME_X = 200 ;
	public static final int DEFAULT_FRAME_Y = 200 ;
	public static final boolean DEFAULT_THEMED_BORDER = false ;
	public static final boolean DEFAULT_SAVE_NEW_SIZE = false ;
	public static final boolean DEFAULT_SAVE_NEW_LOCATION = false ;
	public static final String DEFAULT_THEME = "javax.swing.plaf.metal.MetalLookAndFeel" ;
	public static final int DEFAULT_INIT_TAB = 4 ;

	//Μεταβλητές που χρειάζονται για την αρχικοποίηση της εμφάνισης του παραθύρου.
	//Μεταβάλονται κατά την φόρτωση από τις μεταβλητές που διαβάζονται από το
	//αρχείο πληροφοριών. Αν αυτό δεν υπάρχει χρησιμοποιούνται οι τιμές θέτονται εδώ.
	private int frameWidth  = DEFAULT_FRAME_WIDTH ;
	private int frameHeight = DEFAULT_FRAME_HEIGHT ;
	private int frameX      = DEFAULT_FRAME_X ;
	private int frameY      = DEFAULT_FRAME_Y ;
	private boolean themedBorder    = DEFAULT_THEMED_BORDER ;
	private boolean saveNewSize     = DEFAULT_SAVE_NEW_SIZE ;
	private boolean saveNewLocation = DEFAULT_SAVE_NEW_LOCATION ;
	private String theme = DEFAULT_THEME ;
	private String inSplitFolder  = "" ;
	private String outSplitFolder = "" ;
	private String inMergeFolder  = "" ;
	private String outMergeFolder = "" ;
	private int initTab = DEFAULT_INIT_TAB ;
	private Properties props ;
	//Το όνομα του αρχείου ξεκινάει με . ώστε στα UNIX συστήματα να είναι κρυφό.
	private String propertiesFilename = new String(".Afterzip.properties") ;

	public PropertiesRW(){

		/*Εύρεση του γονικού καταλόγου του χρήστη για αποθήκευση του αρχείου ιδιοτήτων
		  Σε περίπτωση σφάλματος (SecurityException, NullPointerException
		  και IllegalArgumentException) δίνεται η τιμή null, η οποία επιστρέφεται
		  αυτόματα όταν η ιδιότητα του συστήματος δεν έχει τιμή.)
		  Αν βρεθεί null, η διαδρομή του αρχείου παραμένει ως έχει (διαδρομή της
		  εφαρμογής) αλλιώς συνδιάζεται με τον κατάλογο του χρήστη.
		*/
		String userHome = null ;
		try{
			userHome = System.getProperty(new String("user.home")) ;
		}
		catch(Exception exception){
			userHome = null ;
		}
		finally{
			if(userHome != null)
				propertiesFilename = userHome.concat(new String("/").concat(propertiesFilename)) ;
		}


		//Ανάγνωση από το αρχείο πληροφοριών για την αρχικοποίηση των στοιχείων του παραθύρου.
		InputStream propertiesInputStream ;
		props = new Properties() ;

		try{

			propertiesInputStream = new FileInputStream(new File(propertiesFilename)) ;
			props.load(propertiesInputStream) ;

			frameWidth = Integer.parseInt(props.getProperty("WIDTH")) ;
			frameHeight = Integer.parseInt(props.getProperty("HEIGHT")) ;
			frameX = Integer.parseInt(props.getProperty("X")) ;
			frameY = Integer.parseInt(props.getProperty("Y")) ;
			initTab = Integer.parseInt(props.getProperty("INIT_TAB")) ;

			if(props.getProperty("SAVE_NEW_SIZE").equals("true"))
				saveNewSize = true ;
			else
				saveNewSize = false ;

			if(props.getProperty("SAVE_NEW_LOCATION").equals("true"))
				saveNewLocation = true ;
			else
				saveNewLocation = false ;

			if(props.getProperty("THEMED_BORDER").equals("true"))
				themedBorder = true ;
			else
				themedBorder = false ;

			theme = props.getProperty("THEME") ;

			inSplitFolder  = props.getProperty("IN_SPLIT_FOLDER" ) ;
			outSplitFolder = props.getProperty("OUT_SPLIT_FOLDER") ;
			inMergeFolder  = props.getProperty("IN_MERGE_FOLDER" ) ;
			outMergeFolder = props.getProperty("OUT_MERGE_FOLDER") ;

			propertiesInputStream.close() ;

		}
		catch(Exception exception){
			//Σε περίπτωση αστοχίας ανάγνωσης των περιεχομένων του αρχείου
			//Να δημιουργηθεί νέο. Οι μεταβλητές που έχουν διαβαστεί παραπάνω
			//με επιτυχία αποθηκεύονται και στο νέο, αλλιώς αποθηκεύονται
			//οι προεπιλεγμένες τιμές.
			storeProperties() ;
		}
	}

	/**
	 * Μέθοδος που αποθηκεύει όλες τις ιδιότητες
	 */
	public void storeProperties() {

		OutputStream propertiesOutputStream ;
		props = new Properties() ;

		try{
			propertiesOutputStream = new FileOutputStream(new File(propertiesFilename)) ;

			props.setProperty("WIDTH", Integer.toString(this.frameWidth)) ;
			props.setProperty("HEIGHT", Integer.toString(this.frameHeight)) ;
			props.setProperty("X", Integer.toString(this.frameX)) ;
			props.setProperty("Y", Integer.toString(this.frameY)) ;
			props.setProperty("INIT_TAB", Integer.toString(this.initTab)) ;
			props.setProperty("THEME", this.theme) ;
			props.setProperty("THEMED_BORDER", Boolean.toString(this.themedBorder)) ;
			props.setProperty("SAVE_NEW_SIZE", Boolean.toString(this.saveNewSize)) ;
			props.setProperty("SAVE_NEW_LOCATION", Boolean.toString(this.saveNewLocation)) ;
			props.setProperty("IN_SPLIT_FOLDER" , this.inSplitFolder) ;
			props.setProperty("OUT_SPLIT_FOLDER", this.outSplitFolder) ;
			props.setProperty("IN_MERGE_FOLDER" , this.inMergeFolder) ;
			props.setProperty("OUT_MERGE_FOLDER", this.outMergeFolder) ;
			props.store(propertiesOutputStream, new String("Afterzip Graphic User Interface Options")) ;

			propertiesOutputStream.flush() ;
			propertiesOutputStream.close() ;
		}
		catch(IOException newException){
			System.err.println("Can't create Afterzip.ini\n") ;
			System.out.println(newException.getMessage()) ;
			System.out.println(newException.getStackTrace()) ;

		}
	}


	/*  Μέθοδοι ανάγνωσης-επιστροφής των ιδιοτήτων  */

	public int getFrameWidth(){
		return frameWidth ;
	}

	public int getFrameHeight(){
		return frameHeight ;
	}

	public int getFrameX(){
		return frameX ;
	}

	public int getFrameY(){
		return frameY ;
	}

	public int getInitTab(){
		return initTab ;
	}

	public String getTheme(){
		return theme ;
	}

	public boolean getSaveNewSize(){
		return saveNewSize ;
	}

	public boolean getSaveNewLocation(){
		return saveNewLocation ;
	}

	public boolean getThemedBorder(){
		return themedBorder ;
	}

	public String getInSplitFolder(){
		return inSplitFolder ;
	}

	public String getOutSplitFolder(){
		return outSplitFolder ;
	}

	public String getInMergeFolder(){
		return inMergeFolder ;
	}

	public String getOutMergeFolder(){
		return outMergeFolder ;
	}



	/* Μέθοδοι μεταβολής-θεσίματος νέων τιμών στις μεταβλητές */

	public void setFrameWidth(int frameWidth){
		this.frameWidth = frameWidth ;
	}

	public void setFrameHeight(int frameHeight){
		this.frameHeight = frameHeight ;
	}

	public void setFrameX(int frameX){
		this.frameX = frameX ;
	}

	public void setFrameY(int frameY){
		this.frameY = frameY ;
	}

	public void setInitTab(int initTab){
		this.initTab = initTab ;
	}

	public void setTheme(String theme){
		this.theme = theme ;
	}

	public void setSaveNewSize(boolean saveNewSize){
		this.saveNewSize = saveNewSize ;
	}

	public void setSaveNewLocation(boolean saveNewLocation){
		this.saveNewLocation = saveNewLocation ;
	}

	public void setThemedBorder(boolean themedBorder){
		this.themedBorder = themedBorder ;
	}

	public void setInSplitFolder(String inSplitFolder){
		this.inSplitFolder = inSplitFolder ;
	}

	public void setOutSplitFolder(String outSplitFolder){
		this.outSplitFolder = outSplitFolder ;
	}

	public void setInMergeFolder(String inMergeFolder){
		this.inMergeFolder = inMergeFolder ;
	}

	public void setOutMergeFolder(String outMergeFolder){
		this.outMergeFolder = outMergeFolder ;
	}



	/*   Μέθοδοι επαναφοράς των αρχικών τιμών στις μεταβλητές    */

	public void resetFrameWidth(){
		this.frameWidth = this.DEFAULT_FRAME_WIDTH ;
	}

	public void resetFrameHeight(){
		this.frameHeight = this.DEFAULT_FRAME_HEIGHT ;
	}

	public void resetFrameX(){
		this.frameX = this.DEFAULT_FRAME_X ;
	}

	public void resetFrameY(){
		this.frameY = this.DEFAULT_FRAME_Y ;
	}

	public void resetInitTab(){
		this.initTab = this.DEFAULT_INIT_TAB ;
	}

	public void resetTheme(){
		this.theme = this.DEFAULT_THEME ;
	}

	public void resetSaveNewSize(){
		this.saveNewSize = this.DEFAULT_SAVE_NEW_SIZE ;
	}

	public void resetSaveNewLocation(){
		this.saveNewLocation = this.DEFAULT_SAVE_NEW_LOCATION ;
	}

	public void resetThemedBorder(){
		this.themedBorder = this.DEFAULT_THEMED_BORDER ;
	}

	public void resetInSplitFolder(){
		this.inSplitFolder = "" ;
	}

	public void resetOutSplitFolder(){
		this.outSplitFolder = "" ;
	}

	public void resetInMergeFolder(){
		this.inMergeFolder = "" ;
	}

	public void resetOutMergeFolder(){
		this.outMergeFolder = "" ;
	}

}