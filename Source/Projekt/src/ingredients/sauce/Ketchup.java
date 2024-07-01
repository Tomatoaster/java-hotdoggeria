package ingredients.sauce;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ingredients.HotDog;
import ingredients.HotDogIngredient;

public class Ketchup extends HotDogIngredient {
	private BufferedImage img;
	
	public Ketchup(HotDog hotDog) {
		super(hotDog);
		try {
			img = ImageIO.read(this.getClass().getResource("/img/sauce/Ketchup.png"));
		}catch(IOException e) {
			System.out.println("Error: Can't load image file!");
		}
	}
	
	@Override
	public void serve(Graphics g) {
		super.serve(g);
		g.drawImage(img, 120, 350, 340, 160, null);
	}
}