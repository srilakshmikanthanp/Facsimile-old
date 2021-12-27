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
import java.security.KeyStore;


/**
 * Class That handles the encrypion and decryption of the data.
 */
public class CryptoEn
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
   public CryptoEn(Path baseDir)
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
   public boolean isKeyExists()
   {
       return secretKey != null;
   }

   /**
    * true if key file exits  else false
    *
    * @return status
    */
   public boolean isKeyFileExits()
   {
        return Files.exists(this.baseDir.resolve(KEY_FILE_NAME));
   }

   /**
    * Generates the key and saves it to the key file.
    * 
    * @param password The password to be used for KeyStore.
    * @throws IOException if io fails
    * @throws GeneralSecurityException if error in creating
    */
   public void createNewKey(String password) throws IOException, GeneralSecurityException
   {
       KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
       var secretKey = keyGen.generateKey();

       KeyStore kStore = KeyStore.getInstance(KeyStore.getDefaultType());
       kStore.load(null, password.toCharArray());

       var skEntry = new KeyStore.SecretKeyEntry(secretKey);
       var prot  = new KeyStore.PasswordProtection(password.toCharArray());
       kStore.setEntry(KEY_ALIAS, skEntry, prot);

       var fileos = new FileOutputStream(baseDir.resolve(KEY_FILE_NAME).toFile());
       
       try {
            kStore.store (fileos, password.toCharArray());
       } finally {
            fileos.close();
       }

       this.secretKey = secretKey;
   }

   /**
    * Loads the key from the key file.
    * 
    * @param password The password to be used for KeyStore.
    * @throws IOException if io fails
    * @throws GeneralSecurityException if error in loading
    */
   public void loadExtistingKey(String password) throws IOException, GeneralSecurityException
   {
        var fileis = new FileInputStream(baseDir.resolve(KEY_FILE_NAME).toFile());
        var kStore = KeyStore.getInstance(KeyStore.getDefaultType());
       
        try {
            kStore.load(fileis, password.toCharArray());
        } finally {
            fileis.close();
        }

        var prot  = new KeyStore.PasswordProtection(password.toCharArray());        
        var skEntry = (KeyStore.SecretKeyEntry) kStore.getEntry(KEY_ALIAS, prot);
        this.secretKey = skEntry.getSecretKey();
   }

   /**
    * Changes the password for the key file.
    *
    * @param newPassword new password
    * @throws IOException if io fails
    * @throws GeneralSecurityException if error in loading
    */
   public void changeKeyPassword(String oldPassword, String newPassword) 
        throws IOException, GeneralSecurityException
   {
        var fileis = new FileInputStream(baseDir.resolve(KEY_FILE_NAME).toFile());
        var kStore = KeyStore.getInstance(KeyStore.getDefaultType());
        
        try {
            kStore.load(fileis, oldPassword.toCharArray());
        } finally {
            fileis.close();
        }

        var prot  = new KeyStore.PasswordProtection(oldPassword.toCharArray());
        var skEntry = (KeyStore.SecretKeyEntry) kStore.getEntry(KEY_ALIAS, prot);

        KeyStore newKStore = KeyStore.getInstance(KeyStore.getDefaultType());
        newKStore.load(null, newPassword.toCharArray());

        var newProt  = new KeyStore.PasswordProtection(newPassword.toCharArray());
        newKStore.setEntry(KEY_ALIAS, skEntry, newProt);

        var fileos = new FileOutputStream(baseDir.resolve(KEY_FILE_NAME).toFile());

        try {
            newKStore.store (fileos, newPassword.toCharArray());
        } finally {
            fileos.close();
        }
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
