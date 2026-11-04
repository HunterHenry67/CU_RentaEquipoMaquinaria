# Renta de Equipo y Maquinaria: capa de presentación

Proyecto de Diseño de Software, equipo Maquinaria.
Caso de uso: gestionar contratación y orden de entrega de equipo en renta.

## Proyectos

| Proyecto | Qué contiene |
|---|---|
| `sistema_maquinaria` | Proyecto POM que agrupa los módulos |
| `presentacion_maquinaria` | Pantallas, Control y Coordinador de Aplicación (paquete `presentacion`) |
| `objetosdto_maquinaria` | Los DTO (paquete `objetosdto`) |
| `almacentemporal_maquinaria` | Información en memoria mientras la aplicación está abierta (paquete `almacentemporal`) |

Los módulos `RentaEquipoMaquinaria_*` están reservados para las siguientes capas.

## Cómo correrlo en NetBeans

Se necesita Java 21.

1. Abrir `sistema_maquinaria` con sus proyectos requeridos.
2. Hacer *Clean and Build* en `sistema_maquinaria`.
3. Hacer *Run* en `presentacion_maquinaria`. La clase inicial es `presentacion.Main`.

Para entrar sirve cualquier correo con formato válido y una contraseña de 8 caracteres o más.

## Cómo está organizada

Las operaciones siguen este camino: **Vista → Control de Aplicación → Coordinador de Aplicación → Vista**.

| Parte | Clase | Responsabilidad |
|---|---|---|
| Vistas | `presentacion.vistas.*Frame` | Muestran y capturan datos y le avisan al Control; no se conocen entre sí ni usan el almacén |
| Control de Aplicación | `presentacion.control.ContratacionRenta` | Atiende a las vistas, valida, administra los DTO y usa el almacén |
| Coordinador de Aplicación | `presentacion.control.CoordinadorAplicacion` | Muestra las ventanas, les pasa sus datos y les pasa los mensajes |
| Validaciones | `presentacion.control.ValidadorPresentacion` | Campos obligatorios y formatos |
| Mensajes | `presentacion.mensajes.Mensaje` | Los textos que ve el asesor; el Control solo dice cuál toca |
| Respuestas de negocio | `presentacion.simulacion.NegocioSimulado` | Simula lo que después harán los subsistemas de negocio |
| Almacén Temporal | `almacentemporal.AlmacenTemporal` | Registra, busca y actualiza DTO; no decide nada del negocio |

`presentacion.Main` crea estos objetos y los conecta.

## Pruebas y evidencia

`presentacion_maquinaria/src/test/java/presentacion/PruebasPresentacion.java` recorre los casos P01 a P16 de la rúbrica en las pantallas reales. Se corre con *Run File* en NetBeans. No corre con cada *Clean and Build* porque abre ventanas.

El resultado de cada caso, las capturas y dónde se comprueba cada indicador están en [`evidencias_presentacion/RegistroPruebas.md`](evidencias_presentacion/RegistroPruebas.md).
