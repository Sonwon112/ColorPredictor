import java.util.Random;

public class rgbColor {
	int r,g,b;
	Random random;
	
	public rgbColor() {
		random = new Random();
	}
	
	public void reset() {
		r = random.nextInt(255);
		g = random.nextInt(255);
		b = random.nextInt(255);
	}
	
	public void setColor(int n) {
		r = n;
		g = n;
		b = n;
	}
	
}
