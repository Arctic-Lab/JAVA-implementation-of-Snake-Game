import java.awt.Graphics;
import java.util.Queue;
import java.util.LinkedList;
import java.security.SecureRandom;

public class Snake {
	Node head = null;
	Node tail = null;
	int size = 0;
	Queue<Node> q = new LinkedList<Node>();
	enum direction {up, right, left, down};
	direction Dir = direction.right;
	void setDirection(direction op) 
	{ 
		int i = Dir.ordinal();
		int j = op.ordinal();
		
		if( i+j!=3 || size == 1)
		Dir = op;
	}
	
	public int getScore() { return size; }
	
	public Snake() //构造函数
	{
		SecureRandom random = new SecureRandom();
		int x = random.nextInt() % Yard.COLUMNS;
		int y = random.nextInt() % Yard.ROWS;
		x = x<0? -x: x;
		y = y<0? -y: y;
		while( Yard.getVis(x,y) ) //保证不与已被占用的点重叠
		{
			y = random.nextInt() % Yard.ROWS;
			x = random.nextInt() % Yard.COLUMNS;
			x = x<0? -x: x;
			y = y<0? -y: y;
		}
		Node e = new Node(x, y);
		head = tail = e;
		size = 1;
		Yard.setVis(x, y, true);
		q.offer(e);
	}
	
	public boolean Move(Egg egg) { //移动贪吃蛇
		direction op = Dir;
		int x = 0, y = 0;
		if(op == direction.up) 
		{
			y = head.row-1; x = head.col;
		}
		else if(op == direction.down)
		{
			y = head.row+1; x = head.col;
		}
		else if(op == direction.left) 
		{
			y = head.row; x = head.col-1;
		}
		else if(op == direction.right)
		{
			y = head.row; x = head.col+1;
		}
		
		y = (y+Yard.ROWS)%Yard.ROWS;
		x = (x+Yard.COLUMNS)%Yard.COLUMNS;
		if(Yard.getVis(x, y) && !egg.HereIsEgg(x, y)) 
		{
			System.out.println("game seems over");
			return false; //撞死
		}
		
		head = new Node(x, y);
		q.offer(head);
		Yard.setVis(x, y, true);
		
		if(!egg.HereIsEgg(x, y))
		{
			Node e = q.poll();
			Yard.setVis(e.col, e.row, false);
			tail = q.element();
		}
		else 
		{
			egg.beEaten();
			size++;
		}
		
		Dir = op;
		
		return true; //顺利移动
	}
	
	public void addNodeToSnake(Node e)
	{
		head = e;
		q.offer(e);
		size++;
	}
	
	public void draw(Graphics g, Egg egg, Yard yad){
		yad.drawHead(g, head, Dir);
		for(Node e:q)
			if(e != head)
				e.draw(g, yad, null);
		Move(egg);
	}
}
