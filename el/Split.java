package kFileUtils ;

import java.io.* ;
import java.util.Properties ;

/**
 * Τάξη με μεθόδους τεμαχισμού ενός αρχείου
 * @author Πέτρος Φ. Κυλαδίτης (petros.kyladitis@gmail.com)
 * @version 2.0.0 (200402181545)
 */
public class Split extends Object {
	private File inFile ;		//Αντιπροσοπεύει το αρχείο εισόδου
	private File outFile ;		//Αντιπροσοπεύει το αρχείο εξόδου
	private InputStream in ;	//Ροή εισόδου με αντι/νο FileInputStream
	private OutputStream out ;	//Ροή εξόδου με αντι/νο FileOutputStream
	private long partLength ;	//Το μέγεθος που θα κοπεί το αρχείο
	private int parts ;			//Τα κομμάτια που θα δημιουργηθούν
	private byte[] data;		//Πίνακας που αποθηκεύονται τα δεδομένα από το αρχείο εισόδου
	private long usedMemory ; 	//Μνήμη που χρησιμοποιείται
	private long maxFreeMemory ;//Μέγιστη ποσότητα ελεύθερης μνήμης που μπορεί να δεσμευτεί

	public Split(){}
	
	/**
	 * Δομητής του αντικειμένου
	 * @param inputFile Αντικείμενο File που αντιπροσωπεύει το κοινό όνμα των αρχείων που θα δημιουργηθούν
	 * @param outputFile Αντικείμενο File με το όνομα του αρχείου που θα τεμαχιστεί
	 * @param partLength Πρσοσημασμένος ακαίρεος εύρους 64bit, αντιπροσοπεύει το μέγεθος των τεμαχίων
	 */
	public Split(File inputFile, File outputFile, long partLength) throws IOException {
		if(inputFile != null)
			setInputFileName(inputFile) ;
		if(outputFile != null)
			setOutputFileName(outputFile) ;
		if(partLength > 0)
			setPartLength(partLength) ;
	}
	
	/**
	 * Μέθοδος κατασκευής με όρισμα αντικείμενο της ίδιας τάξης
	 * @param splitObject Αντικείμενο Split, από τοποίο θα δημιουργηθεί κλώνος
	 * @since 2.0.0
	 */
	 public Split(Split splitObject) throws IOException{
	 	if(splitObject != null){
	 		this.setInputFileName(splitObject.getInputFile()) ;
	 		this.setOutputFileName(splitObject.getOutputFile()) ;
	 		this.setPartLength(splitObject.getPartLength()) ;
	 	}
	 }
	
	/**
	 * Μέθοδος καθορισμού του αρχείου εισόδου (αυτού που θα τεμαχιστεί)
	 * @param inputFile Αντικείμενο της τάξης File
	 */	
	public void setInputFileName(File inputFile) throws IOException, FileNotFoundException {
		if(inputFile == null)
			throw new FileNotFoundException("Null argument") ;

		inFile = inputFile ;

		if(!inFile.exists()) //Αν το αρχείο δεν υπάρχει πρόκληση εξαίρεσης
			throw new FileNotFoundException();
		
		if(!inFile.canRead()) //Αν δεν επιτρέπεται η ανάγνωση πρόκληση εξαίρεσης
			throw new IOException("Readable access to file " + inFile.getAbsolutePath() + " forbiden!");
		
	}
	
	/**
	 * Μέθοδος καθορισμού του κοινού ονόματος των αρχείων που θα δημιουργηθούν
	 * τα τεμάχια καθώς & το αρχείο πληροφοριών συναρμολόγησης θα έχουν όνομα
	 * το όνομα που καθορίζεται εδώ και κατάληξη .PART1, .PART2 ... & .ASSEMBLY_INFO αντίστοιχα
	 * @param outputFile Αντικείμενο της τάξης File
	 */
	public void setOutputFileName(File outputFile) throws IOException, FileNotFoundException{
		if(outputFile != null)
			outFile = outputFile ;
		else
			throw new FileNotFoundException("Null argument") ;		
	}
	
	/**
	 * Μέθοδος καθορισμού του μεγέθους που θα έχει κάθε τεμάχιο
	 * @param partLength Προσημασμένος ακαίρεος 64bit (έως το 9.223.372.036.854.775.807)
	 */
	public void setPartLength(long partLength) throws ArithmeticException{
		if(partLength > 0)
			this.partLength = partLength ;
		else
			throw new ArithmeticException("The length must be a possitive number") ;
	}
	
	/**
	 * Μέθοδος επιστροφής του αντικειμένου που αντιπροσωπεύει το αρχείο εισόδου
	 * @return Αντικείμενο της τάξης File
	 * @since 2.0.0
	 */
	public File getInputFile(){
		return this.inFile ;
	}
	
	/**
	 * Μέθοδος επιστροφής του αντικειμένου που αντιπροσωπεύει το αρχείο εξόδου
	 * @return Αντικείμενο της τάξης File
	 * @since 2.0.0
	 */
	public File getOutputFile(){
		return this.outFile ;
	}
	
	/**
	 * Μέθοδος επιστροφής του μεγέθους των κομματιών, που έχει οριστεί
	 * @return Μια τιμή >0 και μέχρι 9223372036854775807l (2<sup>63</sup>-1)
	 * @since 2.0.0
	 */
	public long getPartLength(){
		return this.partLength ;
	}
	
	/**
	 * Μέθοδος έναρξης της διαδικασίας τεμαχισμού
	 * @return Αν ο διαχωρισμός των αρχείων τελειώσει επιτυχώς επιστρέφει true, αλλιώς false
	 */
	public boolean startSplit() throws FileNotFoundException, IOException, BadInitializationException, Exception{
		//Aν οι αναγκαίες μεταβλητές δεν έχουν αρχικοποιηθεί ή είναι εσφαλμένες, τότε να προκληθεί εξαίρεση
		if(inFile == null)
			throw new BadInitializationException("The input file name is null, maybe not initiallized");
		if(outFile == null)
			throw new BadInitializationException("The output file name is null, maybe not initiallized");
		if(partLength <= 0 )
			throw new BadInitializationException("Length of parts not initiallized");
		
		usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() ; //Μνήμη που χρησιμοποιείται
		maxFreeMemory = (long)(Runtime.getRuntime().maxMemory() - usedMemory) ;	//Μέγιστη ποσότητα ελεύθερης μνήμης που μπορεί να δεσμευτεί			

		//Έλεγχος του μεγέθους του αρχείου και επιλογή της κατάλότερης μεθόδου
		if((inFile.length() > maxFreeMemory) && (partLength > maxFreeMemory))
			return splitHugeFileAndParts() ;
		if(inFile.length() > maxFreeMemory){
			return splitHugeFile() ;
		}
		else{
			return splitNormalFile() ;
		}
	}
	
	/**
	 * Μέθοδος δημιουργίας του αρχείου πληροφοριών συγχώνευσης
	 * @return Αν ο διαχωρισμός των αρχείων τελειώσει επιτυχώς επιστρέφει true, αλλιώς false
	 * @since 2.0.0
	 */
	private void createAssemblyInfo() throws AssemblyInfoCreationException{		
		try{
			out = new FileOutputStream(new File(outFile.getAbsoluteFile() + ".assembly_info"));
			Properties assemblyInfo = new Properties();
			assemblyInfo.setProperty("FILE_NAME", String.valueOf(inFile.getName()));
			assemblyInfo.setProperty("PART_NAME", String.valueOf(outFile.getName()));
			assemblyInfo.setProperty("PARTS", String.valueOf(parts));
			assemblyInfo.setProperty("ASSEMBLED_SIZE", String.valueOf(inFile.length()));
			assemblyInfo.store(out, "ASSEBMLY INFO");	//To .save(out, "ASSEBMLY INFO"); δημιουργεί διένεξη
			out.flush();
			out.close();
		}
		catch(IOException exception){
			throw new AssemblyInfoCreationException() ;
		}
	}
	
	/**
	 * Μέθοδος τεμαχισμού ενός κανονικού αρχείου<br>
	 * <em>Κανονικό θεωρείται το αρχείο που το μέγεθός του είναι μικρότερο από τη διαθέσιμη κύρια μνήμη.</em>
	 * @return Αν ο διαχωρισμός των αρχείων τελειώσει επιτυχώς επιστρέφει true, αλλιώς false
	 * @since 2.0.0
	 */
	private boolean splitNormalFile() throws  FileNotFoundException, IOException, Exception{
			
		//Καθορισμός των κομματιών που θα κοπεί το αρχείο
		parts = (int)((inFile.length()) / partLength) ;
		if(((inFile.length()) % partLength)!=0) parts++ ;


		/* Ανάγνωση από το αρχείο εισόδου */
		
		//Δημιουργία ροής εισόδου δεδομένων από το αρχείο που δείχνει η μεταβλητή inFile
		//Δημιουργία ενός πίνακα τύπου byte με μέγεθος όσα και τα δεδομένα της ροής
		//Ανάγνωση των δεδομένων από τη ροή & τοποθέτησή τους στον πίνακα.
		try{
			in = new FileInputStream(inFile) ;
			data = new byte[in.available()];
			in.read(data) ;
		}
		catch(FileNotFoundException exception){
			throw new FileNotFoundException(exception.getLocalizedMessage()) ;
		}
		catch(IOException exception){
			throw new IOException(exception.getLocalizedMessage());
		}
		catch(OutOfMemoryError error){
			splitHugeFile() ;
		}		
		finally{
			in.close();
		}

		/* Εγγραφή σε μικρότερα αρχεία */

		int lastPosition = 0 ;	//Αποθηκεύει την τελευταία θέση (που δεν διαβάστηκε)
		int readingPart = 1 ;	//Αποθηκεύει το κομμάτι που δημιουργείται αυτή τη στιγμή
		/*Αν το κομμάτι που γράφεται δεν είναι το τελευταίο
		 *Δημιουργία νέας ροής εξόδου δεδομένων σε αρχείο που δείχνει η μεταβλητή outFile + τον αριθμό του κομματιού
		 *Εγγραφή στην ροή, από τον πίνακα data, αρχίζοντας από την θέση που σταμάτησε πριν η εγγραφή
		 *(και η θέση αυτή δεν γράφτηκε) & γράφοντας τόσους χαρακτήρες όσους θα έχει το κάθε κομμάτι
		 */	
		try{
			do{	
				out = new FileOutputStream(new File(outFile.getAbsoluteFile() + ".part" + readingPart)) ;
				out.write(data, lastPosition,(int)(partLength)) ;
				out.flush() ;
				out.close() ;
				lastPosition += partLength ;
				readingPart++ ;			
			}while(readingPart != parts) ;

			//Εγγραφή όπως και παραπάνω, μόνο που τώρα από τον πίνακα θα παρθούν οι χαρακτήρες που απέμειναν
			out = new FileOutputStream(new File(outFile.getAbsoluteFile() + ".part" + readingPart ));
			out.write(data, lastPosition, (int)((data.length)-((parts-1)*partLength)));				
			out.flush() ;
			out.close() ;
			
		}
		catch(FileNotFoundException exception){
			throw new IOException(exception.getLocalizedMessage());
		}			
		catch(IOException exception){
			throw new IOException(exception.getLocalizedMessage());
		}
		catch(Exception exception){
			throw new Exception(exception.getLocalizedMessage());
		}
		catch(OutOfMemoryError error){
			splitHugeFile() ;
		}
		finally{
			out.close() ;
		}

		createAssemblyInfo() ;
		return true;
	}
	
	/**
	 * Μέθοδος τεμαχισμού μεγάλου αρχείου<br>
	 * <em>μεγάλο αρχείο θεωρείται αυτό που το μέγεθός του υπερβαίνει
	 * την διαθέσιμη κύρια μνήμη, το μέγεθος των τεμαχίων όμως όχι.</em>
	 * @return  Αν ο διαχωρισμός των αρχείων τελειώσει επιτυχώς επιστρέφει true, αλλιώς false
	 * @since 2.0.0
	 */
	private boolean splitHugeFile() throws  FileNotFoundException, IOException, Exception{

		//Καθορισμός των κομματιών που θα κοπεί το αρχείο
		parts = (int)((inFile.length()) / partLength) ;
		if(((inFile.length()) % partLength)!=0) parts++ ;			


		/* Ανάγνωση από το αρχείο εισόδου δεδομένα μεγέθους όσο το
		 * μέγεθος των τεμαχίων και εγγραφής τους στα τεμάχια */
		
		//Δημιουργία ροής εισόδου δεδομένων από το αρχείο που δείχνει η μεταβλητή inFile
		//Δημιουργία ενός πίνακα τύπου byte με μέγεθος όσα και τα δεδομένα της ροής
		//Ανάγνωση των δεδομένων από τη ροή & τοποθέτησή τους στον πίνακα.
		try{
			//int readingPart = 1 ;
			in = new FileInputStream(inFile) ;
			data = new byte[(int)partLength] ;
			for(int i=1; i<parts; i++){
				in.read(data) ;
				
				out = new FileOutputStream(new File(outFile.getAbsoluteFile() + ".part" + i)) ;
				out.write(data) ;
				out.flush() ;
				out.close() ;
			}
			data = null ;
			
			data = new byte[in.available()] ;
			in.read(data) ;
			
			out = new FileOutputStream(new File(outFile.getAbsoluteFile() + ".part" + parts));
			out.write(data);
			out.flush() ;
			out.close() ;
			data = null ;
			
		}
		catch(FileNotFoundException exception){
			throw new FileNotFoundException(exception.getLocalizedMessage()) ;
		}
		catch(IOException exception){
			throw new IOException(exception.getLocalizedMessage()) ;
		}
		catch(Exception exception){
			throw new Exception(exception.getLocalizedMessage()) ;
		}
		catch(OutOfMemoryError error){
			splitHugeFileAndParts() ;
		}	
		finally{
			in.close() ;
			out.close() ;
		}
		createAssemblyInfo() ;
		return true ;
	}
	
	/**
	 * Μέθοδος τεμαχισμού μεγάλου αρχείου τεμμαχίου<br>
	 * <em>μεγάλο αρχείο και τεμμάχιο θεωρείται αυτό που το 
	 * μέγεθός του υπερβαίνει την διαθέσιμη κύρια μνήμη.</em>
	 * @return Αν ο διαχωρισμός των αρχείων τελειώσει επιτυχώς επιστρέφει true, αλλιώς false
	 * @since 2.0.0
	 */	
	private boolean splitHugeFileAndParts() throws  FileNotFoundException, IOException, Exception{

		//Καθορισμός των κομματιών που θα κοπεί το αρχείο
		parts = (int)(inFile.length() / partLength) ;
		if(((inFile.length()) % partLength) != 0) parts ++ ;
		
		//Καθορισμός των κομματιών που θα κοπούν τα τεμμάχια
		int partClusterLength = (int)(maxFreeMemory * 0.5) ;

		int partClusters = (int)(partLength / partClusterLength) ;
		if((partLength % partClusterLength) != 0) partClusters++ ;

		int lastClusterLength = (int)(partLength - ((partClusters - 1) * partClusterLength)) ;
		
		int lastPartLength = (int)(inFile.length() - ((parts - 1) * partLength)) ;

		int lastPartClusters = (int)(lastPartLength / partClusterLength) ;
		if((partLength % partClusterLength) != 0) lastPartClusters ++ ;

		int lastPartLastClusterLength = (int)(lastPartLength - ((lastPartClusters - 1) * partClusterLength)) ;		

		try{
			in = new FileInputStream(inFile) ;
			for(int i=1; i<parts; i++){
				out = new FileOutputStream(new File(outFile.getAbsoluteFile() + ".part" + i)) ;
				data = new byte[partClusterLength] ;
				for(int j=1; j<partClusters; j++){
					in.read(data) ;
					out.write(data) ;
				}
				data = null ;				

				data = new byte[lastClusterLength] ;
				in.read(data) ;
				out.write(data);
				out.flush() ;
				out.close() ;
			}
			data = null ;
			
			out = new FileOutputStream(new File(outFile.getAbsoluteFile() + ".part" + parts)) ;
			data = new byte[partClusterLength] ;
			for(int i=1; i<lastPartClusters; i++){
				in.read(data) ;
				out.write(data) ;
			}
			data = null ;
			
			data = new byte[lastPartLastClusterLength] ;
			in.read(data) ;
			out.write(data) ;
			out.flush() ;
			out.close() ;
			data = null ;				
		}
		catch(FileNotFoundException exception){
			throw new FileNotFoundException(exception.getLocalizedMessage()) ;
		}
		catch(IOException exception){
			throw new IOException(exception.getLocalizedMessage()) ;
		}
		catch(Exception exception){
			throw new Exception(exception.getLocalizedMessage()) ;
		}
		finally{
			in.close() ;
			out.close() ;
		}
		createAssemblyInfo() ;
		return true;
	}			
}