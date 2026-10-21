/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rentaequipomaquinaria_dto;

/**
 *
 * @author alejandromontijo
 */
public class DetalleCotizacionDTO {

    private String tipoConcepto;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double importe;
    private EquipoDTO equipo;
    private AccesorioDTO accesorio;

    public DetalleCotizacionDTO() {
    }

    public DetalleCotizacionDTO(String tipoConcepto, String descripcion, Integer cantidad, Double precioUnitario, Double importe, EquipoDTO equipo, AccesorioDTO accesorio) {
        this.tipoConcepto = tipoConcepto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.importe = importe;
        this.equipo = equipo;
        this.accesorio = accesorio;
    }

    public String getTipoConcepto() {
        return tipoConcepto;
    }

    public void setTipoConcepto(String tipoConcepto) {
        this.tipoConcepto = tipoConcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public EquipoDTO getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoDTO equipo) {
        this.equipo = equipo;
    }

    public AccesorioDTO getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(AccesorioDTO accesorio) {
        this.accesorio = accesorio;
    }

}
