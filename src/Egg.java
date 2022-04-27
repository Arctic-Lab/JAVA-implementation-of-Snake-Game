import java.awt.Color;
import java.awt.Graphics;
import java.security.SecureRandom;
public class Egg {
	int col, row;
	private static final int ROWS = Yard.ROWS, COLUMNS = Yard.COLUMNS; 
	private static final int BLOCK = Yard.BLOCK_SIZE;
	SecureRandom random = new SecureRandom();
	public Egg()
	{
		CreateEgg();
	}
	
	private void CreateEgg()
	{
		int x, y;
		do
		{
			x = random.nextInt();
			y = random.nextInt();
			x = x<0?-x:x; 
			y = y<0?-y:y;
			x = x%COLUMNS;
			y = y%ROWS;
		} while(Yard.getVis(x, y));
		Yard.setVis(x, y, true);
		col = x;
		row = y;
	}
	
	public boolean HereIsEgg(int x, int y)
	{
		return  x == col && y == row;
	}
	
	public void beEaten()
	{
		Yard.setVis(col, row, false);
		CreateEgg();
	}
	
	public void draw(Graphics g, Yard y)
	{
		y.paintEgg(g, col*BLOCK, row*BLOCK);
	}
}
