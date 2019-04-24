package appliactions;

interface ElectronState {
	boolean isGround();
}

class Ground implements ElectronState {
	@Override
	public boolean isGround() {
		return true;
	}
}

class Excited implements ElectronState {
	
	@Override
	public boolean isGround() {
		return false;
	}
}