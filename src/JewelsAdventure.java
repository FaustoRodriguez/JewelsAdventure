import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class JewelsAdventure extends JFrame {
	Tablero tablero;
	public JewelsAdventure(String usuario) {
			super();
			this.setTitle("Jewels Adventure");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("verde.png"));
			tablero=new Tablero(usuario);
			this.add(tablero);
			this.pack();
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setVisible(true);
	}
	
	public static String verficaNombre() {
		String nombre;
		nombre=JOptionPane.showInputDialog("Inserta tu nombre");
		while(nombre.length()>10)
		{
			JOptionPane.showMessageDialog(null,"Ingresaste un nombre demasiado largo", "Largo excedido", JOptionPane.WARNING_MESSAGE);;
			nombre=JOptionPane.showInputDialog("Inserta tu nombre");
		}
		return nombre;
	}
	
	public static void main(String[] args) {
		new JewelsAdventure(JewelsAdventure.verficaNombre());
	}

}
