package tarjetas;

import java.time.LocalDate;

public class Movimiento {
	protected String mConcepto;
	protected LocalDate mFecha;
	protected double mImporte;

	public Movimiento() {
		mFecha = LocalDate.now();
	}

	public String getmConcepto() {
		return mConcepto;
	}

	public void setmConcepto(String mConcepto) {
		this.mConcepto = mConcepto;
	}

	public LocalDate getmFecha() {
		return mFecha;
	}

	public void setmFecha(LocalDate mFecha) {
		this.mFecha = mFecha;
	}

	public double getmImporte() {
		return mImporte;
	}

	public void setmImporte(double mImporte) {
		this.mImporte = mImporte;
	}

}