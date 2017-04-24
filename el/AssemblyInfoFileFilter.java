/**
 * Τάξη φιλτραρίσματος των αρχείων με κατάλληξη .assembly_info
 * Η τάξη αυτή θα χρησιμοποιηθεί στο JFileChooser του MergePanel.inputButton
 * @author Πέτρος Φ. Κυλαδίτης
 * @version 1.0.0
 * @since 040110
 */
class AssemblyInfoFileFilter extends javax.swing.filechooser.FileFilter{
	
	private final String EXTENSION = new String("assembly_info") ;	
	private final String DESCRIPTION = new String("Αρχεία πληροφοριών συγχώνευσης \'.assembly_info\'") ;
	
	
	//Μέθοδος που επιστρεφει την επέκταση των αρχείων
	public String getExtension(){
		return EXTENSION ;
	}	
	
	//Μέθοδος που επιστρέφει την περιγραφή της συσχετισμένης επέκτασης των αρχείων
	// !! Υλοποίηση της αφηρημένης μεθόδου της τάξης που κληρονομεί αυτή η τάξη !!
	public String getDescription(){
		return DESCRIPTION ;
	}
	
	//Μέθοδος που επιστρέφει αν το αρχείο που επιλέχθηκε έχει την σωστή κατάληξη
	// !! Υλοποίηση της αφηρημένης μεθόδου της τάξης που κληρονομεί αυτή η τάξη !!	
	public boolean accept(java.io.File file){
		
		if(file == null)
			return false ;
			
		else if(file.isDirectory())
			return true ;
			
		else{
			int i = file.getName().lastIndexOf('.') ;
	    	if(i>0 && i<file.getName().length()-1){
				String fileExtension = file.getName().substring(i+1).toLowerCase() ;
				if(fileExtension.equals(this.EXTENSION))
					return true ;
				else
					return false ;
			}
			else
				return false ;			
		}			
	}
}
	