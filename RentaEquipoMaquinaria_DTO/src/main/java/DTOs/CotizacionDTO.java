/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alejandromontijo
 */
public class CotizacionDTO {

    private Long idCotizacion;
    private LocalDate fechaEmision;
    private LocalDate vigencia;
    private Double subtotal;
    private Double iva;
    private Double total;
    private List<DetalleCotizacionDTO> detalles;
    private DecisionClienteDTO decision;

    public CotizacionDTO() {
    }

    public CotizacionDTO(Long idCotizacion, LocalDate fechaEmision, LocalDate vigencia, Double subtotal, Double iva, Double total, List<DetalleCotizacionDTO> detalles, DecisionClienteDTO decision) {
        this.idCotizacion = idCotizacion;
        this.fechaEmision = fechaEmision;
        this.vigencia = vigencia;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
        this.detalles = detalles;
        this.decision = decision;
    }

    public Long getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getVigencia() {
        return vigencia;
    }

    public void setVigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<DetalleCotizacionDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCotizacionDTO> detalles) {
        this.detalles = detalles;
    }

    public DecisionClienteDTO getDecision() {
        return decision;
    }

    public void setDecision(DecisionClienteDTO decision) {
        this.decision = decision;
    }

}
