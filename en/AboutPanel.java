import java.io.* ;
import java.awt.* ;
import javax.swing.* ;
import javax.swing.event.* ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseEvent ;
import java.net.URL ;
import java.net.UnknownHostException ;

/**
 * Τάξη που δημιουργεί το πλαίσιο πληροφοριών κατασκευής κλπ. της εφαρμογής
 * @version 1.1.9
 * @serial 040807
 * @since  031210
 */
class AboutPanel extends JPanel {

	private JLabel logoIconLabel ;	//Ετικέτα που θα εμφανίζει το λογότυπο
	private JLabel appNameLabel ;	//Ετικέτα με το όνομα της εφαρμογής
	private JLabel versionLabel ;	//Ετικέτα με την έκδοση της εφαρμογής
	private JLabel copyrightLabel ;	//Ετικέτα με πληροφορίες πνευματικών δικαιωμάτων
	private JLabel  sysInfoLabel ;	//Ετικέτα-κουμπί για εμφάνιση πληροφοριών του συστήματος
	private JLabel webLabel ;		//Ετικέτα με σύνδεσμο προς τον ιστότοπό μου
	private EmbeddedBrowser otherInfoPanel ; //Πλαίσιο εμφάνισης copyright, φυλλομετρητής κ.α.
	private JScrollPane scrollPanel ;	 //Κυλιόμενο πλαίσιο που θα εφαρμοστεί το otherInfoPanel

	//Σταθερές που περιέχουν τα Strings του περιβάλλοντος
	private final String APP_NAME = "Afterzip" ;
	private final String APP_VERSION = "Version 1.2.0" ;
	private final String COPYRIGHT = "(C) 2004 - Petros F. Kyladitis" ;
	private final String SITE_URL = "<html><body><center>Web site: <a href=\"mp\">http://www.multipetros.gr/</a></center></body></html>" ;
	private final String SYSINFO = "<html><body><center><a href=\"hh\">System Information</a></center></body></html>" ;
	private final String APP_NAME_TOOLTIP = "The program's name." ;
	private final String APP_VERSION_TOOLTIP = "The current version of the program." ;
	private final String COPYRIGHT_TOOLTIP = "The copyrights holder" ;
	private final String SITE_URL_TOOLTIP = "The developer's web site" ;
	private final String SYSINFO_TOOLTIP = "Click to see system info." ;

	//Τοποθεσίες των αρχείων που χρειάζονται για το περιβάλλον.
	private final String LOGO_FILENAME 		= "/images/logo.png" ;
	private final String CREDITS_FILENAME 	= "/docs/credits.html" ;
	private final String JAVA_LOGO_PATH 	= "/images/java.gif" ;

	//Σταθερές για τη δημιουργία πληροφοριών του περιβάλλοντος
	private final String MSG_TITLE = "System info" ;
	private final String USERNAME = "Logged in user: " ;
	private final String COMPUTER = "at a computer with " ;
	private final String ARCHITECTURE = " architecture " ;
	private final String CPU = " one CPU" ;
	private final String CPUS = " CPUs" ;
	private final String OPERATING_SYSTEM = "with operating system " ;
	private final String JAVA = "with Java VM" ;
	private final String JAVA_VENDOR = " of " ;
	private final String VERSION = " version " ;
	private final String JAVA_URL = "Java VM creator website: " ;
	private final String NEW_LINE = "\n" ;
	private final String NONAME_USER = "Unkwnown Connected User" ;

	//Το αντικείμενο στο οποίο θα χτιστεί το κείμενο πληροφοριών συστήματος.
	private StringBuffer sysInfoBuffer = new StringBuffer() ;

	public AboutPanel(){


		//Δημιουργία του κειμένου πληροφοριών συστήματος.
		//Ελέγχεται αν υπάτχουν οι ιδιότητες και χτίζεται σταδιακά.

		if(System.getProperty("user.name") != null)
			sysInfoBuffer.append(USERNAME).append(System.getProperty("user.name")) ;
		else
			sysInfoBuffer.append(NONAME_USER) ;

		sysInfoBuffer.append(NEW_LINE) ;
		sysInfoBuffer.append(COMPUTER) ;

		if(Runtime.getRuntime().availableProcessors() != 1)
			sysInfoBuffer.append(Runtime.getRuntime().availableProcessors()).append(CPUS) ;
		else
			sysInfoBuffer.append(CPU) ;

		if(System.getProperty("os.arch") != null)
			sysInfoBuffer.append(ARCHITECTURE).append(System.getProperty("os.arch")) ;

		if(System.getProperty("os.name") != null || System.getProperty("os.version") != null){
			sysInfoBuffer.append(NEW_LINE) ;
			sysInfoBuffer.append(OPERATING_SYSTEM) ;
		}

		if(System.getProperty("os.name") != null)
			sysInfoBuffer.append(System.getProperty("os.name")) ;

		if(System.getProperty("os.version") != null)
			sysInfoBuffer.append(VERSION).append(System.getProperty("os.version")) ;

		if(System.getProperty("java.vendor") != null || System.getProperty("java.version") != null || System.getProperty("java.vendor.url") != null){
			sysInfoBuffer.append(NEW_LINE) ;
			sysInfoBuffer.append(JAVA) ;
		}

		if(System.getProperty("java.vendor") != null)
			sysInfoBuffer.append(JAVA_VENDOR).append(System.getProperty("java.vendor")) ;

		if(System.getProperty("java.version") != null)
			sysInfoBuffer.append(VERSION).append(System.getProperty("java.version")) ;

		if(System.getProperty("java.vendor.url") != null)
			sysInfoBuffer.append(NEW_LINE).append(JAVA_URL).append(System.getProperty("java.vendor.url")) ;



		//Αρχικοποίηση των ετικετών του περιβάλλοντος
		appNameLabel = new JLabel(APP_NAME) ;
		appNameLabel.setToolTipText(APP_NAME_TOOLTIP) ;
		versionLabel = new JLabel(APP_VERSION) ;
		versionLabel.setToolTipText(APP_VERSION_TOOLTIP) ;
		copyrightLabel = new JLabel(COPYRIGHT) ;
		copyrightLabel.setToolTipText(COPYRIGHT_TOOLTIP) ;
		webLabel = new JLabel(SITE_URL) ;
		webLabel.setToolTipText(SITE_URL_TOOLTIP) ;
		webLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)) ;
		webLabel.addMouseListener(
			new MouseAdapter(){
				public void mouseClicked(MouseEvent event){
					try{
						Desktop.getDesktop().browse(new URL("http://www.multipetros.gr/").toURI());
					}catch(Exception exception){
						JOptionPane.showMessageDialog(getParent(), exception.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE) ;
					}
				}
			}
		) ;
		sysInfoLabel = new JLabel(SYSINFO) ;
		sysInfoLabel.setToolTipText(SYSINFO_TOOLTIP) ;
		sysInfoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)) ;
		sysInfoLabel.addMouseListener(
			new MouseAdapter(){
				public void mouseClicked(MouseEvent event){
					try{
						JOptionPane.showMessageDialog(getParent(), sysInfoBuffer, MSG_TITLE, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource(JAVA_LOGO_PATH))) ;
					}catch(NullPointerException exception){
						JOptionPane.showMessageDialog(getParent(), sysInfoBuffer, MSG_TITLE, JOptionPane.INFORMATION_MESSAGE) ;

					}
				}
			}
		) ;
		try{
			logoIconLabel = new JLabel(new ImageIcon(this.getClass().getResource(LOGO_FILENAME))) ;
		}catch(NullPointerException exception){
			//Αν δεν βρεθεί το αρχείο της εικόνας δημιουργία κενής ετικέτας.
			logoIconLabel = new JLabel() ;
		}



		//Δημιουργία και αρχικοποίηση του ενσωματομένου φυλλομετρητή
		URL homepageURL = null ;
		try{
			homepageURL = this.getClass().getResource(CREDITS_FILENAME) ;

		}catch(NullPointerException exception){}
		finally{
			otherInfoPanel = new EmbeddedBrowser(homepageURL) ;
		}

		//Τοποθέτηση του JEditorPane σε ένα πλαίσιο κύλισης JSCrollPane
		scrollPanel = new JScrollPane(otherInfoPanel) ;

		//Διάταξη των ετικετών σε πλέγμα 5 x 1
		JPanel mainInfoPanel = new JPanel() ;
		mainInfoPanel.setLayout(new GridLayout(5,1)) ;
		mainInfoPanel.add(appNameLabel) ;
		mainInfoPanel.add(versionLabel) ;
		mainInfoPanel.add(copyrightLabel) ;
		mainInfoPanel.add(webLabel) ;
		mainInfoPanel.add(sysInfoLabel) ;

		//Διάταξη των ομαδοποιημένων ετικετών στα δεξιά και της εικόνας στα αριστερά
		JPanel topPanel = new JPanel() ;
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,1)) ;
		topPanel.add(logoIconLabel) ;
		topPanel.add(mainInfoPanel) ;

		//Διάταξη των παραπάνω στο άνω τμήμα του παραθύρου και
		//του πλαισίου κύλισης στο κατώτερο τμήμα που απομένει
		setLayout(new BorderLayout(5,4)) ;
		add(topPanel, BorderLayout.NORTH) ;
		add(scrollPanel) ;
	}
}