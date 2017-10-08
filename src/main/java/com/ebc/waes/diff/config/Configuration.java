package com.ebc.waes.diff.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is responsible to provide the configurations used in the application
 * @version 1.0
 * @author eduardo.costa
 * @since 06/10/2017
 */
public class Configuration {

    private Properties properties;

    public static final String DIFF_STORE_NAME_PROP="diff.store.name";
    public static final String DIFF_STORE_FOLDER_PROP="diff.store.folder";

    private static Logger logger = LoggerFactory.getLogger(Configuration.class);

    private static Configuration instance;

    public static Configuration getInstance(){
        if(instance == null){
            instance = new Configuration();
            instance.create();
        }
        return instance;
    }

    private void create(){
        InputStream resource = Configuration.class.getClassLoader().getResourceAsStream("diff.properties");
        this.properties = new Properties();
        try{
            properties.load(resource);
        } catch (Exception e){
            logger.warn("configuration file didn't load!", e);
        }
    }

    /**
     * Get the value of a property in the configuration file
     * @param key the identifier of the property
     * @return a String with the value
     */
    public String getProperty(String key){
        if (properties == null){
            return null;
        }
        if(System.getProperties().getProperty(key) != null){
            return System.getProperties().getProperty(key);
        }else {
            return properties.getProperty(key);
        }
    }

    /**
     * Get the information about the store directory
     * @return a String with the path of the file system
     */
    public String getStoreDir(){
        StringBuilder builder = new StringBuilder();
        String baseFolder = getProperty(DIFF_STORE_FOLDER_PROP);
        if(StringUtils.isEmpty(baseFolder)){
            builder.append(System.getProperty("java.io.tmpdir"));
        }else{
            builder.append(baseFolder);
        }
        builder.append(File.separator);
        builder.append(getProperty(DIFF_STORE_NAME_PROP));
        return builder.toString();
    }
}
