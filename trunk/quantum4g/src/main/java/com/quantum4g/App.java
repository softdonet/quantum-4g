package com.quantum4g;

import com.quantum4g.algoritmos.grasp.AlgoritmoGRASP;
import com.quantum4g.algoritmos.grover.AlgoritmoGrover;
import com.quantum4g.core.entidades.Triada;
import com.quantum4g.experimentos.principal.Principal;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class App 
{
    public static void main(String[] args) throws FileNotFoundException, IOException {
          double resultadoGrover;
          double resultadoGrasp;
          double groverWins=0;
          double graspWins=0;

          Writer out = new OutputStreamWriter(new FileOutputStream("./resultadosExperimento.txt"));

          int cantidadIteraciones=100;
          for (int i=0;i<cantidadIteraciones;i++)
          {
              Principal principal=new Principal(10);
              Triada[] triadaExperimento= principal.generaValoresTriadaGen(10);
              //Ejecucion GRASP
              AlgoritmoGRASP algoritmoGRASP=new AlgoritmoGRASP(10, triadaExperimento);
              resultadoGrasp=algoritmoGRASP.ejecucionGRASP();
              //Ejecucion Grover
              AlgoritmoGrover algoritmoGrover=new AlgoritmoGrover(10, principal.inicializaUniverso(triadaExperimento));
              resultadoGrover=algoritmoGrover.ejecucionGrover();

              out.write("Iteracion : " + i + " de " + cantidadIteraciones + "\n");
              out.write("Algoritmo Grasp: "+resultadoGrasp + "\n");
              out.write("Algoritmo Grover: "+resultadoGrover + "\n\n");

              if (resultadoGrover>resultadoGrasp){
                  groverWins++;
              }
              else{
                  graspWins++;
              }
          }
          System.out.println("\n\n\n");
          System.out.println("Victorias GROVER: "+groverWins);
          System.out.println("Victorias GRASP: "+graspWins);

    }
}
