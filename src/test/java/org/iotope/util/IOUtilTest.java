package org.iotope.util;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class IOUtilTest {

	@Test
	public void orBit0() {
		Assert.assertEquals(1,IOUtil.bitset(0, 0));
	}

	@Test
	public void orBit7() {
		Assert.assertEquals(128,IOUtil.bitset(0, 7));
	}

	@Test
	public void orBit6() {
		Assert.assertEquals(64,IOUtil.bitset(0, 6));
	}

}
