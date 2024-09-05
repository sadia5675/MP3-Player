package business;
import java.util.LinkedList;
import java.util.List;
/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 * Klasse die die Playliste mit ihren Eigenschaften verwaltet.
 *
 */
public class Playlist {
	List<Track> tracks;
	private String title;

	/**
	 * Eine neue leere Liste von Tracks wird erstellt.
	 */
	public Playlist() {
		tracks = new LinkedList<>();
	}
	
	/**
	 * @return den Titel der Playlist.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @param title der Playlist zu setzen.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return die Liste der Tracks. 
	 */
	public List<Track> getTracks() {
		return tracks;
	}
	
	/**
	 * 
	 * @return die Anzahl der Tracks in der Playlist.
	 */
	public int numberOfTracks() {
		return tracks.size();
	}
}
