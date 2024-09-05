package presentation.scences.playlistview;
import business.Track;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 * Klasse für die Playlist View Gui
 *
 */
public class PlaylistView extends BorderPane {
	Label headerLabel;
	ListView<Track> playlistView;
	Button switchToPlayerButton;
	
	/**
	 * hier werden die Mermerkmale von den Buttons, Label und ListView bestimmt
	 */
	public PlaylistView() {
		//Überschrift
		headerLabel = new Label("Playlist");
		headerLabel.setId("playlist-header");// für die Css
		this.setTop(headerLabel);// position in der Playlist View
		//Liste
		playlistView = new ListView<>();
		playlistView.setId("playlistView");
		this.setCenter(playlistView);
		//BUtton
		switchToPlayerButton = new Button();
		switchToPlayerButton.setId("icon-player-button");
		
		this.setBottom(switchToPlayerButton);
		
		// setAlignment-Methode setzt die Ausrichtung des Elements innerhalb der BorderPane.
		// setMargin-Methode setzt den Abstand des Elements von den Rändern der BorderPane.
		BorderPane.setAlignment(headerLabel, Pos.CENTER);
		BorderPane.setMargin(headerLabel, new Insets(10));
		
		BorderPane.setAlignment(switchToPlayerButton,Pos.CENTER);
		BorderPane.setMargin(switchToPlayerButton, new Insets(10));
	}

}
