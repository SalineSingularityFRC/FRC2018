package src.org.usfirst.frc.team5066.controller2018;

import static org.usfirst.frc.team5066.controller2017.GripRunner.makeCamera;
import static org.usfirst.frc.team5066.controller2017.GripRunner.makeWindow;

import org.usfirst.frc.team5066.controller2017.GripRunner;
import org.usfirst.frc.team5066.controller2017.GripRunner.Listener;
import org.usfirst.frc.team5066.controller2017.VideoViewer;

public class FindGreenAreasApp {
	
	static final int IMG_WIDTH = 320;
	static final int IMG_HEIGHT = 240;

	final VideoViewer window;
	final Listener<FindGreenAreas> listener;
	final GripRunner<FindGreenAreas> gripRunner;
	
	public FindGreenAreasApp() {
		this.window = makeWindow("GRIP", IMG_WIDTH, IMG_HEIGHT);
		this.listener = (this.window!=null) ? (processor -> { window.imshow(processor.hslThresholdOutput());}) : null;
		this.gripRunner = new GripRunner<>(
				makeCamera(0, IMG_WIDTH, IMG_HEIGHT, -1.0), 
				new FindGreenAreas(), 
				listener);
	}

	public static void main(String[] args) {
		FindGreenAreasApp app = new FindGreenAreasApp();
		app.gripRunner.runForever();
	}

}