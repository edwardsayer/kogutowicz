/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.anzix.kogutowicz.config;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import net.anzix.kogutowicz.app.TilesMap;

/**
 *
 * @author elek
 */
public class ConfigReader {

    private Properties props;

    private void setProperty(Object o, String propertyName, Object value, boolean convert) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BeanInfo bean = Introspector.getBeanInfo(o.getClass());
        for (PropertyDescriptor pdsc : bean.getPropertyDescriptors()) {
            if (pdsc.getName().equals(propertyName)) {
                if (convert) {
                    Object val = convertString((String) value, pdsc.getPropertyType());
                    pdsc.getWriteMethod().invoke(o, val);
                } else {
                    pdsc.getWriteMethod().invoke(o, value);
                }
                return;
            }
        }
        System.out.println("No such property: " + o.getClass().getCanonicalName() + " " + propertyName);
    }

    private Object convertString(String value, Class clazz) {

        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else if (clazz == Double.class) {
            return Double.valueOf(value);
        } else if (clazz == File.class) {
            return new File(value);
        } else {
            throw new IllegalArgumentException("No such value converter " + clazz);
        }
    }

    public void start(File propertyFile) throws Exception {

        FileInputStream fis = new FileInputStream(propertyFile);
        props = new Properties();
        props.load(fis);
        fis.close();

        Runnable o = (Runnable) createClass("map");
        ValidatorFactory fact = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<Runnable>> errors = fact.getValidator().validate(o);
        if (errors.size() > 0) {
            System.err.println("Error in configuration " + propertyFile.getAbsolutePath());
            for (ConstraintViolation<Runnable> error : errors) {
                System.err.println("map." + error.getPropertyPath() + " " + error.getMessage());
            }

        } else {

            o.run();
        }
    }

    private Object createClass(String k) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException {
        String clazzName = (String) props.get(k);
        Object o = Class.forName(clazzName).newInstance();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            if (keyStr.startsWith(k) && keyStr.length() > k.length() && !keyStr.substring(k.length() + 1).contains(".")) {
                String value = props.getProperty(keyStr);
                String propertyName = keyStr.substring(k.length() + 1);
                try {
                    if (value.startsWith("net") || value.startsWith("org") || value.startsWith("com")) {
                        getClass().getClassLoader().loadClass(value);
                        setProperty(o, propertyName, createClass(keyStr), false);
                        continue;
                    }
                } catch (Exception ex) {
                    System.out.println("no such class " + value);
                    ex.printStackTrace();
                }
                setProperty(o, propertyName, value, true);
            }
        }
        return o;
    }
}
