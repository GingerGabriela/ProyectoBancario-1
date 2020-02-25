package tarjetas;

import java.time.LocalDate;
import java.util.ArrayList;

public class Credito extends Tarjeta {
	protected double mCredito;
	protected ArrayList<Movimiento> mMovimientos;

	public Credito(String numero, String titular, LocalDate fechaCaducidad, double credito) {
		super(numero, titular, fechaCaducidad);
		mCredito = credito;
		mMovimientos = new ArrayList<Movimiento>();
	}

	public void retirar(double x) throws Exception {
		Movimiento m = new Movimiento();
		m.setmConcepto("Retirada en cajero automático");
		x = (x * 0.05 < 3.0 ? 3 : x * 0.05); // Añadimos una comisión de un 5%, mínimo de 3 euros.
		m.setmImporte(x);
		mMovimientos.add(m);
		if (x > getCreditoDisponible())
			throw new Exception("Crédito insuficiente");
	}

	public void ingresar(double x) throws Exception {
		Movimiento m = new Movimiento();
		m.setmConcepto("Ingreso en cuenta asociada (cajero automático)");
		m.setmImporte(x);
		mMovimientos.add(m);
		mCuentaAsociada.ingresar(x);
	}

	public void pagoEnEstablecimiento(String datos, double x) throws Exception {
		Movimiento m = new Movimiento();
		m.setmConcepto("Compra a crédito en: " + datos);
		m.setmImporte(x);
		mMovimientos.add(m);
	}

	public double getSaldo() {
		double r = 0.0;
		for (int i = 0; i < this.mMovimientos.size(); i++) {
			Movimiento m = mMovimientos.get(i);
			r += m.getmImporte();
		}
		return r;
	}

	public double getCreditoDisponible() {
		return mCredito - getSaldo();
	}

	public void liquidar(int mes, int anyo) {
		Movimiento liq = new Movimiento();
		liq.setmConcepto("Liquidación de operaciones tarj. crédito, " + (mes + 1) + " de " + (anyo + 1900));
		double r = 0.0;
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