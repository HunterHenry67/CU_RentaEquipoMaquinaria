/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.rentaequipomaquinaria_dto;

/**
 *
 * @author alejandromontijo
 */
public class ClienteEmpresaDTO {

    private ClienteDTO cliente;
    private String nombreEncargadoEmpresa;
    private String telefonoEncargadoEmpresa;
    private String puestoEncargadoEmpresa;

    public ClienteEmpresaDTO() {
    }

    public ClienteEmpresaDTO(ClienteDTO cliente, String nombreEncargadoEmpresa, String telefonoEncargadoEmpresa, String puestoEncargadoEmpresa) {
        this.cliente = cliente;
        this.nombreEncargadoEmpresa = nombreEncargadoEmpresa;
        this.telefonoEncargadoEmpresa = telefonoEncargadoEmpresa;
        this.puestoEncargadoEmpresa = puestoEncargadoEmpresa;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public String getNombreEncargadoEmpresa() {
        return nombreEncargadoEmpresa;
    }

    public void setNombreEncargadoEmpresa(String nombreEncargadoEmpresa) {
        this.nombreEncargadoEmpresa = nombreEncargadoEmpresa;
    }

    public String getTelefonoEncargadoEmpresa() {
        return telefonoEncargadoEmpresa;
    }

    public void setTelefonoEncargadoEmpresa(String telefonoEncargadoEmpresa) {
        this.telefonoEncargadoEmpresa = telefonoEncargadoEmpresa;
    }

    public String getPuestoEncargadoEmpresa() {
        return puestoEncargadoEmpresa;
    }

    public void setPuestoEncargadoEmpresa(String puestoEncargadoEmpresa) {
        this.puestoEncargadoEmpresa = puestoEncargadoEmpresa;
    }

}
