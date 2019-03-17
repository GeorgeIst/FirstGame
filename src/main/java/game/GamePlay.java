package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {


    private boolean play = false;
    private int score = 0;

    private int totalBriks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballDirectionX = -1;
    private int ballDirectionY = -2;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    public void paint(Graphics graphics) {
        //background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(1, 1, 692, 592);
        //drawing map
        map.draw((Graphics2D) graphics);
        //borders
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);
        //score
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString(" " + score,592,30 );
        //paddle
        graphics.setColor(Color.blue);
        graphics.fillRect(playerX, 550, 100, 8);
        //ball
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(ballPositionX, ballPositionY, 20, 20);

        if(totalBriks <= 0){
            play = false;
            ballDirectionX = 0;
            ballDirectionY = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 25));
            graphics.drawString("You Won!: " + score,200,300 );

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press enter to restart" ,230,500 );
        }

        if(ballPositionY > 570){
            play = false;
            ballDirectionX = 0;
            ballDirectionY = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 25));
            graphics.drawString("Game Over, Score: " + score,200,300 );

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press enter to restart" ,230,500 );
        }

        graphics.dispose();
    }


    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects
                    (new Rectangle(playerX, 550, 100, 8))) {
                ballDirectionY = -ballDirectionY;
            }
            A : for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brikWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brikWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPositionX, ballPositionY, 20, 20);
                        Rectangle brickRect = rectangle;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrikValue(0, i, j);
                            totalBriks--;
                            score += 5;

                            if (ballPositionX + 19 <= brickRect.x || ballDirectionX + 1 >= brickRect.x + brickRect.width) {
                                ballDirectionX = -ballDirectionX;

                            } else {
                                ballDirectionY = -ballDirectionY;
                            }
                            break A;
                        }
                    }
                }
            }
            ballPositionX += ballDirectionX;
            ballPositionY += ballDirectionY;
            if (ballPositionX < 0) {
                ballDirectionX = -ballDirectionX;
            }
            if (ballPositionY < 0) {
                ballDirectionY = -ballDirectionY;
            }
            if (ballPositionX > 670) {
                ballDirectionX = -ballDirectionX;
            }
        }
        repaint();

    }


    public void keyReleased(KeyEvent e) {

    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();

            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballDirectionX = -1;
                ballDirectionY = -2;
                playerX = 310;
                score = 0;
                totalBriks = 21;
                map = new MapGenerator(3,7);
                repaint();

            }
        }
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