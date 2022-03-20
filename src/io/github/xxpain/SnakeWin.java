package io.github.xxpain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeWin extends JPanel implements ActionListener, KeyListener, Runnable {
    int score = 0, speed = 0;
    boolean start = false;
    int rx = 0, ry = 0;
    int eat1 = 0, eat2 = 0;
    JDialog dialog = new JDialog();
    JLabel label = new JLabel("你的分数是" + score);
    JButton ok = new JButton("结束");
    Random r = new Random();
    JButton newGame, stopGame;
    List<Snake> list = new ArrayList<>();
    int keyValue = 0;
    Thread nThread;

    public SnakeWin() {
        newGame = new JButton("开始");
        stopGame = new JButton("重置");
        newGame.addActionListener(this);
        stopGame.addActionListener(this);
        this.addKeyListener(this);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(newGame);
        this.add(stopGame);
        dialog.setLayout(new GridLayout(2, 1));
        dialog.add(label);
        dialog.add(ok);
        dialog.setSize(200, 200);
        dialog.setLocation(200, 200);
        dialog.setVisible(false);
        ok.addActionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(10, 40, 400, 300);
        g.drawString("分数：" + score, 250, 15);
        g.drawString("速度：" + speed, 250, 35);
        g.setColor(new Color(0, 255, 0));
        if (start) {
            g.fillRect(10 + rx * 10, 40 + ry * 10, 10, 10);
            for (int i = 0; i < list.size(); i++) {
                g.setColor(new Color(0, 0, 255));
                g.fillRect(10 + list.get(i).getX() * 10, 40 + list.get(i).getY() * 10, 10, 10);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            newGame.setEnabled(false);
            start = true;
            rx = r.nextInt(40);
            ry = r.nextInt(30);
            Snake tempAct = new Snake();
            tempAct.setX(20);
            tempAct.setY(15);
            list.add(tempAct);
            this.requestFocus();
            nThread = new Thread(this);
            nThread.start();
            repaint();
        }
        if (e.getSource() == stopGame) {
            list.clear();
            start = false;
            newGame.setEnabled(true);
            dialog.setVisible(false);
            score = 0;
            speed = 0;
            repaint();
        }
        if (e.getSource() == ok) {
            list.clear();
            start = false;
            newGame.setEnabled(true);
            dialog.setVisible(false);
            score = 0;
            speed = 0;
            repaint();
        }
    }

    private void eat() {
        if (rx == list.get(0).getX() && ry == list.get(0).getY()) {
            rx = r.nextInt(40);
            ry = r.nextInt(30);
            Snake tempAct = new Snake();
            tempAct.setX(list.get(list.size() - 1).getX());
            tempAct.setY(list.get(list.size() - 1).getY());
            list.add(tempAct);
            score = score + 100 * speed + 10;
            eat1++;
            if (eat1 - eat2 >= 4) {
                eat2 = eat1;
                speed++;
            }
        }
    }

    public void otherMove() {
        Snake tempAct;
        for (int i = 0; i < list.size(); i++) {
            if (i == 1) {
                list.get(i).setX(list.get(0).getX());
                list.get(i).setY(list.get(0).getY());
            } else if (i > 1) {
                tempAct = list.get(i - 1);
                list.set(i - 1, list.get(i));
                list.set(i, tempAct);
            }

        }
    }

    @Override
    public void setLocation(int x, int y) {
        if (minYes(x, y)) {
            otherMove();
            list.get(0).setX(list.get(0).getX() + x);
            list.get(0).setY(list.get(0).getY() + y);
            eat();
            repaint();
        } else {
            nThread = null;
            label.setText("你挂了！你的分数是" + score + "。");
            dialog.setVisible(true);
        }

    }

    public boolean minYes(int x, int y) {
        return maxYes(list.get(0).getX() + x, list.get(0).getY() + y);
    }

    public boolean maxYes(int x, int y) {
        if (x < 0 || x >= 40 || y < 0 || y >= 30) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (i > 1 && list.get(0).getX() == list.get(i).getX() && list.get(0).getY() == list.get(i).getY()) {
                return false;
            }
        }
        return true;
    }

    public void keyPressed(KeyEvent e) {
        if (start) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    setLocation(0, -1);
                    keyValue = 1;
                    break;
                case KeyEvent.VK_DOWN:
                    setLocation(0, 1);
                    keyValue = 2;
                    break;
                case KeyEvent.VK_LEFT:
                    setLocation(-1, 0);
                    keyValue = 3;
                    break;
                case KeyEvent.VK_RIGHT:
                    setLocation(1, 0);
                    keyValue = 4;
                    break;

                default:
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void run() {
        while (start) {
            switch (keyValue) {
                case 1:
                    setLocation(0, -1);
                    break;
                case 2:
                    setLocation(0, 1);
                    break;
                case 3:
                    setLocation(-1, 0);
                    break;
                case 4:
                    setLocation(1, 0);
                    break;
                default:
                    break;
            }
            repaint();
            try {
                Thread.sleep(300 - 30L * speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
