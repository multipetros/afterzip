/**
 * Τάξη εξαίρεσης παράληψης ορισμού ενός αρχείου.
 * Εμφανίζεται συνήθως μετά την κλήση μιας μεθόδου που απαιτεί
 * να έχουν καθοριστεί αρχεία για Ε/Ε.
 * @author Πέτρος Φ. Κυλαδίτης
 * @version 1.0.0
 * @sine 031225
 */
class NoFilesSelectedException extends Exception{
	private String message ; //Μεταβλητή που αποθηκεύει το String του μηνύματος

	/**
	 * Προεπιλεγμένος δομητήτς δίχως ορίσματα.<br>
	 * Ορίζει σαν μήνυμα της εξαίρεσης το "Bad object initialization"
	 */
	public NoFilesSelectedException(){
		message = new String("No files selected for I/O");
	}

	/**
	 * Δομητής του αντικειμένου με όρισμα
	 * @param message Αντικείμενο String που περιγράφει την εξαίρεση
	 */
	public NoFilesSelectedException(String message){
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
