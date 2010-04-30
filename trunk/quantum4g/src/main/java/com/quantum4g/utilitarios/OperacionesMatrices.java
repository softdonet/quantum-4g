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

    private int N;

    private int totalElementos;
    
    private int[][] matrizIdentidad;

    private final double INVERSA_RAIZ=(1/Math.sqrt(2));

    public OperacionesMatrices(int N){
        this.N=N;
        this.totalElementos=(int) Math.pow(2, N);
        this.matrizIdentidad=new int[totalElementos][totalElementos];

        for (int i=0;i<this.totalElementos;i++){
            for (int j=0;j<this.totalElementos;j++){
                if (i==j){
                    this.matrizIdentidad[i][j]=1;
                }
                else{
                    this.matrizIdentidad[i][j]=0;
                }
            }
        }
    }

    public double[][] crearTransfHadamard(int N) {
        double[][] matrizHadamard=null;
        int tamMatriz=(int) Math.pow(2,N);
        matrizHadamard=new double[tamMatriz][tamMatriz];

          for (int i=0;i<N*2;i++){
                for (int j=0;j<N*2;j++){
                    matrizHadamard[i][j]=(1/(Math.pow(2,N/2)))*Math.pow(-1,productoPuntoBinario(i,j));
                }
          }

        return matrizHadamard;
    }

    private int productoPuntoBinario(int i, int j) {
        int suma=0;
        for (int k=0;k<N;k++){
            suma+= ((i & (int)(Math.pow(2,k)))>>k)*((j & (int) Math.pow(2,k))>>k);
        }
        return suma;
    }

    public EstadoCuantico[] productoMatrizVector(EstadoCuantico[][] matriz, EstadoCuantico[] vector) {
        int tamVector=vector.length;
        //EstadoCuantico[] resultadoProducto=new EstadoCuantico[tamVector];
        double valorParcial;
        for (int i=0;i<tamVector;i++){
            valorParcial=0;
            for (int j=0;j<tamVector;j++){
                valorParcial+=matriz[i][j].getValorMatriz()*vector[j].getValorMatriz();
            }
        }

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
}
