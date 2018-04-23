import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Tablero extends JPanel {
	private int puntaje;
	private Image fondo;
	
	
	public Tablero() {
		this.fondo=new ImageIcon("tablero2.png").getImage();
		this.setPuntaje(999999);
		this.setPreferredSize(new Dimension(this.fondo.getWidth(this),this.fondo.getHeight(this)));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.fondo, 0, 0, this.getWidth(), this.getHeight(),this);
		g.setColor(Color.RED);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 29));
		g.drawString(""+this.puntaje, 531-16*(Integer.toString(this.puntaje).length()),78);
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
}
