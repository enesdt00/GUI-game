import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLifeView {
    private JFrame vindu;
    private JPanel hovedPanel;
    private JPanel indikatorPanel;
    private JPanel cellPanel;
    private JLabel antallLevde;
    private JTextField tekstOmradet;
    private JButton start;
    private JButton avslutt;
    private GameOfLifeKontroll kontroll;
    private JButton[][] cellButtons;
    private Timer timer;

    public GameOfLifeView(GameOfLifeKontroll kontroll) {
        this.kontroll = kontroll;
        vindu = new JFrame("Game Of Life");
        vindu.setSize(600, 400);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hovedPanel = new JPanel();
        vindu.add(hovedPanel);
        indikatorPanel = new JPanel();

        hovedPanel.setLayout(new BorderLayout());
        hovedPanel.add(indikatorPanel, BorderLayout.NORTH);

        Font tekstFont = new Font("Arial", Font.PLAIN, 12);
        antallLevde = new JLabel("Antall Levende: ");

        antallLevde.setFont(tekstFont);
        indikatorPanel.add(antallLevde);

        tekstOmradet = new JTextField(null);
        cellButtons = new JButton[20][5];
        tekstOmradet.setPreferredSize(new Dimension(50, 20));
        tekstOmradet.setFont(tekstFont);
        indikatorPanel.add(tekstOmradet);
        start = new JButton("Start");
        start.setFont(tekstFont);

        timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                taCelle();
                kontroll.antallLevde();
                oppdatering();
            }
        });

        class startTrykk implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (start.getText().equals("Start")) {
                    start.setText("Stop");
                    kontroll.antallLevde();
                    timer.start();
                } else if (start.getText().equals("Stop")) {
                    start.setText("Start");
                    kontroll.antallLevde();
                    oppdatering();
                    timer.stop();
                }
            }
        }

        start.addActionListener(new startTrykk());
        indikatorPanel.add(start);
        avslutt = new JButton("Avslutt");
        avslutt.setFont(tekstFont);

        class avslutt implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                tekstOmradet.setText("0");
                start.setText("Start");
                for (int i = 0; i < cellButtons.length; i++) {
                    for (int j = 0; j < cellButtons[i].length; j++) {
                        cellButtons[i][j].setText("");
                    }
                }
                timer.stop();
            }
        }
        avslutt.addActionListener(new avslutt());
        indikatorPanel.add(avslutt);
        cellPanel = new JPanel();
        hovedPanel.add(cellPanel, BorderLayout.CENTER);
        initGrid();
        vindu.pack();
        vindu.setVisible(true);
    }

    public void hentAntallLevende(int antall) {
        String antallVerdi = String.valueOf(antall);
        tekstOmradet.setText(antallVerdi);
    }

    // Hvis cellen er i live, vil den være grønn, ellers vil rødt være
    // bakgrunnsfargen
    public void taCelle() {
        for (int teller = 0; teller < cellButtons.length; teller++) {
            for (int teller1 = 0; teller1 < cellButtons[teller].length; teller1++) {
                String verdi = String.valueOf(kontroll.hentStatus(teller, teller1));
                System.out.println("HELLO " + verdi);
                cellButtons[teller][teller1].setText(verdi);
                if (verdi.equals("O")) {
                    cellButtons[teller][teller1].setBackground(Color.GREEN);
                } else {
                    cellButtons[teller][teller1].setBackground(Color.RED);
                }
            }

        }
    }

    private void initGrid() {
        int rader = 20;
        int kol = 5;
        cellPanel.setLayout(new GridLayout(rader, kol));
        cellButtons = new JButton[rader][kol];
        for (int teller = 0; teller < rader; teller++) {
            for (int teller1 = 0; teller1 < kol; teller1++) {
                cellButtons[teller][teller1] = new JButton();
                cellButtons[teller][teller1].setPreferredSize(new Dimension(50, 20));
                cellButtons[teller][teller1].setBackground(Color.YELLOW);

                cellPanel.add(cellButtons[teller][teller1]);

            }
        }

    }

    public void avslutt() {
        tekstOmradet.setText("0");
        for (int teller = 0; teller < cellButtons.length; teller++) {
            for (int teller1 = 0; teller1 < cellButtons[teller].length; teller1++) {
                cellButtons[teller][teller1].setText("");
            }
        }
    }

    public void oppdatering() {
        kontroll.oppdatering(20, 5);
    }
}