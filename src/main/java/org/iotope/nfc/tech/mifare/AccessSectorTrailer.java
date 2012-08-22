package org.iotope.nfc.tech.mifare;
class AccessSectorTrailer {

	private Access readKeyA;
	private Access writeKeyA;
	private Access readAC;
	private Access writeAC;
	private Access readKeyB;
	private Access writeKeyB;

	public AccessSectorTrailer(Access readKeyA, Access writeKeyA, Access readAC,
			Access writeAC, Access readKeyB, Access writeKeyB) {
		this.readKeyA = readKeyA;
		this.writeKeyA = writeKeyA;
		this.readAC = readAC;
		this.writeAC = writeAC;
		this.readKeyB = readKeyB;
		this.writeKeyB = writeKeyB;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((readAC == null) ? 0 : readAC.hashCode());
		result = prime * result
				+ ((readKeyA == null) ? 0 : readKeyA.hashCode());
		result = prime * result
				+ ((readKeyB == null) ? 0 : readKeyB.hashCode());
		result = prime * result + ((writeAC == null) ? 0 : writeAC.hashCode());
		result = prime * result
				+ ((writeKeyA == null) ? 0 : writeKeyA.hashCode());
		result = prime * result
				+ ((writeKeyB == null) ? 0 : writeKeyB.hashCode());
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
		AccessSectorTrailer other = (AccessSectorTrailer) obj;
		if (readAC != other.readAC)
			return false;
		if (readKeyA != other.readKeyA)
			return false;
		if (readKeyB != other.readKeyB)
			return false;
		if (writeAC != other.writeAC)
			return false;
		if (writeKeyA != other.writeKeyA)
			return false;
		if (writeKeyB != other.writeKeyB)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccessSectorTrailer [KeyA: R=" + readKeyA
				+ " W=" + writeKeyA + ", AC: R=" + readAC + " W=" + writeAC
				+ ", KeyB: R=" + readKeyB + ", W=" + writeKeyB + "]";
	}
}