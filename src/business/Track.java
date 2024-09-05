package business;
/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 * Klasse in der die Track Eigenschaft beschrieben wird.
 *
 */
public class Track {
	private String albumTitle;
	private String interpret;
	private String dateiName;
	private String title;
	private int length;
	private byte [] image;
	
	/**
	 * 
	 * @return gibt die Länge des Tracks in Sekunden zurück.
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * 
	 * @param length wird verwendet, um die Länge des Tracks zu setzen.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * 
	 * @return den Wert des Attributs albumTitle.
	 */
	public String getAlbumTitle() {
		return albumTitle;
	}
	
	/**
	 * 
	 * @param albumTitle setzt den Wert des Attributs albumTitle.
	 */
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	
	/**
	 * 
	 * @return den Interpreten eines Tracks.
	 */
	public String getInterpret() {
		return interpret;
	}
	/**
	 * 
	 * @param interpret setzt den Wert für das Attribut interpret.
	 */
	public void setInterpret(String interpret) {
		this.interpret = interpret;
	}
	/**
	 * 
	 * @return den Dateinamen des Tracks. 
	 */
	public String getDateiName() {
		return dateiName;
	}

	/**
	 * 
	 * @param soundFile setzt den Namen der Audiodatei.
	 */
	public void setDateiName(String soundFile) {
		this.dateiName = soundFile;
	}
	
	/**
	 * 
	 * @return gibt den Wert des Attributs title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @param title setzt den Wert des Attributs title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @return das Attribut image des Track-Objekts.
	 */
	public byte[] getImage() {
		return image;
	}
	
	/**
	 * 
	 * @param image setzt das Attribut image des Track-Objekts.
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}
	/**
	 * Methode gibt eine  gibt eine Zeichenfolge zurück.
	 */
	public String toString() {
		return title + " - "+ interpret;
	}
	
}
