package org.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(name.startsWith("java.")) { //we can't load classes from java.* by custom loader(will throw SecurityException)
            return super.loadClass(name);
        }

        byte[] classData = loadClassData(name);
        return defineClass(name, classData, 0, classData.length);
    }

    private byte[] loadClassData(String name) {
        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(name.replace(".", "/") + ".class");) {

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int current = 0;

            while((current = input.read()) != -1) {
                output.write(current);
            }

            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
