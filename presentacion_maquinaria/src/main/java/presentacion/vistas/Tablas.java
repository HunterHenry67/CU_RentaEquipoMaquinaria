package presentacion.vistas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * Da el mismo aspecto a las tablas: renglones altos, sin líneas verticales y sin renglón seleccionado,
 * porque en estas tablas no se selecciona nada, solo se marcan las casillas.
 */
public class Tablas {

    private Tablas() {
    }

    public static void preparar(JTable tabla, int... anchos) {
        tabla.setFont(new Font("Dialog", Font.PLAIN, 14));
        tabla.setRowHeight(34);
        tabla.setIntercellSpacing(new Dimension(12, 1));
        tabla.setShowVerticalLines(false);
        tabla.setRowSelectionAllowed(false);
        tabla.setFocusable(false);
        tabla.setFillsViewportHeight(true);
        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setFont(new Font("Dialog", Font.BOLD, 14));
        encabezado.setReorderingAllowed(false);
        encabezado.setResizingAllowed(false);
        // el título a la izquierda, como sus datos; sobre una casilla, centrado como ella
        TableCellRenderer base = encabezado.getDefaultRenderer();
        encabezado.setDefaultRenderer((t, valor, elegido, foco, fila, columna) -> {
            Component celda = base.getTableCellRendererComponent(t, valor, elegido, foco, fila, columna);
            if (celda instanceof JLabel titulo) {
                titulo.setHorizontalAlignment(t.getColumnClass(columna) == Boolean.class ? SwingConstants.CENTER : SwingConstants.LEFT);
                titulo.setBorder(BorderFactory.createCompoundBorder(titulo.getBorder(), BorderFactory.createEmptyBorder(0, 4, 0, 0)));
            }
            return celda;
        });
        for (int i = 0; i < anchos.length; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }

    // Donde no se puede cambiar, la casilla se ve en gris; sin valor, no hay casilla
    public static void casillas(JTable tabla, int... columnas) {
        TableCellRenderer base = tabla.getDefaultRenderer(Boolean.class);
        for (int columna : columnas) {
            tabla.getColumnModel().getColumn(columna).setCellRenderer((t, valor, elegido, foco, fila, col) -> {
                if (valor == null) {
                    return t.getDefaultRenderer(Object.class).getTableCellRendererComponent(t, "", elegido, foco, fila, col);
                }
                Component casilla = base.getTableCellRendererComponent(t, valor, elegido, foco, fila, col);
                casilla.setEnabled(t.isCellEditable(fila, col));
                return casilla;
            });
        }
    }
}
