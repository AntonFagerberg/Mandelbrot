public class Complex {

	private double re;
	private double im;

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}

	public double getAbs2() {
		return re*re+im*im;
	}

	public void add(Complex c) {
		re += c.getRe();
		im += c.getIm();
	}

	public void mul(Complex c) {
		double tempRe = re * c.getRe() - im * c.getIm();

		im = im * c.getRe() + re * c.getIm();
		re = tempRe;
	}
}