package id.ac.its.ela.zetcode.MovingSprite;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Collision extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Collision() {
        initUI();
    }
	
	private void initUI() {
        add(new Board1());
        
        setResizable(false);
        pack();
        
        setTitle("Collision");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            Collision ex = new Collision();
            ex.setVisible(true);
        });
	}
}
