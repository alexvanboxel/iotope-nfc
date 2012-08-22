package org.iotope.nfc.tech.mifare;

class C {
	private boolean c1, c2, c3;

	public C(int c1, int c2, int c3) {
		this(c1 != 0, c2 != 0, c3 != 0);
	}

	public C(boolean c1, boolean c2, boolean c3) {
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}

	public C inverse() {
		return new C(!c1, !c2, !c3);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (c1 ? 1231 : 1237);
		result = prime * result + (c2 ? 1231 : 1237);
		result = prime * result + (c3 ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		C other = (C) obj;
		if (c1 != other.c1)
			return false;
		if (c2 != other.c2)
			return false;
		if (c3 != other.c3)
			return false;
		return true;
	}

	public int getCx1() {
		return c1 ? 1 : 0;
	}

	public int getCx2() {
		return c2 ? 1 : 0;
	}

	public int getCx3() {
		return c3 ? 1 : 0;
	}
}