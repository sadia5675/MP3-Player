package presentation.scences.playerview;

import java.io.ByteArrayInputStream;

import business.MP3Player;
import business.Track;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 *         Klasse in der Evenens und Property in der Player View verarbeitet
 *         werden.
 *
 */
public class PlayerViewController {
	BorderPane root;
	Label title;
	Label artist;
	ImageView imageView;
	Slider songTimeSlider;
	Slider volumen;
	Label time;
	Button skipBackButton;
	Button skipButton;
	Button playerButton;
	Button pauseButton;
	PlayerView mainView;
	MP3Player player;

	ToggleButton repeatButton;
	ToggleButton shuffleButton;
	Button switchToPlaylistButton;
	private boolean isPlaying = false;

	/**
	 * Hier wird die PlayerView initialisiert, um mit den Attributen dieser Klasse
	 * an die Attribute der PlayerView zu verbinden. Und als letztes wird die
	 * initialize() aufgerufen.
	 * 
	 * @param player wird von der Main übergeben um auf die MP3Payer Methoden
	 *               zuzugreifen zu können
	 */
	public PlayerViewController(MP3Player player) {

		this.player = player;
		this.mainView = new PlayerView();
		title = mainView.title;
		artist = mainView.artist;

		imageView = mainView.imgView;
		songTimeSlider = mainView.songTimeSlider;
		volumen = mainView.volumenSlider;
		time = mainView.time;

		skipBackButton = mainView.skipBackButton;
		skipButton = mainView.skipButton;
		playerButton = mainView.playerButton;
		pauseButton = mainView.pauseButton;

		repeatButton = mainView.repeatButton;
		shuffleButton = mainView.shuffleButton;
		switchToPlaylistButton = mainView.switchToPlaylistButton;

		root = mainView;

		initialize();
	}

	/**
	 * Diese Methode ist für die Main da, damit die Main das Switchen ermöglichen
	 * kann.
	 * 
	 * @return den Button switchToPlaylistButton
	 */
	public Button getSwitchToPlaylistButton() {
		return switchToPlaylistButton;
	}

	/**
	 * verwaltet die Events und Property
	 */
	public void initialize() {

		// Ein ChangeListener ist ein Objekt, das überwacht, ob der Wert einer Property
		// geändert wird, und entsprechende Aktionen ausführt, wenn dies der Fall ist.

		playerButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (isPlaying == true) { // wenn schon mal die Play Methode aufgerufen wurde, zb von der Playliste am
											// anfang.
					playerButton.setVisible(true);// playerButton wird sichtbar
					playerButton.setManaged(true);
					pauseButton.setVisible(false);
					pauseButton.setManaged(false);
					isPlaying = false;
				} else {
					// wenn noch nicht die Play Methode aufgerufen wurde, dann wird sie jetzt getan.
					// Der zustand wird sich gemerkt.
					isPlaying = true;
					player.play();
					playerButton.setVisible(false);// pauseButton wird sichtbar
					playerButton.setManaged(false);
					pauseButton.setVisible(true);
					pauseButton.setManaged(true);
				}
			}
		});

		pauseButton.addEventHandler(ActionEvent.ACTION,
			event -> {
				// die Pause Methode wird aufgerufen.
				player.pause();
				playerButton.setVisible(true);
				playerButton.setManaged(true);
				pauseButton.setVisible(false);
				pauseButton.setManaged(false);

			}
		);

		skipButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// wenn noch nicht die Play Methode vom PlayButton aufgerufen wurde, dann wird
				// sie hier getan.
				if (isPlaying == false) {
					isPlaying = true;
					player.play();
					playerButton.setVisible(false);
					playerButton.setManaged(false);
					pauseButton.setVisible(true);
					pauseButton.setManaged(true);

					// sonst wird die skip Methode aufgerufen.
				} else {
					player.skip();
					playerButton.setVisible(false);
					playerButton.setManaged(false);
					pauseButton.setVisible(true);
					pauseButton.setManaged(true);

				}
			}
		});

		skipBackButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// wenn noch nicht die Play Methode vom PlayButton aufgerufen wurde, dann wird
				// sie hier getan.
				if (isPlaying == false) {
					isPlaying = true;
					player.play();
					playerButton.setVisible(false);
					playerButton.setManaged(false);
					pauseButton.setVisible(true);
					pauseButton.setManaged(true);

					// sonst wird die skipBack Methode aufgerufen.
				} else {
					player.skipBack();
					playerButton.setVisible(false);
					playerButton.setManaged(false);
					pauseButton.setVisible(true);
					pauseButton.setManaged(true);

				}
			}
		});

		// einen EventHandler für das "Action"-Ereignis. Das
		// "Action"-Ereignis tritt auf, wenn der Benutzer auf das GUI-Element klickt.

		repeatButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (repeatButton.isSelected()) { // hier wird der zustand vom repeat gesetzt.
					player.setRepeatOn(true);
				} else {
					player.setRepeatOn(false); // erst beim zweiten clicken ändert sich der zustand.
				}
			}
		});

		shuffleButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// das gleiche wie bei repeat, nur das es diesmal shuffel ist.
				if (shuffleButton.isSelected()) {
					player.setShuffleOn(true);
				} else {
					player.setShuffleOn(false);
				}
			}
		});

		// wertebereich vom Volumen
		volumen.setMaxSize(-10, 10);
		// man kann den slider Thumb ziehen und es wird je nach dem der passende wert
		// gesetzt.
		volumen.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> oV, Number oldValue, Number newValue) {
				double volume;
				volume = newValue.doubleValue();
				player.volume(volume);

			}
		});
		// Bindung zwischen zwei Propertys werden hergestellt.
		player.getVolumenProperty().bindBidirectional(volumen.valueProperty());

		// wird verwendet um auf die Property zugreifen zu können, die die aktuellen
		// Werte vom Slider repräsentiert.
		songTimeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println(newValue.doubleValue());
			}
		});

		// eine Property, die den aktuellen Wiedergabezeitpunkt vom Audio Player
		// repräsentiert.
		player.getTimeProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				Platform.runLater(new Runnable() { // alle GUI-bezogenen Aktionen werden in Runnable ausgeführt.
					public void run() {
						time.setText(newValue.toString());// set vom Time Label
						songTimeSlider.valueProperty().set(newValue.intValue());
					}
				});
			}

		});

		// eine Property, die das aktuelle Track repräsentiert.
		player.getTrackProperty().addListener(new ChangeListener<Track>() {

			@Override
			public void changed(ObservableValue<? extends Track> observable, Track oldValue, Track newValue) {
				Platform.runLater(new Runnable() {
					public void run() {
						// setzt immer den aktuellen Track titel, artist, Wiedergabezeitpunkt und bild.
						title.setText(newValue.getTitle());
						artist.setText(newValue.getInterpret());
						songTimeSlider.setMax(newValue.getLength());
						imageView.setImage(new Image(new ByteArrayInputStream(newValue.getImage())));
					}
				});
			}

		});

		// eine Property, die angibt, ob ein Audio Player gerade abgespielt wird oder
		// nicht.
		player.getPlayingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				Platform.runLater(new Runnable() {
					public void run() {
						// wenn grad gespielt wird, zb von der Playliste dann, wird der zustand sich
						// gemerkt.
						if (newValue == true) {
							isPlaying = true;
							// pauseButton wird sichtbar
							playerButton.setVisible(false);
							playerButton.setManaged(false);
							pauseButton.setVisible(true);
							pauseButton.setManaged(true);

							// in der Pause() methode wird repeat und shuffel ausgeschaltet. Repeat oder
							// shuffel müssen wieder angeschaltet werden, wenn sie vom Nutzer aktiviert
							// wurden. Daher merken wir uns das in einer seperaten Variabel und eine andere
							// Variable führt das eigentliche Wiederholungs-Modus oder shuffel-modus aus.
							if (player.isRepeatOn() == true) {
								player.setRepeat(true);

							} else if (player.isShuffleOn() == true) {
								player.setShuffle(true);
							}

						}

					}
				});
			}

		});

	}

	/**
	 * 
	 * @return gibt das Pane-Objekt zurück, das als Wurzelelement für die
	 *         Playeransicht dient.
	 */
	public Pane getRoot() {
		return root;
	}

}
