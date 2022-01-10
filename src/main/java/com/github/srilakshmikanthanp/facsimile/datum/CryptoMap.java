package com.github.srilakshmikanthanp.facsimile.datum;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Map;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.function.BiFunction;

import java.security.KeyStore;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 * This class Provides the Map with the encrypt/decrypt functions.
 */
public class CryptoMap extends HashMap<String, String> {
    /**
     * The Method call on data change
     */
    public static interface MapChangeListener {
        // The type of Operation
        public static final int CHANGED = 0;
        public static final int REMOVED = 1;

        /**
         * The Method call on data change
         *
         * @param key The Key
         * @param value The Value
         * @param type The Type
         */
        void onChange(int type, Object key, Object value);
    }

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

    // List of DataChangeListener
    private ArrayList<MapChangeListener> listeners = new ArrayList<>();

    /**
     * This method is used to encrypt the data.
     * 
     * @param data Data to be encrypted
     * @return Encrypted data
     * @throws GeneralSecurityException if the encryption fails
     */
    private String encrypt(String data) throws GeneralSecurityException {
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
    private String decrypt(String data) throws GeneralSecurityException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data));
        return new String(decrypted);
    }

    /**
     * This method is used to notify the listeners.
     */
    private void notifyListeners(int type, Object key, Object value) {
        for (MapChangeListener listener : listeners) {
            listener.onChange(type, key, value);
        }
    }

    /**
     * Constructor for the Crypto Hash Map.
     * 
     * @param basePath Base path to store data
     */
    public CryptoMap(Path basePath) {
        this.basePath = basePath;
    }

    /**
     * Checks if the key is present in the map.
     */
    public boolean isSecrectKeyExits() {
        return this.secretKey != null;
    }

    /**
     * Is the Key file Exits
     */
    public boolean isKsFileExists() {
        return basePath.resolve(
            KEY_STORE_FILE
        ).toFile().exists();
    }

    /**
     * Is the Json file Exits
     */
    public boolean isJsonFileExists() {
        return basePath.resolve(
            DATA_JSON_FILE
        ).toFile().exists();
    }

    /**
     * Method to create new KeyStore file.
     * 
     * @param Password password
     * @throws IOException              IO fails
     * @throws GeneralSecurityException crypto fails
     */
    public void makeCrypto(String password) 
        throws IOException, GeneralSecurityException {
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
     * @throws IOException              IO fails
     * @throws GeneralSecurityException crypto fails
     */
    public void loadCrypto(String password) 
        throws IOException, GeneralSecurityException {
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
     * @throws IOException              IO fails
     * @throws GeneralSecurityException crypto fails
     */
    public void changePassword(String oldPassword, String newPassword) 
        throws IOException, GeneralSecurityException {
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
     * Method that clears the Map
     */
    @Override
    public void clear() {
        for(var key : this.keySet()) {
            this.remove(key);
        }
    }

    /**
     * Method to add a new entry to the map.
     * 
     * @param key   Key
     * @param value Value
     */
    @Override
    public String put(String key, String value) {
        var oldVal = super.put(key, value);
        this.notifyListeners(
            MapChangeListener.CHANGED, key, value
        );
        return oldVal;
    }

    /**
     * Methos that add all the entries to the map.
     * 
     * @param m Map
     */
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        super.putAll(m);
        for (var e : m.entrySet()) {
            this.notifyListeners(
                MapChangeListener.CHANGED, e.getKey(), e.getValue()
            );
        }
    }

    /**
     * Method to add a new entry to the map if absent.
     * 
     * @param key   Key
     * @param value Value
     */ 
    public String pufIfAbsent(String key, String value) {
        var oldVal = super.putIfAbsent(key, value);
        if (oldVal == null) {
            this.notifyListeners(
                MapChangeListener.CHANGED, key, value
            );
        }
        return oldVal;
    }

    /**
     * Method to remove an entry from the map.
     * 
     * @param key Key
     */
    @Override
    public String remove(Object key) {
        var oldVal = super.remove(key);
        this.notifyListeners(
            MapChangeListener.REMOVED, key.toString(), oldVal
        );
        return oldVal;
    }

    /** 
     * Method to remove an entry from the map.
     * 
     * @param key Key
     * @param value Value
     */
    @Override
    public boolean remove(Object key, Object value) {
        var oldVal = super.remove(key, value);
        if (oldVal == true) {
            this.notifyListeners(
                MapChangeListener.REMOVED, key, value
            );
        }
        return oldVal;
    }

    /**
     * Method to replace the entries from the map.
     */
    @Override
    public String replace(String key, String value) {
        var oldVal = super.replace(key, value);
        this.notifyListeners(
            MapChangeListener.CHANGED, key, value
        );
        return oldVal;
    }

    /**
     * Method to replace the entries from the map if the old value is equal to the new value.
     */
    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        var oldVal = super.replace(key, oldValue, newValue);
        if (oldVal == true) {
            this.notifyListeners(
                MapChangeListener.CHANGED, key, newValue
            );
        }
        return oldVal;
    }

    /**
     * Methos to replace with a new entry to the map.
     */
    @Override
    public void replaceAll(BiFunction<? super String, ? super String, ? extends String> function) {
        super.replaceAll(function);
        for (var e : this.entrySet()) {
            this.notifyListeners(MapChangeListener.CHANGED, e.getKey(), e.getValue());
        }
    }

    /**
     * Method to save the Json file.
     * 
     * @throws IOException IO fails
     * @throws GeneralSecurityException if the encryption fails
     */
    public void saveJson() throws IOException, GeneralSecurityException {
        Gson gson = new Gson();
        var json = gson.toJson(this);
        var path = basePath.resolve(DATA_JSON_FILE);
        Files.write(path, this.encrypt(json.toString()).getBytes());
    }

    /**
     * Method to load the Json file.
     * 
     * @throws IOException IO fails
     * @throws GeneralSecurityException if the encryption fails
     */
    @SuppressWarnings("unchecked")
    public void loadJson() throws IOException, GeneralSecurityException {
        Gson gson = new Gson();
        var path = basePath.resolve(DATA_JSON_FILE);
        var json = this.decrypt(Files.readString(path));
        this.putAll(gson.fromJson(json, HashMap.class));
    }

    /**
     * Add the change Listener
     */
    public void addMapChangeListener(MapChangeListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Remove the change Listener
     */
    public void removeMapChangeListener(MapChangeListener listener) {
        this.listeners.remove(listener);
    }
}
