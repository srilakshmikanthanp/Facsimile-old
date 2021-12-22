// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.datum;


import java.io.*;
import javax.crypto.*;
import java.nio.file.*;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import com.google.gson.Gson;   
import java.security.KeyStore;


/**
 * Class That handles the encrypion and decryption of the data.
 */
class Crypto
{
    // The Key File Name
    private static final String KEY_FILE_NAME = "crypto.key";

    // The algorithm used for encryption and decryption.
    private static final String ALGORITHM = "AES";

    // The Key Alias for KStore
    private static final String KEY_ALIAS = "secretKey";

    // base dir for data
    private Path baseDir = null;
    
    // The Engine used for encryption and decryption.
    private Cipher engine = null;

    // key for encryption
    private SecretKey secretKey = null;

    /**
     * Constructor for the Crypto class.
     * 
     * @param baseDir The base directory for the data.
     * @throws RuntimeException if construction fails
     */
    public Crypto(Path baseDir)
    {
        // store the base dir
        this.baseDir = baseDir;

        // create the engine
        try 
        {
            this.engine = Cipher.getInstance(ALGORITHM);
        } 
        catch (GeneralSecurityException e) 
        {
            throw new RuntimeException(e); // almost not going to happen
        }
    }

    /**
     * Checks if the key file exists.
     * 
     * @return true if the key file exists.
     * @return false if the key file does not exist.
     */
    public boolean isKeyFileExists()
    {
        return Files.exists(this.baseDir.resolve(KEY_FILE_NAME));
    }

    /**
     * Checks if the key is empty.
     * 
     * @return true if the key is empty.
     * @return false if the key is not empty.
     */
    public boolean iskeyEmpty()
    {
        return this.secretKey == null;
    }

    /**
     * Generates the key and saves it to the key file.
     * 
     * @param password The password to be used for KeyStore.
     * @throws IOException if io fails
     * @throws GeneralSecurityException if error in creating
     */
    public void createKey(String password) throws IOException, GeneralSecurityException
    {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        var secretKey = keyGen.generateKey();

        KeyStore kStore = KeyStore.getInstance(KeyStore.getDefaultType());
        kStore.load(null, password.toCharArray());

        var skEntry = new KeyStore.SecretKeyEntry(this.secretKey);
        var prot  = new KeyStore.PasswordProtection(password.toCharArray());
        kStore.setEntry(KEY_ALIAS, skEntry, prot);

        var fileos = new FileOutputStream(baseDir.resolve(KEY_FILE_NAME).toFile());
        kStore.store (fileos, password.toCharArray());

        this.secretKey = secretKey;
    }

    /**
     * Loads the key from the key file.
     * 
     * @param password The password to be used for KeyStore.
     * @throws IOException if io fails
     * @throws GeneralSecurityException if error in loading
     */
    public void loadKey(String password) throws IOException, GeneralSecurityException
    {
        var fis = new FileInputStream(baseDir.resolve(KEY_FILE_NAME).toFile());
        var kStore = KeyStore.getInstance(KeyStore.getDefaultType());
        kStore.load(fis, password.toCharArray());

        var prot  = new KeyStore.PasswordProtection(password.toCharArray());        
        var skEntry = (KeyStore.SecretKeyEntry) kStore.getEntry(KEY_ALIAS, prot);
        this.secretKey = skEntry.getSecretKey();
    }

    /**
     * Encrypt the data.
     * 
     * @param data the data to be encrypted.
     * @throws GeneralSecurityException if failed to encrypt.
     */
    public String encrypt(String text) throws GeneralSecurityException
    {
        engine.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = engine.doFinal(text.getBytes());
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encrypted);
    }

    /**
     * Decrypt the data.
     * 
     * @param data the data to be decrypted.
     * @throws GeneralSecurityException if failed to decrypt.
     */
    public String decrypt(String text) throws GeneralSecurityException
    {
        engine.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = engine.doFinal(Base64.getDecoder().decode(text));
        return new String(decrypted);
    }
}


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
    public Crypto crypto;

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
     * Get method deletion.
     */
    @Override
    public String get(Object key)
    {
        throw new UnsupportedOperationException("Use gutSecure() instead.");
    }

    /**
     * Put method deletion.
     */
    @Override
    public String put(String key, String value)
    {
        throw new UnsupportedOperationException("Use PutSecure() instead.");
    }

    /**
     * Get the value of the key.
     * 
     * @param key the key.
     * @return the value of the key.
     * @throws GeneralSecurityException if failed to decrypt.
     */
    public String getSecure(String key) throws GeneralSecurityException
    {
        return this.crypto.decrypt(super.get(key));
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
    public String putSecure(String key, String value) throws GeneralSecurityException, IOException
    {
        var oldValue = super.put(key, this.crypto.encrypt(value));
        this.saveData();
        return this.crypto.decrypt(oldValue);
    }
}
