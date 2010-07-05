/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.gui.ventanas;

import com.quantum4g.algoritmos.grasp.AlgoritmoGRASP;
import com.quantum4g.algoritmos.grover.AlgoritmoGrover;
import com.quantum4g.core.entidades.Triada;
import com.quantum4g.experimentos.principal.Principal;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose
 */
public class HiloExperimento extends Thread {
    public VentanaExperimento ventanaE;
    public boolean terminarHilo=false;
    public int velocidad=1;
    public int N;
    public int iteraciones;

    public HiloExperimento(VentanaExperimento ventanaE,int velocidad,int N,int iteraciones){
        this.ventanaE=ventanaE;
        this.velocidad=velocidad;
        this.N=N;
        this.iteraciones=iteraciones;
    }
    @Override
    public void run(){
        DataOutputStream out1 = null;
        try {
            double resultadoGrover;
            double resultadoGrasp;
            double groverWins = 0;
            double graspWins = 0;
            out1 = new DataOutputStream(new FileOutputStream("./resultadosExperimento.txt"));
            DataOutputStream out2 = new DataOutputStream(new FileOutputStream("./resultadosGrover.txt"));
            DataOutputStream out3 = new DataOutputStream(new FileOutputStream("./resultadosGrasp.txt"));
            out2.writeBytes("Algoritmo Grover: \n\n");
            out3.writeBytes("Algoritmo Grasp: \n\n");
            this.ventanaE.iniciarExperimento = true;
            //Que se lean de un archivo de texto, la cantidad, el alfa, el N
            int cantidadIteraciones = 10;
            for (int i = 0; i < cantidadIteraciones && !terminarHilo; i++) {
                Principal principal = new Principal(10);
                Triada[] triadaExperimento = principal.generaValoresTriadaGen(10);
                //Ejecucion GRASP
                AlgoritmoGRASP algoritmoGRASP = new AlgoritmoGRASP(10, triadaExperimento);
                resultadoGrasp = algoritmoGRASP.ejecucionGRASP();
                this.ventanaE.valorGrasp = resultadoGrasp;
                this.ventanaE.repaint();
                Thread.sleep(1000*this.velocidad);
                //Ejecucion Grover
                AlgoritmoGrover algoritmoGrover = new AlgoritmoGrover(10, principal.inicializaUniverso(triadaExperimento));
                resultadoGrover = algoritmoGrover.ejecucionGrover();
                this.ventanaE.valorGrover = resultadoGrover;
                this.ventanaE.repaint();
                Thread.sleep(1000*this.velocidad);
                this.ventanaE.dibujarMayorMenor = true;
                this.ventanaE.repaint();
                Thread.sleep(1000*this.velocidad);
                out1.writeBytes("Iteracion : " + i + " de " + cantidadIteraciones + "\n\n");
                out1.writeBytes("Datos de Entrada: \n\n");
                for (int h = 0; h < triadaExperimento.length; h++) {
                    out1.writeBytes("Triada " + h + ":\n");
                    out1.writeBytes("Factor Positivo: " + triadaExperimento[h].getFactorPositivo() + ":\n");
                    out1.writeBytes("Factor Negativo: " + triadaExperimento[h].getFactorNegativo() + ":\n");
                    out1.writeBytes("Factor Ponderación: " + triadaExperimento[h].getFactorPonderacion() + ":\n\n");
                }
                out1.writeBytes("Datos de Salida: \n\n");
                out1.writeBytes("Algoritmo Grasp: " + resultadoGrasp + "\n");
                out1.writeBytes("Algoritmo Grover: " + resultadoGrover + "\n\n");
                out1.writeBytes("Algoritmo Grasp demoro: " + algoritmoGRASP.getCantidadOperaciones() + " operaciones \n");
                out1.writeBytes("Algoritmo Grover demoro: " + algoritmoGrover.getCantidadOperaciones() + " operaciones \n\n");
                out2.writeBytes(resultadoGrover + "\t" + algoritmoGrover.getCantidadOperaciones() + "\n");
                out3.writeBytes(resultadoGrasp + "\t" + algoritmoGRASP.getCantidadOperaciones() + "\n");
                if (resultadoGrover > resultadoGrasp) {
                    groverWins++;
                    out1.writeBytes("GROVER ganó por : " + (resultadoGrover - resultadoGrasp) + "\n\n");
                    this.ventanaE.operacionGrover++;
                } else {
                    graspWins++;
                    out1.writeBytes("GRASP ganó por : " + (resultadoGrasp - resultadoGrover) + "\n\n");
                    this.ventanaE.operacionGrasp++;
                }
                this.ventanaE.repaint();
                out1.writeBytes("----------------------------------------------------------------------- \n\n");
            }
            System.out.println("\n\n\n");
            System.out.println("Victorias GROVER: " + groverWins);
            System.out.println("Victorias GRASP: " + graspWins);
            out1.close();
            out2.close();
            out3.close();
            //this.ventanaE.dispose();

        } catch (InterruptedException ex) {
            Logger.getLogger(HiloExperimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloExperimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
