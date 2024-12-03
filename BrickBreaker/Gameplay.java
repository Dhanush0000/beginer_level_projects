package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    private int delay = 5;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;
    private Timer timer;

    public Gameplay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start(); // Timer should only start once here
    }

    public void paint(Graphics g) {
        // Background and borders
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        map.draw((Graphics2D) g);  // Draw the map (bricks)

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);  // Left border
        g.fillRect(0, 0, 692, 3);  // Top border
        g.fillRect(692, 0, 3, 592);  // Right border

        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score,590,30);
        
        // Draw the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // Draw the ball
        g.setColor(Color.red);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if(totalBricks <= 0) {
        	play = false;
        	ballXdir = 0;
        	ballYdir = 0;
        	g.setColor(Color.red);
        	 g.setFont(new Font("serif",Font.BOLD,40));
             g.drawString("You Won!",260,300);
             
             g.setFont(new Font("serif",Font.BOLD,30));
             g.drawString("Press Enter to Restart ",215,350);
        	
        }

        if(ballposY > 570) {
        	play = false;
        	ballXdir = 0;
        	ballYdir = 0;
        	g.setColor(Color.red);
        	 g.setFont(new Font("serif",Font.BOLD,40));
             g.drawString("Game Over!  Score:",190,300);
             
             g.setFont(new Font("serif",Font.BOLD,30));
             g.drawString("Press Enter to Restart ",215,350);
        }
        
        g.dispose();  // Cleanup resources
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Only update the game state if it's in play
        if (play) {
            // Ball collision with paddle
            Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
            Rectangle paddleRect = new Rectangle(playerX, 550, 100, 8);
            if (ballRect.intersects(paddleRect)) {
                ballYdir = -ballYdir;  // Reverse the ball's Y direction
            }

            // Ball collision with bricks
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[i].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        Rectangle brickRect = new Rectangle(brickX, brickY, map.brickWidth, map.brickHeight);

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);  // Mark brick as hit
                            totalBricks--;
                            score += 2;  // Increase score

                            // Change ball direction based on where it hit the brick
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break;  // No need to check further once a brick is hit
                        }
                    }
                }
            }

            // Update ball position
            ballposX += ballXdir * 2;
            ballposY += ballYdir * 2;

            // Ball collision with walls
            if (ballposX < 0 || ballposX > 670) {
                ballXdir = -ballXdir;  // Reverse X direction if hitting left or right wall
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;  // Reverse Y direction if hitting the top wall
            }

            // Ball falling below the paddle (game over)
            if (ballposY > 570) {
                play = false;  // End the game
            }
        }

        repaint();  // Redraw the screen
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but could be implemented later if needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_RIGHT && playerX < 600) {
            moveRight();  // Move right if within bounds
        } else if (key == KeyEvent.VK_LEFT && playerX > 10) {
            moveLeft();  // Move left if within bounds
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        	if(!play) {
        		play = true;
        		ballposX = 120;
        		ballposY = 350;
        		ballXdir = -1;
        		ballYdir = -2;
        		playerX = 310;
        		score = 0;
        		totalBricks = 21;
        		map = new MapGenerator(3,7);
        		repaint();
        	}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Required to implement KeyListener but not used here
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
