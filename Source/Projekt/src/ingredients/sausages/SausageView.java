package ingredients.sausages;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SausageView extends JPanel {
	private SausageModel sausage;
	
	public SausageView(SausageModel sausage) {
		this.sausage = sausage;
		this.setBackground(new Color(180, 60, 0));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		sausage.kirajzol(g);
	}
}
