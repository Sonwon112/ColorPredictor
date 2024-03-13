import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Circle extends JPanel {
	
	String color = "black";
	
	public Circle(String color) {
		super();
		this.color = color;
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(color=="black" ? Color.BLACK:Color.WHITE);
		//g.setColor(Color.blue);
		g.fillOval(0, 0, 80, 80);
		g.setColor(color=="black" ? Color.WHITE:Color.BLACK);	
		g.fillOval(5, 5, 70, 70);
	}
}
