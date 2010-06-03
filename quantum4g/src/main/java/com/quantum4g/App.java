package com.quantum4g;

import com.quantum4g.algoritmos.grasp.AlgoritmoGRASP;
import com.quantum4g.algoritmos.grover.AlgoritmoGrover;
import com.quantum4g.experimentos.principal.Principal;

public class App 
{
    public static void main(String[] args) {

          Principal principal=new Principal(12);
          AlgoritmoGrover algoritmoGrover=new AlgoritmoGrover(12, principal.inicializaUniverso());
          algoritmoGrover.ejecucionGrover();

          AlgoritmoGRASP algoritmoGRASP=new AlgoritmoGRASP(12, principal.generaValoresTriadaGen(12));
          algoritmoGRASP.ejecucionGRASP();
          //        OperacionesMatrices operacion=new OperacionesMatrices(2);
//        double[][] matrizHadamard=operacion.crearTransfHadamard( 2);
//
//        for (int i=0;i<matrizHadamard.length;i++){
//            for (int j=0;j<matrizHadamard.length;j++){
//                System.out.print(matrizHadamard[i][j]+" ");
//            }
//            System.out.println();
//        }
    }
}
