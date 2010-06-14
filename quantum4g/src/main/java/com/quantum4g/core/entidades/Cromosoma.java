/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.core.entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antartec
 */
public class Cromosoma {

    private Double gradoBondadIndividuo;
    private double sumaFactorPonderacion;

    //Cantidad de Genes
    private Integer N;
    private List<Gen> genes;

    public Cromosoma(int N){
        this.N=N;
        this.genes=new ArrayList<Gen>(N);
    }

    public void inicializarGenes(){
        for (int i=0;i<N;i++){
           Gen gen=new Gen();
           gen.setNumeroGen(i);
           this.genes.add(gen);
        }
    }


    public double hallaFactorBondadIndividuo(){
        double suma=0;
        for (int i=0;i<genes.size();i++){
            if (genes.get(i).getValor()){
                suma+=genes.get(i).getGradoBondad();
            }
        }
        this.setGradoBondadIndividuo(suma);
        return suma;
    }

    public double hallaSumaFactorPonderacion(){
        double suma=0;
        for (int i=0;i<genes.size();i++){
            if (genes.get(i).getValor()){
                suma+=genes.get(i).getTriada().getFactorPonderacion();
            }
        }
        return suma;
    }


    public void activarGen(Gen genSeleccionado){
        if (!existeGen(genSeleccionado)){
            this.genes.add(genSeleccionado);
        }
    }
    public boolean existeGen(Gen genSeleccionado){
        boolean resultado=false;
        for (Gen gen:this.genes){
            if (gen.getNumeroGen().equals(genSeleccionado.getNumeroGen())){
                resultado=true;
            }
        }
        return resultado;
    }

    public Double getGradoBondadIndividuo() {
        return gradoBondadIndividuo;
    }

    public void setGradoBondadIndividuo(Double gradoBondadIndividuo) {
        this.gradoBondadIndividuo = gradoBondadIndividuo;
    }

    public List<Gen> getGenes() {
        return genes;
    }

    public void setGenes(List<Gen> genes) {
        this.genes = genes;
    }


    public void activarGen(Integer numeroGen,boolean valor) {
        for (Gen gen:this.genes){
            if (gen.getNumeroGen().equals(numeroGen)){
                gen.setValor(valor);
            }
        }
    }

    public double getSumaFactorPonderacion() {
        return sumaFactorPonderacion;
    }

    public void setSumaFactorPonderacion(double sumaFactorPonderacion) {
        this.sumaFactorPonderacion = sumaFactorPonderacion;
    }


}
