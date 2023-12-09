import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVida extends JFrame {

    private int vivos = 0;
    private int[] glider = new int[25];
    private int[] gliderTemp = new int[25];
    private JPanel panel;

    public JogoDaVida() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int y = 0; y < 5; y++) {
                    for (int x = 0; x < 5; x++) {
                        int index = y * 5 + x;
                        if (glider[index] == 1) {
                            // Célula viva (bolinha preta maior)
                            g.fillOval(x * 60, y * 60, 50, 50);
                        } else {
                            // Célula morta (caveirinha maior)
                            Font largerFont = g.getFont().deriveFont(36f); // Tamanho da fonte aumentado para 36
                            g.setFont(largerFont);
                            g.drawString("☠", x * 60 + 10, y * 60 + 40); // Ajuste na posição
                        }
                    }
                }
            }
        };

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(330, 340); // Tamanho da janela aumentado para 300x300
        setLocationRelativeTo(null);
        setVisible(true);

        inicializar();
        Timer timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizar();
                panel.repaint();
            }
        });
        timer.start();
    }

    private void inicializar() {
        for (int índice = 0; índice < 25; índice++) {
            glider[índice] = 0;
        }
        glider[0] = 1;
        glider[2] = 1;
        glider[6] = 1;
        glider[7] = 1;
        glider[11] = 1;
    }

    private void atualizar() {
        for (int y2 = 0; y2 < 5; y2++) {
            for (int x2 = 0; x2 < 5; x2++) {
                vivos = 0;
                vivos += glider[(y2 + (-1 + 5)) % 5 * 5 + (x2 + (-1 + 5)) % 5];
                vivos += glider[(y2 + (-1 + 5)) % 5 * 5 + (x2 + 1) % 5];
                vivos += glider[(y2 + 1) % 5 * 5 + (x2 + (-1 + 5)) % 5];
                vivos += glider[(y2 + 1) % 5 * 5 + (x2 + 1) % 5];
                vivos += glider[(y2 + (-1 + 5)) % 5 * 5 + x2];
                vivos += glider[(y2 + 1) % 5 * 5 + x2];
                vivos += glider[y2 * 5 + (x2 + (-1 + 5)) % 5];
                vivos += glider[y2 * 5 + (x2 + 1) % 5];
                gliderTemp[y2 * 5 + x2] = 0;

                if (glider[y2 * 5 + x2] == 1) {
                    if (vivos >= 2 && vivos <= 3) {
                        gliderTemp[y2 * 5 + x2] = 1;
                    }
                } else {
                    if (vivos == 3) {
                        gliderTemp[y2 * 5 + x2] = 1;
                    }
                }
            }
        }

        for (int y3 = 0; y3 < 5; y3++) {
            for (int x3 = 0; x3 < 5; x3++) {
                glider[y3 * 5 + x3] = gliderTemp[y3 * 5 + x3];
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JogoDaVida();
            }
        });
    }
}
