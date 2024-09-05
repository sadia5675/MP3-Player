package presentation.scences.playlistview;

import java.util.List;
import business.MP3Player;
import business.Playlist;
import business.PlaylistManager;
import business.Track;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 *         Klasse in der Evenens und Property in der Playlist View verarbeitet
 *         werden.
 *
 */
public class PlaylistViewController {
	Pane root;
	ListView<Track> playlistView;
	Button switchToPlayerButton;

	private boolean isPlaying = false;
	private boolean isPlayerPlaying = false;

	MP3Player player;
	PlaylistManager playlistManager;

	/**
	 * 
	 * Hier wird die PlaylistView initialisiert, um mit den Attributen dieser Klasse
	 * an die Attribute der PlayerView zu verbinden und als letztes wird die
	 * initialize() aufgerufen.
	 * 
	 * @param player          wird von der Main übergeben um auf die MP3Payer
	 *                        Methoden zuzugreifen zu können.
	 * @param playlistManager wird übergeben um auf die playlistManager zuzugreifen
	 *                        zu können.
	 */
	public PlaylistViewController(MP3Player player, PlaylistManager playlistManager) {
		this.player = player;
		this.playlistManager = playlistManager;

		PlaylistView view = new PlaylistView();
		playlistView = view.playlistView;
		switchToPlayerButton = view.switchToPlayerButton;

		root = view;

		initialize();
	}

	/**
	 * diese Methode ist für die Main da, damit die Main das Switchen ermöglichen
	 * kann.
	 * 
	 * @return den Button switchToPlayerButton
	 */
	public Button getSwitchToPlayerButton() {
		return switchToPlayerButton;
	}

	/**
	 * verwaltet die Events
	 */
	public void initialize() {

		// holt sich die tracks
		Playlist mockPlaylist = playlistManager.getPlaylist();
		List<Track> mockTracks = mockPlaylist.getTracks();

		// ObservableList wird verwendet, um eine Liste von Elementen zu repräsentieren,
		// die sich selbst aktualisieren, wenn sich deren Inhalt ändert.
		ObservableList<Track> content = FXCollections.observableArrayList();
		content.addAll(mockTracks);
		playlistView.setItems(content);

		// setCellFactory verwendet, um eine benutzerdefinierte Zellen-Anzeige für die
		// trackListView-Liste festzulegen.
		playlistView.setCellFactory(new Callback<ListView<Track>, ListCell<Track>>() {

			// besitzt über eigene Methoden und Eigenschaften, um die Anzeige der Zellen in
			// der Liste anzupassen.
			@Override
			public ListCell<Track> call(ListView<Track> param) {
				return new TrackCell();
			}

		});

		// hier werden die SelectionModel-Objekt der Liste abgerufen. Das SelectionModel
		// ist verantwortlich für das Verwalten der ausgewählten Elemente in der Liste.
		playlistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Track>() {
			@Override
			public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
				if (isPlayerPlaying == false) { // fragt ab, ob im player schon Play gedrückt wurde
					if (isPlaying == true) {
						player.setSelectedTrackNo(newValue); // der angeklickte song wird hier verarbeitet
					} else {
						player.play(); // sonst wird normal gespielt
						isPlaying = true;
					}
				}

			}
		});

		// der erste song wird als erstes Selektiert
		playlistView.getSelectionModel().clearAndSelect(0);

		// eine Property, die das aktuelle Track repräsentiert, der von einem Audio
		// Player abgespielt wird.
		player.getTrackProperty().addListener(new ChangeListener<Track>() {

			@Override
			public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
				Platform.runLater(new Runnable() {// alle GUI-bezogenen Aktionen werden in Runnable ausgeführt.
					public void run() {
						// immer der aktuelle track der spielt wird, wird sektiert.
						playlistView.getSelectionModel().select(newValue);
					}
				});
			}

		});

		// Innerhalb der Schleife wird eine Methode aufgerufen, die das erste Element
		// der content-Liste entfernt und für 500 Millisekunden schläft.
		Thread deleteThread = new Thread(() -> {
			while (content.size() > 2) {
				try {
					Platform.runLater(() -> content.remove(0));
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		deleteThread.start();

	}

	/**
	 * 
	 * @return ob der Player gerade Musik abspielt oder nicht. 
	 */
	public boolean isPlayerPlaying() {
		return isPlayerPlaying;
	}

	/**
	 * die Methode wird in der Main mit den Switch Buttons gesetzt
	 * 
	 * @param isPlayerPlaying gibt zurückt ob vom Player gespielt wurde
	 */
	public void setPlayerPlaying(boolean isPlayerPlaying) {
		this.isPlayerPlaying = isPlayerPlaying;
	}

	/**
	 * 
	 * @return gibt das Pane-Objekt zurück, das als Wurzelelement für die Playlistansicht dient.
	 */
	public Pane getRoot() {
		return root;
	}

}
