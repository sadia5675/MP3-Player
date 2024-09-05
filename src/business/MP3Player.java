package business;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 *         Klasse in der man mit der Playliste/Player arbeitet und ihre zustände
 *         verarbeitet werden.
 *
 */
public class MP3Player {
	SimpleMinim minim;
	SimpleAudioPlayer audioPlayer;
	private Playlist actPlaylist;
	private int trackNo = 0;

	private boolean repeat = false;
	private boolean shuffle = false;
	
	private boolean repeatOn = false;
	private boolean shuffleOn = false;
	
	private boolean autoPlay = false;

	private long position;
	private int zeit = 1;
	private int zeithilfe = 0;
	private int actTime = 0;

	minimThread audioPlayer1;
	SimpleIntegerProperty time;
	SimpleObjectProperty<Track> track;
	SimpleBooleanProperty playing;
	SimpleDoubleProperty volumen;
	Thread playThread;

	/**
	 * 
	 * Eine Thread-Implementation, die es ermöglicht, parallele Ausführung von Code
	 * zu realisieren.Die Methode run() enthält den Code, der von dem neuen Thread
	 * ausgeführt werden soll.
	 *
	 */
	public class minimThread extends Thread {
		public void run() {

			// startet das Abspielen eines Audiosignals an der Stelle, die durch den
			// int-Wert position angegeben wird.
			audioPlayer.play((int) position);

			// überprüft, ob der AudioPlayer gerade abgespielt wird.
			if (audioPlayer.isPlaying() == false) {
		
				// Wenn repeat true ist, wird der AudioPlayer wiederholt abgespielt, wenn er
				// beendet wurde.
				if (repeat == true) {
					//aktuelle Thread wird für 1 Sekunde angehalten,
					// da sonst die zeitleiste verrück spielt.
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// um anzuzeigen, dass das Audiosignal vom Anfang abgespielt werden soll.
					position = 0;
					play();
					
					// Wenn shuffle true ist, wird eine zufällige Stelle in der Playlist ausgewählt
					// und der AudioPlayer wird an dieser Stelle gestartet.
				} else if (shuffle == true) {
					int shuffle = (int) (Math.random() * actPlaylist.numberOfTracks());
					trackNo = shuffle;
					
					//aktuelle Thread wird für 1 Sekunde angehalten
					// da sonst die zeitleiste verrück spielt
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					position = 0;
					play();
					// Wenn autoPlay true ist und die aktuelle Abspielposition im Audiosignal
					// zwischen 29 Sekunden und der Länge des aktuellen Tracks liegt, wird die
					// Methode skip() aufgerufen.
				} else if (autoPlay == true) {
					if (actTime >= 29 && actTime <= actPlaylist.tracks.get(trackNo).getLength()) {
						skip();
					}
				}
			}

		}
	}

	/**
	 * Der Konstruktor wird aufgerufen, um ein neues Objekt der Klasse MP3Player zu
	 * erstellen und dessen Eigenschaften zu initialisieren.
	 */
	public MP3Player() {
		minim = new SimpleMinim();// Audiosignale zu verarbeiten und abzuspielen
		time = new SimpleIntegerProperty();// Aktuelle Abspielposition des Audiosignals zu speichern und zu verfolgen.
		track = new SimpleObjectProperty<>();// aktuellen AudioInformation zu speichern und zu verfolgen.
		playing = new SimpleBooleanProperty();// Anzeige, ob das Audiosignal gerade abgespielt wird oder nicht.
		volumen = new SimpleDoubleProperty();// Lautstärkelevel des Audiosignals zu speichern und zu verfolgen.
	}

	/**
	 * die Methode wird verwendet, um eine Wiedergabeliste abzuspielen.
	 */
	public void play() {
		// AutoPlay-Modus zu aktivieren
		this.autoPlay = true;

		// um anzuzeigen, dass das Audiosignal gerade abgespielt wird.
		playing.set(true);

		String fileName;
		// Setzt den Wert von track auf den aktuellen Track in der Wiedergabeliste
		track.set(actPlaylist.tracks.get(trackNo));

		// Lädt das Audiosignal des aktuellen fileName und speichert es im
		// AudioPlayer
		fileName = actPlaylist.tracks.get(trackNo).getDateiName();
		audioPlayer = minim.loadMP3File(fileName);

		// Erstellt ein neues Objekt der Klasse minimThread und ruft die Methode start()
		// auf, um den neuen Thread zu starten.
		audioPlayer1 = new minimThread();
		audioPlayer1.start();

		// Erstellt einen neuen Thread playThread und startet ihn
		playThread = new Thread() {
			public void run() {
				// Wenn der Thread nicht unterbrochen wurde, wird der Thread für 1 Sekunde
				// pausiert.
				if (!isInterrupted()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						interrupt();
					}

					// Der Wert von zeithilfe wird auf die aktuelle Abspielposition des Audiosignals
					// gesetzt und der Wert von zeit wird auf die aktuelle Abspielposition in
					// Sekunden gesetzt.
					zeithilfe = audioPlayer.position();
					zeit = zeithilfe / 1000;

					// In jedem Durchlauf der Schleife wird der Wert von time auf actTime gesetzt
					// und der Thread wird für 1 Sekunde pausiert.
					for (actTime = zeit; actTime <= actPlaylist.tracks.get(trackNo).getLength()
							&& audioPlayer.isPlaying(); actTime++) {
						time.set(actTime);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							interrupt();
						}
					}

				}
			}
		};
		playThread.start();
	}

	/**
	 * Methode wird verwendet, um die aktuelle Wiedergabeliste des MP3Players
	 * festzulegen.
	 * 
	 * @param actPlaylist wird verwendet, um die aktuelle Wiedergabeliste zu
	 *                    speichern und zu verfolgen.
	 */
	public void setPlaylist(Playlist actPlaylist) {
		this.actPlaylist = actPlaylist;
	}
	
	/**
	 * @param repeat Wiederholungs-Modus des MP3Players aktiviert oder deaktiviert
	 *               ist.
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
		System.out.println("repeat: "+repeat);
	}

	/**
	 * @param shuffle Shuffle-Modus des MP3Players aktiviert oder deaktiviert ist.
	 */
	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
		System.out.println("shuffle: "+shuffle);
	}
	
	/**
	 * 
	 * @return den Zustand des Wiederholungs-Modus
	 */
	public boolean isRepeatOn() {
		return repeatOn;
	}
	
	/**
	 * 
	 * @param repeatOn shuffleOn zeigt, ob der Wiederholungs-Moduss aktiviert oder deaktiviert werden soll.
	 */
	public void setRepeatOn(boolean repeatOn) {
		this.repeatOn = repeatOn;
		setRepeat(repeatOn);
	}
	
	
	/**
	 * 
	 * @return den Zustand des Shuffle-Modus
	 */
	public boolean isShuffleOn() {
		return shuffleOn;
	}
	/**
	 * 
	 * @param shuffleOn zeigt, ob der Shuffle-Modus aktiviert oder deaktiviert werden soll.
	 */
	public void setShuffleOn(boolean shuffelOn) {
		this.shuffleOn = shuffelOn;
		setShuffle(shuffelOn);
	}

	/**
	 * Methode wird verwendet, um den aktuellen Track im MP3Player zu überspringen
	 * und den nächsten Track in der Wiedergabeliste abzuspielen.
	 */
	public synchronized void skip() {
		// um das Audiosignal anzuhalten.
		pause();

		// Prüft, ob der aktuelle Track der letzte Track in der
		// Wiedergabeliste ist. Wenn dies der Fall ist, wird der Wert von trackNo auf 0
		// gesetzt, um den ersten Track in der Wiedergabeliste abzuspielen. Wenn
		// dies nicht der Fall ist, wird der Wert von trackNo um 1 erhöht, um den
		// nächsten Track in der Wiedergabeliste abzuspielen.
		if (this.trackNo < actPlaylist.numberOfTracks() - 1) {
			this.trackNo++;

		} else {
			this.trackNo = 0;
		}
		// um anzuzeigen, dass das Audiosignal vom Anfang abgespielt werden soll.
		this.position = 0;
		// um den nächsten Track in der Wiedergabeliste abzuspielen.
		play();
	}

	/**
	 * Methode wird verwendet, um den aktuellen Track im MP3Player zu überspringen
	 * und den vorherigen Track in der Wiedergabeliste abzuspielen.
	 */
	public synchronized void skipBack() {
		pause();

		// Prüft, ob der aktuelle Track der erste Track in der Wiedergabeliste
		// ist. Wenn dies der Fall ist, wird der Wert von trackNo auf den letzten
		// Track in der Wiedergabeliste gesetzt. Wenn dies nicht der Fall ist, wird
		// der Wert von trackNo um 1 reduziert, um den vorherigen Track in der
		// Wiedergabeliste abzuspielen.
		if (trackNo == 0) {
			trackNo = (actPlaylist.numberOfTracks() - 1);
		} else {
			trackNo--;
		}
		this.position = 0;
		play();
	}

	/**
	 * Methode wird verwendet, um das Audiosignal anzuhalten.
	 */
	public void pause() {
		// um anzuzeigen, dass der AutoPlay-Modus deaktiviert ist.
		this.autoPlay = false;
		// um anzuzeigen, dass der Wiederholungsmodus deaktiviert ist.
		this.repeat = false;
		// um anzuzeigen, dass der Shuffle-Modus deaktiviert ist.
		this.shuffle = false;
		// um anzuzeigen, dass der AudioPlayer gerade nicht abgespielt wird.
		playing.set(false);
		// Unterbricht den Thread playThread.
		playThread.interrupt();

		// Prüft, ob actPlaylist null ist. Wenn dies nicht der Fall ist, wird der Wert
		// von position auf die aktuelle Abspielposition des Audiosignals gesetzt und
		// die Methode pause() des AudioPlayers wird aufgerufen, um das Audiosignal
		// anzuhalten.
		if (actPlaylist != null) {
			this.position = audioPlayer.position();
			audioPlayer.pause();

		}
	}

	/**
	 * Methode wird verwendet, um die Lautstärke des Audiosignals festzulegen.
	 * 
	 * @param value Konvertiert den double-Wert in einen float-Wert und speichert
	 *              ihn in einer lokalen Variablen value.
	 */
	public void volume(double value) {
		audioPlayer.setGain((float) value);
	}

	/**
	 * Methode wird verwendet, um die aktuelle Wiedergabeliste des MP3Players
	 * zurückzugeben.
	 * 
	 * @return den Wert von actPlaylist, der die aktuelle Wiedergabeliste des
	 *         MP3Players darstellt.
	 */
	public Playlist getActPlaylist() {
		return actPlaylist;
	}

	/**
	 * 
	 * @return trackNo, der die Nummer des aktuellen Tracks darstellt.
	 */
	public int getTrackNo() {
		return trackNo;
	}


	/**
	 * 
	 * @return den Wert von time zurück, der eine SimpleIntegerProperty ist.
	 */
	public SimpleIntegerProperty getTimeProperty() {
		return time;
	}

	/**
	 * 
	 * @return den Wert von track zurück, der eine SimpleObjectProperty ist.
	 */
	public SimpleObjectProperty<Track> getTrackProperty() {
		return track;
	}

	/**
	 * 
	 * @return den Wert von volumen zurück, der eine SimpleDoubleProperty ist.
	 */
	public SimpleDoubleProperty getVolumenProperty() {
		return volumen;
	}

	/**
	 * 
	 * @return den Wert von playing zurück, der eine SimpleBooleanProperty ist.
	 */
	public SimpleBooleanProperty getPlayingProperty() {
		return playing;
	}

	/**
	 * Methode wird verwendet, um den aktuellen Track des MP3Players zu ändern.
	 * 
	 * @param newValue stellt den neuen Track da, der abgespielt werden soll.
	 */
	public void setSelectedTrackNo(Track newValue) {
		pause();
		
		// Setzt die Variable trackNo auf den Index des neuen Track in der
		// Playliste.
		this.trackNo = actPlaylist.tracks.indexOf(newValue);
		this.position = 0;
		play();
	}

}
