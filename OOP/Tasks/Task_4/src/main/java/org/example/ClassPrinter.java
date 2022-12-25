package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassPrinter {
    private final Class<?> classData;

    public ClassPrinter(String name) {
        try {
            CustomClassLoader loader = new CustomClassLoader();
            classData = loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void printAll() {
        printName();
        printPackage();
        printSuperClasses();
        printImplementedInterfaces();
        printFields();
        printConstructors();
        printMethods();
    }

    public void printName() {
        System.out.println("----------------------Name----------------------");
        System.out.println(classData.getName());
    }

    public void printPackage() {
        System.out.println("----------------------Package----------------------");
        System.out.println(classData.getPackage().getName());
    }

    public void printSuperClasses() {
        Class<?> currentSuperClass = classData.getSuperclass();

        System.out.println("----------------------Super classes----------------------");

        while(currentSuperClass != null) {
            System.out.println(currentSuperClass.getName());
            currentSuperClass = currentSuperClass.getSuperclass();
        }
    }

    public void printImplementedInterfaces() {
        Class<?>[] interfaces = classData.getInterfaces();

        System.out.println("----------------------Implemented interfaces----------------------");

        for (Class<?> implInterface : interfaces) {
            System.out.println(implInterface.getName());
        }
    }

    public void printFields() {
        System.out.println("----------------------Fields----------------------");

        Field[] fields = classData.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field);
        }
    }

    public void printConstructors() {
        System.out.println("----------------------Constructors----------------------");

        Constructor<?>[] constructors = classData.getConstructors();

        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
    }

    public void printMethods() {
        System.out.println("----------------------Methods----------------------");

        Method[] methods = classData.getMethods();

        for (Method method : methods) {
            System.out.println(method);
        }
    }

    public Class<?> getClassData() {
        return classData;
    }
}
