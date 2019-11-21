package id.ac.its.ela.zetcode.MovingSprite;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board1 extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 300;
    private final int DELAY = 10;
    private Timer timer;
    private SpaceShip1 spaceShip;
    private List<Asteroid> asteroid;
    private final int[][] position = {
    		{2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
        };
    
    public Board1() {
    	initBoard();
    }
    
    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        spaceShip = new SpaceShip1(ICRAFT_X, ICRAFT_Y);
        
        initAsteroid();

        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void initAsteroid() {
        
        asteroid = new ArrayList<>();

        for (int[] p : position) {
            asteroid.add(new Asteroid(p[0], p[1]));
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }
    
    private void doDrawing(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;

    	g2d.drawImage(spaceShip.getImage(), spaceShip.getX(), 
                spaceShip.getY(), this);

        List<Missile> ms = spaceShip.getMissiles();

        for (Missile missile : ms) {
        	g2d.drawImage(missile.getImage(), missile.getX(), 
                    missile.getY(), this);
        }

        for (Asteroid asteroid : asteroid) {
        	g2d.drawImage(asteroid.getImage(), asteroid.getX(), 
                    asteroid.getY(), this);
        }
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
    	
        updateMissiles();
        updateSpaceShip();
        updateAsteroid();
        
        checkCollisions();

        repaint();
    }
    
    
    private void updateSpaceShip() {
            spaceShip.move();
    }
    
    private void updateMissiles() {
        List<Missile> missiles = spaceShip.getMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

                missiles.remove(i);
            }
        }
    }
    
    private void updateAsteroid() {
        for (int i = 0; i < asteroid.size(); i++) {
            Asteroid a = asteroid.get(i);
            
            if (a.isVisible()) {
                a.move();
            } else {
                asteroid.remove(i);
            }
        }
    }
    
    public void checkCollisions() {
        Rectangle r3 = spaceShip.getBounds();

        for (Asteroid asteroid : asteroid) {
            
            Rectangle r2 = asteroid.getBounds();
            if (r3.intersects(r2)) {
                spaceShip.setVisible(false);
                asteroid.setVisible(false);
            }
        }

        List<Missile> ms = spaceShip.getMissiles();

        for (Missile m : ms) {
            Rectangle r1 = m.getBounds();
            for (Asteroid asteroid : asteroid) {
                Rectangle r2 = asteroid.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    asteroid.setVisible(false);
                }
            }
        }
    }

    
    
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            spaceShip.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            spaceShip.keyPressed(e);
        }
    }
}
