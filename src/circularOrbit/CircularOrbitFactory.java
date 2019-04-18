package circularOrbit;

import java.io.IOException;

public interface CircularOrbitFactory {
	public CircularOrbit CreateAndLoad(String loadFrom) throws IOException;
	public CircularOrbit Create(String type);
}
