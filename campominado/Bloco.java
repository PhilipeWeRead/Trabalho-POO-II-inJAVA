/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominado;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author guit_
 */
public class Bloco extends javax.swing.JButton implements KeyListener {

    private int i;
    private int j;
    private int value;
    private boolean opened = false;
    private boolean marked = false;
    private final Icon Icon_Vazio = new javax.swing.ImageIcon(getClass().getResource("/images/0.png"));
    private final Icon Icon_1 = new javax.swing.ImageIcon(getClass().getResource("/images/1.png"));
    private final Icon Icon_2 = new javax.swing.ImageIcon(getClass().getResource("/images/2.png"));
    private final Icon Icon_3 = new javax.swing.ImageIcon(getClass().getResource("/images/3.png"));
    private final Icon Icon_4 = new javax.swing.ImageIcon(getClass().getResource("/images/4.png"));
    private final Icon Icon_5 = new javax.swing.ImageIcon(getClass().getResource("/images/5.png"));
    private final Icon Icon_6 = new javax.swing.ImageIcon(getClass().getResource("/images/6.png"));
    private final Icon Icon_7 = new javax.swing.ImageIcon(getClass().getResource("/images/7.png"));
    private final Icon Icon_8 = new javax.swing.ImageIcon(getClass().getResource("/images/8.png"));
    private final Icon Icon_Bomba = new javax.swing.ImageIcon(getClass().getResource("/images/9.png"));
    private final Icon Icon_Padrao = new javax.swing.ImageIcon(getClass().getResource("/images/10.png"));
    private final Icon Icon_Bandeira = new javax.swing.ImageIcon(getClass().getResource("/images/11.png"));
    private final Icon Icon_Bomba_Errada = new javax.swing.ImageIcon(getClass().getResource("/images/12.png"));
    private final Icon Icon_Bomba_Estourada = new javax.swing.ImageIcon(getClass().getResource("/images/13.png"));

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public Bloco() {
        this.setI(0);
        this.setJ(0);
    }

    public Bloco(int i, int j) {
        this.setPreferredSize(new Dimension(27, 27));
        this.addMouseListener(new MouseHandler());
        this.addKeyListener(this);
        this.setIcon(Icon_Padrao);
        this.setI(i);
        this.setJ(j);
        this.setValue(0);

    }

    public void desmarcarBloco() {
        setBorder(null);
    }

    public void marcarBloco() {
        setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
    }

    public void acrescentarValue() {
        setValue(getValue() + 1);
    }

    public void reset() {
        setMarked(false);
        setOpened(false);
        setValue(0);
        setIcon(Icon_Padrao);
    }

    public void mostrarValor(int valor) {
        switch (valor) {
            case 0:
                setIcon(Icon_Vazio);
                setOpened(true);
                break;
            case 1:
                setIcon(Icon_1);
                setOpened(true);
                break;
            case 2:
                setIcon(Icon_2);
                setOpened(true);
                break;
            case 3:
                setIcon(Icon_3);
                setOpened(true);
                break;
            case 4:
                setIcon(Icon_4);
                setOpened(true);
                break;
            case 5:
                setIcon(Icon_5);
                setOpened(true);
                break;
            case 6:
                setIcon(Icon_6);
                setOpened(true);
                break;
            case 7:
                setIcon(Icon_7);
                setOpened(true);
                break;
            case 8:
                setIcon(Icon_8);
                setOpened(true);
                break;
            case 9:
                setIcon(Icon_Bomba);
                setOpened(true);
                break;
            case 12:
                setIcon(Icon_Bomba_Errada);
                setOpened(true);
                break;
            case 13:
                setIcon(Icon_Bomba_Estourada);
                setOpened(true);
                break;
        }
        Tabuleiro.acrescentarAbertos();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        new Tabuleiro().keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            // TODO: Colocar o código para lidar com os eventos do mouse

            if (!isOpened()) {
                if (event.isMetaDown()) {
                    clicouDireitoFechado();
                } else {
                    clicouEsquerdo();
                }
            } else {
                if (event.isMetaDown() && isMarked()) {
                    clicouDireitoAberto();
                }
            }
            Tabuleiro.verificaVitoria();
        }
    }

    public void clicouEsquerdo() {
        if (getValue() == 9) //bomba
        {
            setValue(13); //bomba estourada
            Tabuleiro.SadSmilePerdeu();
            return;
        } else if (getValue() == 0) {
            Tabuleiro.abrirEspaçosVazios(getI(), getJ());
        } else {
            mostrarValor(getValue());
        }
    }

    public void clicouDireitoAberto() {
        setIcon(Icon_Padrao);
        setMarked(false);
        setOpened(false);
        Tabuleiro.decrementarAbertos();
        Tabuleiro.decrementarBandeiras();
        Tabuleiro.alterarContador();
    }

    public void clicouDireitoFechado() {
        setIcon(Icon_Bandeira);
        setMarked(true);
        setOpened(true);
        Tabuleiro.acrescentarAbertos();
        Tabuleiro.acrescentarBandeiras();
        Tabuleiro.alterarContador();
    }

}
