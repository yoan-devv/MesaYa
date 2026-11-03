package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JPanel;

import domain.Mesa;
import domain.Restaurante;

// Dibuja las mesas con un color segun si estan libres, reservadas u ocupadas.
public class PanelMesas extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int ANCHO = 80;
	private static final int ALTO = 60;

	private Restaurante restaurante;

	public PanelMesas(Restaurante restaurante, VentanaPrincipal ventana) {
		this.restaurante = restaurante;
		setPreferredSize(new Dimension(360, 300));
		setBackground(Color.WHITE);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for (Mesa m : restaurante.getMesas()) {
					if (e.getX() >= m.getX() && e.getX() <= m.getX() + ANCHO && e.getY() >= m.getY()
							&& e.getY() <= m.getY() + ALTO) {
						ventana.mesaPulsada(m);
					}
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Mesa m : restaurante.getMesas()) {
			String estado = restaurante.getEstadoMesa(m, LocalDate.now());
			if (estado.equals("OCUPADA")) {
				g.setColor(new Color(246, 196, 192));
			} else if (estado.equals("RESERVADA")) {
				g.setColor(new Color(255, 236, 170));
			} else {
				g.setColor(new Color(205, 238, 212));
			}
			g.fillRect(m.getX(), m.getY(), ANCHO, ALTO);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(m.getX(), m.getY(), ANCHO, ALTO);
			g.drawString("Mesa " + m.getNumero(), m.getX() + 18, m.getY() + 27);
			g.drawString(m.getCapacidad() + " pers.", m.getX() + 20, m.getY() + 45);
		}
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
