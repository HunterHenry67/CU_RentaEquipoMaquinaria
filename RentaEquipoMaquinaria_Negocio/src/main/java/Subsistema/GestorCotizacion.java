/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subsistema;

/**
 *
 * @author BALAMRUSH
 */
import Excepciones.NegocioException;
import almacentemporal.IAlmacenTemporal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import objetosdto.AccesorioDTO;
import objetosdto.CotizacionDTO;
import objetosdto.DetalleCotizacionDTO;
import objetosdto.EquipoDTO;
import objetosdto.EquipoSolicitadoDTO;
import objetosdto.SolicitudRentaDTO;

public class GestorCotizacion {

    private IAlmacenTemporal almacenTemp;

    public GestorCotizacion(IAlmacenTemporal almacenTemp) {
        this.almacenTemp = almacenTemp;
    }

    public List<DetalleCotizacionDTO> calcularDetalle(SolicitudRentaDTO solicitud) throws NegocioException {

        if (solicitud == null || solicitud.getPeriodo() == null) {
            throw new NegocioException("La solicitud no es valida.");
        }

        if (solicitud.getEquiposSolicitados() == null || solicitud.getEquiposSolicitados().isEmpty()) {
            throw new NegocioException("La solicitud no tiene equipos.");
        }

        LocalDate inicio = solicitud.getPeriodo().getFechaInicio();
        LocalDate fin = solicitud.getPeriodo().getFechaFin();

        if (inicio == null || fin == null || fin.isBefore(inicio)) {
            throw new NegocioException("El periodo de renta no es valido.");
        }

        int dias = (int) ChronoUnit.DAYS.between(inicio, fin) + 1;

        List<DetalleCotizacionDTO> detalles = new ArrayList<>();

        for (EquipoSolicitadoDTO solicitado : solicitud.getEquiposSolicitados()) {

            if (solicitado == null || solicitado.getEvaluacion() == null || solicitado.getEvaluacion().getRecomendado() == null) {
                throw new NegocioException("Hay equipos sin evaluar.");
            }

            EquipoDTO equipo = solicitado.getEvaluacion().getRecomendado();

            if (equipo.getCostoDiario() == null) {
                throw new NegocioException("El equipo no tiene costo diario.");
            }

            int cantidad = 1;

            if (solicitado.getCantidad() != null && solicitado.getCantidad() > 0) {
                cantidad = solicitado.getCantidad();
            }

            double importe = equipo.getCostoDiario() * dias * cantidad;

            DetalleCotizacionDTO detalle = new DetalleCotizacionDTO("Equipo", equipo.getModelo(), dias * cantidad, equipo.getCostoDiario(), importe, equipo, null);

            detalles.add(detalle);

            if (equipo.getAccesorios() != null) {

                for (AccesorioDTO accesorio : equipo.getAccesorios()) {

                    if (accesorio == null) {
                        continue;
                    }

                    if (accesorio.getCostoRenta() == null) {
                        throw new NegocioException("El accesorio no tiene costo de renta.");
                    }

                    double costo = accesorio.getCostoRenta() * cantidad;

                    DetalleCotizacionDTO detalleAccesorio = new DetalleCotizacionDTO("Accesorio", accesorio.getNombre(), cantidad, accesorio.getCostoRenta(), costo, null, accesorio);

                    detalles.add(detalleAccesorio);
                }
            }
        }

        return detalles;
    }

    public double calcularTotal(List<DetalleCotizacionDTO> detalles) {

        double subtotal = 0;

        if (detalles == null) {
            return 0;
        }

        for (DetalleCotizacionDTO detalle : detalles) {

            if (detalle != null && detalle.getImporte() != null) {
                subtotal += detalle.getImporte();
            }
        }

        double iva = subtotal * 0.16;

        return subtotal + iva;
    }

    public CotizacionDTO guardar(String folio, CotizacionDTO cotizacion) throws NegocioException {

        if (folio == null || folio.isBlank()) {
            throw new NegocioException("El folio no es valido.");
        }

        if (cotizacion == null) {
            throw new NegocioException("La cotizacion no es valida.");
        }

        SolicitudRentaDTO solicitud = almacenTemp.buscarSolicitudPorFolio(folio);

        if (solicitud == null) {
            throw new NegocioException("La solicitud no existe.");
        }

        CotizacionDTO existente = almacenTemp.buscarCotizacionPorFolio(folio);

        if (existente != null) {
            throw new NegocioException("La solicitud ya tiene una cotizacion.");
        }

        cotizacion.setFolioSolicitud(folio);

        if (cotizacion.getFechaEmision() == null) {
            cotizacion.setFechaEmision(LocalDate.now());
        }

        return almacenTemp.registrarCotizacion(cotizacion);
    }

    public CotizacionDTO determinarCondiciones(EquipoDTO equipo, int diasRenta, String tipoSuelo) throws NegocioException {

        if (equipo == null) {
            throw new NegocioException("El equipo no es valido.");
        }

        if (diasRenta <= 0) {
            throw new NegocioException("Los dias de renta no son validos.");
        }

        if (tipoSuelo == null || tipoSuelo.isBlank()) {
            throw new NegocioException("Debe indicar el tipo de suelo.");
        }

        return null;
    }
}
