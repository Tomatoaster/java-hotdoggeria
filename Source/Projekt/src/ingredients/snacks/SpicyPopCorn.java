package ingredients.snacks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ingredients.HotDog;
import ingredients.HotDogIngredient;

public class SpicyPopCorn extends HotDogIngredient {
private BufferedImage img;
	
	public SpicyPopCorn(HotDog hotDog) {
		super(hotDog);
		try {
			img = ImageIO.read(this.getClass().getResource("/img/snacks/SpicyPopCorn.png"));
		}catch(IOException e) {
			System.out.println("Error: Can't load image file!");
		}
	}
	
	@Override
	public void serve(Graphics g) {
		g.drawImage(img, 20, 180, 140, 270, null);
		super.serve(g);
	}
}
