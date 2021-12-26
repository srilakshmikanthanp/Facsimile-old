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
        Assert.assertTrue(!orgmapData.getCryptoEn().isKeyExists());

        // create Key
        orgmapData.getCryptoEn().createNewKey("12345678");

        // assert to existitance of file and Key
        Assert.assertTrue(orgmapData.getCryptoEn().isKeyExists());

        // add some initial data
        Assert.assertEquals(null, orgmapData.put("Key1", "Value1"));
        Assert.assertEquals(null, orgmapData.put("Key2", "Value2"));
        Assert.assertEquals(null, orgmapData.put("Key3", "Value3"));

        // get the keys
        Assert.assertEquals("Value1", orgmapData.get("Key1"));
        Assert.assertEquals("Value2", orgmapData.get("Key2"));
        Assert.assertEquals("Value3", orgmapData.get("Key3"));

        orgmapData.getCryptoEn().changeKeyPassword(
            "12345678", "ABCDEF"
        );

        // delete original map
        orgmapData = null;

        // create new map data
        Mapping dupmapData = new Mapping(Paths.get("./target/"));

        // assert to not existitance of file and Key
        Assert.assertTrue(dupmapData.getCryptoEn().isKeyExists());

        // create Key
        dupmapData.getCryptoEn().loadExtistingKey("ABCDEF");

        // assert to existitance of file and Key
        Assert.assertTrue(dupmapData.getCryptoEn().isKeyExists());

        // get the keys
        Assert.assertEquals("Value1", dupmapData.get("Key1"));
        Assert.assertEquals("Value2", dupmapData.get("Key2"));
        Assert.assertEquals("Value3", dupmapData.get("Key3"));
    }
}
