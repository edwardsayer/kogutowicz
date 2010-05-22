package net.anzix.kogutowicz.config;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import net.anzix.kogutowicz.RenderException;
import net.anzix.kogutowicz.app.MapApplication;
import net.anzix.kogutowicz.processor.RenderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Build object hierarchy based on a property file.
 * 
 * @author elek
 */
public class ConfigReader {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String BASE_NAME = "map";

    private Properties props = new Properties();

    private Injector injector;

    private ClassLoader loader;

    @Inject
    private RenderContext context;

    public ConfigReader(Properties props) {
        this();
        this.props = props;

    }

    public ConfigReader(File propertyFile) {
        this();
        processProperties(propertyFile);
    }

    public ConfigReader() {
        initGuice();
        this.loader = getClass().getClassLoader();
    }

    public ConfigReader(File propFile, ClassLoader loader) {
        this(propFile);
        this.loader = loader;
    }

    public void initGuice() {
        injector = Guice.createInjector(new IocModule());
        injector.injectMembers(this);
    }

    private void setProperty(String key, Object o, String propertyName, Object value) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.getName().equals(propertyName)) {
                Object val = convertFromString(key, propertyName, (String) value, field.getType());
                if (val != null) {
                    field.setAccessible(true);
                    field.set(o, val);
                }
                return;
            }
        }
//        BeanInfo bean = Introspector.getBeanInfo(o.getClass());
//        for (PropertyDescriptor pdsc : bean.getPropertyDescriptors()) {
//            if (pdsc.getName().equals(propertyName)) {
//                Object val = convertFromString(key, propertyName, (String) value, pdsc.getPropertyType());
//                if (val != null) {
//                    pdsc.getWriteMethod().invoke(o, val);
//                }
//
//                return;
//            }
//        }
        System.out.println("No such property: " + o.getClass().getCanonicalName() + " " + propertyName);
    }

    /**
     * Convert a property String to a file instance object.
     *
     * @param property The property key. eg. map.dataSource.location
     * @param key The property field name eg. location
     * @param value The property field value eg. /tmp/test
     * @param clazz the field type eg. File.class
     * @return
     */
    protected Object convertFromString(String property, String key, String value, Class clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == Double.class) {
            return Double.valueOf(value);
        } else if (clazz == Float.class) {
            return Float.valueOf(value);
        } else if (clazz == File.class) {
            return context.getAbsolutePath(value);
        } else {
            try {
                return createClass(property, clazz);
            } catch (Exception ex) {
                logger.error("Error on processing property value: " + key + "=" + value, ex);
                return null;
            }
        }
    }

    protected Class findImplementation(Class ifce, String name, ClassLoader classloader) throws IOException, ClassNotFoundException {
        Enumeration<URL> urls = classloader.getResources("META-INF/services/" + ifce.getCanonicalName());
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            System.out.println("URL " + url);
            InputStream is = url.openStream();
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                while (reader.ready()) {
                    String line = reader.readLine().trim();
                    if (line.endsWith("." + name)) {
                        return classloader.loadClass(line);
                    }
                }
            }
            is.close();
        }
        return null;
    }

    protected Class findImplementation(Class ifce, String name) throws IOException, ClassNotFoundException {
        return findImplementation(ifce, name, loader);
    }

    public MapApplication start() {
        try {
            MapApplication o = (MapApplication) createClass(BASE_NAME, MapApplication.class);
            ValidatorFactory fact = Validation.buildDefaultValidatorFactory();
            Set<ConstraintViolation<MapApplication>> errors = fact.getValidator().validate(o);
            if (errors.size() > 0) {
                for (ConstraintViolation<MapApplication> error : errors) {
                    System.err.println(BASE_NAME + "." + error.getPropertyPath() + " " + error.getMessage());
                }
            } else {
                o.run();
            }
            return o;
        } catch (Exception ex) {
            String message = "Error on render execution";
            logger.error(message, ex);
            throw new RenderException(message, ex);
        }
    }

    /**
     * Create a class and fill all the field based on the properties.
     *
     * @param the perfix for the field properties and the class name
     * @return the new instance
     */
    public Object createClass(String k, Class interfaceClass) throws IOException, ClassNotFoundException {
        Class clz = findImplementation(interfaceClass, props.getProperty(k));
        if (clz != null) {
            return createClass(k, clz.getCanonicalName());
        } else {
            return createClass(k, props.getProperty(k));
        }
    }

    /**
     * Create a class and fill all the field based on the properties.
     * 
     * @param clazzName the fully qualified java class name
     * @param the perfix for the field properties
     * @return the new instance
     */
    private Object createClass(String k, String clazzName) {
        try {
            Class clazz = loader.loadClass(clazzName);
            if (clazz == null) {
                throw new FieldInitializationException("No such class " + clazzName);
            }
            Object o = injector.getInstance(clazz);
            //iterate over properties
            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                //find filed settings
                if (keyStr.startsWith(k) && keyStr.length() > k.length() && !keyStr.substring(k.length() + 1).contains(".")) {
                    String value = props.getProperty(keyStr);
                    String propertyName = keyStr.substring(k.length() + 1);
                    setProperty(key.toString(), o, propertyName, value);
                }
            }
            return o;
        } catch (Exception ex) {
            throw new FieldInitializationException(ex);
        }
    }

    public void processProperties(File propertyFile) {
        if (!propertyFile.exists()) {
            throw new IllegalArgumentException("File not found: " + propertyFile.getAbsolutePath());
        }
        context.setBasedir(propertyFile.getParentFile());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(propertyFile);
            props = new Properties();
            props.load(fis);
            fis.close();
            logger.debug("property file {} is loaded", propertyFile.getAbsolutePath());
        } catch (Exception ex) {
            throw new FieldInitializationException("Error on read property file " + propertyFile.getAbsolutePath(), ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                throw new FieldInitializationException("Error on closing property file.", ex);
            }
        }

    }
}
