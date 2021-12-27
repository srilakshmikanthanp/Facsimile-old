// Copyright (c) 2021 Sri Lakshmi Kanthan P
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package com.github.srilakshmikanthanp.facsimile.datum;


import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;
import javafx.util.Pair;


/**
 * Mapping of the datum key to the value objects.
 */
public class Mapping
{
    // Mappint change Event Listener
    public interface ChangeListener
    {
        void onChange(); // called on change
    }

    // datum file name
    private final String DATUM_FILE = "datum.json";

    // base dir to save
    private Path baseDir;

    // Crypto object
    private CryptoEn cryptoEn;

    // create data map
    private HashMap<String, String> data = new HashMap<>();

    // change listener
    private ArrayList<ChangeListener> cngLis = new ArrayList<>();

    /**
     * Notifies the change listeners.
     */
    private void notifyChange()
    {
        for (var lis : this.cngLis)
        {
            lis.onChange();
        }
    }

    /**
     * Saves the data to presistant storage.
     * 
     * @throws IOException is an io error occurs
     */
    private void saveData() throws IOException
    {
        Gson gson = new Gson();
        var json = gson.toJson(this.data);
        var path = baseDir.resolve(DATUM_FILE);
        Files.write(path, json.getBytes());
        this.notifyChange();
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
        this.data.putAll(gson.fromJson(json, HashMap.class));
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
        this.cryptoEn = new CryptoEn(baseDir);

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
    public CryptoEn getCryptoEn()
    {
        return this.cryptoEn;
    }

    /**
     * Get the value of the key.
     * 
     * @param key the key.
     * @return the value of the key.
     * @throws GeneralSecurityException if failed to decrypt.
     */
    public String get(String key) 
        throws GeneralSecurityException
    {
        var value = this.data.get(key);

        if(value == null)
        {
            return null;
        }

        return this.cryptoEn.decrypt(value);
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
    public String put(String key, String value) 
        throws GeneralSecurityException, IOException
    {
        var oldValue = this.data.put(key, this.cryptoEn.encrypt(value));
        
        this.saveData();
        
        if(oldValue == null)
        {
            return null;
        }

        return this.cryptoEn.decrypt(oldValue);
    }

    /**
     * removes the key from map
     * 
     * @param key kay to remove
     * @return old value
     * @throws GeneralSecurityException if failed to encrypt or decrypt.
     * @throws IOException if io error occurs while saving
     */
    public String remove(String key) 
        throws GeneralSecurityException, IOException
    {
        var oldValue = this.data.remove(key);

        this.saveData();
        
        if(oldValue == null)
        {
            return null;
        }

        return this.cryptoEn.decrypt(oldValue);
    }

    /**
     * replace the kay value pair
     * 
     * @param key Key to replace
     * @param value new value
     * @return oldvalue
     * @throws GeneralSecurityException if failed to encrypt or decrypt.
     * @throws IOException if io error occurs while saving
     */
    public String replace(String key, String value) 
        throws GeneralSecurityException, IOException
    {
        var oldValue = this.data.replace(key, this.cryptoEn.encrypt(value));
        this.saveData();
        return oldValue;
    }

    /**
     * returns the list of key value pair
     * 
     * @return list key value pair
     * @throws GeneralSecurityException if decrtption fails
     */
    ArrayList<Pair<String, String>> getData() 
        throws GeneralSecurityException
    {
        ArrayList<Pair<String, String>> list = new ArrayList<>();

        for(String key: this.data.keySet())
        {
            list.add(new Pair<>(
                key, this.cryptoEn.decrypt(this.data.get(key))
            ));
        }

        return list;
    }

    /**
     * Add change listener
     * 
     * @param listener listener to add
     */
    public void addChangeListener(ChangeListener listener)
    {
        this.cngLis.add(listener);
    }

    /**
     * Remove change listener
     * 
     * @param listener listener to remove
     */
    public void removeChangeListener(ChangeListener listener)
    {
        this.cngLis.remove(listener);
    }
}
