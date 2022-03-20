package io.github.xxpain;

import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;

public class GameWindow {

    private JPanel contentPanel = new SnakeWin();

    private Project project;

    private void init() {

    }

    public GameWindow(Project project) {
        this.project = project;
        init();
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }


    class Demo extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(1.0f, 1.0f, 1.0f));


            g2d.fillRect(0, 0, 540, 540);

            g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
            for (int x = 0; x <= 540; x += 20) {

                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(x, 0, x, 540);
            }

            for (int y = 0; y <= 540; y += 20) {

                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(0, y, 540, y);
            }

            Font f = new Font("Times New Roman", Font.PLAIN, 16);
            g2d.setFont(f);
            FontRenderContext fContext = g2d.getFontRenderContext();

        }

    }
}
