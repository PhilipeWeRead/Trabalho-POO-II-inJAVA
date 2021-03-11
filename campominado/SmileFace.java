/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominado;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;

/**
 *
 * @author guit_
 */
public class SmileFace extends javax.swing.JButton {

    private final Icon Icon_Smile = new javax.swing.ImageIcon(getClass().getResource("/images/buttonsmile.png"));
    private final Icon Icon_Sad_Smile = new javax.swing.ImageIcon(getClass().getResource("/images/buttonsadsmile.png"));

    public SmileFace() {
        this.setPreferredSize(new Dimension(35, 35));
        this.addMouseListener(new MouseHandler());
        this.setIcon(Icon_Smile);
    }

    public Icon getIcon_Smile() {
        return Icon_Smile;
    }

    public Icon getIcon_Sad_Smile() {
        return Icon_Sad_Smile;
    }

    public void Reiniciar() {
        Tabuleiro.ReiniciarTabuleiro();
        setIcon(Icon_Smile);
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            // TODO: Colocar o código para lidar com os eventos do mouse
            Reiniciar();

        }
    }
}
