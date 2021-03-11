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
public class PainelDigital extends javax.swing.JLabel {

    private final Icon Icon_vitoria = new javax.swing.ImageIcon(getClass().getResource("/images/VITORIA.png"));
    private final Icon Icon_derrota = new javax.swing.ImageIcon(getClass().getResource("/images/DERROTA.png"));
    private final Icon Icon_default = new javax.swing.ImageIcon(getClass().getResource("/images/digit.png"));

    public PainelDigital() {
        this.setPreferredSize(new Dimension(240, 40));
        this.setIcon(Icon_default);
    }

    public Icon getIcon_vitoria() {
        return Icon_vitoria;
    }

    public Icon getIcon_derrota() {
        return Icon_derrota;
    }

    public Icon getIcon_default() {
        return Icon_default;
    }

}
