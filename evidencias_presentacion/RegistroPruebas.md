# Registro de pruebas de la capa de presentación

Equipo Maquinaria. Caso de uso: Gestionar contratación y orden de entrega de equipo en renta.

Corrida del 04/11/2026, sobre el commit `f17373a` con las correcciones de abajo. Resultado: **16 de 16 casos aprobados, 213 revisiones bien y 0 mal.**

## Cómo se prueba

La clase `presentacion_maquinaria/src/test/java/presentacion/PruebasPresentacion.java` arma la aplicación como `Main`, abre las pantallas reales, escribe en sus campos y oprime sus botones desde el programa. Los avisos se leen y se contestan con el botón que aparece. Cada caso empieza con la aplicación recién armada.

En NetBeans: clic derecho sobre la clase y *Run File*. Abre ventanas, por eso no corre con cada *Clean and Build*. El detalle de cada revisión está en [`resultados.txt`](resultados.txt).

Las capturas las saca la misma prueba de lo que pinta cada ventana; la barra de título la dibuja la prueba. No sustituyen la demostración en vivo.

## Correcciones de la revisión del 31/10

| Indicador | Lo que se observó | Lo que se hizo | Dónde se comprueba |
|---|---|---|---|
| 2.2 | Los mensajes estaban escritos dentro del Control | El Control solo dice cuál toca (`Mensaje`); el Coordinador se lo pasa a la ventana a la vista, también en las confirmaciones | P04, P07 |
| 2.1 | `RequisitosRentaFrame` cambiaba el estado del requisito | El estado lo cambia el Control con `cambiarEstadoRequisitos`; la pantalla avisa y redibuja. Las pantallas ya no cambian los DTO que les da el Control: mandan lo capturado y el Control lo guarda después de validar | P07, P13, P14 |
| 3.2 | No se vio cancelar ni cerrar | Cancelar registra la razón. Hay Regresar en la razón de cancelación, Análisis, Equipos compatibles, Evaluación técnica, Propuesta, Accesorio indispensable y Verificación; la orden tiene Terminar y Cerrar Sesión deja vacío el inicio | P02, P03, P04, P16 |
| 3.3 | Faltaba regresar y cambiar periodo sin perder datos | Probado. Al modificar la solicitud se conservan los requerimientos y al cambiar accesorios se rehace la cotización | P03, P13 |
| 3.4 | Sin prueba de doble clic, cancelaciones seguidas y reapertura | Probado; dos clics en Registrar ya no crean otra solicitud | P04, P05 |
| 5.4 | Faltaba cliente, equipo y folio inexistentes en pantalla | Consulta de orden por folio en el menú; avisos sin excepción | P10 |
| 6.1, 6.4, 8.2 | Faltaba ver los errores y su corrección | Los avisos dicen qué dato falta; los demás campos se conservan | P11, P12, P13 |
| 7.1 | Faltaba la orden final y recuperarla por folio | Consulta por folio en el menú | P09, P16 |
| 7.2, 7.3 | Faltaban alternativas y la secuencia completa | Verificación de condiciones otra vez en el flujo; alternativas probadas | P02, P14, P16 |
| 8.1 | Confirmaciones del sistema en inglés | Botones Sí, No y Aceptar; calendario y selector de archivos en español | P04, P15 |
| 8.3 | No se vio la confirmación de la orden | Aviso al registrar la orden | P15 |
| 10.1 | Compilar e iniciar | `clean install` de `sistema_maquinaria` y arranque con Maven | P01 |
| 10.2 | Las pruebas no eran del equipo | La clase de pruebas y este registro quedan en el repositorio | Todos |

## Dónde se comprueba cada indicador

| Indicador | Clase o método | Caso |
|---|---|---|
| 1.1 | `pom.xml` de `presentacion_maquinaria`, `objetosdto_maquinaria`, `almacentemporal_maquinaria` y `sistema_maquinaria` | P01 |
| 1.2 | Paquetes `presentacion.vistas`, `presentacion.control`, `presentacion.simulacion` y `presentacion.mensajes` | P07 |
| 1.3 | `ContratacionRenta`, `CoordinadorAplicacion`, `AlmacenTemporal`, `ValidadorPresentacion` | P07 |
| 2.1 | `RequisitosRentaFrame.btnMarcarPendienteActionPerformed`, `ContratacionRenta.cambiarEstadoRequisitos`, `VerificacionInventarioFrame.btnSeleccionarActionPerformed` | P07, P13, P14 |
| 2.2 | `Main.iniciar`, `ContratacionRenta.registrarSolicitud`, `CoordinadorAplicacion.mostrar` | P02, P07 |
| 2.3 | `ContratacionRenta.guardarSolicitud`, `IAlmacenTemporal` | P07, P08 |
| 2.4 | `NegocioSimulado`, `ValidadorPresentacion`, `Mensaje` | P07 |
| 3.1 | `SolicitudRentaFrame.btnAgregarEquipoActionPerformed`, `CoordinadorAplicacion.mostrar` | P02, P07 |
| 3.2 | `EquiposCompatiblesFrame.btnRegresarActionPerformed`, `OrdenEntregaFrame.btnTerminarActionPerformed`, `Ventanas.cerrarCon`, `ContratacionRenta.pasarDatosCancelacion` | P02, P03, P04 |
| 3.3 | `SolicitudRentaFrame.mostrarCaptura`, `ContratacionRenta.conservarLoCapturado`, `AnalisisRequerimientoFrame.cargarSolicitud` | P03, P13 |
| 3.4 | `CoordinadorAplicacion.mostrar`, `ContratacionRenta.guardarSolicitud` | P04, P05 |
| 4.1 | Clases del paquete `objetosdto` | P06 |
| 4.2 | `SolicitudRentaFrame.btnRegistrarActionPerformed`, `RegistroClienteFrame.btnRegistrarActionPerformed` | P06 |
| 4.3 | `AlmacenTemporal.registrarSolicitud`, `ContratacionRenta.pasarDatosDecisionCliente` | P08, P09 |
| 4.4 | `Main.iniciar`, `CoordinadorAplicacion.mostrarCotizacion`, `CotizacionFrame.cargarCotizacion` | P07 |
| 5.1 | `AlmacenTemporal` | P08 |
| 5.2 | `IAlmacenTemporal` | P08, P09 |
| 5.3 | `Main.iniciar`, `AlmacenTemporal.buscarContratacionPorFolio` | P09 |
| 5.4 | `ContratacionRenta.consultarOrdenPorFolio`, `ContratacionRenta.buscarClientes`, `ContratacionRenta.buscarEquipos` | P10 |
| 6.1 | `ValidadorPresentacion.faltantesCliente`, `ContratacionRenta.faltanDatos` | P11 |
| 6.2 | `ValidadorPresentacion.correoValido`, `ValidadorPresentacion.rfcValido`, `ValidadorPresentacion.telefonoValido`, `ValidadorPresentacion.esPeriodoValido`, `RequerimientosTecnicos.normalizar` | P12 |
| 6.3 | `ValidadorPresentacion`, `ContratacionRenta.registrarCliente` | P07, P11 |
| 6.4 | `ContratacionRenta.requerimientosValidos`, `ContratacionRenta.registrarCliente` | P13 |
| 7.1 | Flujo completo | P16 |
| 7.2 | `ContratacionRenta.pasarDatosCancelacion`, `ContratacionRenta.usarEquipoAlternativo`, `ContratacionRenta.ningunEquipoCumple` | P04, P10, P14 |
| 7.3 | `ContratacionRenta.pasarDatosDecisionCliente`, `ContratacionRenta.pasarDatosOrdenEntrega` | P02, P16 |
| 8.1 | Capturas; botones Sí, No y Aceptar en `CoordinadorAplicacion` | P04, P15 |
| 8.2 | `Mensaje` | P11, P12 |
| 8.3 | `Mensaje`, `CoordinadorAplicacion.mostrarMensaje` | P15 |
| 9.1 a 9.3 | La hoja dice "No aplica en esta revisión" | Sin evaluar |
| 10.1 | `README.md`; `clean install` de `sistema_maquinaria` | P01 |
| 10.2 | `PruebasPresentacion` y este registro | Todos |
| 10.3 | `README.md` y este registro | Todos |

## Casos

### P01. Inicio del sistema (C10)

- **Datos:** Crear todas las ventanas como Main y abrir la inicial.
- **Esperado:** La aplicación inicia sin errores que bloqueen el uso.
- **Obtenido:** Abre el inicio de sesión con las 25 pantallas creadas y sin excepciones. Con Maven, `clean install` en `sistema_maquinaria` termina en BUILD SUCCESS y la aplicación arranca con `exec`.
- **Estado:** Aprobado (3 revisiones bien, 0 mal)
- **Evidencia:** [P01_inicio](capturas/P01_inicio.png)

### P02. Avance de pantalla (C3)

- **Datos:** Botones del flujo principal, desde el inicio de sesión hasta la orden.
- **Esperado:** Se muestra la vista prevista sin abrir pantallas incorrectas.
- **Obtenido:** Cada botón del flujo principal abrió la pantalla que sigue y solo esa, desde el inicio de sesión hasta la orden de entrega.
- **Estado:** Aprobado (18 revisiones bien, 0 mal)
- **Evidencia:** `resultados.txt`

### P03. Regresar (C3)

- **Datos:** Avanzar y regresar en cada pantalla que tiene Regresar; cambiar el periodo, el equipo y los accesorios desde la propuesta.
- **Esperado:** Regresa a la pantalla esperada y conserva datos necesarios.
- **Obtenido:** Cada Regresar volvió a su pantalla. La solicitud y el análisis conservaron lo capturado. Al cambiar el periodo a 4 días la cotización se rehízo y siguió con el mismo cliente. Al cambiar a la CAT 336 conservó cliente y periodo, y el requisito pendiente se conservó al regresar. Modificar la solicitud no creó otra y conservó los requerimientos. Sin el opcional, condiciones, cotización y propuesta quedaron en $10,788.
- **Estado:** Aprobado (34 revisiones bien, 0 mal)
- **Evidencia:** [P03_datos_faltantes](capturas/P03_datos_faltantes.png), [P03_motivo_del_rechazo](capturas/P03_motivo_del_rechazo.png), [P03_cambiar_periodo](capturas/P03_cambiar_periodo.png), [P03_cambio_de_periodo](capturas/P03_cambio_de_periodo.png), [P03_propuesta_modificada](capturas/P03_propuesta_modificada.png)

### P04. Cancelar operación (C3/C7)

- **Datos:** Capturar la solicitud y usar Cancelar: primero No, luego Sí, razón vacía, Regresar y razón "El cliente ya no requiere el equipo", contestando primero No y luego Sí al registrar.
- **Esperado:** Se cancela el flujo de forma controlada; no hay registros involuntarios.
- **Obtenido:** Pide confirmar al cancelar y al registrar. Con No sigue en la solicitud con sus datos. Sin razón avisa "Favor de describir la razón de su cancelación." y no registra nada. Con razón y No, sigue en la pantalla sin registrar. Regresar vuelve a la captura. Con razón avisa "La cancelación de la solicitud ha sido registrada.", vuelve al menú y queda una sola solicitud, Cancelada y con su razón, sin contratación. Una segunda cancelación seguida también funcionó.
- **Estado:** Aprobado (17 revisiones bien, 0 mal)
- **Evidencia:** [P04_confirmacion](capturas/P04_confirmacion.png), [P04_razon_de_cancelacion](capturas/P04_razon_de_cancelacion.png), [P04_razon_vacia](capturas/P04_razon_vacia.png), [P04_cancelacion_registrada](capturas/P04_cancelacion_registrada.png)

### P05. Evitar duplicados (C3)

- **Datos:** Oprimir dos veces seguidas los botones que abren una pantalla y Registrar de la solicitud.
- **Esperado:** No se crean ventanas o flujos duplicados inesperadamente.
- **Obtenido:** Dos clics seguidos dejaron una sola pantalla visible y las mismas 25 ventanas. El mismo equipo no se repite y dos clics en Registrar dejan una sola solicitud. Al repetir la contratación, la segunda orden (CAT 336, N-001) no se mezcla con la primera (CAT 320, M-001).
- **Estado:** Aprobado (11 revisiones bien, 0 mal)
- **Evidencia:** `resultados.txt`

### P06. Captura en DTO (C4)

- **Datos:** Registro de cliente con datos válidos y solicitud de renta con los del escenario.
- **Esperado:** Los valores se integran correctamente al DTO correspondiente.
- **Obtenido:** `ClienteDTO`, `ClienteEmpresaDTO` y `SolicitudRentaDTO` traen lo capturado: nombre, RFC en mayúsculas, teléfono, correo, encargado, obra, dirección, actividad, periodo de 6 días y equipo.
- **Estado:** Aprobado (6 revisiones bien, 0 mal)
- **Evidencia:** `resultados.txt`

### P07. Intercambio de datos sin acoplar vistas (C2/C4)

- **Datos:** Revisar los atributos y constructores de las 25 pantallas, del Control y del Coordinador.
- **Esperado:** Las vistas no dependen directamente unas de otras ni transportan el estado del proceso mediante constructores.
- **Obtenido:** Cada pantalla recibe solo `IContratacionRenta`, no guarda otra pantalla, ni el almacén, ni el coordinador, y la interfaz del Control no se lo entrega. Ninguna pantalla cambia los DTO que le da el Control. El Control no tiene ventanas ni mensajes escritos, y el Coordinador no usa el almacén ni el Control.
- **Estado:** Aprobado (10 revisiones bien, 0 mal)
- **Evidencia:** `resultados.txt`

### P08. Registrar temporalmente (C5)

- **Datos:** Registrar un cliente y una solicitud desde las pantallas.
- **Esperado:** El dato queda guardado en memoria y puede consultarse.
- **Obtenido:** El almacén tiene un cliente más, con id, y se encuentra por RFC.
- **Estado:** Aprobado (3 revisiones bien, 0 mal)
- **Evidencia:** `resultados.txt`

### P09. Recuperar información (C3/C5)

- **Datos:** Registrar cliente, volver al menú y buscarlo; completar una contratación y consultarla por folio.
- **Esperado:** Se recupera la misma información mientras la aplicación siga abierta.
- **Obtenido:** El cliente registrado se encontró al volver del menú con el mismo nombre y teléfono. La orden se recuperó por folio con el mismo total ($11,020) y la misma unidad (M-001).
- **Estado:** Aprobado (7 revisiones bien, 0 mal)
- **Evidencia:** [P09_cliente_recuperado](capturas/P09_cliente_recuperado.png), [P09_orden_recuperada_por_folio](capturas/P09_orden_recuperada_por_folio.png)

### P10. Dato no encontrado (C5/C7)

- **Datos:** Buscar cliente "ZZZ", equipo "XYZ" y folio "FOL-1"; consultar una solicitud sin orden.
- **Esperado:** Se informa sin bloquear ni provocar excepciones.
- **Obtenido:** Folio: "No se encontró ninguna solicitud con el folio FOL-1." Cliente: "No se encontró ningún cliente con ese dato. Si es nuevo, regístrelo." Equipo: "No hay equipos en el catálogo con ese nombre." Solicitud cancelada: "La solicitud FOL-89241 no tiene orden de entrega. Estado: Cancelada." Ninguna excepción y sigue en la misma pantalla.
- **Estado:** Aprobado (9 revisiones bien, 0 mal)
- **Evidencia:** [P10_folio_no_encontrado](capturas/P10_folio_no_encontrado.png), [P10_cliente_no_encontrado](capturas/P10_cliente_no_encontrado.png), [P10_equipo_no_encontrado](capturas/P10_equipo_no_encontrado.png), [P10_solicitud_sin_orden](capturas/P10_solicitud_sin_orden.png)

### P11. Campos obligatorios vacíos (C6)

- **Datos:** Inicio de sesión, registro de cliente y de empresa, solicitud, cancelación, datos faltantes y motivo de rechazo, vacíos y con solo espacios.
- **Esperado:** La operación se detiene y se informa qué dato falta.
- **Obtenido:** Se detiene y dice qué falta. Por ejemplo: "Falta capturar: Nombre, Correo electrónico, Teléfono, RFC." y, con los demás llenos, "Falta capturar: RFC." Lo mismo con solo espacios. También avisa sin equipo, sin unidad, sin motivo de rechazo y con condiciones sin marcar.
- **Estado:** Aprobado (14 revisiones bien, 0 mal)
- **Evidencia:** [P11_inicio_sesion_vacio](capturas/P11_inicio_sesion_vacio.png), [P11_cliente_vacio](capturas/P11_cliente_vacio.png), [P11_solicitud_vacia](capturas/P11_solicitud_vacia.png)

### P12. Formato incorrecto (C6)

- **Datos:** Correo "asesor@", contraseña "123", RFC "ABC", teléfono "644abc", profundidad "abc" y fecha final antes de la inicial.
- **Esperado:** Se muestra un error comprensible y no se procesa el dato inválido.
- **Obtenido:** Correo, contraseña, RFC (con ejemplo), teléfono, RFC repetido, número en profundidad, periodo sin fechas y fecha final antes de la inicial muestran su mensaje; no se registró ningún dato inválido.
- **Estado:** Aprobado (14 revisiones bien, 0 mal)
- **Evidencia:** [P12_correo_invalido](capturas/P12_correo_invalido.png), [P12_rfc_invalido](capturas/P12_rfc_invalido.png), [P12_numero_invalido](capturas/P12_numero_invalido.png), [P12_fecha_invalida](capturas/P12_fecha_invalida.png)

### P13. Corrección tras error (C6)

- **Datos:** RFC inválido en el registro y obra vacía en la solicitud; corregir solo ese campo y continuar.
- **Esperado:** Permite corregir y continuar, sin perder datos válidos innecesariamente.
- **Obtenido:** Después del error sigue en la misma pantalla con los demás campos llenos. Al corregir solo el campo mal, continúa. El dato incorrecto no llega a la solicitud del Control y seleccionar sin fila no borra la unidad ya elegida.
- **Estado:** Aprobado (11 revisiones bien, 0 mal)
- **Evidencia:** [P13_cliente_tras_error](capturas/P13_cliente_tras_error.png), [P13_solicitud_tras_error](capturas/P13_solicitud_tras_error.png)

### P14. Flujo alternativo (C7)

- **Datos:** Unidad en mantenimiento y ocupada, usar la alternativa, ninguno cumple, requisito pendiente, aceptación parcial, sin indispensable, no generar orden y rechazo.
- **Esperado:** Se sigue el comportamiento alternativo según el diseño.
- **Obtenido:** Funcionaron: unidad en mantenimiento y ocupada, usar la alternativa (CAT 336), ninguno cumple, ajustar y cerrar la evaluación, requisito pendiente y Reintentar, aceptación parcial, no generar orden (la unidad no se aparta), sin el indispensable y rechazo con motivo.
- **Estado:** Aprobado (18 revisiones bien, 0 mal)
- **Evidencia:** [P14_unidad_en_mantenimiento](capturas/P14_unidad_en_mantenimiento.png), [P14_unidad_ocupada](capturas/P14_unidad_ocupada.png), [P14_inventario_con_la_alternativa](capturas/P14_inventario_con_la_alternativa.png), [P14_sin_equipo_compatible](capturas/P14_sin_equipo_compatible.png), [P14_requisito_marcado_pendiente](capturas/P14_requisito_marcado_pendiente.png), [P14_requisito_pendiente](capturas/P14_requisito_pendiente.png), [P14_propuesta_pendiente](capturas/P14_propuesta_pendiente.png), [P14_solicitud_pendiente_por_condicion](capturas/P14_solicitud_pendiente_por_condicion.png), [P14_solicitud_pendiente_sin_indispensable](capturas/P14_solicitud_pendiente_sin_indispensable.png), [P14_rechazo](capturas/P14_rechazo.png)

### P15. Mensaje de resultado (C8)

- **Datos:** Acciones válidas: entrar, registrar cliente, registrar solicitud, generar la orden.
- **Esperado:** Se muestra confirmación, resultado o advertencia adecuada.
- **Obtenido:** Salen los mensajes de inicio de sesión, cliente, solicitud, evaluación, requisitos y "La orden de entrega del folio FOL-89241 quedó registrada." Los botones dicen Sí, No y Aceptar.
- **Estado:** Aprobado (9 revisiones bien, 0 mal)
- **Evidencia:** [P15_inicio_de_sesion](capturas/P15_inicio_de_sesion.png), [P15_solicitud_registrada](capturas/P15_solicitud_registrada.png), [P15_orden_registrada](capturas/P15_orden_registrada.png)

### P16. Flujo completo (C7/C10)

- **Datos:** Contratación del escenario de principio a fin: Julián Martínez, CAT 320, M-001, del 10 al 15 de septiembre. Después, otra con otra clienta, otro equipo y otro periodo.
- **Esperado:** El recorrido concluye sin fallas y respeta el orden establecido.
- **Obtenido:** Del inicio de sesión al cierre: folio FOL-89241, CAT 320, M-001, total $11,020, entrega 10/09/2026 antes de las 8:00 AM en Av. Guerrero #1105. La contratación queda FORMALIZADA, la unidad apartada y la solicitud Formalizada. Con otra clienta, otro equipo y otro periodo ninguna pantalla mostró datos fijos del escenario. Entre todos los casos se abrieron las 25 pantallas y ninguna etiqueta se cortó.
- **Estado:** Aprobado (29 revisiones bien, 0 mal)
- **Evidencia:** [P16_01_identificacion](capturas/P16_01_identificacion.png), [P16_02_busqueda_equipo](capturas/P16_02_busqueda_equipo.png), [P16_03_solicitud](capturas/P16_03_solicitud.png), [P16_04_analisis](capturas/P16_04_analisis.png), [P16_05_equipos_compatibles](capturas/P16_05_equipos_compatibles.png), [P16_06_accesorios](capturas/P16_06_accesorios.png), [P16_07_evaluacion_tecnica](capturas/P16_07_evaluacion_tecnica.png), [P16_08_inventario](capturas/P16_08_inventario.png), [P16_09_calendario](capturas/P16_09_calendario.png), [P16_10_condiciones](capturas/P16_10_condiciones.png), [P16_11_cotizacion](capturas/P16_11_cotizacion.png), [P16_12_requisitos](capturas/P16_12_requisitos.png), [P16_13_propuesta](capturas/P16_13_propuesta.png), [P16_14_verificacion](capturas/P16_14_verificacion.png), [P16_15_orden_de_entrega](capturas/P16_15_orden_de_entrega.png), [P16_16_otro_cliente](capturas/P16_16_otro_cliente.png)
