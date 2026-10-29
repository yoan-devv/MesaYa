package domain;

public class Mesa {

	private int numero;
	private int capacidad;
	private int x; // posicion en el plano
	private int y;

	public Mesa(int numero, int capacidad, int x, int y) {
		this.numero = numero;
		this.capacidad = capacidad;
		this.x = x;
		this.y = y;
	}

	public int getNumero() {
		return numero;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Mesa " + numero + " (" + capacidad + " pers.)";
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
