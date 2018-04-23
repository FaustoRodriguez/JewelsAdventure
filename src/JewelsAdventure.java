import java.awt.Toolkit;
import javax.swing.JFrame;

public class JewelsAdventure extends JFrame {
	
	public JewelsAdventure() {
		super();
		this.setTitle("Jewels Adventure");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("verde.png"));
		this.add(new Tablero());
		this.pack();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new JewelsAdventure();
	}

}
