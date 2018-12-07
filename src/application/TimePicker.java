package application;

import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import javafx.scene.control.Control;
import org.controlsfx.control.RangeSlider;

import event.LibReserveEvent;

public class TimePicker extends VBox {

	long lowTime, highTime;
	Label lowLabel, highLabel;

	static String minute2str(long l) {
		return String.format("%02d:%02d", l / 60, l % 60);
	}

	static long roundMinute(long l) {
		return Math.round(l / 30) * 30;
	}

	public TimePicker() {
		this.setMinHeight(100);
		
		this.lowTime = roundMinute(780);
		this.highTime = roundMinute(850);
		this.lowLabel = new Label(minute2str(lowTime));
		this.highLabel = new Label(minute2str(highTime));
		RangeSlider slider = new RangeSlider(480, 1200, lowTime, highTime);
		slider.setPadding(new Insets(15));
		slider.setLowValue(this.lowTime);
		slider.setHighValue(this.highTime);
		
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
			this.lowLabel.setText(minute2str(this.highTime = roundMinute(newValue.longValue())));
			this.fireEvent(new LibReserveEvent(LibReserveEvent.INPUT_CHANGE, this));
		});
		slider.highValueProperty().addListener((observable, oldValue, newValue) -> {
			this.highLabel.setText(minute2str(this.lowTime = roundMinute(newValue.longValue())));
			this.fireEvent(new LibReserveEvent(LibReserveEvent.INPUT_CHANGE, this));
		});
		
		this.getChildren().addAll(slider);
	}

}
