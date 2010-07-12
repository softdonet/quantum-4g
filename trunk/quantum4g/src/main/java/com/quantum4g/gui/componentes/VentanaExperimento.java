/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.gui.componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Jose
 */
public class VentanaExperimento extends JFrame implements WindowListener {

    public int operacionGrover;
    public int operacionGrasp;
    public double valorGrover=-1;
    public double valorGrasp=-1;
    public boolean dibujarMayorMenor=false;
    public boolean iniciarExperimento=false;
    public HiloExperimento hiloE;
    public DecimalFormat df;

    public VentanaExperimento(int velocidad,int N,int iteraciones){
        this.setTitle("Quantum4G");
        this.addWindowListener(this);
        this.setBounds(20, 20, 800, 450);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.white);
        this.setVisible(true);
        hiloE=new HiloExperimento(this,velocidad,N,iteraciones);
        hiloE.start();
        df = new DecimalFormat("####.000");
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        /*if (iniciarExperimento){
            g.drawString("Algoritmo Grover:", 25, 50);
            g.drawString("Algoritmo Grasp:", 25, 100);
            g.drawString("Victorias Grover = " + operacionGrover, 100,150 );
            g.drawString("Victorias Grasp = " + operacionGrasp, 400, 150);
            g.setColor(Color.blue);
            g.drawLine(200, 45, 200+10*operacionGrover,45 );
            g.setColor(Color.red);
            g.drawLine(200, 95, 200+10*operacionGrasp,95);

            if (valorGrasp>-1){
                g.drawString ("Grasp:"+ Math.round(valorGrasp),120, 300);
            }
            if (valorGrover>-1){
                g.drawString ("Grover:"+ Math.round(valorGrover),440, 300);
            }
            if (valorGrasp>-1 && valorGrover>-1 && dibujarMayorMenor){
                if (valorGrasp>valorGrover){
                    g.drawString ("ES MAYOR QUE ", 260, 300);
                }
                else{
                    g.drawString("ES MENOR QUE",260,300);
                }
                valorGrasp=-1;
                valorGrover=-1;
                dibujarMayorMenor=false;
            }
        }*/
    }

    @Override
    public void windowOpened(WindowEvent e) {
       
    }

    @Override
    public void windowClosing(WindowEvent e) {
    
    }

    @Override
    public void windowClosed(WindowEvent e) {
        try {
            this.hiloE.terminarHilo=true;
            this.hiloE.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(VentanaExperimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
    
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    
    }

    @Override
    public void windowActivated(WindowEvent e) {
    
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    
    }
    
}
