package kFileUtils;

import java.io.* ;

/**
 * Τάξη εξαίρεσης κατεστραμμένου αρχείου πληροφοριών συγχώνευσης
 * Εμφανίζεται κατά την ανάγνωση πληροφοριών ενός αρχείου, όταν
 * δεν είναι δυνατός ο εντοπισμός των ζητούμενων πεδίων.
 * @author Πέτρος Φ. Κυλαδίτης (petros.kyladitis@gmail.com)
 * @version 1.0.0
 * @since 040207
 * @see java.io.IOException
 */
public class DamagedAssemblyInfoException extends IOException{
	
	private String message ; //Μεταβλητή που αποθηκεύει το String του μηνύματος
	
	/**
	 * Προεπιλεγμένος δομητής δίχως ορίσματα.<br>
	 * Ορίζει σαν μήνυμα της εξαίρεσης το "Damaged assembly informations"
	 */
	public DamagedAssemblyInfoException(){
		message = new String("Damaged assembly informations");
	}
	
	/**
	 * Δομητής του αντικειμένου με όρισμα
	 * @param message Αντικείμενο String που περιγράφει την εξαίρεση
	 */
	public DamagedAssemblyInfoException(String message){
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