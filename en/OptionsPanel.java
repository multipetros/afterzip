import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;

/**
 * Τάξη που εμφανίζει την καρτέλα ρυθμίσεων της εφαρμογής
 * @version 1.0.3
 * @serial 041011
 * @since  040105
 */
class OptionsPanel extends JPanel {

	private JLabel themeLabel ; 					//Εμφανίζει μια συμ/ρά για την εμφάνιση της εφαρμογής
	private JComboBox themesListComboBox ;			//Μια λίστα με τις εμφανίσεις που μπορεί να επιλέξει ο χρήστης
	private JCheckBox frameBorderThemeCheckBox ;	//Κουμπί επιλογής της εφαρμογής του θέματος στο περίγραμμα του παραθύρου
	private JCheckBox saveUserLocationCheckBox ;	//Κουμπί επιλογής αποθήκευσης της αλλαγμένης θέσης του παραθύρου
	private JCheckBox saveUserSizeCheckBox ;		//Κουμπί επιλογής αποθήκευσης των αλλαγμένων διατάσεων του παραθύρου
	private JButton resetLocationButton ;			//Κουμπί επαναφοράς της αρχικής θέσης του παραθύρου στην επιφ. εργασίας
	private JButton resetSizeButton ;				//Κουμπί επαναφοράς των αρχικών διαστάσεων του παραθύρου
	private PropertiesRW props = new PropertiesRW() ;//Δημιουργία αντικειμένου που επεξεργάζεται τις ιδιότητες

	private final String[] THEMES_LIST_STRINGS = {"Same as the system", "Java Default", "Microsoft Windows", "Motif", "Gtk", "Mac OS"} ;

	//Ονόματα των τάξεων των θεμάτων
	private final String SYSTEM = UIManager.getSystemLookAndFeelClassName() ;
	private final String METAL = "javax.swing.plaf.metal.MetalLookAndFeel" ;
	private final String WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel" ;
	private final String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel" ;
	private final String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel" ;
	private final String MAC = "com.sun.java.swing.plaf.mac.MacLookAndFeel" ;

	//Κείμενο που περιγράφει στον χρήστη ότι το θέμα που επέλεξε δεν δύναται
	//να έχει τροποποιημένο περίγραμμα και τον καλεί να αποφασίσει αν θέλει
	//να επανεκκινήσει την εφαρμογή.
	private final String NO_THEMED_BORDER_SUPPORT_RESET_QUESTION = "Select a theme that does not support, apply it to the border.\nTo apply, the program must be restarted.\nAfter rebooting, the theme selection on the border will automatically turn off.\nIf you do not want to reboot now, the theme will be applied to it\n the next time you start the program." ;
	public OptionsPanel(){

		//Δημιουργία της ετιτκέτας που περιγράφει το themesListComboBox
		themeLabel = new JLabel("Program Appearance") ;

		//Δημιουργία του themesListComboBox
		themesListComboBox = new JComboBox(THEMES_LIST_STRINGS) ;
		themesListComboBox.setToolTipText("Select one of the themes in the list") ;
		themesListComboBox.setEditable(false) ;

		themesListComboBox.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent e){
					//Μόνο κατά την επιλογή του αντικειμένου να εκτελούνται τα παρακάτω, όχι και στην αποεπιλογή
					if(e.getStateChange() == ItemEvent.SELECTED){
						//Με κάθε αλλαγή της επιλογής αλλαγή του θέματος
						switch(themesListComboBox.getSelectedIndex()){
							case 0 :
								props.setTheme(SYSTEM) ;
								break ;
							case 1 :
								props.setTheme(METAL) ;
								break ;
							case 2 :
								props.setTheme(WINDOWS) ;
								break ;
							case 3 :
								props.setTheme(MOTIF) ;
								break ;
							case 4 :
								props.setTheme(GTK) ;
								break ;
							case 5 :
								props.setTheme(MAC) ;
								break ;
						}


						//Αν το θέμα δεν υποστιρίζει εφαρμογή του στο περίγραμμα ερώτηση για
						//επανεκκίνηση, αλλιώς απευθείας εφαρμογή του θέματος.
						if(props.getThemedBorder() && (!props.getTheme().equals(METAL))){
							props.setThemedBorder(false) ;
							props.storeProperties() ;
							AfterzipFrame.reset(NO_THEMED_BORDER_SUPPORT_RESET_QUESTION) ;
						}
						else{
							props.storeProperties() ;
							AfterzipFrame.setTheme(props.getTheme(), props.getThemedBorder()) ;
						}
					}
				}
			}
		) ;

		//Ανάλογα με το ποιο θέμα είναι ενεργοποιημένο
		//να ορίζεται επιλεγμένο και το κατάλληλο πεδίο της λίστας
		String currentTheme = props.getTheme() ;
		if(currentTheme.equals(SYSTEM))
			themesListComboBox.setSelectedIndex(0) ;
		else if(currentTheme.equals(METAL))
			themesListComboBox.setSelectedIndex(1) ;
		else if(currentTheme.equals(WINDOWS))
			themesListComboBox.setSelectedIndex(2) ;
		else if(currentTheme.equals(MOTIF))
			themesListComboBox.setSelectedIndex(3) ;
		else if(currentTheme.equals(GTK))
			themesListComboBox.setSelectedIndex(4) ;

		//Αρχικοποίηση του πλαισίου επιλογής θέματος στο περίγραμμα
		frameBorderThemeCheckBox = new JCheckBox("Apply theme and the border of the window") ;
		frameBorderThemeCheckBox.setToolTipText("Applies only to the Java - Metal theme") ;
		frameBorderThemeCheckBox.setEnabled(UIManager.getLookAndFeel().getSupportsWindowDecorations()) ;
		if(props.getThemedBorder())
			frameBorderThemeCheckBox.setSelected(true) ;
		else
			frameBorderThemeCheckBox.setSelected(false) ;

		frameBorderThemeCheckBox.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent e){
					props.setThemedBorder(!props.getThemedBorder()) ;
					props.storeProperties() ;
					//AfterzipFrame.reset("To apply the theme to the window border,\nyou need to restart the program.\nIf you do not want to reboot now, the theme will be applied to it\n the next time you start the program.") ;
					AfterzipFrame.reset() ;
				}
			}
		) ;

		//Κατά την αλλαγή του θέματος
		//Ενεργοποίηση της επιλογής θέματος για το περίγραμμα μόνο αν το επιτρέπει το τρέχον θέμα
		UIManager.addPropertyChangeListener(
			new java.beans.PropertyChangeListener(){
				public void propertyChange(java.beans.PropertyChangeEvent e) {
					if (e.getPropertyName().equals("lookAndFeel")) {
						frameBorderThemeCheckBox.setEnabled(UIManager.getLookAndFeel().getSupportsWindowDecorations()) ;
					}
    			}
			}
		);

		saveUserLocationCheckBox = new JCheckBox("Save location change   ") ;
		saveUserLocationCheckBox.setToolTipText("Saves the position where the window was last moved") ;
		if(props.getSaveNewLocation())
			saveUserLocationCheckBox.setSelected(true) ;
		else
			saveUserLocationCheckBox.setSelected(false) ;

		saveUserLocationCheckBox.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent e){
					props.setSaveNewLocation(!props.getSaveNewLocation()) ;
					props.storeProperties() ;
				}
			}
		) ;



		saveUserSizeCheckBox = new JCheckBox("Save resizing") ;
		saveUserSizeCheckBox.setToolTipText("Saves the dimensions the window took last time") ;
		if(props.getSaveNewSize())
			saveUserSizeCheckBox.setSelected(true) ;
		else
			saveUserSizeCheckBox.setSelected(false) ;

		saveUserSizeCheckBox.addItemListener(
			new ItemListener(){
				public void itemStateChanged(ItemEvent e){
					props.setSaveNewSize(!props.getSaveNewSize()) ;
					props.storeProperties() ;
				}
			}
		) ;


		resetLocationButton = new JButton("Reset") ;
		resetLocationButton.setToolTipText("Reset the window to the preset position") ;
		resetLocationButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					AfterzipFrame.setLocation(props.DEFAULT_FRAME_X, props.DEFAULT_FRAME_Y) ;
				}
			}
		) ;

		resetSizeButton = new JButton("Reset") ;
		resetSizeButton.setToolTipText("Reset the window dimensions to the preset") ;
		resetSizeButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					AfterzipFrame.setSize(props.DEFAULT_FRAME_WIDTH, props.DEFAULT_FRAME_HEIGHT) ;
				}
			}
		) ;


		JPanel themeSelectPanel = new JPanel() ;
		themeSelectPanel.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		themeSelectPanel.add(themeLabel) ;
		themeSelectPanel.add(themesListComboBox) ;

		JPanel themePanel = new JPanel() ;
		themePanel.setLayout(new GridLayout(3, 1)) ;
		themePanel.setBorder(BorderFactory.createTitledBorder("Customize Appearance")) ;
		themePanel.add(themeSelectPanel) ;
		themePanel.add(frameBorderThemeCheckBox) ;

		JPanel userSizePanel = new JPanel() ;
		userSizePanel.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		userSizePanel.add(saveUserSizeCheckBox) ;
		userSizePanel.add(resetSizeButton) ;

		JPanel userLocationPanel = new JPanel() ;
		userLocationPanel.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
		userLocationPanel.add(saveUserLocationCheckBox) ;
		userLocationPanel.add(resetLocationButton) ;

		JPanel userSavePanel = new JPanel() ;
		userSavePanel.setLayout(new GridLayout(2,1)) ;
		userSavePanel.setBorder(BorderFactory.createTitledBorder("Customize Dimensions")) ;
		userSavePanel.add(userSizePanel) ;
		userSavePanel.add(userLocationPanel) ;

		JPanel userSaveWithImagePanel = new JPanel() ;
		userSaveWithImagePanel.setLayout(new BorderLayout()) ;
		userSaveWithImagePanel.add(userSavePanel, BorderLayout.WEST) ;
		try{
			userSaveWithImagePanel.add(new JLabel(new ImageIcon(this.getClass().getResource("/images/customize.png"))), BorderLayout.EAST) ;
		}catch(NullPointerException exception){
			/* Αν δεν βρεθεί η εικόνα δεν τοποθετείτε τίποτα */
		}

		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)) ;
		add(themePanel) ;
		add(userSaveWithImagePanel) ;
	}
}
