package ingredients;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HotDogPanel extends JPanel {
	private HotDog hotDog;
	
	public HotDogPanel(HotDog hotDog, Color color) {
		this.hotDog = hotDog;
		this.setBackground(color);
		// this.setPreferredSize(new Dimension(400, 400));
	}
	
	public HotDog getHotDog() {
		return hotDog;
	}
	
	public void setHotDog(HotDog hotDog) {
		this.hotDog = hotDog;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		hotDog.serve(g);
	}
}
