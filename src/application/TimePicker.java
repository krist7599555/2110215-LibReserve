package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import org.controlsfx.control.RangeSlider;

import event.LibReserveEvent;


/*
 * TimePicker = simple time interval selection (controlsfx)
 */
public class TimePicker extends HBox {

	long lowTime, highTime;
	Label lowLabel, highLabel;
	RangeSlider slider;

	static String minute2str(long l) {
		return String.format("%02d:%02d", l / 60, l % 60);
	}

	static long roundMinute(long l) {
		return Math.round(l / 30) * 30;
	}

	public TimePicker(boolean showLabel) {
		this();
		if (showLabel) {
			this.getChildren().clear();
			lowLabel.getStyleClass().add("time-picker-label");
			highLabel.getStyleClass().add("time-picker-label");
			this.getChildren().addAll(lowLabel, slider, highLabel);
		}
	}

	public TimePicker() {
		super();
		this.setAlignment(Pos.CENTER);
		this.lowTime = roundMinute(780);
		this.highTime = roundMinute(850);
		this.lowLabel = new Label(minute2str(lowTime));
		this.highLabel = new Label(minute2str(highTime));

		slider = new RangeSlider(480, 1200, lowTime, highTime);
		slider.setPadding(new Insets(15));
		slider.setLowValue(this.lowTime);
		slider.setHighValue(this.highTime);
		slider.setPrefSize(500, 100);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setBlockIncrement(120);
		slider.setMajorTickUnit(120);

		slider.setLabelFormatter(new StringConverter<>() {
			@Override
			public Double fromString(String string) {
				return null;
			}

			@Override
			public String toString(Number object) {
				return minute2str(object.longValue());
			}
		});
		slider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
			this.lowLabel.setText(minute2str(this.lowTime = roundMinute(newValue.longValue())));
			this.fireEvent(new LibReserveEvent(LibReserveEvent.INPUT_CHANGE, this));
		});
		slider.highValueProperty().addListener((observable, oldValue, newValue) -> {
			this.highLabel.setText(minute2str(this.highTime = roundMinute(newValue.longValue())));
			this.fireEvent(new LibReserveEvent(LibReserveEvent.INPUT_CHANGE, this));
		});

		this.getChildren().addAll(slider);
	}

}
