import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class Joya {
	private int color;
	
	public Joya(int color) {
		this.setColor(color);
		
	}
	public Image dibujaJoya(Graphics g) {
		Image img=new ImageIcon().getImage();
		switch(this.color){
		case 0: img= new ImageIcon("amarillo.png").getImage();
				break;
		case 1: img= new ImageIcon("azul.png").getImage();
				break;
		case 2: img= new ImageIcon("morado.png").getImage();
				break;
		case 3: img= new ImageIcon("naranja.png").getImage();
				break;
		case 4: img= new ImageIcon("plata.png").getImage();
				break;
		case 5: img= new ImageIcon("rojo.png").getImage();
				break;
		case 6: img= new ImageIcon("verde.png").getImage();
				break;
		}
		return img;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
}
