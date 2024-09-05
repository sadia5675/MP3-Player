package business;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import de.hsrm.mi.prog2.TextIO;

/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 *         Die Klasse ist für das Auslesen von Informationen über Tracks aus
 *         einer Textdatei verantwortlich und für das Setzen dieser
 *         Informationen.
 */
public class PlaylistManager {
	private Playlist playlist;

	/**
	 * Ein neues Playlist-Objekt wird erstellt und dem Attribut playlist zugewiesen.
	 */
	public PlaylistManager() {
		playlist = new Playlist();
	}

	/**
	 * Methode liest eine Textdatei mit dem Namen ein. Die Playlist wird anhand der
	 * Informationen, die in der Textdatei enthalten sind, initialisiert.
	 * 
	 * @param name ist die Playlist textdatei.
	 * @return die erstellte Playlist.
	 */
	public Playlist getPlaylist(String name) {
		playlist.setTitle(name);
		List<String> songs = null;
		int i = 0;

		// dateien einlesen
		try {
			songs = TextIO.read(new File(name));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Datei nicht gefunden");
		}
		// Es werden für jeden Song in der Liste die Dateinamen gesetzt und dann die
		// Mp3-Dateien gelesen, um weitere Attribute wie Interpret, Albumtitel, Länge
		// und Titel zu setzen. Wenn vorhanden, wird auch ein Albumcover-Bild in der
		// Playlist gespeichert.
		for (String song : songs) {
			Track track = new Track();
			track.setDateiName(song);
			playlist.tracks.add(track);

			try {
				Mp3File mp3file = new Mp3File(song);
				if (mp3file.hasId3v1Tag()) {
					ID3v1 id3v1Tag = mp3file.getId3v1Tag();

					playlist.tracks.get(i).setInterpret(id3v1Tag.getArtist());
					playlist.tracks.get(i).setAlbumTitle(id3v1Tag.getAlbum());
					playlist.tracks.get(i).setLength((int) mp3file.getLengthInSeconds());
					playlist.tracks.get(i).setTitle(id3v1Tag.getTitle());

				}
				if (mp3file.hasId3v2Tag()) {
					ID3v2 id3v2Tag = mp3file.getId3v2Tag();
					byte[] imageData = id3v2Tag.getAlbumImage();
					playlist.tracks.get(i).setImage(imageData);
				}

			} catch (UnsupportedTagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			i++;
		}
		return playlist;
	}

	/**
	 * liest die Textdatei "Playlist.txt" ein und erstellt eine neue Playliste auf Basis der Informationen in der Textdatei.
	 * 
	 * @return gibt die erstellte Playliste am Ende zurück.
	 */
	public Playlist getAllTracks() {
		getPlaylist("Playlist.txt");
		return getPlaylist();
	}

	/**
	 * 
	 * @return gibt das aktuelle Playlist-Objekt zurück.
	 */
	public Playlist getPlaylist() {
		return playlist;
	}

}
