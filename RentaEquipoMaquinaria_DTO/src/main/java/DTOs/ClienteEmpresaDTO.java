/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOs;

/**
 *
 * @author alejandromontijo
 */
public class ClienteEmpresaDTO extends ClienteDTO{
    
    private String nombreEncargadoEmpresa;
    private String telefonoEncargadoEmpresa;
    private String puestoEncargadoEmpresa;

    public ClienteEmpresaDTO() {
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
