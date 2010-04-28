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

    private double[][] matrizHadamardInicial;

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

        this.matrizHadamardInicial=new double[2][2];
        this.matrizHadamardInicial[0][0]=INVERSA_RAIZ;
        this.matrizHadamardInicial[0][1]=INVERSA_RAIZ;
        this.matrizHadamardInicial[1][0]=INVERSA_RAIZ;
        this.matrizHadamardInicial[1][1]=INVERSA_RAIZ*(-1);
    }

    public double[][] crearTransfHadamard(EstadoCuantico[] vectorEstados, int N) {
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

//    private double[][] formaMatrizHadamard(double[][] matrizHadamardAnterior, int N) {
//
//        double[][] matrizHadamardActual= new double[N*2][N*2];
//
//        for (int i=0;i<N*2;i++){
//                for (int j=0;j<N*2;j++){
//                    if (i<N && j<N){
//                        matrizHadamardActual[i][j]=matrizHadamardAnterior[i][j]*INVERSA_RAIZ;
//                    }
//                    if (i>=N && j<N){
//                        matrizHadamardActual[i][j]=matrizHadamardAnterior[i-N][j]*INVERSA_RAIZ;
//                    }
//                    if (i<N && j>=N){
//                        matrizHadamardActual[i][j]=matrizHadamardAnterior[i][j-N]*INVERSA_RAIZ;
//                    }
//                    if (i>=N && j>=N){
//                        matrizHadamardActual[i][j]=matrizHadamardAnterior[i-N][j-N]*INVERSA_RAIZ*(-1);
//                    }
//                }
//        }
//        return matrizHadamardActual;
//    }

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

    private int productoPuntoBinario(int i, int j) {
        int suma=0;
        for (int k=0;k<N;k++){
            suma+= ((i & (int)(Math.pow(2,k)))>>k)*((j & (int) Math.pow(2,k))>>k);
        }
        return suma;
    }

}
