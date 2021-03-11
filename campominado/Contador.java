/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominado;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author guit_
 */
public class Contador extends javax.swing.JLabel {

    private final Icon Icon_c0 = new javax.swing.ImageIcon(getClass().getResource("/images/c0.png"));
    private final Icon Icon_c1 = new javax.swing.ImageIcon(getClass().getResource("/images/c1.png"));
    private final Icon Icon_c2 = new javax.swing.ImageIcon(getClass().getResource("/images/c2.png"));
    private final Icon Icon_c3 = new javax.swing.ImageIcon(getClass().getResource("/images/c3.png"));
    private final Icon Icon_c4 = new javax.swing.ImageIcon(getClass().getResource("/images/c4.png"));
    private final Icon Icon_c5 = new javax.swing.ImageIcon(getClass().getResource("/images/c5.png"));
    private final Icon Icon_c6 = new javax.swing.ImageIcon(getClass().getResource("/images/c6.png"));
    private final Icon Icon_c7 = new javax.swing.ImageIcon(getClass().getResource("/images/c7.png"));
    private final Icon Icon_c8 = new javax.swing.ImageIcon(getClass().getResource("/images/c8.png"));
    private final Icon Icon_c9 = new javax.swing.ImageIcon(getClass().getResource("/images/c9.png"));

    public Contador(int x) {
        this.setPreferredSize(new Dimension(16, 29));
        this.setIcon(selecionarIcone(x));
        this.setText("");
    }

    public Icon selecionarIcone(int x) {
        switch (x) {
            case 0:
                return Icon_c0;
            case 1:
                return Icon_c1;
            case 2:
                return Icon_c2;
            case 3:
                return Icon_c3;
            case 4:
                return Icon_c4;
            case 5:
                return Icon_c5;
            case 6:
                return Icon_c6;
            case 7:
                return Icon_c7;
            case 8:
                return Icon_c8;
            case 9:
                return Icon_c9;
            default:
                return Icon_c0;

        }
    }

    public static int unidade(int x) {
        return (x / 1) % 10;
    }

    public static int dezena(int x) {
        return (x / 10) % 10;
    }

    public static int centena(int x) {
        return (x / 100) % 10;
    }
}
