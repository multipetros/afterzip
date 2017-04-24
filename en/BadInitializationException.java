package kFileUtils;

import java.io.* ;

/**
 * Τάξη εξαίρεσης αρχικοποίησης ενός αντικειμένου.
 * Εμφανίζεται συνήθως μετά την κλήση μιας μαθόδου που απαιτεί 
 * να έχουν αρχικοποιηθεί ορισμένες μεταβλητές.
 * @author Πέτρος Φ. Κυλαδίτης (petros.kyladitis@gmail.com)
 * @version 1.0.0
 * @since 031010
 * @see java.io.IOException
 */
public class BadInitializationException extends IOException{
	
	private String message ; //Μεταβλητή που αποθηκεύει το String του μηνύματος
	
	/**
	 * Προεπιλεγμένος δομητήτς δίχως ορίσματα.<br>
	 * Ορίζει σαν μήνυμα της εξαίρεσης το "Bad object initialization"
	 */
	public BadInitializationException(){
		message = new String("Bad object initialization");
	}
	
	/**
	 * Δομητής του αντικειμένου με όρισμα
	 * @param message Αντικείμενο String που περιγράφει την εξαίρεση
	 */
	public BadInitializationException(String message){
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