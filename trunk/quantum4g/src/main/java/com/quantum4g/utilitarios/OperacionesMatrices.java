/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.utilitarios;

import com.quantum4g.core.entidades.EstadoCuantico;

/**
 *
 * @author Pepe
 */
public class OperacionesMatrices {

    private int[][] matrizIdentidad;

    private EstadoCuantico[][] matrizHadamardInicial;

    public OperacionesMatrices(){
        
    }

    public EstadoCuantico[][] crearTransfHadamard(EstadoCuantico[] vectorEstados, int N) {
        EstadoCuantico[][] matrizHadamard=null;
        if (N==0){
            matrizHadamard=this.matrizHadamardInicial;
        }
        else{
            matrizHadamard=formaMatrizHadamard(crearTransfHadamard(vectorEstados,N),N-1);
        }
        return matrizHadamard;
    }

    public EstadoCuantico[] productoMatrizVector(EstadoCuantico[][] matrizGrover, EstadoCuantico[] estadosBaseOraculo) {
        return null;
    }

    public EstadoCuantico[] hallaConjugado(EstadoCuantico[] vectorEstados) {
        return null;
    }

    public EstadoCuantico[] productoVectorial(EstadoCuantico[] vectorEstados, EstadoCuantico[] estadoBaseConjugado) {
        return null;
    }

    public EstadoCuantico[][] producto(int i, EstadoCuantico[] vectorProducto) {
        return null;
    }

    public EstadoCuantico[][] sumar(EstadoCuantico[][] estadoCuantico, int[][] matrizIdentidad) {
        return null;
    }

    public int[][] getMatrizIdentidad() {
        return matrizIdentidad;
    }

    public void setMatrizIdentidad(int[][] matrizIdentidad) {
        this.matrizIdentidad = matrizIdentidad;
    }

    private EstadoCuantico[][] formaMatrizHadamard(EstadoCuantico[][] crearTransfHadamard, int i) {
        return null;
    }

}
