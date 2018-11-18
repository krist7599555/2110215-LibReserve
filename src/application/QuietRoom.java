package application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class QuietRoom extends GridPane {

	private Button seat1A, seat1B, seat1C, seat1D;
	private Button seat2A, seat2B;
	private Button seat3A, seat3B;
	private Button seat4A, seat4B, seat4C, seat4D;
	private Label walkway1, walkway2, walkway3;
	
	public QuietRoom () {
		
		walkway1 = new Label("WalkWay");
		walkway2 = new Label("WalkWay");
		walkway2.setRotate(-90);
		walkway3 = new Label("WalkWay");
		seat1A = new Button("1A");
		seat1B = new Button("1B");
		seat1C = new Button("1C");
		seat1D = new Button("1D");
		seat2A = new Button("2A");
		seat2B = new Button("2B");
		seat3A = new Button("3A");
		seat3B = new Button("3B");
		seat4A = new Button("4A");
		seat4B = new Button("4B");
		seat4C = new Button("4C");
		seat4D = new Button("4D");
		
		this.add(walkway1, 1, 1, 4, 1);
		this.add(walkway2, 0, 0, 1, 6);
		this.add(walkway3, 1, 4, 4, 1);
		this.add(seat1A, 1, 0);
		this.add(seat1B, 2, 0);
		this.add(seat1C, 3, 0);
		this.add(seat1D, 4, 0);
		this.add(seat2A, 1, 2);
		this.add(seat2B, 2, 2);
		this.add(seat3A, 1, 3);
		this.add(seat3B, 2, 3);
		this.add(seat4A, 1, 5);
		this.add(seat4B, 2, 5);
		this.add(seat4C, 3, 5);
		this.add(seat4D, 4, 5);
		
	}

}
