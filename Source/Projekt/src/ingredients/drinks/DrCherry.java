package ingredients.drinks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ingredients.HotDog;
import ingredients.HotDogIngredient;

public class DrCherry extends HotDogIngredient {
private BufferedImage img;
	
	public DrCherry(HotDog hotDog) {
		super(hotDog);
		try {
			img = ImageIO.read(this.getClass().getResource("/img/drinks/DrCherry.png"));
		}catch(IOException e) {
			System.out.println("Error: Can't load image file!");
		}
	}
	
	@Override
	public void serve(Graphics g) {
		g.drawImage(img, 400, 220, 140, 245, null);
		super.serve(g);
	}
}
