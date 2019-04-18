package appliactions;

import circularOrbit.PhysicalObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PhysicalObjectFactory {
	
	@Nullable
	public static PhysicalObject produce(@NotNull String[] args){
		assert args.length > 0;
		switch(args[0]){
			case "Planet":
				assert args.length == 9;
				return new Planet(args[1], Enum.valueOf(Planet.Form.class, args[2]), args[3],
						Float.valueOf(args[4]), Float.valueOf(args[5]), Double.valueOf(args[6]),
								Enum.valueOf(Planet.Dir.class, args[7]), Float.valueOf(args[8]));
			case "Electron":
				assert args.length == 2;
				return new Electron(Float.valueOf(args[1]));
			case "CentralUser":
				assert args.length == 4;
				return new CentralUser(args[1], Integer.valueOf(args[2]), Enum.valueOf(Gender.class, args[3]));
			default: return null;
		}
	}
}
