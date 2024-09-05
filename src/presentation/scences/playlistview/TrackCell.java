package presentation.scences.playlistview;

import java.io.ByteArrayInputStream;

import business.Track;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Sadia Miah und Marigona Sejdiu
 * 
 * Klasse in der Zellen von der TrackCell die eigenschaften gesetzt werden.
 *
 */
public class TrackCell extends ListCell<Track> {
	VBox box;
	Label titleLabel;
	Label artistLabel;

	/**
	 * hier werden die labels wie Titel,artist,song länge und images gesetzt.
	 */
	public TrackCell() {
		box = new VBox(3); // 3 steht für den abstand der Box
		titleLabel = new Label();
		artistLabel = new Label();

		box.getChildren().addAll(titleLabel, artistLabel); // labels werden in die Box getan

		this.setGraphic(box);// die Box wird in die Graphic gesetzt
	}

	/**
	 * hier wird die Anzeige einer Zelle in einer Liste aktualisieren.
	 * @param item ist der neue Wert für die Zelle.
	 * @param empty gibt an ob die Zelle leer ist oder nicht.
	 */
	@Override
	public void updateItem(Track item, boolean empty) {
		super.updateItem(item, empty); // interne verhaltung
		//löschen falls was schon steht
		setText(null);
		setGraphic(null);
		// wenn die Zelle nicht leer ist 
		if (!empty) {
			// jeweils passende titel, artist und image werden gesetzt
			titleLabel.setText(item.getTitle());
			artistLabel.setText(item.getInterpret());
			setText(item.getTitle() + " - " + item.getLength());

			Image image = new Image(new ByteArrayInputStream(item.getImage()));
			ImageView imageView = new ImageView(image);
			
			imageView.setFitWidth(40);  // setzen von width
			imageView.setFitHeight(45); // setzten von height
			setGraphic(imageView); // buisness daten werden gefühllt und angezeigt

		} else {
			setGraphic(null); // sonst soll nichts dagestellt werden
		}
	}
}
