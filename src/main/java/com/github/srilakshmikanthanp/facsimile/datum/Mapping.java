// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.datum;


import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.HashMap;
import com.google.gson.Gson;


/**
 * Mapping of the datum key to the value objects.
 */
public class Mapping extends HashMap<String, String>
{
    // datum file name
    private final String DATUM_FILE = "datum.json";

    // base dir to save
    private Path baseDir;

    // Crypto object
    private Crypto crypto;

    /**
     * Saves the data to presistant storage.
     * 
     * @throws IOException is an io error occurs
     */
    private void saveData() throws IOException
    {
        Gson gson = new Gson();
        var json = gson.toJson(this);
        var path = baseDir.resolve(DATUM_FILE);
        Files.write(path, json.getBytes());
    }


    /**
     * Loads the data from presistant storage.
     * 
     * @throws IOException if io error occurs
     */
    @SuppressWarnings("unchecked")
    private void loadData() throws IOException
    {
        Gson gson = new Gson();
        var path = baseDir.resolve(DATUM_FILE);
        var json = Files.readString(path);
        this.putAll(gson.fromJson(json, HashMap.class));
    }

    /**
     * Constructor of Mapping.
     * 
     * @param baseDir the base directory of the datum. 
     */
    public Mapping(Path baseDir)
    {
        // save base dir
        this.baseDir = baseDir;

        // create the crypto object
        this.crypto = new Crypto(baseDir);

        // load the data
        try 
        {
            this.loadData();
        } 
        catch (IOException e) 
        {
            // no file exits or first time
        }
    }

    /**
     * Gets the crypto for Mappng
     * 
     * @return Crypto
     */
    public Crypto getCrypto()
    {
        return this.crypto;
    }

    /**
     * Get method deletion.
     */
    @Override
    public String get(Object key)
    {
        throw new UnsupportedOperationException(
            "Use gutSecure() instead."
        );
    }

    /**
     * Put method deletion.
     */
    @Override
    public String put(String key, String value)
    {
        throw new UnsupportedOperationException(
            "Use PutSecure() instead."
        );
    }

    /**
     * Get the value of the key.
     * 
     * @param key the key.
     * @return the value of the key.
     * @throws GeneralSecurityException if failed to decrypt.
     */
    public String getSecure(String key)
    throws GeneralSecurityException
    {
        var value = super.get(key);

        if(value == null)
        {
            return null;
        }

        return this.crypto.decrypt(value);
    }

    /**
     * Set the value of the key.
     * 
     * @param key the key.
     * @param value the value of the key.
     * @return the value of the key.
     * @throws GeneralSecurityException if failed to encrypt or decrypt.
     * @throws IOException if io error occurs while saving
     */
    public String putSecure(String key, String value)
    throws GeneralSecurityException, IOException
    {
        var oldValue = super.put(key, this.crypto.encrypt(value));
        
        this.saveData();
        
        if(oldValue == null)
        {
            return null;
        }

        return this.crypto.decrypt(oldValue);
    }
}
