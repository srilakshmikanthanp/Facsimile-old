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
     * Test function for the Datum PAckage
     * 
     * @throws GeneralSecurityException if error in security
     * @throws IOException if error in io
     */
    @Test
    public void testDatum() throws GeneralSecurityException, IOException
    {
        // delete file if exits
        if(Paths.get("./target/crypto.key").toFile().exists())
        {
            Assert.assertTrue(Paths.get("./target/crypto.key").toFile().delete());
        }

        // delete file if exits
        if(Paths.get("./target/datum.json").toFile().exists())
        {
            Assert.assertTrue(Paths.get("./target/datum.json").toFile().delete());
        }

        // creater map
        Mapping orgmapData = new Mapping(Paths.get("./target/"));

        // assert to not existitance of file and Key
        Assert.assertTrue(
            !orgmapData.getCrypto().isKeyFileExists() &&
            orgmapData.getCrypto().iskeyEmpty()
        );

        // create Key
        orgmapData.getCrypto().createKey("12345678");

        // assert to existitance of file and Key
        Assert.assertTrue(
            orgmapData.getCrypto().isKeyFileExists() && 
            !orgmapData.getCrypto().iskeyEmpty()
        );

        // add some initial data
        Assert.assertEquals(null, orgmapData.putSecure("Key1", "Value1"));
        Assert.assertEquals(null, orgmapData.putSecure("Key2", "Value2"));
        Assert.assertEquals(null, orgmapData.putSecure("Key3", "Value3"));

        // get the keys
        Assert.assertEquals("Value1", orgmapData.getSecure("Key1"));
        Assert.assertEquals("Value2", orgmapData.getSecure("Key2"));
        Assert.assertEquals("Value3", orgmapData.getSecure("Key3"));

        // delete original map
        orgmapData = null;

        // create new map data
        Mapping dupmapData = new Mapping(Paths.get("./target/"));

        // assert to not existitance of file and Key
        Assert.assertTrue(
            dupmapData.getCrypto().isKeyFileExists() && 
            dupmapData.getCrypto().iskeyEmpty()
        );

        // create Key
        dupmapData.getCrypto().loadKey("12345678");

        // assert to existitance of file and Key
        Assert.assertTrue(
            dupmapData.getCrypto().isKeyFileExists() && 
            !dupmapData.getCrypto().iskeyEmpty()
        );

        // get the keys
        Assert.assertEquals("Value1", dupmapData.getSecure("Key1"));
        Assert.assertEquals("Value2", dupmapData.getSecure("Key2"));
        Assert.assertEquals("Value3", dupmapData.getSecure("Key3"));
    }
}
