import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelJoyas extends JPanel{
	
	public PanelJoyas() {
		super();
		this.setPreferredSize(new Dimension(320, 320));
		this.setLayout(new GridLayout(8, 8));
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				this.add(new Button("ggg"));
	}

}
