package tarjetas;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * 
 * @author GingerGabriela
 * @version v1.2020
 * @since 28/04/2020
 *
 */
public class Credito extends Tarjeta {
	private static final double contImporte = 0.0;
	/**
	 * Atributo  mCredito
	 */
	protected double mCredito;
	/**
	 * Atributo lista de movimiento
	 */
	protected ArrayList<Movimiento> mMovimientos;

	/**
	 * Constructor con 5 par�metros
	 * @param numero Numero de Credito
	 * @param titular Titular de la Cuenta
	 * @param fechaCaducidad Fecha de la caducidad
	 * @param credito Credito
	 */
	public Credito(String numero, String titular, LocalDate fechaCaducidad, double credito) {
		super(numero, titular, fechaCaducidad);
		mCredito = credito;
		mMovimientos = new ArrayList<Movimiento>();
	}

	/**
	 * Metodo retirada en cajero automatico
	 * @param x
	 */
	public void retirar(double x) throws Exception {
		Movimiento m = new Movimiento();
		m.setmConcepto("Retirada en cajero automático");
		x = (x * 0.05 < 3.0 ? 3 : x * 0.05); // Añadimos una comisión de un 5%, mínimo de 3 euros.
		m.setmImporte(x);
		mMovimientos.add(m);
		if (x > getCreditoDisponible())
			throw new Exception("Crédito insuficiente");
	}

	/**
	 * Metodo ingresar dinero en cuenta asociada
	 * @param x 
	 */
	public void ingresar(double x) throws Exception {
		Movimiento m = new Movimiento();
		m.setmConcepto("Ingreso en cuenta asociada (cajero automático)");
		m.setmImporte(x);
		mMovimientos.add(m);
		mCuentaAsociada.ingresar(x);
	}

	/**
	 * Metodo para pagar en establecimeinto
	 * @param datos datos de la persona
	 */
	public void pagoEnEstablecimiento(String datos, double x) throws Exception {
		Movimiento m = new Movimiento();
		m.setmConcepto("Compra a crédito en: " + datos);
		m.setmImporte(x);
		mMovimientos.add(m);
	}

	public double getSaldo() {
		double r = contImporte;
		for (int i = 0; i < this.mMovimientos.size(); i++) {
			Movimiento m = mMovimientos.get(i);
			r += m.getmImporte();
		}
		return r;
	}

	public double getCreditoDisponible() {
		return mCredito - getSaldo();
	}

	/**
	 * Metodo liquida las operaciones de la tarjeta de credito 
	 * @param mes Mes
	 * @param anyo A�o
	 */
	public void liquidar(int mes, int anyo) {
		Movimiento liq = new Movimiento();
		liq.setmConcepto("Liquidación de operaciones tarj. crédito, " + (mes + 1) + " de " + (anyo + 1900));
		double r = contImporte;
		for (int i = 0; i < this.mMovimientos.size(); i++) {
			Movimiento m = mMovimientos.get(i);
			if (m.getmFecha().getMonth().getValue() + 1 == mes && m.getmFecha().getYear() + 1900 == anyo)
				r += m.getmImporte();
		}
		liq.setmImporte(r);
		if (r != 0)
			mCuentaAsociada.addMovimiento(liq);
	}
}