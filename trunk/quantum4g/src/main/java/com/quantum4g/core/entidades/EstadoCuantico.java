/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.quantum4g.core.entidades;

/**
 *
 * @author Pepe
 */
public class EstadoCuantico {

    private Integer numeroEstado;

    private Double amplitudProbabilidad;

    private Double valorFitness;

    private Boolean conDesfaseXOraculo;

    private Boolean existe;

    private Double valorMatriz;

    public Integer getNumeroEstado() {
        return numeroEstado;
    }

    public void setNumeroEstado(Integer numeroEstado) {
        this.numeroEstado = numeroEstado;
    }

    public Double getAmplitudProbabilidad() {
        return amplitudProbabilidad;
    }

    public void setAmplitudProbabilidad(Double amplitudProbabilidad) {
        this.amplitudProbabilidad = amplitudProbabilidad;
    }

    public Double getValorFitness() {
        return valorFitness;
    }

    public void setValorFitness(Double valorFitness) {
        this.valorFitness = valorFitness;
    }

    public Boolean getConDesfaseXOraculo() {
        return conDesfaseXOraculo;
    }

    public void setConDesfaseXOraculo(Boolean conDesfaseXOraculo) {
        this.conDesfaseXOraculo = conDesfaseXOraculo;
    }

    public Boolean getExiste() {
        return existe;
    }

    public void setExiste(Boolean existe) {
        this.existe = existe;
    }

    public Double getValorMatriz() {
        return valorMatriz;
    }

    public void setValorMatriz(Double valorMatriz) {
        this.valorMatriz = valorMatriz;
    }

}
