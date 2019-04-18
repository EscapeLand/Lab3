package appliactions;

interface ElectronState {
	void up(Electron e);
	void down(Electron e);
}

class Ground implements ElectronState {
	
	@Override
	public void up(Electron e) {
		//TODO
	}
	
	@Override
	public void down(Electron e) {
		throw new RuntimeException("Ground state cannot transition down. ");
	}
}

class Excited implements ElectronState {
	
	@Override
	public void up(Electron e) {
		throw new RuntimeException("Excited state cannot transition up. ");
	}
	
	@Override
	public void down(Electron e) {
		//TODO
	}
}