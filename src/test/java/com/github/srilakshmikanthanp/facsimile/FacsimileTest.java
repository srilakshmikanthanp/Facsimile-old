// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile;

import java.io.*;
import org.junit.*;
import java.security.*;
import org.junit.Test;
import java.nio.file.Paths;
import com.github.srilakshmikanthanp.facsimile.datum.*;

public class FacsimileTest 
{
    /**
     * This test always must be run on new file
     */
    @Test
    public void testDatum()
    {
        Mapping orgMapData = new Mapping(Paths.get("target/"));
    
        try
        {
            orgMapData.createCryptoKey("12345678");
        }
        catch(GeneralSecurityException | IOException e)
        {
            Assert.assertTrue(false);   
        }

        try
        {
            Assert.assertEquals(null, orgMapData.putSecure("Key1", "Value1"));
            Assert.assertEquals(null, orgMapData.putSecure("Key2", "Value2"));
            Assert.assertEquals(null, orgMapData.putSecure("Key3", "Value3"));
        }
        catch(GeneralSecurityException e)
        {
            Assert.assertTrue(false);
        }
        catch(IOException e)
        {
            Assert.assertTrue(false);
        }

        try
        {
            String val1 = orgMapData.getSecure("Key1");
            String val2 = orgMapData.getSecure("Key2");
            String val3 = orgMapData.getSecure("Key3");
            String val4 = orgMapData.getSecure("Key4");

            Assert.assertEquals("Value1", val1);
            Assert.assertEquals("Value2", val2);
            Assert.assertEquals("Value3", val3);
            Assert.assertEquals(null, val4);
        }
        catch(GeneralSecurityException e)
        {
            Assert.assertTrue(false);   
        }

        Mapping dupMapData = new Mapping(Paths.get("target/"));

        try
        {
            dupMapData.loadCryptoKey("12345678");
        }
        catch(GeneralSecurityException | IOException e)
        {
            Assert.assertTrue(false);   
        }

        try
        {
            String val1 = dupMapData.getSecure("Key1");
            String val2 = dupMapData.getSecure("Key2");
            String val3 = dupMapData.getSecure("Key3");
            String val4 = dupMapData.getSecure("Key4");

            Assert.assertEquals("Value1", val1);
            Assert.assertEquals("Value2", val2);
            Assert.assertEquals("Value3", val3);
            Assert.assertEquals(null, val4);
        }
        catch(GeneralSecurityException e)
        {
            Assert.assertTrue(false);   
        }
    }
}
