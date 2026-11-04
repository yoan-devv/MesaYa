package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

// Pinta cada fila de la tabla de reservas segun su estado (columna 4).
public class ReservaRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (!isSelected) {
			String estado = table.getValueAt(row, 4).toString();
			if (estado.equals("SENTADA")) {
				setBackground(new Color(246, 196, 192));
			} else if (estado.equals("PENDIENTE")) {
				setBackground(new Color(255, 236, 170));
			} else {
				setBackground(Color.LIGHT_GRAY);
			}
		}
		return this;
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
