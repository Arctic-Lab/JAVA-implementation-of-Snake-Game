import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Yard extends Frame {
	private static final long serialVersionUID = 1L; //序列版本ID
	
	public static final int ROWS = 24;
	public static final int COLUMNS = 34;
	public static final int BLOCK_SIZE = 25;
	public static final int Board_Weight = 880, Board_Height = 720;
	public static final int Correct_x = 15, Correct_y = 105;
	private static boolean[][] vis = new boolean[COLUMNS+1][ROWS+1];
	public static boolean getVis(int x, int y) { return vis[x][y]; }
	public static void setVis(int x, int y, boolean f) { vis[x][y] = f; } 
	
	Snake s = new Snake();
	Egg egg = new Egg();
	
	Image offScreenImage = null;
	

	
	private class PaintThread implements Runnable{
		
		public void run()
		{
			while(true)
			{
				repaint();
				try {
					Thread.sleep(100); //迭代速度
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	public void drawHead(Graphics g, Node e, Snake.direction op)
	{
		Image m;
		if(op == Snake.direction.up) m = new ImageIcon("img/up.png").getImage();
		else if(op == Snake.direction.down) m = new ImageIcon("img/down.png").getImage();
		else if(op == Snake.direction.left) m = new ImageIcon("img/left.png").getImage();
		else m = new ImageIcon("img/right.png").getImage();
		e.draw(g, this, m);
	}
	
	public void paintNode(Graphics g, int col, int row, int w, int h, Image m)
	{
		if(m == null) m = new ImageIcon("img/body.png").getImage();
		g.drawImage(m, Correct_x+col*w, Correct_y+row*h, null);
	}
	
	public void paintEgg(Graphics g, int x, int y)
	{
		Image m = new ImageIcon("img/bean.png").getImage();
		g.drawImage(m, Correct_x+x, Correct_y+y, null);
	}
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(Correct_x, Correct_y, COLUMNS*BLOCK_SIZE, ROWS*BLOCK_SIZE);
		
		Image title = new ImageIcon("img/title.png").getImage();
		g.setColor(Color.WHITE);
		Font f = new Font("SansSerif",Font.BOLD,24);
		g.setFont(f);
		g.drawImage(title, 15, 30, null);
		g.drawString("Score: "+s.getScore(), 720, 80);
		g.setColor(c);
		s.draw(g, egg, this);
		egg.draw(g, this);
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) 
			offScreenImage = this.createImage(Board_Weight, Board_Height);
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
//			System.out.println("ooops"); 
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_UP)
				s.setDirection(Snake.direction.up); else
			if(key == KeyEvent.VK_DOWN)
				s.setDirection(Snake.direction.down); else
			if(key == KeyEvent.VK_LEFT)
				s.setDirection(Snake.direction.left); else
			if(key == KeyEvent.VK_RIGHT)
				s.setDirection(Snake.direction.right);
		}
		
	}
	
	public void launch() //启动函数
	{
		this.setLocation(300, 300);
		this.setSize(Board_Weight, Board_Height);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}  
		} );
		this.addKeyListener( new KeyMonitor() );
		this.setVisible(true);
		this.setTitle("Snake Game!");
		
		new Thread(new PaintThread()).start();;
	}

	public static void main(String[] args) {
		new Yard().launch();
	}

}
