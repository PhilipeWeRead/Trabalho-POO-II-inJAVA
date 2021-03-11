/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominado;

import java.awt.Dimension;
import javax.swing.Icon;

/**
 *
 * @author guit_
 */
public class Ajuda extends javax.swing.JLabel {

    private final Icon Icon_help = new javax.swing.ImageIcon(getClass().getResource("/images/help.png"));

    public Ajuda() {
        this.setPreferredSize(new Dimension(16, 27));
        this.setIcon(Icon_help);
        this.setText("");
    }
}
