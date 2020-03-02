package FlappyBird;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Demo extends JPanel {

	Image   img = Toolkit.getDefaultToolkit().createImage("/G:/java/Flappy_Bird/bin/FlappyBird/tap.png");
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		 g.drawImage(img, 0, 0,965,600,null );
		FlappyBird.flappybird.repaint(g);
	}
	
	
}
