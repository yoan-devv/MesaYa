package domain;

public class Pedido {

	private Mesa mesa;
	private String platos;
	private String estado; // "EN COLA", "PREPARANDO" o "LISTO"

	public Pedido(Mesa mesa, String platos) {
		this.mesa = mesa;
		this.platos = platos;
		this.estado = "EN COLA";
	}

	public Mesa getMesa() {
		return mesa;
	}

	public String getPlatos() {
		return platos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Mesa " + mesa.getNumero() + ": " + platos;
	}
}

//IAG (herramientas: Claude y ChatGPT): nos ayudaron a planificar y estructurar los ficheros y a decidir qué métodos usar en cada parte.
//SIN-CAMBIOS
