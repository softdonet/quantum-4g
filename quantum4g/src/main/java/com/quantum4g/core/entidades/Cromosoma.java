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

    //Cantidad de Genes
    private Integer N;

    private List<Gen> genes;

    public Cromosoma(){
        this.genes=new ArrayList<Gen>();
    }

    public double hallaSumaFactorPonderacion(){
        //TODO
        return 0;
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

    public void apagarGen(int indiceRCL) {
        this.genes.remove(indiceRCL);
    }


}
