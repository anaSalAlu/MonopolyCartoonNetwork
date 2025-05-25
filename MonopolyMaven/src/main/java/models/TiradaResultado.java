package models;

public class TiradaResultado {
	public final int dado1;
	public final int dado2;
	public boolean esDoble;

	public TiradaResultado(int dado1, int dado2) {
		this.dado1 = dado1;
		this.dado2 = dado2;
	}

	public int getDado1() {
		return dado1;
	}

	public int getDado2() {
		return dado2;
	}

	public boolean isEsDoble() {
		return esDoble;
	}

}