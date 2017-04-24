package kFileUtils ;

import java.io.* ;
import java.util.Properties ;

/**
 * Τάξη με μεθόδους συναρμολόγησης ενός τεμαχίων αρχείων
 * @author Πέτρος Φ. Κυλαδίτης (petros.kyladitis@gmail.com)
 * @version 2.0.0 (200402181545)
 */
public class Merge extends Object {
	private File[] inFile ;		//Αντιπροσοπεύει το αρχείο εισόδου
	private File outFile ;		//Αντιπροσοπεύει το αρχείο εξόδου
	private String mergeFileName ;
	private InputStream in ;	//Ροή εισόδου με αντι/νο FileInputStream
	private OutputStream out ;	//Ροή εξόδου με αντι/νο FileOutputStream
	private byte[] data ;		//Πίνακας που αποθηκεύονται τα δεδομένα από το αρχείο εισόδου	
	private long usedMemory ; 	//Μνήμη που χρησιμοποιείται
	private long maxFreeMemory ;//Μέγιστη ποσότητα ελεύθερης μνήμης που μπορεί να δεσμευτεί
	private long mergeFileSize ;//Το μέγεθος του αρχείου που θα προκύψει
	
	
	/**
	 * Προεπιλεγμένος δομητής του αντικειμένου. Δεν δέχεται ορίσματα. Για να λειτουργήσει το αντικείμενο οι απαραίτητες μεταλβητές πρέπει να αρχικοποιηθούν από τις ανάλογες μεθόδους της τάξης
	 */
	public Merge(){}
	
	/**
	 * Δομητής του αντικειμένου
	 * @param inputFilesList Πίνακας με στιγμιότυπα της τάξης File, που παριστάνουν τα αρχεία προς συναρμολόγηση
	 * @param outputFile Στιγμιότυπο της τάξη File που αναπαριστά το όνομα του αρχείου που θα δημιουργηθεί
	 */
	public Merge(File[] inputFilesList, File outputFile) throws IOException{
		setInputFileNameList(inputFilesList) ;
		setOutputFileName(outputFile) ;
	}

	/**
	 * Δομητής του αντικειμένου
	 * @param assemblyInfo Στιγμιότυπο της τάξης File που αναπαριστά το αρχείο που περιέχει πληροφορίες για αυτόματη συναρμολόγηση της ομάδας που περιγράφει
	 */
	public Merge(File assemblyInfo) throws IOException{
		if(assemblyInfo != null)
			setAssemblyInfo(null, assemblyInfo);
	}
	
	/**
	 * Μέθοδος κατασκευής του αντικειμένου με όρισμα ένα αντικείμενο της ίδιας τάξης
	 * @param mergeObject Αντικείμενο της τάξης Merge, το οποίο θα κλωνοποιηθεί.
	 * @since 2.0.0
	 */
	public Merge(Merge mergeObject) throws IOException{
		if((mergeObject.getInputFileList() != null) && (mergeObject.getOutputFile() != null)){
			this.setInputFileNameList(mergeObject.getInputFileList()) ;
			this.setOutputFileName(mergeObject.getOutputFile()) ;
		}
	}
	
	/**
	 * Μέθοδος που επιστρέφει το όνομα του αρχείου εξόδου - του αρχείου που θα δημιουργηθεί
	 * @return Ένα αντικείμενο String με την πλήρη διαδρομή του αρχείου
	 
	public String getOutputFileName(){
		return outFile.getAbsolutePath() ;
	}*/

	/**
 	 * Μέθοδος που επιστρέφει τα ονόματα των των τεμαχίων
	 * @return Ένας πίνακας με αντικείμενα String με τα τις πλήρεις διαδρομές των τεμαχίων
	 
	public String[] getInputFileName(){
		String[] inputFileName = new String[inFile.length] ;
		for(int i=0; i<inputFileName.length; i++){
			inputFileName[i] = inFile[i].getAbsolutePath();
		}
		return inputFileName ;
	}*/	

	/**
 	 * Μέθοδος που επιστρέφει τα αρχεία εισόδου - τα τεμαχία
	 * @return Ένας πίνακας με αντικείμενα File με τα τις πλήρεις διαδρομές των τεμαχίων
	 * @since 2.0.0
	 */	
	public File[] getInputFileList(){
		return this.inFile ;
	}

	/**
	 * Μέθοδος που επιστρέφει το αρχείου εξόδου - του αρχείου που θα δημιουργηθεί
	 * @return Ένα αντικείμενο File με την πλήρη διαδρομή του αρχείου
	 * @since 2.0.0
	 */	
	public File getOutputFile(){
		return this.outFile ;
	}

	/**
	 * Μέθοδος καθορισμού του αρχείου πληροφοριών συναρμολόγησης
	 * @param dirName Αντικείμενο String με τον φάκελο που βρίσκεται το αρχείο πληροφοριών συναρμολόγησης. Αν δοθεί η τιμή null, τότε το επόμενο όρισμα θα πρέπει να περιέχει και τον κατάλογο
	 * @param assemblyInfo Αντικείμενο File MONO με το όνομα του αρχείου πληροφοριών συναρμολόγησης, ΕΚΤΟΣ και αν το πρώτο όρισμα έχει την τιμή null
	 */	
	public void setAssemblyInfo(String dirName, File assemblyInfo) throws IOException, FileNotFoundException, DamagedAssemblyInfoException{
		
		Properties asmInfo = new Properties();
		asmInfo.load(new FileInputStream(assemblyInfo));
		String parts = asmInfo.getProperty("PARTS") ;
		String part_name = asmInfo.getProperty("PART_NAME") ;
		String file_name = asmInfo.getProperty("FILE_NAME") ;
		
		if(parts == null || part_name == null || file_name == null)
			throw new DamagedAssemblyInfoException() ;
				
		File[] inputFileList = new File[Integer.parseInt(parts)];
		int j = 1;
		if(dirName==null)
			dirName = assemblyInfo.getAbsolutePath().substring(0, assemblyInfo.getAbsolutePath().length() - assemblyInfo.getName().length()); 
		for(int i=0; i<inputFileList.length ; i++){
			inputFileList[i] = new File(dirName, part_name + ".part" + j);
			j++ ;
		}
		setInputFileNameList(inputFileList);
		setOutputFileName(new File(dirName, file_name));
		mergeFileName = file_name ;
	}

	/**
	 * Μέθοδος καθορισμού του αρχείου πληροφοριών συναρμολόγησης
	 * @param assemblyInfo Αντικείμενο File που αντιπροσωπεύει το αρχείο πληροφοριών συναρμολόγησης
	 */	
	public void setAssemblyInfo(File assemblyInfo) throws IOException, FileNotFoundException{
		if(assemblyInfo != null)
			setAssemblyInfo(null, assemblyInfo) ;
		else
			throw new FileNotFoundException("Null argument") ;
	}

	
	/**
	 * Μέθοδος καθορισμού της λίστας των τεμαχίων προς συναρμολόγηση
	 * @param inputFileList Πίνακας αντικειμένων File, που αναπαριστάνουν τα ονόματα των τεμαχίων προς συναρμολόγηση
	 */	
	public void setInputFileNameList(File[] inputFileList) throws IOException, FileNotFoundException{
		
		if(inputFileList == null)
			throw new FileNotFoundException("Null argument") ;

		inFile = inputFileList ;
		
		for(int i=0; i<inFile.length; i++){
			if(!inFile[i].exists()) //Αν το αρχείο δεν υπάρχει πρόκληση εξαίρεσης
				throw new FileNotFoundException();		
			if(!inFile[i].canRead()) //Αν δεν επιτρέπεται η ανάγνωση πρόκληση εξαίρεσης
				throw new IOException("Readable access to file " + inFile[i].getAbsolutePath() + " forbiden!");
		}
	}
	
	/**
	 * Μέθοδος καθορισμού του αρχείου που θα δημιουργηθεί
	 * @param outputDirName Αντικείμενο Stirng με το όνομα του φακέλου που θα αποθηκευθεί το αρχείο που θα δημιουργηθεί
	 * @param outputFileName Αντικείμενο String με το όνομα του αρχείου που θα δημιουργηθεί
	 */
	public void setOutputFileName(String outputDirName, String outputFileName) throws IOException {
		if((outputDirName != null) && (outputFileName != null))
			setOutputFileName(new File(outputDirName, outputFileName));
		else
			throw new FileNotFoundException("Null arguments") ;
	}
	
	/**
	 * Μέθοδος καθορισμού του αρχείου που θα δημιουργηθεί
	 * @param outputFile Αντικείμενο File που αναπαριστά τον φάκελο και το όνομα του αρχείου που θα δημιουργηθεί
	 */
	public void setOutputFileName(File outputFile) throws IOException {
		if(outputFile != null)
			outFile = outputFile ;
		else
			throw new FileNotFoundException("Null argument") ;
	}
	
	/**
	 * Μέθοδος καθορισμού του καταλόγου όπου θα δημιουργηθεί το αρχείο
	 * @param dirName Αντικείμενο τηε τάξης File που αναπαριστά τον κατάλογο.
	 */
	public void setOutputDirName(File dirName) throws IOException, FileNotFoundException{
		if(dirName != null)
			setOutputFileName(new File(dirName, mergeFileName)) ;
		else
			throw new FileNotFoundException("Null argument") ;
	}
	
	private long getMergedFileSize(){
		long mergedFileSize = 0 ;
		for(int i=0; i<inFile.length; i++){
			mergedFileSize += inFile[i].length();
		}
		return mergedFileSize ;
	}
	

	
	
	/* .: Διαδικασία συνένωσης :. */
	
	/**
	 * Μέθοδος έναρξης της συναρμολόγησης των τεμαχίων
	 * @return True αν η διαδικασία τελειώσει επιτυχώς, αλλιώς false
	 */
	public boolean startMerge() throws IOException, FileNotFoundException, Exception{
		
		
		//Aν οι αναγκαίες μεταβλητές δεν έχουν αρχικοποιηθεί, τότε να προκληθεί εξαίρεση
		if(inFile == null)
			throw new BadInitializationException("The input files names is null, maybe not initiallized");
		if(outFile == null)
			throw new BadInitializationException("The output file name is null, maybe not initiallized");
			
		usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() ; //Μνήμη που χρησιμοποιείται
		maxFreeMemory = (long)(Runtime.getRuntime().maxMemory() - usedMemory) ;	//Μέγιστη ποσότητα ελεύθερης μνήμης που μπορεί να δεσμευτεί			

		//Έλεγχος του μεγέθους του αρχείου και επιλογή της κατάλότερης μεθόδου
		if((getMergedFileSize() > maxFreeMemory) && (inFile[0].length() > maxFreeMemory))
			return mergeHugeFileAndParts() ;
		if(getMergedFileSize() > maxFreeMemory){
			return mergeHugeFile() ;
		}
		else{
			return mergeNormalFile() ;
		}
	}
	
	/**
	 * Μέθοδος συγχώνευσης κανονικού αρχείου.<br>
	 * <em>Κανονικό θεωρείται το αρχείο που το σύνολο του μέγεθου των τεμαχίων του δεν ξεπερνά το μέγεθος της μνήμης</em>
	 * @return True αν η διαδικασία τελειώσει επιτυχώς, αλλιώς false
	 */
	private boolean mergeNormalFile() throws IOException, FileNotFoundException, Exception{	
		int lastPosition = 0 ;	//Αποθκεύει την τελευταία θέση, πριν από την οποία σταμάτησε το γράψιμο

		data = new byte[(int)getMergedFileSize()];
		//Διάβασμα των αρχείων με τη σειρά και τοποθέτηση των δεδομένων στον πίνακα
		for(int i=0; i<inFile.length; i++){
			try{
				in = new FileInputStream(inFile[i]) ;
				in.read(data, lastPosition, (int)(in.available()));
			}
			catch(FileNotFoundException exception){
				throw new FileNotFoundException(exception.getMessage()) ;
			}
			catch(IOException exception){
				throw new IOException(exception.getMessage()) ;
			}
			finally{
				in.close();
			}
			lastPosition += inFile[i].length();
		}
		//Εγγραφή όλων των δεδομένων του πίνακα στο αρχείο εξόδου
		try{
			out = new FileOutputStream(outFile) ;
			out.write(data) ;
			out.flush() ;
		}
		catch(IOException exception){
			throw new IOException(exception.getMessage());
		}
		catch(OutOfMemoryError error){
			mergeHugeFile() ;
		}
		finally{
			out.close() ;
		}
		return true ;
	}
	
	/**
	 * Μέθοδος συγχώνευσης μεγάλου αρχείου.<br>
	 * <em>Μεγάλο θεωρείται το αρχείο που το σύνολο του μέγεθους των τεμαχίων του ξεπερνά το μέγεθος της μνήμης</em>
	 * @return True αν η διαδικασία τελειώσει επιτυχώς, αλλιώς false
	 */
	private boolean mergeHugeFile() throws IOException, FileNotFoundException, Exception{
		
		try{
			out = new FileOutputStream(outFile) ;
			data = new byte[(int)inFile[0].length()] ;
			for(int i=0; i<inFile.length-1; i++){
				in = new FileInputStream(inFile[i]) ;
				in.read(data) ;
				out.write(data) ;		
			}
			data = null ;
			
			data = new byte[(int)inFile[inFile.length-1].length()] ;
			in = new FileInputStream(inFile[inFile.length-1]) ; 
			in.read(data) ;
			out.write(data) ;
			out.flush() ;
			data = null ;
		}
		catch(FileNotFoundException exception){
			throw new FileNotFoundException(exception.getMessage()) ;
		}
		catch(IOException exception){
			throw new IOException(exception.getMessage()) ;
		}
		catch(Exception exception){
			throw new IOException(exception.getMessage()) ;
		}
		catch(OutOfMemoryError error){
			mergeHugeFileAndParts() ;
		}	
		finally{
			out.close() ;
			in.close() ;
		}
	
		return true ;
	}

	/**
	 * Μέθοδος συγχώνευσης μεγάλου αρχείου και τεμαχίων.<br>
	 * <em>Μεγάλο θεωρείται το αρχείο που το σύνολο του μέγεθου των τεμαχίων του,
	 * αλλά και το μέγεθος του κάθε τεμαχίου ξεχωριστά ξεπερνά το μέγεθος της μνήμης</em>
	 * @return True αν η διαδικασία τελειώσει επιτυχώς, αλλιώς false
	 */	
	private boolean mergeHugeFileAndParts() throws IOException, FileNotFoundException, Exception{

		int clusterLength = (int)(maxFreeMemory * 0.50) ;

		int clusters = (int)(inFile[0].length() / clusterLength) ;
		if((inFile[0].length() % clusterLength) != 0) clusters++ ;

		int lastClusterLength = (int)(inFile[0].length() - ((clusters-1)*clusterLength)) ;

		int lastPartClusters = (int)(inFile[inFile.length-1].length() / clusterLength) ;
		if((inFile[inFile.length-1].length() % clusterLength) != 0) lastPartClusters ++ ;
		
		int lastPartLastClusterLength = (int)(inFile[inFile.length-1].length() - ((lastPartClusters-1)*clusterLength)) ;

		try{
			out = new FileOutputStream(outFile) ;
			//System.gc() ;
			for(int i=0; i<inFile.length-1; i++){
				data = new byte[clusterLength] ;
				in = new FileInputStream(inFile[i]) ;
				for(int j=1; j<clusters; j++){
					in.read(data) ;
					out.write(data) ;
				}
				data = null ;
				//System.gc() ;
				data = new byte[lastClusterLength] ;
				in.read(data) ;
				out.write(data) ;
				data = null ;
			}
			//System.gc() ;
			in = new FileInputStream(inFile[inFile.length-1]) ; 
			data = new byte[clusterLength] ;			
			for(int i=1; i<lastPartClusters; i++){
				in.read(data) ;
				out.write(data) ;
			}
			data = null ;
			//System.gc() ;
			data = new byte[lastPartLastClusterLength] ;
			in.read(data) ;
			out.write(data) ;
			data = null ;
		}
		catch(FileNotFoundException exception){
			throw new FileNotFoundException(exception.getMessage()) ;
		}
		catch(IOException exception){
			throw new IOException(exception.getMessage()) ;
		}
		catch(Exception exception){
			throw new IOException(exception.getMessage()) ;
		}		
		finally{
			out.close() ;
			in.close();
		}
	
		return true ;		
	}			

}