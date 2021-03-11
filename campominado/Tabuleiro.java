/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominado;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Icon;

/**
 *
 * @author guit_
 */
public class Tabuleiro extends javax.swing.JFrame implements KeyListener {

    private final Icon Icon_vitoria = new javax.swing.ImageIcon(getClass().getResource("/images/VITORIA.png"));
    private final Icon Icon_derrota = new javax.swing.ImageIcon(getClass().getResource("/images/DERROTA.png"));
    private final Icon Icon_default = new javax.swing.ImageIcon(getClass().getResource("/images/digit.png"));
    private static int linha;
    private static int coluna;
    private static int quantMinas;
    private static Bloco[][] campoMinado;
    private static Bloco auxiliar;
    private static Contador[] contador;
    private static int quantAbertos = 0;
    private static int quantBandeiras = 0;
//getters and setters

    public static Contador[] getContador() {
        return contador;
    }

    public static void setContador(Contador[] contador) {
        Tabuleiro.contador = contador;
    }

    public static int getQuantBandeiras() {
        return quantBandeiras;
    }

    public static void setQuantBandeiras(int quantBandeiras) {
        Tabuleiro.quantBandeiras = quantBandeiras;
    }

    public static void acrescentarBandeiras() {
        Tabuleiro.quantBandeiras++;
    }

    public static void decrementarBandeiras() {
        Tabuleiro.quantBandeiras--;
    }

    public static int getQuantAbertos() {
        return quantAbertos;
    }

    public static void setQuantAbertos(int quantAbertos) {
        Tabuleiro.quantAbertos = quantAbertos;
    }

    public static void acrescentarAbertos() {
        Tabuleiro.quantAbertos++;
    }

    public static void decrementarAbertos() {
        Tabuleiro.quantAbertos--;
    }

    public static int getQuantMinas() {
        return quantMinas;
    }

    public static void setQuantMinas(int quantMinas) {
        Tabuleiro.quantMinas = quantMinas;
    }

    public static Bloco[][] getCampoMinado() {
        return campoMinado;
    }

    public static void setCampoMinado(Bloco[][] campoMinado) {
        Tabuleiro.campoMinado = campoMinado;
    }

    public static int getLinha() {
        return linha;
    }

    public static void setLinha(int linha) {
        Tabuleiro.linha = linha;
    }

    public static int getColuna() {
        return coluna;
    }

    public static void setColuna(int coluna) {
        Tabuleiro.coluna = coluna;
    }

    /**
     * Creates new form Tabuleiro
     *
     * @param linha
     * @param coluna
     * @param quantBomb
     */
    public Tabuleiro() {
    }

    public Tabuleiro(int linha, int coluna, int quantBomb) {

        initComponents();
        auxiliar = new Bloco();
        Tabuleiro.setColuna(coluna);
        addKeyListener(this);
        Tabuleiro.setLinha(linha);
        Tabuleiro.setQuantMinas(quantBomb);
        Tabuleiro.setQuantBandeiras(0);
        panicLabel.setVisible(false);
        this.setBounds(540, 150, 288, 469);
        this.setResizable(false);
        setIcon();
        instructionsLabel.setVisible(false);
        PainelContador.setLayout(new GridLayout(1, 3));
        Contador[] contador = new Contador[3];
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    contador[i] = new Contador(Contador.centena(quantMinas));
                    PainelContador.add(contador[i]);
                    break;
                case 1:
                    contador[i] = new Contador(Contador.dezena(quantMinas));
                    PainelContador.add(contador[i]);
                    break;
                case 2:
                    contador[i] = new Contador(Contador.unidade(quantMinas));
                    PainelContador.add(contador[i]);
                    break;
            }
        }
        Bloco[][] blocos = new Bloco[linha][coluna];
        PainelMatriz.setLayout(new GridLayout(linha, coluna));
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                blocos[i][j] = new Bloco(i, j);
                PainelMatriz.add(blocos[i][j]);
            }
        }
        Tabuleiro.setCampoMinado(blocos);
        campoMinado[0][0].marcarBloco();
        Tabuleiro.setContador(contador);
        Tabuleiro.IniciarMinas();
    }

    public static void alterarContador() {
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    contador[i].setIcon(contador[i].selecionarIcone(Contador.centena(quantMinas - quantBandeiras)));
                    break;
                case 1:
                    contador[i].setIcon(contador[i].selecionarIcone(Contador.dezena(quantMinas - quantBandeiras)));
                    break;
                case 2:
                    contador[i].setIcon(contador[i].selecionarIcone(Contador.unidade(quantMinas - quantBandeiras)));
                    break;
            }
        }
    }

    //Fechar todos os campos do tabuleiro e criar novo jogo
    public static void ReiniciarTabuleiro() {
        Bloco[][] minesweeper = getCampoMinado();
        for (int i = 0; i < getLinha(); i++) {
            for (int j = 0; j < getColuna(); j++) {
                minesweeper[i][j].reset();
            }
        }
        Tabuleiro.setQuantAbertos(0);
        Tabuleiro.setQuantBandeiras(0);
        Tabuleiro.alterarContador();
        Tabuleiro.IniciarMinas();
        painelDigital1.setIcon(painelDigital1.getIcon_default());
    }

    //Colocar aleatoriamente as bombas no tabuleiro
    public static void IniciarMinas() {
        Random gerador = new Random();
        Bloco[][] cm = getCampoMinado();
        int x, y;
        for (int i = 0; i < Tabuleiro.getQuantMinas(); i++) {
            do {
                x = gerador.nextInt(Tabuleiro.getLinha() - 1);
                y = gerador.nextInt(Tabuleiro.getColuna() - 1);

            } while (cm[x][y].getValue() == 9); //campo já com uma bomba

            cm[x][y].setValue(9);
            Tabuleiro.CalcularArredores(x, y);
        }

    }

    public static void CalcularArredores(int x, int y) {
        Bloco[][] cm = getCampoMinado();
        //(X,Y+1)
        if ((y + 1 >= 0 && y + 1 < Tabuleiro.getColuna()) && cm[x][y + 1].getValue() != 9) {
            cm[x][y + 1].acrescentarValue();
        }
        //(X,Y-1)
        if ((y - 1 >= 0 && y - 1 < Tabuleiro.getColuna()) && cm[x][y - 1].getValue() != 9) {
            cm[x][y - 1].acrescentarValue();
        }
        //(X+1,Y)
        if ((x + 1 >= 0 && x + 1 < Tabuleiro.getLinha()) && cm[x + 1][y].getValue() != 9) {
            cm[x + 1][y].acrescentarValue();
        }
        //(X-1,Y)
        if ((x - 1 >= 0 && x - 1 < Tabuleiro.getLinha()) && cm[x - 1][y].getValue() != 9) {
            cm[x - 1][y].acrescentarValue();
        }
        //(X+1,Y+1)
        if (((x + 1 >= 0 && x + 1 < Tabuleiro.getLinha()) && (y + 1 >= 0 && y + 1 < Tabuleiro.getColuna()))
                && cm[x + 1][y + 1].getValue() != 9) {
            cm[x + 1][y + 1].acrescentarValue();
        }
        //(X+1,Y-1)
        if (((x - 1 >= 0 && x - 1 < Tabuleiro.getLinha()) && (y - 1 >= 0 && y - 1 < Tabuleiro.getColuna()))
                && cm[x - 1][y - 1].getValue() != 9) {
            cm[x - 1][y - 1].acrescentarValue();
        }
        //(X-1,Y-1)
        if (((x + 1 >= 0 && x + 1 < Tabuleiro.getLinha()) && (y - 1 >= 0 && y - 1 < Tabuleiro.getColuna()))
                && cm[x + 1][y - 1].getValue() != 9) {
            cm[x + 1][y - 1].acrescentarValue();
        }
        //(X-1,Y+1)
        if (((x - 1 >= 0 && x - 1 < Tabuleiro.getLinha()) && (y + 1 >= 0 && y + 1 < Tabuleiro.getColuna()))
                && cm[x - 1][y + 1].getValue() != 9) {
            cm[x - 1][y + 1].acrescentarValue();
        }
    }

    public static void abrirEspaçosVazios(int x, int y) {
        if (x >= 0 && y >= 0 && x < campoMinado.length && y < campoMinado.length && !campoMinado[x][y].isOpened()) {
            if (campoMinado[x][y].getValue() == 9) {
                return;
            } else if (campoMinado[x][y].isMarked()) {
                return;
            } else if (campoMinado[x][y].getValue() != 0) {
                campoMinado[x][y].mostrarValor(campoMinado[x][y].getValue());
                return;
            } else if (campoMinado[x][y].getValue() == 0) {
                campoMinado[x][y].mostrarValor(campoMinado[x][y].getValue());
                abrirEspaçosVazios(x - 1, y - 1);
                abrirEspaçosVazios(x - 1, y);
                abrirEspaçosVazios(x - 1, y + 1);
                abrirEspaçosVazios(x, y - 1);
                abrirEspaçosVazios(x, y + 1);
                abrirEspaçosVazios(x + 1, y - 1);
                abrirEspaçosVazios(x + 1, y);
                abrirEspaçosVazios(x + 1, y + 1);
            }
        }
    }

    public static void revelarTabuleiro() {
        Bloco[][] minesweeper = getCampoMinado();
        for (int i = 0; i < getLinha(); i++) {
            for (int j = 0; j < getColuna(); j++) {
                if (!minesweeper[i][j].isOpened() || minesweeper[i][j].isMarked()) {
                    if (minesweeper[i][j].isMarked() && minesweeper[i][j].getValue() != 9) {
                        minesweeper[i][j].mostrarValor(12);
                    } else {
                        minesweeper[i][j].mostrarValor(minesweeper[i][j].getValue());
                    }
                }
            }
        }

    }

    public static void verificaVitoria() {
        int certas = 0;
        for (int i = 0; i < getLinha(); i++) {
            for (int j = 0; j < getColuna(); j++) {
                /*if((!campoMinado[i][j].isOpened() || campoMinado[i][j].isMarked()) 
                    && campoMinado[i][j].getValue() == 9)
                   certas++;*/
                if (campoMinado[i][j].isOpened() && campoMinado[i][j].getValue() != 9) {
                    certas++;
                }
            }
        }
        if (certas == (getLinha() * getColuna()) - getQuantMinas()) {
            SmileGanhou();
        }

    }

    public static void SadSmilePerdeu() {
        smileFace1.setIcon(smileFace1.getIcon_Sad_Smile());
        Tabuleiro.revelarTabuleiro();
        painelDigital1.setIcon(painelDigital1.getIcon_derrota());
    }

    public static void SmileGanhou() {
        Tabuleiro.revelarTabuleiro();
        painelDigital1.setIcon(painelDigital1.getIcon_vitoria());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PainelGeral = new javax.swing.JPanel();
        instructionsLabel = new javax.swing.JLabel();
        PainelMatriz = new javax.swing.JPanel();
        PainelContador = new javax.swing.JPanel();
        PainelHelp = new javax.swing.JPanel();
        ajuda2 = new campominado.Ajuda();
        bordaHelp = new javax.swing.JLabel();
        bordaCont = new javax.swing.JLabel();
        painelDigital1 = new campominado.PainelDigital();
        smileFace1 = new campominado.SmileFace();
        background = new javax.swing.JLabel();
        panicLabel = new javax.swing.JLabel();
        panic = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Campo Minado");

        PainelGeral.setLayout(null);

        instructionsLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/instructions.png"))); // NOI18N
        instructionsLabel.setFocusable(false);
        PainelGeral.add(instructionsLabel);
        instructionsLabel.setBounds(0, 0, 280, 380);

        PainelMatriz.setBackground(new java.awt.Color(102, 102, 102));
        PainelMatriz.setLayout(null);
        PainelGeral.add(PainelMatriz);
        PainelMatriz.setBounds(20, 100, 243, 243);

        PainelContador.setBackground(new java.awt.Color(0, 0, 0));
        PainelContador.setToolTipText("Contador de bombas");
        PainelContador.setFocusable(false);

        javax.swing.GroupLayout PainelContadorLayout = new javax.swing.GroupLayout(PainelContador);
        PainelContador.setLayout(PainelContadorLayout);
        PainelContadorLayout.setHorizontalGroup(
            PainelContadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        PainelContadorLayout.setVerticalGroup(
            PainelContadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        PainelGeral.add(PainelContador);
        PainelContador.setBounds(45, 30, 60, 33);

        PainelHelp.setBackground(new java.awt.Color(0, 0, 0));
        PainelHelp.setFocusable(false);
        PainelHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PainelHelpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PainelHelpMouseExited(evt);
            }
        });

        ajuda2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/HELPP.png"))); // NOI18N
        ajuda2.setText("ajuda2");

        javax.swing.GroupLayout PainelHelpLayout = new javax.swing.GroupLayout(PainelHelp);
        PainelHelp.setLayout(PainelHelpLayout);
        PainelHelpLayout.setHorizontalGroup(
            PainelHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ajuda2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, Short.MAX_VALUE)
        );
        PainelHelpLayout.setVerticalGroup(
            PainelHelpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelHelpLayout.createSequentialGroup()
                .addComponent(ajuda2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        PainelGeral.add(PainelHelp);
        PainelHelp.setBounds(180, 30, 65, 33);

        bordaHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cont.png"))); // NOI18N
        bordaHelp.setFocusable(false);
        PainelGeral.add(bordaHelp);
        bordaHelp.setBounds(180, 20, 83, 50);

        bordaCont.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cont.png"))); // NOI18N
        bordaCont.setFocusable(false);
        PainelGeral.add(bordaCont);
        bordaCont.setBounds(40, 20, 70, 50);

        painelDigital1.setText("painelDigital1");
        painelDigital1.setToolTipText("");
        painelDigital1.setFocusable(false);
        PainelGeral.add(painelDigital1);
        painelDigital1.setBounds(20, 380, 240, 40);

        smileFace1.setToolTipText("Reiniciar o jogo");
        smileFace1.setFocusable(false);
        PainelGeral.add(smileFace1);
        smileFace1.setBounds(125, 30, 40, 35);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background2.png"))); // NOI18N
        background.setFocusable(false);
        PainelGeral.add(background);
        background.setBounds(0, 0, 300, 440);

        panicLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paniclabel.png"))); // NOI18N
        panicLabel.setFocusable(false);
        panicLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panicLabelMouseClicked(evt);
            }
        });
        PainelGeral.add(panicLabel);
        panicLabel.setBounds(0, 0, 290, 440);

        panic.setFocusable(false);
        panic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panicMouseEntered(evt);
            }
        });
        PainelGeral.add(panic);
        panic.setBounds(0, 420, 130, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelGeral, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelGeral, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panicMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panicMouseEntered
        // TODO add your handling code here:
        System.out.println("Modo Pânico");
        panicLabel.setVisible(true);
        PainelMatriz.setVisible(false);
        background.setVisible(false);
        smileFace1.setVisible(false);
        PainelHelp.setVisible(false);
        PainelContador.setVisible(false);
        bordaHelp.setVisible(false);
        bordaCont.setVisible(false);
        painelDigital1.setVisible(false);
    }//GEN-LAST:event_panicMouseEntered

    private void panicLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panicLabelMouseClicked
        // TODO add your handling code here:
        System.out.println("Saiu do Modo Pânico");
        panicLabel.setVisible(false);
        PainelMatriz.setVisible(true);
        background.setVisible(true);
        smileFace1.setVisible(true);
        PainelHelp.setVisible(true);
        PainelContador.setVisible(true);
        bordaHelp.setVisible(true);
        bordaCont.setVisible(true);
        painelDigital1.setVisible(true);
    }//GEN-LAST:event_panicLabelMouseClicked

    private void PainelHelpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PainelHelpMouseEntered
        // TODO add your handling code here:
        instructionsLabel.setVisible(true);
        for (int i = 0; i < getLinha(); i++) {
            for (int j = 0; j < getColuna(); j++) {
                campoMinado[i][j].setVisible(false);
            }
        }
    }//GEN-LAST:event_PainelHelpMouseEntered

    private void PainelHelpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PainelHelpMouseExited
        // TODO add your handling code here:
        instructionsLabel.setVisible(false);
        for (int i = 0; i < getLinha(); i++) {
            for (int j = 0; j < getColuna(); j++) {
                campoMinado[i][j].setVisible(true);
            }
        }
    }//GEN-LAST:event_PainelHelpMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelContador;
    private javax.swing.JPanel PainelGeral;
    private javax.swing.JPanel PainelHelp;
    private static javax.swing.JPanel PainelMatriz;
    private campominado.Ajuda ajuda2;
    private javax.swing.JLabel background;
    private javax.swing.JLabel bordaCont;
    private javax.swing.JLabel bordaHelp;
    private javax.swing.JLabel instructionsLabel;
    private static campominado.PainelDigital painelDigital1;
    private javax.swing.JLabel panic;
    private javax.swing.JLabel panicLabel;
    private static campominado.SmileFace smileFace1;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/9.png")));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("keycode " + e.getKeyChar() + ":" + e.getKeyCode());
        switch (e.getKeyCode()) {
            // CIMA ou W
            case 87:
            case 38:
                campoMinado[auxiliar.getI()][auxiliar.getJ()].desmarcarBloco();
                blocoCima();
                campoMinado[auxiliar.getI()][auxiliar.getJ()].marcarBloco();
                break;
            // BAIXO ou S
            case 83:
            case 40:
                campoMinado[auxiliar.getI()][auxiliar.getJ()].desmarcarBloco();
                blocoBaixo();
                campoMinado[auxiliar.getI()][auxiliar.getJ()].marcarBloco();
                break;
            // DIREITA ou D
            case 68:
            case 39:
                campoMinado[auxiliar.getI()][auxiliar.getJ()].desmarcarBloco();
                blocoDireita();
                campoMinado[auxiliar.getI()][auxiliar.getJ()].marcarBloco();
                break;
            // ESQUERDA ou A
            case 65:
            case 37:
                campoMinado[auxiliar.getI()][auxiliar.getJ()].desmarcarBloco();
                blocoEsquerda();
                campoMinado[auxiliar.getI()][auxiliar.getJ()].marcarBloco();
                break;
            // ENTER (abrir bloco)
            case 10:
                if (!campoMinado[auxiliar.getI()][auxiliar.getJ()].isOpened()) {
                    campoMinado[auxiliar.getI()][auxiliar.getJ()].clicouEsquerdo();
                }
                Tabuleiro.verificaVitoria();
                break;
            // SPACE (colocar/tirar bandeira)
            case 32:
                if (campoMinado[auxiliar.getI()][auxiliar.getJ()].isMarked()) {
                    campoMinado[auxiliar.getI()][auxiliar.getJ()].clicouDireitoAberto();
                } else if (!campoMinado[auxiliar.getI()][auxiliar.getJ()].isOpened()) {
                    campoMinado[auxiliar.getI()][auxiliar.getJ()].clicouDireitoFechado();
                }
                break;
            // R (reiniciar jogo)
            case 82:
                smileFace1.Reiniciar();
                break;
            default:

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void blocoCima() {
        if (auxiliar.getI() == 0) {
            auxiliar.setI(getLinha() - 1);
            if (auxiliar.getJ() != 0) {
                auxiliar.setJ(auxiliar.getJ() - 1);
            } else {
                auxiliar.setJ(getColuna() - 1);
            }
        } else {
            auxiliar.setI(auxiliar.getI() - 1);
        }
    }

    private void blocoBaixo() {
        if (auxiliar.getI() == getLinha() - 1) {
            auxiliar.setI(0);
            if (auxiliar.getJ() == getColuna() - 1) {
                auxiliar.setJ(0);
            } else {
                auxiliar.setJ(auxiliar.getJ() + 1);
            }
        } else {
            auxiliar.setI(auxiliar.getI() + 1);
        }
    }

    private void blocoDireita() {
        if (auxiliar.getJ() == getColuna() - 1) {
            auxiliar.setJ(0);
            if (auxiliar.getI() == getLinha() - 1) {
                auxiliar.setI(0);
            } else {
                auxiliar.setI(auxiliar.getI() + 1);
            }
        } else {
            auxiliar.setJ(auxiliar.getJ() + 1);
        }
    }

    private void blocoEsquerda() {
        if (auxiliar.getJ() == 0) {
            auxiliar.setJ(getColuna() - 1);
            if (auxiliar.getI() == 0) {
                auxiliar.setI(getLinha() - 1);
            } else {
                auxiliar.setI(auxiliar.getI() - 1);
            }
        } else {
            auxiliar.setJ(auxiliar.getJ() - 1);
        }
    }

}
