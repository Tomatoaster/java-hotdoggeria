package ingredients.garnish;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ingredients.HotDog;
import ingredients.HotDogIngredient;

public class Cheese extends HotDogIngredient {
private BufferedImage img;
	
	public Cheese(HotDog hotDog) {
		super(hotDog);
		try {
			img = ImageIO.read(this.getClass().getResource("/img/garnish/Cheese.png"));
		}catch(IOException e) {
			System.out.println("Error: Can't load image file!");
		}
	}
	
	@Override
	public void serve(Graphics g) {
		super.serve(g);
		g.drawImage(img, 70, 320, 440, 100, null);
	}
}
