/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosdto;

import java.time.LocalDate;

/**
 *
 * @author alejandromontijo
 */
public class EvaluacionTecnicaDTO {

    private String resultado;
    private String motivoIncumplimiento;
    private LocalDate fechaEvaluacion;
    private EquipoDTO recomendado;
    private EquipoDTO alternativo;

    public EvaluacionTecnicaDTO() {
    }

    public EvaluacionTecnicaDTO(String resultado, String motivoIncumplimiento, LocalDate fechaEvaluacion, EquipoDTO recomendado, EquipoDTO alternativo) {
        this.resultado = resultado;
        this.motivoIncumplimiento = motivoIncumplimiento;
        this.fechaEvaluacion = fechaEvaluacion;
        this.recomendado = recomendado;
        this.alternativo = alternativo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMotivoIncumplimiento() {
        return motivoIncumplimiento;
    }

    public void setMotivoIncumplimiento(String motivoIncumplimiento) {
        this.motivoIncumplimiento = motivoIncumplimiento;
    }

    public LocalDate getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDate fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public EquipoDTO getRecomendado() {
        return recomendado;
    }

    public void setRecomendado(EquipoDTO recomendado) {
        this.recomendado = recomendado;
    }

    public EquipoDTO getAlternativo() {
        return alternativo;
    }

    public void setAlternativo(EquipoDTO alternativo) {
        this.alternativo = alternativo;
    }

}
