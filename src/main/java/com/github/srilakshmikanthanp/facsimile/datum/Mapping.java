// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.datum;

import javax.crypto.*;
import java.nio.file.*;
import java.util.HashMap;


/**
 * Class That handles the encrypion and decryption of the data.
 */
class Crypto
{
    // base dir for data
    private Path baseDir;
    
    // key for encryption
    private SecretKey key;

    /**
     * Constructor for the Crypto class.
     * 
     * @param baseDir The base directory for the data.
     */
    public Crypto(Path baseDir)
    {
        this.baseDir = baseDir;
    }

    /**
     * Authorize the user.
     * 
     * @param password the password.
     * @throws IlligalArgumentException if password was incorrect.
     */
    public void auth(String password) throws IllegalArgumentException
    {

    }

    /**
     * Encrypt the data.
     * 
     * @param data the data to be encrypted.
     */
    public String encrypt(String text)
    {
        return "";
    }

    /**
     * Decrypt the data.
     * 
     * @param data the data to be decrypted.
     */
    public String decrypt(String text)
    {
        return "";
    }
}

/**
 * Mapping of the datum key to the value objects.
 */
public class Mapping extends HashMap<String, String>
{
    // base dir for data
    private Path baseDir;

    // Crypto object
    private Crypto crypto;

    // is authorized
    private boolean authorized;

    /**
     * Saves the data to presistant storage.
     */
    private void save()
    {

    }

    /**
     * Loads the data from presistant storage.
     */
    private void load()
    {

    }

    /**
     * Constructor of Mapping.
     * 
     * @param baseDir the base directory of the datum. 
     */
    public Mapping(Path baseDir)
    {
        this.baseDir = baseDir;
    }

    /**
     * Authorize the datum.
     * 
     * @param password the password.
     * @throws IlligalArgumentException if password was incorrect.
     */
    public void auth(String password) throws IllegalArgumentException
    {

    }

    /**
     * Is the mapping authorized.
     */
    public boolean isAuthorized()
    {
        return authorized;
    }

    /**
     * Get the value of the key.
     * 
     * @param key the key.
     * @return the value of the key.
     */
    @Override
    public String get(Object key)
    {
        return "";
    }

    /**
     * Set the value of the key.
     * 
     * @param key the key.
     * @param value the value of the key.
     * @return the value of the key.
     */
    @Override
    public String put(String key, String value)
    {
        return "";
    }
}
