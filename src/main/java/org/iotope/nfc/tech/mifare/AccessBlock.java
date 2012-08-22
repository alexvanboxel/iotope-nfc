package org.iotope.nfc.tech.mifare;
class AccessBlock {

	private Access read;
	private Access write;
	private Access increment;
	private Access decrement;

	public AccessBlock(Access read, Access write, Access increment,
			Access decrement) {
		this.read = read;
		this.write = write;
		this.increment = increment;
		this.decrement = decrement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((decrement == null) ? 0 : decrement.hashCode());
		result = prime * result
				+ ((increment == null) ? 0 : increment.hashCode());
		result = prime * result + ((read == null) ? 0 : read.hashCode());
		result = prime * result + ((write == null) ? 0 : write.hashCode());
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
		AccessBlock other = (AccessBlock) obj;
		if (decrement != other.decrement)
			return false;
		if (increment != other.increment)
			return false;
		if (read != other.read)
			return false;
		if (write != other.write)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Access Conditions for Data Blocks  [R=" + read + ", W=" + write
				+ ", I=" + increment + ", D=" + decrement + "]";
	}
}