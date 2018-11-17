package application;

import java.util.Arrays;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class Form {
	String zone;
	Integer seat;
	String[] students;

	Form(String zone, Integer seat, String[] students) {
		this.zone = zone;
		this.seat = seat;
		this.students = students;
	}

	public String toString() {
		String res = "zone: " + zone + ", seat: " + seat + "\n";
		for (int i = 0; i != students.length; ++i) {
			res += i + ": " + students[i] + "\n";
		}
		return res;
	}
}

public class Controll extends VBox {

	private ChoiceBox<String> zones = new ChoiceBox<String>(FXCollections.observableArrayList(Config.zones));
	private ChoiceBox<Integer> seats = new ChoiceBox<Integer>();
	private ObservableList<TextField> students = FXCollections.observableArrayList();
	private Button submitBtn;
	private int studentsIdx;

	public Controll() {
		super();
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(new Label("Engineer Library"));

		this.getChildren().add(new TextField());

		HBox select = new HBox();
		select.getChildren().addAll(zones, seats);
		this.getChildren().add(select);

		this.studentsIdx = this.getChildren().size();
		this.getChildren().add(new VBox());

		zones.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					this.seats.getItems().clear();
					final Info info = Config.seats.get(newValue);
					for (Integer i : info.getPosition()) {
						this.seats.getItems().add(i);
					}
					setStudentNeed(info.getReserveNeed());
				});

		this.getChildren().add(this.submitBtn = new Button("submit"));
		this.submitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			System.out.println("SUBMIT");
			System.out.println(getForm());
		});
	}

	public Form getForm() {
		return new Form(zones.getValue(), seats.getValue(), getStudentList());
	}
	public String[] getStudentList() {
		return Arrays.asList(students.toArray(new TextField[students.size()]))
				.stream().map(o -> o.getText()).toArray(String[]::new);
	}
	private void setStudentNeed(int need) {
		while (students.size() > need) students.remove(students.size() - 1);
		while (students.size() < need) students.add(new TextField());
		VBox box = new VBox();
		box.getChildren().addAll(students);
		this.getChildren().set(studentsIdx, box);
	}
}
