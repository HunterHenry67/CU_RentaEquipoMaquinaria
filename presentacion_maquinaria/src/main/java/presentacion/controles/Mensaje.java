package presentacion.controles;

/**
 * Los mensajes que ve el asesor. El Control de Aplicación solo dice cuál toca y el Coordinador de
 * Aplicación se lo pasa a la ventana que está a la vista.
 */
public enum Mensaje {

    // Inicio de sesión
    FALTAN_DATOS_SESION("Error de Inicio de Sesión", "Falta capturar: %s."),
    CORREO_SESION_INVALIDO("Error de Inicio de Sesión", "El correo no tiene un formato válido (por ejemplo julianmar12@gmail.com)."),
    CONTRASENIA_CORTA("Error de Inicio de Sesión", "La contraseña debe tener al menos 8 caracteres."),
    CREDENCIALES_INCORRECTAS("Error de Inicio de Sesión", "El correo o la contraseña no corresponden a ningún usuario registrado."),
    SESION_INICIADA("Inicio de Sesión", "Inicio de Sesión Exitoso."),

    // Clientes
    TELEFONO_BUSQUEDA_INVALIDO("Búsqueda de Cliente", "Para buscar por teléfono escribe solo números."),
    CLIENTE_NO_ENCONTRADO("Búsqueda de Cliente", "No se encontró ningún cliente con ese dato. Si es nuevo, regístrelo."),
    CLIENTE_SIN_SELECCIONAR("Falla de Registro de Solicitud", "Favor de seleccionar un cliente para realizar el registro de solicitud."),
    CLIENTE_A_MODIFICAR_SIN_SELECCIONAR("Modificar Datos", "Favor de seleccionar un cliente para modificar."),
    FALTAN_DATOS_CLIENTE("Error de Registro", "Falta capturar: %s."),
    FALTA_IDENTIFICACION_ENCARGADO("Error de Registro", "Selecciona el archivo con la identificación del encargado."),
    RFC_INVALIDO("Error de Registro", "El RFC no tiene un formato válido (por ejemplo MARJ8503158A1)."),
    TELEFONO_INVALIDO("Error de Registro", "El teléfono debe tener 10 dígitos."),
    CORREO_CLIENTE_INVALIDO("Error de Registro", "El correo electrónico no tiene un formato válido (por ejemplo julianmar12@gmail.com)."),
    TELEFONO_ENCARGADO_INVALIDO("Error de Registro", "El teléfono del encargado debe tener 10 dígitos."),
    RFC_REPETIDO("Error de Registro", "Ya hay un cliente registrado con ese RFC."),
    EMPRESA_REGISTRADA("Cliente Registrado", "La Empresa ha sido registrada correctamente."),
    CLIENTE_REGISTRADO("Cliente Registrado", "El Cliente ha sido registrado correctamente."),
    EMPRESA_ACTUALIZADA("Cliente Registrado", "Los datos de la Empresa se actualizaron."),
    CLIENTE_ACTUALIZADO("Cliente Registrado", "Los datos del Cliente se actualizaron."),

    // Solicitud
    EQUIPO_NO_ENCONTRADO("Búsqueda", "No hay equipos en el catálogo con ese nombre."),
    EQUIPO_SIN_SELECCIONAR("Advertencia", "Por favor, seleccione un equipo de la tabla."),
    FALTAN_DATOS_SOLICITUD("Error de Solicitud", "Falta capturar: %s."),
    FECHAS_SOLICITUD_INVALIDAS("Error de Solicitud", "La fecha final no puede ser anterior a la de inicio."),
    FALTA_EQUIPO_SOLICITUD("Error de Solicitud", "Favor de agregar el equipo que solicita el cliente."),
    SOLICITUD_REGISTRADA("Solicitud Renta", "La Solicitud de Renta ha sido Registrada Correctamente."),
    SOLICITUD_REGISTRADA_VARIOS_EQUIPOS("Solicitud Renta", "La Solicitud de Renta ha sido Registrada Correctamente con %s equipos.\nEn la evaluación técnica se revisan todos y se elige un equipo recomendado y una alternativa."),
    CONFIRMAR_REGRESAR_SOLICITUD("Solicitud Renta", "¿Regresar a Identificación de cliente?\nLo que no se haya registrado de esta solicitud se pierde."),
    CONFIRMAR_CANCELAR_SOLICITUD("Cancelación Solicitud", "¿Realmente desea cancelar la solicitud?"),
    FALTA_RAZON_CANCELACION("Error en el Registro de Cancelación", "Favor de describir la razón de su cancelación."),
    CONFIRMAR_REGISTRAR_CANCELACION("Registrar Cancelación", "¿Está seguro de que desea registrar la cancelación?"),
    CANCELACION_NO_PERMITIDA("Registrar Cancelación", "La solicitud no se puede cancelar: %s."),
    CANCELACION_REGISTRADA("Solicitud Renta", "La cancelación de la solicitud ha sido registrada."),

    // Equipo adecuado
    FALTA_RESPUESTA_CLIENTE("Solicitud de Datos Faltantes", "Captura por lo menos un dato de la respuesta del cliente."),
    NUMERO_INVALIDO_DATOS_FALTANTES("Solicitud de Datos Faltantes", "Escribe un número en «%s» (en %s)."),
    NUMERO_INVALIDO_ANALISIS("Análisis de Requerimiento Técnico", "Escribe un número en «%s» (en %s)."),
    FALTAN_DATOS_ANALISIS("Análisis de Requerimiento Técnico", "Falta capturar: %s.\nSi no los tienes, usa «Solicitar datos al cliente»."),
    SIN_DATOS_FALTANTES("Análisis de Requerimiento Técnico", "Ya están todos los datos. Puedes buscar los equipos compatibles."),
    CONFIRMAR_NINGUNO_CUMPLE("Equipos Compatibles", "Con lo capturado, el sistema marcó equipos que sí cumplen.\n¿Registrar de todos modos que ninguno cumple?"),
    FALTA_MOTIVO_EVALUACION("Sin Equipo Compatible", "Escribe el motivo antes de cerrar la evaluación."),
    EVALUACION_CERRADA("Sin Equipo Compatible", "Evaluación cerrada. La solicitud %s quedó como sin solución técnica."),
    SIN_EQUIPO_COMPATIBLE("Equipos Compatibles", "Ningún equipo cumple con lo requerido. Usa «Ninguno cumple»."),
    FALTA_ELEGIR_RECOMENDADO("Equipos Compatibles", "Marca en la tabla el equipo que vas a recomendar."),
    EQUIPOS_IGUALES("Equipos Compatibles", "El equipo recomendado y la alternativa tienen que ser distintos."),
    FALTA_RECOMENDADO("Selección de Accesorios", "Primero hay que elegir el equipo recomendado."),
    EVALUACION_REGISTRADA("Evaluación Técnica", "Evaluación técnica registrada con éxito.\nSolicitud enviada a verificación de inventario."),

    // Disponibilidad
    UNIDAD_SIN_SELECCIONAR("Verificación de Inventario", "Selecciona una unidad de la tabla."),
    UNIDAD_NO_DISPONIBLE("Verificación de Inventario", "Solo puede seleccionarse una unidad disponible durante todo el periodo."),
    UNIDAD_DISPONIBLE("Verificación de Inventario", "La unidad está disponible: se puede seleccionar."),
    EQUIPO_SIN_UNIDADES("Verificación de Inventario", "El equipo %s no tiene unidades registradas."),
    SIN_ALTERNATIVA("Usar la alternativa", "No se eligió un equipo alternativo; se abre Equipos compatibles para elegir otro."),
    FALTAN_FECHAS_PERIODO("Cambiar periodo", "Selecciona la fecha de inicio y la de fin."),
    FECHAS_PERIODO_INVALIDAS("Cambiar periodo", "La fecha de fin no puede ser anterior a la de inicio."),

    // Cotización, requisitos y decisión del cliente
    FALTA_EQUIPO_Y_PERIODO("Cotización", "Primero hay que elegir el equipo recomendado y el periodo."),
    EQUIPO_SIN_TARIFA("Cotización", "El equipo %s no tiene tarifa registrada: no se puede cotizar con él."),
    REQUISITOS_PENDIENTES("Requisitos de la renta", "Hay requisitos obligatorios pendientes: la renta no puede continuar."),
    REQUISITOS_VALIDADOS("Requisitos de la renta", "Requisitos validados con éxito.\nLa renta puede continuar a su confirmación."),
    FALTA_MOTIVO_RECHAZO("Motivo del rechazo", "Favor de escribir el motivo del rechazo."),
    UNIDAD_YA_NO_LIBRE("Verificación de condiciones", "La unidad elegida ya no está libre en el periodo: elige otra."),
    FALTA_CONDICION("Verificación de condiciones", "Falta revisar: %s. Marca todas las condiciones para generar la orden."),
    ORDEN_REGISTRADA("Orden de Entrega", "La orden de entrega del folio %s quedó registrada."),

    // Consulta por folio
    FALTA_FOLIO("Consulta por Folio", "Falta capturar: Folio."),
    FOLIO_NO_ENCONTRADO("Consulta por Folio", "No se encontró ninguna solicitud con el folio %s."),
    SOLICITUD_SIN_ORDEN("Consulta por Folio", "La solicitud %s no tiene orden de entrega. Estado: %s."),

    // Generales
    FALTA_SOLICITUD("Contratación de equipo", "Primero hay que registrar la solicitud de renta."),
    CONFIRMAR_DEJAR_OPERACION("Contratación de equipo", "¿Dejar la operación y volver al menú principal?");

    private final String titulo;
    private final String texto;

    Mensaje(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    // El texto con sus datos en lugar de cada %s
    public String getTexto(Object... datos) {
        return datos.length == 0 ? texto : String.format(texto, datos);
    }
}
