import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class GameOfLifeView {
    private JFrame vindu;
    private JPanel hovedJPanel;
    private JPanel iPanel;
    private JPanel cPanel;
    private JLabel antalllevendLabel;
    private JTextField teksJTextField;
    private JButton start;
    private JButton avslutt;
    //private JButton cellButton;
    private GameOfLifeKontroll kontroll;
    private JButton[][] cellButtons;
    private Timer timer;

    public GameOfLifeView(GameOfLifeKontroll kontroll){
        try { // valgfritt å ha med
            UIManager.setLookAndFeel(
             UIManager.getCrossPlatformLookAndFeelClassName());
         } catch (Exception e) { System.exit(1); }
        this.kontroll=kontroll;
        vindu = new JFrame("Game of Life");
        vindu.setSize(600, 400);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hovedJPanel = new JPanel();
        vindu.add(hovedJPanel);

        iPanel = new JPanel();
        hovedJPanel.setLayout(new BorderLayout());
        hovedJPanel.add(iPanel, BorderLayout.NORTH);

        Font teksFont = new Font("Arial", Font.PLAIN, 12);
        antalllevendLabel = new JLabel("Antall Levende");
        antalllevendLabel.setFont(teksFont);
        iPanel.add(antalllevendLabel);
        
        teksJTextField = new JTextField();
        cellButtons = new JButton[20][5];
        teksJTextField.setPreferredSize(new Dimension(50, 20));
        teksJTextField.setFont(teksFont);
        iPanel.add(teksJTextField);
        start = new JButton("Start");
        start.setFont(teksFont);

        timer=new Timer(2000,new ActionListener() {
            public void actionPerformed(ActionEvent e){
                
                taCelle();
                kontroll.antallLevde();;
                oppdatering();
            }
            
        });


        class startknapp implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (start.getText().equals("Start")) {
                  //  System.out.println("-----start");
                    start.setText("Stop");
                    kontroll.antallLevde();
                    timer.start();
                    
                   
                   
                    
                } else if(start.getText().equals("Stop")){
                   // System.out.println("-----stop");

                   start.setText("Start"); 
                   kontroll.antallLevde();
                    
                   oppdatering();
                   timer.stop();
                }
            }
        }
        start.addActionListener(new startknapp());
        iPanel.add(start);
        avslutt = new JButton("Avslutt");
        avslutt.setFont(teksFont);

        class avsluttknapp implements ActionListener{
            
            public void actionPerformed(ActionEvent e){
            avslutt();
                
            }
        }
        avslutt.addActionListener(new avsluttknapp());
        iPanel.add(avslutt);
        cPanel = new JPanel();
        hovedJPanel.add(cPanel,BorderLayout.CENTER);
        
        
        initGrid();
        vindu.pack();
        vindu.setVisible(true);

    } public void hentAntallLevende(int antall){
        String verdi = String.valueOf(antall);
        teksJTextField.setText(verdi);
    }
    private void initGrid() {
        int rader = 20;
        int kol = 5;
        cPanel.setLayout(new GridLayout(rader, kol));
        cellButtons = new JButton[rader][kol];
        for (int teller = 0; teller < rader; teller++) {
            for (int teller1 = 0; teller1 < kol; teller1++) {
                cellButtons[teller][teller1] = new JButton();
                cellButtons[teller][teller1].setPreferredSize(new Dimension(50, 20));
                cPanel.add(cellButtons[teller][teller1]);
            }
        }
    }
    public void oppdatering(){
       kontroll.oppdatering(20,5);
    }
    public void taCelle(){
        for (int teller = 0; teller < cellButtons.length; teller++) {
            for (int teller1 = 0; teller1 < cellButtons[teller].length; teller1++) {
                String verdi = String.valueOf(kontroll.hentStatus(teller, teller1));
                cellButtons[teller][teller1].setText(verdi);
                if(verdi.equals("*")){
                    System.out.println("HELLO " + verdi);
                   
                    cellButtons[teller][teller1].setOpaque(true);
                   cellButtons[teller][teller1].setBackground(Color.GREEN);
                   // cellButtons[teller][teller1].setForeground(new Color(128,100,255));
                }else {
                    cellButtons[teller][teller1].setBackground(null);
                }

               

            }
        }

    }
    public void avslutt(){
        timer.stop();
        start.setText("Start");
        teksJTextField.setText("0");
                for (int teller = 0; teller < cellButtons.length; teller++) {
                    for (int teller1 = 0; teller1 < cellButtons[teller].length; teller1++) {
                       
                        cellButtons[teller][teller1].setText("");
                        
                    }
                }
    }
    
}