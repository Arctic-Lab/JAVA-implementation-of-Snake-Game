import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class Node {
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	int row = 0, col = 0;
	
	public Node(int x, int y){
		col = x;
		row = y;
	}


	void draw( Graphics g, Yard f, Image m ) {
		f.paintNode(g, col, row, w, h, m);
	}
}