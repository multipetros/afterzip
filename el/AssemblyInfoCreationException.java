package kFileUtils;

import java.io.* ;

/**
 * Τάξη εξαίρεσης αδυναμίας δημιουργίας του
 * αρχείου πληροφοριών συγχώνευσης
 * Εμφανίζεται κατά την δημιουργία του .assembly_info
 * δεν είναι δυνατός ή η δημιουργία του.
 * @author Πέτρος Φ. Κυλαδίτης (petros.kyladitis@gmail.com)
 * @version 1.0.0
 * @since 040216
 * @see java.io.IOException
 */
public class AssemblyInfoCreationException extends IOException{
	
	private String message ; //Μεταβλητή που αποθηκεύει το String του μηνύματος
	
	/**
	 * Προεπιλεγμένος δομητής δίχως ορίσματα.<br>
	 * Ορίζει σαν μήνυμα της εξαίρεσης το "Can't create the file with assembly informations"
	 */
	public AssemblyInfoCreationException(){
		message = new String("Can't create the file with assembly informations");
	}
	
	/**
	 * Δομητής του αντικειμένου με όρισμα
	 * @param message Αντικείμενο String που περιγράφει την εξαίρεση
	 */
	public AssemblyInfoCreationException(String message){
		this.message = message ;
	}

	/**
	 * Μέθοδος λήψης του μηνύματος περιγραφής της εξαίρεσης
	 * @return Ένα αντικείμενο String με το μήνυμα
	 */	
	public String getMessage(){
		return message ;
	}		
}