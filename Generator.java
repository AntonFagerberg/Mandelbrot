import java.awt.Color;

public class Generator {

	private int pixelResolution = 1;
	private int iterations;
	private Color[] colorLevels = new Color[255];;
	
	public Generator() {
		for (int i = 0; i < 255; i++) {
			colorLevels[i] = new Color(i, 0, i); // From black to pink.
		}
	}

	public void render(MandelbrotGUI mGui) {

		switch (mGui.getResolution()) {
			case MandelbrotGUI.RESOLUTION_VERY_HIGH:
				pixelResolution = 1;
				break;
			case MandelbrotGUI.RESOLUTION_HIGH:
				pixelResolution = 3;
				break;
			case MandelbrotGUI.RESOLUTION_MEDIUM:
				pixelResolution = 5;
				break;
			case MandelbrotGUI.RESOLUTION_LOW:
				pixelResolution = 7;
				break;
			case MandelbrotGUI.RESOLUTION_VERY_LOW:
				pixelResolution = 9;
				break;
		}

		try {
			iterations = Integer.parseInt(mGui.getExtraText());
		} catch (NumberFormatException e) {
			if (mGui.getMode() == MandelbrotGUI.MODE_COLOR) {
				iterations = 50;
			} else {
				iterations = 200;
			}
		}

		Color[][] picture = new Color[mGui.getHeight() / pixelResolution][mGui.getWidth() / pixelResolution];
		Complex[][] c = mesh(mGui.getMinimumReal(), mGui.getMaximumReal(), mGui.getMinimumImag(), mGui.getMaximumImag(), mGui.getWidth(), mGui.getHeight());

		for (int x = 0; x < mGui.getHeight() / pixelResolution; x++) {
			for (int y = 0; y < mGui.getWidth() / pixelResolution; y++) {

				int i = 0;

				Complex almonds = new Complex(0.0, 0.0);

				while (almonds.getAbs2() <= 4 && i < iterations) {
					almonds.mul(almonds);
					almonds.add(c[x][y]);
					i++;
				}

				if (almonds.getAbs2() <= 4) {
					if (mGui.getMode() == MandelbrotGUI.MODE_BW) {
						picture[x][y] = Color.BLACK;
					} else {
						picture[x][y] = new Color(20, 20, 20);
					}
				} else {
					if (mGui.getMode() == MandelbrotGUI.MODE_BW) {
						picture[x][y] = Color.WHITE;
					} else {
						picture[x][y] = colorLevels[(int) Math.ceil((254 / (double) iterations) * i)];
					}
				}
			}
		}

		mGui.putData(picture, pixelResolution, pixelResolution);
	}

	private Complex[][] mesh(double minRe, double maxRe, double minIm, double maxIm, int width, int height) {

		double positionIm = (maxIm - minIm) / height;
		double positionRe = (maxRe - minRe) / width;

		Complex[][] c = new Complex[height / pixelResolution][width / pixelResolution];

		double alignIm = 0.5 * positionIm * (pixelResolution + 1) - maxIm;
		double alignRe = 0.5 * positionRe * (pixelResolution + 1) + minRe;
		double posResIm = positionIm * pixelResolution;
		double posResRe = positionRe * pixelResolution;
		
		double[] complexX = new double[(width / pixelResolution)];
		
		for (int y = 0; y < width / pixelResolution; y++) {
			complexX[y] = posResRe * y + alignRe;
		}

		for (int a = 0; a < height / pixelResolution; a++) {
			double complexY =  posResIm * a  + alignIm;
			for (int b = 0; b < width / pixelResolution; b++) {
				c[a][b] = new Complex(complexX[b], complexY);
			}
		}

		return c;
	}
}