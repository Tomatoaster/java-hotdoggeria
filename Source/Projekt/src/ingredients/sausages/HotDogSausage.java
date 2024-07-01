package ingredients.sausages;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ingredients.HotDog;

public class HotDogSausage extends Sausage{
	
	private BufferedImage img;
	private int grilledLevel;
	private int goalLevel;
	private int wOffset, hOffset;
	private int wSize, hSize;
	
	@Override
	public HotDog toHotDog(HotDog hotDog) {
		HotDogSausage ret =  new HotDogSausage(hotDog);
		ret.setGrilledLevel(grilledLevel);
		return ret;
	}
	
	public HotDogSausage(HotDog hotDog) {
		super(hotDog);
		try {
			this.img = ImageIO.read(this.getClass().getResource("/img/HotDog_Sausage.png"));
			goalLevel = 25;
			grilledLevel = 0;
			hOffset = 340;
			wOffset = 120;
			hSize = 160;
			wSize = 320;
		} catch (IOException e) {
			System.out.println("Error: Couldn't load sausage resource!");
		}
	}
	
	public HotDogSausage() {
		super(null);
		try {
			this.img = ImageIO.read(this.getClass().getResource("/img/HotDog_Sausage.png"));
			goalLevel = 25;
			grilledLevel = 0;
			hOffset = 80;
			wOffset = 20;
			hSize = 80;
			wSize = 150;
		} catch (IOException e) {
			System.out.println("Error: Couldn't load sausage resource!");
		}
	}
	
	@Override
	public int getGoalLevel() {
		return goalLevel;
	}
	
	@Override
	public void setGoalLevel(int goalLevel) {
		this.goalLevel = goalLevel;
	}
	
	@Override
	public int getGrilledLevel() {
		return grilledLevel;
	}
	
	@Override
	public void setGrilledLevel(int grilledLevel) {
		this.grilledLevel = grilledLevel;
	}
	
	@Override
	public void serve(Graphics g) {
		super.serve(g);
		g.drawImage(img, wOffset, hOffset, wSize, hSize, null);
	}
}