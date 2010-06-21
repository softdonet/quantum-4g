package com.quantum4g;

import com.quantum4g.algoritmos.grasp.AlgoritmoGRASP;
import com.quantum4g.algoritmos.grover.AlgoritmoGrover;
import com.quantum4g.core.entidades.Triada;
import com.quantum4g.experimentos.principal.Principal;

public class App 
{
    public static void main(String[] args) {
          double resultadoGrover;
          double resultadoGrasp;
          double groverWins=0;
          double graspWins=0;

          for (int i=0;i<100;i++)
          {
              Principal principal=new Principal(10);
              Triada[] triadaExperimento= principal.generaValoresTriadaGen(10);
              //Ejecucion GRASP
              AlgoritmoGRASP algoritmoGRASP=new AlgoritmoGRASP(10, triadaExperimento);
              resultadoGrasp=algoritmoGRASP.ejecucionGRASP();
              //Ejecucion Grover
              AlgoritmoGrover algoritmoGrover=new AlgoritmoGrover(10, principal.inicializaUniverso(triadaExperimento));
              resultadoGrover=algoritmoGrover.ejecucionGrover();

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
