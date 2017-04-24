/**
 * Τάξη εξαίρεσης εσφαλμένου μεγέθους τεμαχίου.
 * Εμφανίζεται συνήθως όταν το μέγεθος που έχει οριστεί για να δημιουργηθούν
 * τεμάχια από ένα αρχείο, ξεπερνά το μέγεθος του αρχείου αυτού.
 * @author Πέτρος Φ. Κυλαδίτης
 * @version 1.0.0
 * @sine 031225
 */
class BadSegmentSizeException extends Exception{
	private String message ; //Μεταβλητή που αποθηκεύει το String του μηνύματος
	
	/**
	 * Προεπιλεγμένος δομητήτς δίχως ορίσματα.<br>
	 * Ορίζει σαν μήνυμα της εξαίρεσης το "Bad object initialization"
	 */
	public BadSegmentSizeException(){
		message = new String("Bad segment size");
	}
	
	/**
	 * Δομητής του αντικειμένου με όρισμα
	 * @param message Αντικείμενο String που περιγράφει την εξαίρεση
	 */
	public BadSegmentSizeException(String message){
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
	