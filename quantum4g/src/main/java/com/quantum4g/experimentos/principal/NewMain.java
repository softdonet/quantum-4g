/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.experimentos.principal;

import com.quantum4g.utilitarios.OperacionesMatrices;
import com.quantum4g.core.entidades.EstadoCuantico;
/**
 *
 * @author Pepe
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OperacionesMatrices operacion=new OperacionesMatrices(2);
        double[][] matrizHadamard=operacion.crearTransfHadamard(new  EstadoCuantico[1], 10);
        
        for (int i=0;i<matrizHadamard.length;i++){
            for (int j=0;j<matrizHadamard.length;j++){
                System.out.print(matrizHadamard[i][j]+" ");
            }
            System.out.println();
        }
    }

}
