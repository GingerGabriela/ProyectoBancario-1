package tarjetas;

import java.util.ArrayList;

public class Cuenta {
	protected String mNumero;
	protected String mTitular;
	protected ArrayList<Movimiento> mMovimientos;

	public Cuenta(String numero, String titular) {
		mNumero = numero;
		mTitular = titular;
		mMovimientos = new ArrayList<Movimiento>();
	}

	public void ingresar(double x) throws Exception {
		if (x <= 0)
			throw new Exception("No se puede ingresar una cantidad negativa");
		Movimiento m = new Movimiento();
		m.setmConcepto("Ingreso en efectivo");
		m.setmImporte(x);
		this.mMovimientos.add(m);
	}

	public void retirar(double x) throws Exception {
		if (x <= 0)
			throw new Exception("No se puede retirar una cantidad negativa");
		if (getSaldo() < x)
			throw new Exception("Saldo insuficiente");
		Movimiento m = new Movimiento();
		m.setmConcepto("Retirada de efectivo");
		m.setmImporte(-x);
		this.mMovimientos.add(m);
	}

	public void ingresar(String concepto, double x) throws Exception {
		if (x <= 0)
			throw new Exception("No se puede ingresar una cantidad negativa");
		Movimiento m = new Movimiento();
		m.setmConcepto(concepto);
		m.setmImporte(x);
		this.mMovimientos.add(m);
	}

	public void retirar(String concepto, double x) throws Exception {
		if (x <= 0)
			throw new Exception("No se puede retirar una cantidad negativa");
		if (getSaldo() < x)
			throw new Exception("Saldo insuficiente");
		Movimiento m = new Movimiento();
		m.setmConcepto(concepto);
		m.setmImporte(-x);
		this.mMovimientos.add(m);
	}

	public double getSaldo() {
		double r = 0.0;
		for (int i = 0; i < this.mMovimientos.size(); i++) {
			Movimiento m = (Movimiento) mMovimientos.get(i);
			r += m.getmImporte();
		}
		return r;
	}

	public void addMovimiento(Movimiento m) {
		mMovimientos.add(m);
	}
}