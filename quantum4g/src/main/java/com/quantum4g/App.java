package com.quantum4g;

import com.quantum4g.algoritmos.grasp.AlgoritmoGRASP;
import com.quantum4g.algoritmos.grover.AlgoritmoGrover;
import com.quantum4g.core.entidades.Triada;
import com.quantum4g.experimentos.principal.Principal;
import java.io.DataOutputStream;
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

          DataOutputStream out1 = new DataOutputStream(new FileOutputStream("./resultadosExperimento.txt"));
          DataOutputStream out2 = new DataOutputStream(new FileOutputStream("./resultadosGrover.txt"));
          DataOutputStream out3 = new DataOutputStream(new FileOutputStream("./resultadosGrasp.txt"));

          out2.writeBytes("Algoritmo Grover: \n\n");
          out3.writeBytes("Algoritmo Grasp: \n\n");

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

              out1.writeBytes("Datos de Entrada: \n\n");

              for (int h=0;h<triadaExperimento.length;h++){
                  out1.writeBytes("Triada "+h + ":\n");
                  out1.writeBytes("Factor Positivo: "+ triadaExperimento[h].getFactorPositivo() + ":\n");
                  out1.writeBytes("Factor Negativo: "+ triadaExperimento[h].getFactorNegativo() + ":\n");
                  out1.writeBytes("Factor Ponderación: "+ triadaExperimento[h].getFactorPonderacion() + ":\n\n");
              }

              out1.writeBytes("Datos de Salida: \n\n");
              out1.writeBytes("Iteracion : " + i + " de " + cantidadIteraciones+ "\n");
              out1.writeBytes("Algoritmo Grasp: "+resultadoGrasp + "\n");
              out1.writeBytes("Algoritmo Grover: "+resultadoGrover + "\n\n");

              out2.writeBytes(resultadoGrover + "\n");
              out3.writeBytes(resultadoGrasp + "\n");

              if (resultadoGrover>resultadoGrasp){
                  groverWins++;
                  out1.writeBytes("GROVER ganó por : "+(resultadoGrover-resultadoGrasp) + "\n\n");
              }
              else{
                  graspWins++;
                  out1.writeBytes("GRASP ganó por : "+(resultadoGrasp-resultadoGrover) + "\n\n");
              }

              out1.writeBytes("----------------------------------------------------------------------- \n\n");

          }
          System.out.println("\n\n\n");
          System.out.println("Victorias GROVER: "+groverWins);
          System.out.println("Victorias GRASP: "+graspWins);
          out1.close();
          out2.close();
          out3.close();

          //TODO- Medir la eficiencia
    }
}
