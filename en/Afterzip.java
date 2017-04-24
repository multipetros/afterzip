/**
 * Τάξη εισόδου της εφαρμογής, που αρχικοποιεί την τάξη AfterzipFrame
 * Αρχικά γίνεται έλεγχος της έκδοσης της εικονικής μηχανής Java που είναι εγκατεστημένη
 * Aν αυτή είναι μικρότερη της 1.1.2, δηλαδή δεν περιέχει τα JFC και Swing τότε
 * να σχεδιάζεται ένα παράθυρο χρησιμοποιώντας το Abstract Windowing Toolkit
 * να ειδοποιεί το χρήστη και να τον προτρέπει να κατεβάσει την νέα JVM
 * @version 1.0.0
 * @serial 031220
 * @since  031220
 */

class Afterzip extends Object {
 	public static void main(String[] args) {
 		try {
        	String vers = System.getProperty("java.version") ;
        	if (vers.compareTo("1.2.0") < 0) {
        		java.awt.Frame AWTFrame = new java.awt.Frame("General Execution Error") ;
        		java.awt.TextArea AWTTextArea = new java.awt.TextArea() ;
        		AWTTextArea.setEditable(false) ;
        		AWTTextArea.setText("Warning!!!\nYour system's VM version is " + vers + "\nThe program make usage of Java Fountation Classes (JFC).\nSo, to run needs Java Virtual Machine at version 1.2.0 or above.\n\nYou can download the latest version of JVM from the website http://java.sun.com/") ;
        		AWTFrame.add(AWTTextArea) ;
        		AWTFrame.setSize(550,150) ;
        		AWTFrame.setLocation(100,200) ;
        		AWTFrame.addWindowListener(
        			new java.awt.event.WindowAdapter(){
        				public void windowClosing(java.awt.event.WindowEvent e){
        					System.exit(0) ;
        				}
        			}
        		) ;
        		AWTFrame.show() ;
			}
			else {
				new AfterzipFrame() ;
			}
		}
		catch(Throwable error){
			System.err.println("Unexpected error " + error.toString()) ;
		}
	}
 }
