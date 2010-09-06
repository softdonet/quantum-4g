/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.gui.componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Jose
 */
public class VentanaExperimento extends JFrame implements WindowListener {

    private int operacionGrover;
    private int operacionGrasp;
    private double valorGrover=-1;
    private double valorGrasp=-1;
    private boolean dibujarMayorMenor=false;
    private boolean iniciarExperimento=false;
    private int iteraciones;
    private HiloExperimento hiloE;
    
    public VentanaExperimento(int velocidad,int N,int iteraciones){
        this.setTitle("Quantum4G");
        this.addWindowListener(this);
        this.setBounds(20, 20, 600+iteraciones*6, 450);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.white);
        this.setVisible(true);
        this.iteraciones=iteraciones;
        hiloE=new HiloExperimento(this,velocidad,N,iteraciones);
        hiloE.start();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if (isIniciarExperimento()){
            g.drawString("Algoritmo Grover:", 25, 50);
            g.drawString("Algoritmo Grasp:", 25, 100);
            g.drawString("Victorias Grover = " + getOperacionGrover(), 100,150 );
            g.drawString("Victorias Grasp = " + getOperacionGrasp(), 400, 150);

            g.setColor(Color.WHITE);
            g.fillRect(180, 40, 10*this.iteraciones, 20);
            g.fillRect(180, 90, 10*this.iteraciones, 20);

            g.setColor(Color.blue);

            g.fillRect(180, 45,10*getOperacionGrover(),10 );
            g.setColor(Color.red);
            g.fillRect(180, 95, 10*getOperacionGrasp(),10);

            if (getValorGrasp()>-1){
                g.drawString ("Grasp:"+ Math.round(getValorGrasp()),120, 300);
            }
            if (getValorGrover()>-1){
                g.drawString ("Grover:"+ Math.round(getValorGrover()),440, 300);
            }
            if (getValorGrasp()>-1 && getValorGrover()>-1 && isDibujarMayorMenor()){
                if (getValorGrasp()>getValorGrover()){
                    g.drawString ("ES MAYOR QUE ", 260, 300);
                }
                else{
                    g.drawString("ES MENOR QUE",260,300);
                }
                setValorGrasp(-1);
                setValorGrover(-1);
                setDibujarMayorMenor(false);
            }
        }
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
            this.hiloE.setTerminarHilo(true);
            this.getHiloE().join();
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
        this.repaint();   
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    
    }

    public int getOperacionGrover() {
        return operacionGrover;
    }

    public void setOperacionGrover(int operacionGrover) {
        this.operacionGrover = operacionGrover;
    }

    public int getOperacionGrasp() {
        return operacionGrasp;
    }

    public void setOperacionGrasp(int operacionGrasp) {
        this.operacionGrasp = operacionGrasp;
    }

    public double getValorGrover() {
        return valorGrover;
    }

    public void setValorGrover(double valorGrover) {
        this.valorGrover = valorGrover;
    }

    public double getValorGrasp() {
        return valorGrasp;
    }

    public void setValorGrasp(double valorGrasp) {
        this.valorGrasp = valorGrasp;
    }

    public boolean isDibujarMayorMenor() {
        return dibujarMayorMenor;
    }

    public void setDibujarMayorMenor(boolean dibujarMayorMenor) {
        this.dibujarMayorMenor = dibujarMayorMenor;
    }

    public boolean isIniciarExperimento() {
        return iniciarExperimento;
    }

    public void setIniciarExperimento(boolean iniciarExperimento) {
        this.iniciarExperimento = iniciarExperimento;
    }

    public HiloExperimento getHiloE() {
        return hiloE;
    }

    public void setHiloE(HiloExperimento hiloE) {
        this.hiloE = hiloE;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

}
