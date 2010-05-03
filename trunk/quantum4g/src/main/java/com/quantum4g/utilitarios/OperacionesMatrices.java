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

    public void productoMatrizVector(EstadoCuantico[][] matriz, EstadoCuantico[] vector) {

        int tamVector=vector.length;
        double[] listaValoresParciales= new double[tamVector];
        double valorParcial;
        for (int i=0;i<tamVector;i++){
            valorParcial=0;
            for (int j=0;j<tamVector;j++){
                valorParcial+=matriz[i][j].getValorMatriz()*vector[j].getValorMatriz();
            }
            listaValoresParciales[i]=valorParcial;
        }

        for (int i=0;i<vector.length;i++){
            vector[i].setValorMatriz(listaValoresParciales[i]);
            vector[i].setAmplitudProbabilidad(vector[i].getValorMatriz());
        }
    }

    public EstadoCuantico[] hallaDual(EstadoCuantico[] vectorEstados) {
        EstadoCuantico[] resultadoDual=new EstadoCuantico[vectorEstados.length];
        for (int i=0;i<vectorEstados.length;i++){
            resultadoDual[i]=new EstadoCuantico();
            //TODO, hallar el complejo conjugado
            resultadoDual[i]=vectorEstados[i];
        }
        return resultadoDual;
    }

    public EstadoCuantico[][] productoVectorial(EstadoCuantico[] vector1, EstadoCuantico[] vector2) {
        EstadoCuantico[][] producto;
        //Vector1 equivale al KET y Vector2 al BRA
        if (vector1.length==vector2.length){
            producto=new EstadoCuantico[vector1.length][vector2.length];
            for (int i=0;i<vector1.length;i++){
                for(int j=0;j<vector2.length;j++){
                    producto[i][j]=new EstadoCuantico();
                    producto[i][j].setAmplitudProbabilidad(vector1[i].getAmplitudProbabilidad()*vector2[j].getAmplitudProbabilidad());
                    producto[i][j].setValorMatriz(vector1[i].getValorMatriz()*vector2[j].getValorMatriz());
                }
            }
            
        }
        else{
            throw new UnsupportedOperationException("Operacion no Permitida");
        }       
        return producto;
    }

    public void productoConEscalar(int i, EstadoCuantico[][] vectorProducto) {
        for (int j=0;j<vectorProducto.length;j++){
            for (int k=0;k<vectorProducto.length;k++){
                vectorProducto[j][k].setAmplitudProbabilidad(vectorProducto[j][k].getAmplitudProbabilidad()*i);
                vectorProducto[j][k].setValorMatriz(vectorProducto[j][k].getValorMatriz()*i);
            }
        }
    }

    public EstadoCuantico[][] restar(EstadoCuantico[][] matriz1, int[][] matriz2) {

         int tamVector=matriz1.length;
         EstadoCuantico[][] resta = new EstadoCuantico[matriz1.length][matriz1[0].length];
         
         for (int i=0;i<tamVector;i++){
            for (int j=0;j<tamVector;j++){
                resta[i][j]=new EstadoCuantico();
                resta[i][j].setAmplitudProbabilidad(matriz1[i][j].getAmplitudProbabilidad()-matriz2[i][j]);
                resta[i][j].setValorMatriz(resta[i][j].getAmplitudProbabilidad());
            }
        }
       return resta;
    }

    public int[][] getMatrizIdentidad() {
        return matrizIdentidad;
    }

    public void setMatrizIdentidad(int[][] matrizIdentidad) {
        this.matrizIdentidad = matrizIdentidad;
    }

}
