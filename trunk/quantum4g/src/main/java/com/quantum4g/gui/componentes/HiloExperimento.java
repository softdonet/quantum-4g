/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.gui.componentes;

import com.quantum4g.algoritmos.grasp.AlgoritmoGRASP;
import com.quantum4g.algoritmos.grover.AlgoritmoGrover;
import com.quantum4g.core.entidades.Triada;
import com.quantum4g.experimentos.principal.Principal;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose
 */
public class HiloExperimento extends Thread {
    private VentanaExperimento ventanaE;
    private boolean terminarHilo=false;
    private int velocidad=1;
    private int N;
    private int iteraciones;

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
            DecimalFormat df=new DecimalFormat("####.000");
            DataOutputStream out2 = new DataOutputStream(new FileOutputStream("./resultadosGrover.txt"));
            DataOutputStream out3 = new DataOutputStream(new FileOutputStream("./resultadosGrasp.txt"));
            out2.writeBytes("Algoritmo Grover: \n\n");
            out3.writeBytes("Algoritmo Grasp: \n\n");
            this.ventanaE.setIniciarExperimento(true);
            //Que se lean de un archivo de texto, la cantidad, el alfa, el N
            for (int i = 0; i < this.getIteraciones() && !isTerminarHilo(); i++) {
                Principal principal = new Principal(this.getN());
                Triada[] triadaExperimento = principal.generaValoresTriadaGen(this.getN());
                //Ejecucion GRASP
                AlgoritmoGRASP algoritmoGRASP = new AlgoritmoGRASP(this.getN(), triadaExperimento);
                resultadoGrasp = algoritmoGRASP.ejecucionGRASP();
                this.ventanaE.setValorGrasp(resultadoGrasp);
                this.getVentanaE().repaint();
                Thread.sleep(1000*this.getVelocidad());
                //Ejecucion Grover
                AlgoritmoGrover algoritmoGrover = new AlgoritmoGrover( this.getN(), principal.inicializaUniverso(triadaExperimento));
                resultadoGrover = algoritmoGrover.ejecucionGrover();
                this.ventanaE.setValorGrover(resultadoGrover);
                this.getVentanaE().repaint();
                Thread.sleep(1000*this.getVelocidad());
                this.ventanaE.setDibujarMayorMenor(true);
                this.getVentanaE().repaint();
                Thread.sleep(1000*this.getVelocidad());
                out1.writeBytes("Iteración: " + i + " de " + this.getIteraciones() + "\n\n");
                out1.writeBytes("Datos de Entrada: \n\n");
                for (int h = 0; h < triadaExperimento.length; h++) {
                    out1.writeBytes("Triada " + h + ": \n");
                    out1.writeBytes("Factor Positivo: " + df.format(triadaExperimento[h].getFactorPositivo()) + "\n");
                    out1.writeBytes("Factor Negativo: " + df.format(triadaExperimento[h].getFactorNegativo()) + "\n");
                    out1.writeBytes("Factor Ponderación: " + df.format(triadaExperimento[h].getFactorPonderacion()) + "\n\n");
                }
                out1.writeBytes("Datos de Salida: \n\n");
                out1.writeBytes("Grado de Bondad Máximo - Algoritmo Grasp: " + df.format(resultadoGrasp) + "\n");
                out1.writeBytes("Grado de Bondad Máximo - Algoritmo Grover: " + df.format(resultadoGrover) + "\n\n");
                out1.writeBytes("Algoritmo Grasp demoro: " + algoritmoGRASP.getCantidadOperaciones() + " operaciones \n");
                out1.writeBytes("Algoritmo Grover demoro: " + algoritmoGrover.getCantidadOperaciones() + " operaciones \n\n");
                out2.writeBytes(df.format(resultadoGrover) + "\t" + algoritmoGrover.getCantidadOperaciones() + "\t" +"\n");
                out3.writeBytes(df.format(resultadoGrasp) + "\t" + algoritmoGRASP.getCantidadOperaciones() + "\n");
                if (resultadoGrover > resultadoGrasp) {
                    groverWins++;
                    out1.writeBytes("Algoritmo Grover ganó por: " + df.format((resultadoGrover - resultadoGrasp)) + " unidades\n\n");
                    this.ventanaE.setOperacionGrover(this.ventanaE.getOperacionGrover()+1);
                } else
                    if (resultadoGrasp > resultadoGrover){
                        graspWins++;
                        out1.writeBytes("Algoritmo Grasp ganó por : " + df.format((resultadoGrasp - resultadoGrover)) + " unidades\n\n");
                        this.ventanaE.setOperacionGrasp(this.ventanaE.getOperacionGrasp()+1);
                    } else{
                        out1.writeBytes("Empate");
                    }
                this.getVentanaE().repaint();
                out1.writeBytes("----------------------------------------------------------------------- \n\n");
            }
            System.out.println("Victorias del Algoritmo Grover: " + groverWins);
            System.out.println("Victorias del Algoritmo Grasp: " + graspWins);
            out1.close();
            out2.close();
            out3.close();

        } catch (InterruptedException ex) {
            Logger.getLogger(HiloExperimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloExperimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public VentanaExperimento getVentanaE() {
        return ventanaE;
    }

    public void setVentanaE(VentanaExperimento ventanaE) {
        this.ventanaE = ventanaE;
    }

    public boolean isTerminarHilo() {
        return terminarHilo;
    }

    public void setTerminarHilo(boolean terminarHilo) {
        this.terminarHilo = terminarHilo;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }

}
