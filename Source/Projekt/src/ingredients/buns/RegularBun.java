package ingredients.buns;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ingredients.HotDog;

public class RegularBun implements HotDog {
	private BufferedImage img;

	public RegularBun() {
		try {
			img = ImageIO.read(this.getClass().getResource("/img/Regular_Bun.png"));

		} catch (IOException e) {
			System.out.println("Error: Couldn't find bun type!");
		}

	}

	@Override
	public void serve(Graphics g) {
		g.drawImage(img, 80, 330, 410, 210, null);
	}
	
	@Override
	public int getGrilledLevel() {
		return 0;
	}
	
	@Override
	public int getGoalLevel() {
		return 0;
	}
	
	@Override
	public int getIngredientNr() {
		return 1;
	}

	@Override
	public void getIngredients(String[] ingr, int ind) {
		ingr[ind] = this.getClass().getName().split("\\.")[this.getClass().getName().split("\\.").length - 1];
	}
}
