package presentation.scences.playerview;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import presentation.ImageViewPaneClass.ImageViewPane;

/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 *
 *         Klasse für die Player View Gui
 */
public class PlayerView extends BorderPane {
	Label title;
	Label artist;
	Image image;
	Slider songTimeSlider;
	Slider volumenSlider;
	Label time;
	ImageView imgView;
	Button laut;
	Button leise;
	Button skipBackButton;
	Button skipButton;
	Button playerButton;
	Button pauseButton;
	ToggleButton repeatButton;
	ToggleButton shuffleButton;
	Button switchToPlaylistButton;

	/**
	 * Die Klasse enthält mehrere Button- und ToggleButton-Instanzen, die für
	 * die Steuerung des MP3Players verwendet werden, z.B. für das Abspielen,
	 * Pausieren und Überspringen von Songs. Die Labels dienen der Anzeige von
	 * Informationen wie dem Titel und dem Interpreten des aktuellen Songs. Der
	 * Slider ermöglicht es dem Benutzer, die Lautstärke anzupassen und den
	 * Fortschritt des Songs anzuzeigen.
	 */
	public PlayerView() {
		//title und Artist
		VBox header = new VBox();// vertikale box
		title = new Label("Title");
		artist = new Label("Artist");
		title.setId("title-text");
		artist.setId("artist-text");

		switchToPlaylistButton = new Button();
		switchToPlaylistButton.setId("icon-playlist-button"); // für die css

		header.getChildren().addAll(switchToPlaylistButton, title, artist);// buttons und Label wird in die VBox gelegt
		header.setId("player-header");
		this.setTop(header);
		
		//image
		VBox bild = new VBox();
		image = new Image("/images/bsp.jpg");

		imgView = new ImageView();
		ImageViewPane imagPane = new ImageViewPane(imgView);
		imgView.setImage(image);
		bild.setId("cover"); // css
		bild.getChildren().add(imagPane);

		this.setCenter(bild);
		
		//lautstärke slider 
		VBox left = new VBox();// spalte
		HBox buttonsLeftUp = new HBox();// zeile
		HBox buttonsLeftDown = new HBox();// zeile
		VBox slidebarLeft = new VBox();// spalte
		// dienen nur zur schau
		laut = new Button();
		laut.setId("icon-laut-button");
		leise = new Button();
		leise.setId("icon-leise-button");

		volumenSlider = new Slider();
		volumenSlider.setId("volumen");
		volumenSlider.setOrientation(Orientation.VERTICAL);// slider soll nicht horizontal sein

		slidebarLeft.getChildren().add(volumenSlider);
		buttonsLeftUp.getChildren().add(laut);
		buttonsLeftDown.getChildren().add(leise);

		left.getStyleClass().add("player-control");
		left.getChildren().addAll(buttonsLeftUp, slidebarLeft, buttonsLeftDown);
		this.setLeft(left);
		// play,pause,skip oder SkipBack, repeat und shuffel button
		VBox control = new VBox(); // spalte
		HBox slidebar = new HBox();// zeile
		HBox buttons = new HBox(); // zeile

		// sollen beim responsive layout helfen.
		HBox grow = new HBox();
		HBox grow1 = new HBox();
		HBox grow2 = new HBox();
		HBox grow4 = new HBox();
		HBox grow5 = new HBox();
		HBox grow6 = new HBox();
		
		// SongTime Slider anzeige
		songTimeSlider = new Slider();
		songTimeSlider.setShowTickMarks(true);
		songTimeSlider.setShowTickLabels(true);
		songTimeSlider.setMajorTickUnit(5);
		songTimeSlider.setMinorTickCount(4);

		// Wenn der Screen größer wird wird auch der slider größer
		HBox.setHgrow(songTimeSlider, Priority.ALWAYS);
		// song time als Label
		time = new Label();
		time.setId("time");

		skipBackButton = new Button();
		skipBackButton.setId("icon-skip-back-button");

		skipButton = new Button();
		skipButton.setId("icon-skip-button");

		playerButton = new Button();
		playerButton.setId("icon-play-button");

		pauseButton = new Button();
		pauseButton.setId("icon-pause-button");
		pauseButton.setVisible(false);// es soll nicht dirkt zu sehen sein
		pauseButton.setManaged(false);

		// ToggleButton da man zwei zustände für ein Button setzten kann
		repeatButton = new ToggleButton();
		repeatButton.setId("icon-repeat-button");

		shuffleButton = new ToggleButton();
		shuffleButton.setId("icon-shuffle-button");

		// hier wird gesagt das die Boxen größer werden sollen wenn man den Screen
		// größer macht.
		HBox.setHgrow(grow, Priority.ALWAYS);
		HBox.setHgrow(grow1, Priority.ALWAYS);
		HBox.setHgrow(grow2, Priority.ALWAYS);
		HBox.setHgrow(grow4, Priority.ALWAYS);
		HBox.setHgrow(grow5, Priority.ALWAYS);
		HBox.setHgrow(grow6, Priority.ALWAYS);

		// für den abstand um die Buttons
		HBox.setMargin(playerButton, new Insets(10));
		HBox.setMargin(pauseButton, new Insets(10));

		buttons.getStyleClass().add("player-control");
		slidebar.getStyleClass().add("player-control");

		// hier werden wieder alle Buttons, label und Slider in die bestimmte Box und
		// Position getan.
		slidebar.getChildren().addAll(songTimeSlider, time);
		buttons.getChildren().addAll(grow, shuffleButton, grow1, skipBackButton, grow2, playerButton, pauseButton,
				grow4, skipButton, grow5, repeatButton, grow6);
		control.getChildren().addAll(slidebar, buttons);

		this.setBottom(control);

	}
}
