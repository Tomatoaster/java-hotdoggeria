package ingredients.garnish;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ingredients.HotDog;
import ingredients.HotDogIngredient;

public class Corn extends HotDogIngredient {
private BufferedImage img;
	
	public Corn(HotDog hotDog) {
		super(hotDog);
		try {
			img = ImageIO.read(this.getClass().getResource("/img/garnish/Corn.png"));
		}catch(IOException e) {
			System.out.println("Error: Can't load image file!");
		}
	}
	
	@Override
	public void serve(Graphics g) {
		super.serve(g);
		g.drawImage(img, 100, 320, 380, 100, null);
	}
}
