package models;

public class TiradaResultado {
	public final int dado1;
	public final int dado2;
	public final boolean esDoble;

	public TiradaResultado(int dado1, int dado2, boolean esDoble) {
		this.dado1 = dado1;
		this.dado2 = dado2;
		this.esDoble = esDoble;
	}
}