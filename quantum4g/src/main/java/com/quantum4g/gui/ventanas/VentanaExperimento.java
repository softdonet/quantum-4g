/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.gui.ventanas;

import com.quantum4g.algoritmos.grasp.AlgoritmoGRASP;
import com.quantum4g.algoritmos.grover.AlgoritmoGrover;
import com.quantum4g.core.entidades.Triada;
import com.quantum4g.experimentos.principal.Principal;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public VentanaExperimento(int velocidad,int N,int iteraciones){
        this.setTitle("Quantum4G");
        this.addWindowListener(this);
        this.setBounds(20, 20, 800, 450);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(Color.white);
        this.setVisible(true);
        hiloE=new HiloExperimento(this,velocidad,N,iteraciones);
        hiloE.start();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if (iniciarExperimento){
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
        }
    }


    public void ejecutarExperimentoPrincipal() throws FileNotFoundException, IOException, InterruptedException{

          //VentanaExperimento ventanaExperimento=new VentanaExperimento();
          double resultadoGrover;
          double resultadoGrasp;
          double groverWins=0;
          double graspWins=0;
          //ventanaExperimento.setVisible(true);
          DataOutputStream out1 = new DataOutputStream(new FileOutputStream("./resultadosExperimento.txt"));
          DataOutputStream out2 = new DataOutputStream(new FileOutputStream("./resultadosGrover.txt"));
          DataOutputStream out3 = new DataOutputStream(new FileOutputStream("./resultadosGrasp.txt"));

          out2.writeBytes("Algoritmo Grover: \n\n");
          out3.writeBytes("Algoritmo Grasp: \n\n");
          iniciarExperimento=true;
          //Que se lean de un archivo de texto, la cantidad, el alfa, el N
          int cantidadIteraciones=10;
          for (int i=0;i<cantidadIteraciones;i++)
          {
              Principal principal=new Principal(10);
              Triada[] triadaExperimento= principal.generaValoresTriadaGen(10);
              //Ejecucion GRASP
              AlgoritmoGRASP algoritmoGRASP=new AlgoritmoGRASP(10, triadaExperimento);
              resultadoGrasp=algoritmoGRASP.ejecucionGRASP();
              valorGrasp=resultadoGrasp;
              repaint();
              Thread.sleep(1000);
              //Ejecucion Grover
              AlgoritmoGrover algoritmoGrover=new AlgoritmoGrover(10, principal.inicializaUniverso(triadaExperimento));
              resultadoGrover=algoritmoGrover.ejecucionGrover();
              valorGrover=resultadoGrover;
              repaint();
              Thread.sleep(1000);
              dibujarMayorMenor=true;
              repaint();
              Thread.sleep(1000);
              out1.writeBytes("Iteracion : " + i + " de " + cantidadIteraciones+ "\n\n");
              out1.writeBytes("Datos de Entrada: \n\n");

              for (int h=0;h<triadaExperimento.length;h++){
                  out1.writeBytes("Triada "+h + ":\n");
                  out1.writeBytes("Factor Positivo: "+ triadaExperimento[h].getFactorPositivo() + ":\n");
                  out1.writeBytes("Factor Negativo: "+ triadaExperimento[h].getFactorNegativo() + ":\n");
                  out1.writeBytes("Factor Ponderación: "+ triadaExperimento[h].getFactorPonderacion() + ":\n\n");
              }

              out1.writeBytes("Datos de Salida: \n\n");
              out1.writeBytes("Algoritmo Grasp: "+resultadoGrasp + "\n");
              out1.writeBytes("Algoritmo Grover: "+resultadoGrover + "\n\n");
              out1.writeBytes("Algoritmo Grasp demoro: "+ algoritmoGRASP.getCantidadOperaciones() +" operaciones \n");
              out1.writeBytes("Algoritmo Grover demoro: "+ algoritmoGrover.getCantidadOperaciones()  +" operaciones \n\n");

              out2.writeBytes(resultadoGrover + "\t"+algoritmoGrover.getCantidadOperaciones()+"\n");
              out3.writeBytes(resultadoGrasp + "\t"+algoritmoGRASP.getCantidadOperaciones()+"\n");

              if (resultadoGrover>resultadoGrasp){
                  groverWins++;
                  out1.writeBytes("GROVER ganó por : "+(resultadoGrover-resultadoGrasp) + "\n\n");
                  operacionGrover++;
              }
              else{
                  graspWins++;
                  out1.writeBytes("GRASP ganó por : "+(resultadoGrasp-resultadoGrover) + "\n\n");
                  operacionGrasp++;
              }
              repaint();

              out1.writeBytes("----------------------------------------------------------------------- \n\n");

          }
          System.out.println("\n\n\n");
          System.out.println("Victorias GROVER: "+groverWins);
          System.out.println("Victorias GRASP: "+graspWins);
          out1.close();
          out2.close();
          out3.close();
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
