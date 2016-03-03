package com.dockerandroid.util;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by saymagic on 2016/2/29.
 */
public class RegularUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsIpAddress() throws Exception {
        assertEquals(true, (RegularUtil.isIpAddress("192.168.0.1")));
        assertEquals(false, (RegularUtil.isIpAddress("192.168.0.1.1")));
    }

    @Test
    public void testIsInt() throws Exception {
        assertEquals(true, (RegularUtil.isInt("45645")));
        assertEquals(false, (RegularUtil.isInt("23.1")));
    }
}