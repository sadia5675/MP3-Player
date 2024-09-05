package application;

import java.util.HashMap;
import java.util.Map;
import business.MP3Player;
import business.PlaylistManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import presentation.scences.playerview.PlayerViewController;
import presentation.scences.playlistview.PlaylistViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 *         Main in der man die Playliste zu gesicht bekommt
 *
 */
public class Main extends Application {
	private Map<String, Pane> scenes;
	private MP3Player player;

	PlaylistManager actManager;
	Button switchToPlaylistButton;
	Button switchToPlayerButton;

	private Scene scene;
	private Pane root;

	/**
	 * Methode wird verwendet, um die Initialisierung von bestimmten Elementen die
	 * Anwendung durchzuführen.
	 */
	public void init() {
		// verschiedene Szenen in der Anwendung zu speichern.
		scenes = new HashMap<>();
		// Erstellt ein neues MP3Player-Objekt mit dem Namen player.
		player = new MP3Player();
		// Erstellt ein neues PlaylistManager-Objekt mit dem Namen actManager.
		actManager = new PlaylistManager();
		// Ruft die Methode getPlaylist() des PlaylistManager auf und übergibt ihr den
		// String "Playlist.txt". Diese Methode wird verwendet, um die Playlist aus
		// einer Textdatei zu laden.
		actManager.getPlaylist("Playlist.txt");
		player.setPlaylist(actManager.getPlaylist());
	}

	/**
	 * Methode wird verwendet, um die JavaFX-Anwendung zu starten und die
	 * Benutzeroberfläche (UI) zu initialisieren.
	 * 
	 * @param primaryStage der vom Typ Stage ist und das Hauptfenster der Anwendung
	 *                     darstellt.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// Diese Controller-Klassen werden verwendet, um die Logik der
			// Benutzeroberfläche der Anwendung zu steuern.
			// Danach werden die Szenen, erstellt wurden, der
			// HashMap scenes hinzugefügt. Die Szenen werden unter dem passenden Schlüssel
			// gespeichert.
			PlayerViewController playerViewController = new PlayerViewController(player);
			scenes.put("PlayerView", playerViewController.getRoot());

			PlaylistViewController playlistViewController = new PlaylistViewController(player, actManager);
			scenes.put("PlaylistView", playlistViewController.getRoot());

			// holt Button für den setOnAction event
			switchToPlaylistButton = playerViewController.getSwitchToPlaylistButton();
			switchToPlayerButton = playlistViewController.getSwitchToPlayerButton();

			// um anzuzeigen, dass diese Scene zu Beginn angezeigt wird.
			root = scenes.get("PlaylistView");

			scene = new Scene(root, 400, 500);
			primaryStage.setTitle("EiBo Player");
			primaryStage.getIcons().add(new Image("/images/play.png"));

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());// Style Klasse
			primaryStage.setScene(scene);
			primaryStage.show();
			// wie klein das fenster sein darf.
			primaryStage.setMinHeight(500);
			primaryStage.setMinWidth(400);

			// Die EventHandler ändern die angezeigte Scene, indem sie die Scene im
			// root-Knoten ändern, wodurch die Ansicht von der Playlist-Ansicht zur
			// Player-Ansicht oder umgekehrt gewechselt wird.
			switchToPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					root = scenes.get("PlayerView");
					scene.setRoot(root);
					playlistViewController.setPlayerPlaying(true); // das setzten hift damit man sich merken kann wo der
																	// Nutzer für eine r0ot ist
				}
			});

			switchToPlaylistButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					root = scenes.get("PlaylistView");
					scene.setRoot(root);
					playlistViewController.setPlayerPlaying(false);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode ist dafür verantwortlich, die JavaFX-Anwendung zu starten und zu
	 * initialisieren.
	 * 
	 * @param args die Argumente enthält, die beim Starten der Anwendung von der
	 *             Kommandozeile übergeben werden.
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
