package io.meterian.samples.jackson.logging;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LogMarkers {

	public static final Marker AUDIT = MarkerFactory.getMarker("AUDIT");
	public static final Marker STACKTRACE = MarkerFactory.getMarker("STACKTRACE");
	
	private LogMarkers() {
		// Hide constructor
	}
	
}
