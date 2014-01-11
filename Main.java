public class Main {

    public static void main(String[] args) {

		MandelbrotGUI	mGui		= new MandelbrotGUI();
		Generator		gen			= new Generator();
		boolean			isRendered	= false;
		
		while (true) {
			switch (mGui.getCommand()) {
				case MandelbrotGUI.ZOOM:
					if (!isRendered) {
						mGui.resetPlane();
						mGui.clearPlane();
						break;
					}
				case MandelbrotGUI.RENDER:
					gen.render(mGui);
					isRendered = true;
					break;
				case MandelbrotGUI.RESET:
					mGui.resetPlane();
					mGui.clearPlane();
					isRendered = false;
					break;
				case MandelbrotGUI.QUIT:
					System.exit(0);
					break;
			}
		}
    }
}
