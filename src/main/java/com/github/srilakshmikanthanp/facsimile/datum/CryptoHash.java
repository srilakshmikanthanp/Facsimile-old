package com.github.srilakshmikanthanp.facsimile.datum;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import java.util.Base64;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.KeyStore;
import java.security.GeneralSecurityException;



/**
 * This class Provides the Map with the encrypt/decrypt functions.
 */
public class CryptoHash extends HashMap<String, String>
{
    // Path to KeyStore file
    private final static String KEY_STORE_FILE = "keyStore.ks";

    // Path to the Json file
    private final static String DATA_JSON_FILE = "datum.json";

    // Algorithm to be used for encryption/decryption
    private final static String ALGORITHM = "AES";

    // Key Alias for key store
    private final static String KEY_ALIAS = "key";

    // cipher Engine
    private Cipher cipher;

    // Secret Key
    private SecretKey secretKey;

    // Base path to store data
    private Path basePath;

    /**
     * Constructor for the Crypto Hash Map.
     * 
     * @param basePath Base path to store data
     */
    public CryptoHash(Path basePath)
    {
        this.basePath = basePath;
    }

    /**
     * Checks if the key is present in the map.
     */
    public boolean isSecrectKeyExits()
    {
        return this.secretKey != null;
    }

    /**
     * Is the Key file Exits
     */
    public boolean isKsFileExists()
    {
        return basePath.resolve(KEY_STORE_FILE).toFile().exists();
    }

    /**
     * Is the Json file Exits
     */
    public boolean isJsonFileExists()
    {
        return basePath.resolve(DATA_JSON_FILE).toFile().exists();
    }

    /**
     * Method to create new KeyStore file.
     * 
     * @param Password password
     * @throws IOException IO fails
     * @throws GeneralSecurityException crypto fails
     */
    public void makeCrypto(String password) throws IOException, GeneralSecurityException
    {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        var secretKey = keyGen.generateKey();

        KeyStore kStore = KeyStore.getInstance(KeyStore.getDefaultType());
        kStore.load(null, password.toCharArray());

        var skEntry = new KeyStore.SecretKeyEntry(secretKey);
        var prot = new KeyStore.PasswordProtection(password.toCharArray());
        kStore.setEntry(KEY_ALIAS, skEntry, prot);

        var fileos = new FileOutputStream(basePath.resolve(KEY_STORE_FILE).toFile());

        try {
            kStore.store(fileos, password.toCharArray());
        } finally {
            fileos.close();
        }

        this.secretKey = secretKey;
    }

    /**
     * Method to load the KeyStore file.
     * 
     * @param Password password
     * @throws IOException IO fails
     * @throws GeneralSecurityException crypto fails
     */
    public void loadCrypto(String password) throws IOException, GeneralSecurityException
    {
        var fileis = new FileInputStream(basePath.resolve(KEY_STORE_FILE).toFile());
        var kStore = KeyStore.getInstance(KeyStore.getDefaultType());

        try {
            kStore.load(fileis, password.toCharArray());
        } finally {
            fileis.close();
        }

        var prot = new KeyStore.PasswordProtection(password.toCharArray());
        var skEntry = (KeyStore.SecretKeyEntry) kStore.getEntry(KEY_ALIAS, prot);
        this.secretKey = skEntry.getSecretKey();
    }

    /**
     * Methos to change the password of the KeyStore file.
     * 
     * @param oldPassword old password
     * @param newPassword new password
     * @throws IOException IO fails
     * @throws GeneralSecurityException crypto fails
     */
    public void changePassword(String oldPassword, String newPassword) throws IOException, GeneralSecurityException
    {
        var fileis = new FileInputStream(basePath.resolve(KEY_STORE_FILE).toFile());
        var kStore = KeyStore.getInstance(KeyStore.getDefaultType());

        try {
            kStore.load(fileis, oldPassword.toCharArray());
        } finally {
            fileis.close();
        }

        var prot = new KeyStore.PasswordProtection(oldPassword.toCharArray());
        var skEntry = (KeyStore.SecretKeyEntry) kStore.getEntry(KEY_ALIAS, prot);

        KeyStore newKStore = KeyStore.getInstance(KeyStore.getDefaultType());
        newKStore.load(null, newPassword.toCharArray());

        var newProt = new KeyStore.PasswordProtection(newPassword.toCharArray());
        newKStore.setEntry(KEY_ALIAS, skEntry, newProt);

        var fileos = new FileOutputStream(basePath.resolve(KEY_STORE_FILE).toFile());

        try {
            newKStore.store(fileos, newPassword.toCharArray());
        } finally {
            fileos.close();
        }
    }

    /**
     * This method is used to encrypt the data.
     * 
     * @param data Data to be encrypted
     * @return Encrypted data
     * @throws GeneralSecurityException if the encryption fails
     */
    public String encrypt(String data) throws GeneralSecurityException
    {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encrypted);
    }

    /**
     * This method is used to decrypt the data.
     * 
     * @param data Data to be decrypted
     * @return Decrypted data
     * @throws GeneralSecurityException if the encryption fails
     */
    public String decrypt(String data) throws GeneralSecurityException
    {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(decrypted);
    }
}
