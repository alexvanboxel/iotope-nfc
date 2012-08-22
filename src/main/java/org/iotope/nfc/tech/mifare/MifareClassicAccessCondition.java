package org.iotope.nfc.tech.mifare;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import org.iotope.util.IOUtil;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;

public class MifareClassicAccessCondition {

	/**
	 * <pre>
	 *          KeyA   Acce   KeyB   Remark
	 * C1 C2 C3 R  W   R  W   R  W   
	 * 0  0  0  -  A   A  -   A  A   B may be read[1]
	 * 0  1  0  -  -   A  -   A  -   B may be read[1]
	 * 1  0  0  -  B   AB -   -  B
	 * 1  1  0  -  -   AB -   -  -
	 * 0  0  1  -  A   A  A   A  A   B may be read, transport configuration[1]
	 * 0  1  1  -  B   AB B   -  B
	 * 1  0  1  -  -   AB B   -  -
	 * 1  1  1  -  -   AB -   -  -
	 * 
	 * [1] for this access condition key B is readable and may be used for data
	 * </pre>
	 */
	private static ImmutableBiMap<C, AccessSectorTrailer> sectorTrail;
	static {
		ImmutableBiMap.Builder<C, AccessSectorTrailer> builder = ImmutableBiMap
				.builder(); //
		builder.put(new C(0, 0, 0), new AccessSectorTrailer(Access.NEVER, Access.KEY_A,
				Access.KEY_A, Access.NEVER, Access.KEY_A, Access.KEY_A));
		builder.put(new C(0, 1, 0), new AccessSectorTrailer(Access.NEVER, Access.NEVER,
				Access.KEY_A, Access.NEVER, Access.NEVER, Access.NEVER));
		builder.put(new C(1, 0, 0), new AccessSectorTrailer(Access.NEVER, Access.KEY_B,
				Access.KEY_AB, Access.NEVER, Access.NEVER, Access.KEY_B));
//		builder.put(new C(1, 1, 0), new SectorTrail(Access.NEVER, Access.NEVER,
//				Access.KEY_AB, Access.NEVER, Access.NEVER, Access.NEVER));
		builder.put(new C(0, 0, 1), new AccessSectorTrailer(Access.NEVER, Access.KEY_A,
				Access.KEY_A, Access.KEY_A, Access.NEVER, Access.KEY_A));
		builder.put(new C(0, 1, 1), new AccessSectorTrailer(Access.NEVER, Access.KEY_B,
				Access.KEY_AB, Access.KEY_B, Access.NEVER, Access.KEY_B));
		builder.put(new C(1, 0, 1), new AccessSectorTrailer(Access.NEVER, Access.NEVER,
				Access.KEY_AB, Access.KEY_B, Access.NEVER, Access.NEVER));
		builder.put(new C(1, 1, 1), new AccessSectorTrailer(Access.NEVER, Access.NEVER,
				Access.KEY_AB, Access.NEVER, Access.NEVER, Access.NEVER));
		sectorTrail = builder.build();
	}

	/**
	 * <pre>
	 * C1 C2 C3 R  W  I  D
	 * 0  0  0  AB AB AB AB transport configuration
	 * 0  1  0  AB -  -  -  read/write block
	 * 1  0  0  AB B  -  -  read/write block
	 * 1  1  0  AB B  B  AB value block
	 * 0  0  1  AB -  -  AB value block
	 * 0  1  1  B  B  -  -  read/write block
	 * 1  0  1  B  -  -  -  read/write block
	 * 1  1  1  -  -  -  -  read/write block
	 * </pre>
	 */
	private static ImmutableBiMap<C, AccessDataBlock> accessBlock;
	static {
		ImmutableBiMap.Builder<C, AccessDataBlock> builder = ImmutableBiMap
				.builder(); //
		builder.put(new C(0, 0, 0), new AccessDataBlock(Access.KEY_AB,
				Access.KEY_AB, Access.KEY_AB, Access.KEY_AB));
		builder.put(new C(0, 1, 0), new AccessDataBlock(Access.KEY_AB,
				Access.NEVER, Access.NEVER, Access.NEVER));
		builder.put(new C(1, 0, 0), new AccessDataBlock(Access.KEY_AB,
				Access.KEY_B, Access.NEVER, Access.NEVER));
		builder.put(new C(1, 1, 0), new AccessDataBlock(Access.KEY_AB,
				Access.KEY_B, Access.KEY_B, Access.KEY_AB));
		builder.put(new C(0, 0, 1), new AccessDataBlock(Access.KEY_AB,
				Access.NEVER, Access.NEVER, Access.KEY_AB));
		builder.put(new C(0, 1, 1), new AccessDataBlock(Access.KEY_B, Access.KEY_B,
				Access.NEVER, Access.NEVER));
		builder.put(new C(1, 0, 1), new AccessDataBlock(Access.KEY_B, Access.NEVER,
				Access.NEVER, Access.NEVER));
		builder.put(new C(1, 1, 1), new AccessDataBlock(Access.NEVER, Access.NEVER,
				Access.NEVER, Access.NEVER));
		accessBlock = builder.build();
	}

	private AccessSectorTrailer sector;
	private AccessDataBlock[] block = new AccessDataBlock[3];
	
	private byte[] keyA;
	private byte[] keyB;
	private byte userData;

	public void write(DataOutput output) throws IOException {
		Preconditions.checkNotNull(keyA);
		Preconditions.checkNotNull(keyA.length == 6);
		Preconditions.checkNotNull(keyB);
		Preconditions.checkNotNull(keyB.length == 6);

		output.write(keyA);
		writeAccessBits(output);
		output.writeByte(userData);
		output.write(keyB);
	}
	
	public void writeAccessBits(DataOutput output) throws IOException {
		int b0=0,b1=0,b2=0;

		Map<AccessDataBlock,C> inverse = accessBlock.inverse();
		C c0 = inverse.get(block[0]);
		C c1 = inverse.get(block[1]);
		C c2 = inverse.get(block[2]);
		C c3  = sectorTrail.inverse().get(sector);

		C ci0 = c0.inverse();
		C ci1 = c1.inverse();
		C ci2 = c2.inverse();
		C ci3 = c3.inverse();
		
        // The bits
        // @formatter:off
        b1 |= c0.getCx1() << 4; b2 |= c0.getCx2() << 0 ; b2 |= c0.getCx3() << 4;
        b1 |= c1.getCx1() << 5; b2 |= c1.getCx2() << 1 ; b2 |= c1.getCx3() << 5;
        b1 |= c2.getCx1() << 6; b2 |= c2.getCx2() << 2 ; b2 |= c2.getCx3() << 6;
        b1 |= c3.getCx1() << 7; b2 |= c3.getCx2() << 3 ; b2 |= c3.getCx3() << 7;
        // @formatter:on
        
        // The inverse
        // @formatter:off
        b0 |= ci0.getCx1() << 0; b0 |= ci0.getCx2() << 4 ; b1 |= ci0.getCx3() << 0;
        b0 |= ci1.getCx1() << 1; b0 |= ci1.getCx2() << 5 ; b1 |= ci1.getCx3() << 1;
        b0 |= ci2.getCx1() << 2; b0 |= ci2.getCx2() << 6 ; b1 |= ci2.getCx3() << 2;
        b0 |= ci3.getCx1() << 3; b0 |= ci3.getCx2() << 7 ; b1 |= ci3.getCx3() << 3;
        // @formatter:on

		output.writeByte(b0);
		output.writeByte(b1);
		output.writeByte(b2);
	}

	public void read(DataInput input) throws IOException {
		keyA = new byte[6];
		input.readFully(keyA);
		readAccessBits(input);
		userData = input.readByte();
		keyB = new byte[6];
		input.readFully(keyB);
	}
	
	public void readAccessBits(DataInput input) throws IOException {
		byte b0 = input.readByte();
		byte b1 = input.readByte();
		byte b2 = input.readByte();
		
		// The bits
		C c0 = new C(((b1 >> 4) & 1),((b2 >> 0) & 1),((b2 >> 4) & 1));
		C c1 = new C(((b1 >> 5) & 1),((b2 >> 1) & 1),((b2 >> 5) & 1));
		C c2 = new C(((b1 >> 6) & 1),((b2 >> 2) & 1),((b2 >> 6) & 1));
		C c3 = new C(((b1 >> 7) & 1),((b2 >> 3) & 1),((b2 >> 7) & 1));

		// The inverse
		C ci0 = new C(((b0 >> 0) & 1),((b0 >> 4) & 1),((b1 >> 0) & 1));
		C ci1 = new C(((b0 >> 1) & 1),((b0 >> 5) & 1),((b1 >> 1) & 1));
		C ci2 = new C(((b0 >> 2) & 1),((b0 >> 6) & 1),((b1 >> 2) & 1));
		C ci3 = new C(((b0 >> 3) & 1),((b0 >> 7) & 1),((b1 >> 3) & 1));
		
		Preconditions.checkState(c0.equals(ci0.inverse()), "Formatting error, Cx0 isn't the inverse of ~Cx0");
		Preconditions.checkState(c1.equals(ci1.inverse()), "Formatting error, Cx1 isn't the inverse of ~Cx1");
		Preconditions.checkState(c2.equals(ci2.inverse()), "Formatting error, Cx2 isn't the inverse of ~Cx2");
		Preconditions.checkState(c3.equals(ci3.inverse()), "Formatting error, Cx3 isn't the inverse of ~Cx3");

		sector = sectorTrail.get(c3);
		block[0] = accessBlock.get(c0);
		block[1] = accessBlock.get(c1);
		block[2] = accessBlock.get(c2);
	}

	@Override
	public String toString() {
		return "MifareClassicAccessCondition [\n" +
				" [0] " + block[0] + ",\n" +
				" [1] " + block[1] + ",\n" +
				" [2] " + block[2] + ",\n" +
				" [3] " + sector + ",\n" +
				" USR [" + IOUtil.hex(userData) + "],\n" +
				" [A] " + IOUtil.hex(keyA) + ",\n" +
				" [B] " + IOUtil.hex(keyB) + "\n]";
	}
}
