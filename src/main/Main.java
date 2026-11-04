package main;

import domain.Restaurante;
import gui.VentanaPrincipal;

public class Main {

	public static void main(String[] args) {
		Restaurante restaurante = new Restaurante();
		VentanaPrincipal ventana = new VentanaPrincipal(restaurante);
		ventana.setVisible(true);
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
