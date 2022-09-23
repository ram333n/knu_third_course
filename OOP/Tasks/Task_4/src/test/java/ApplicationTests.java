import org.example.ClassPrinter;
import org.example.CustomClassLoader;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTests {
    @Test
    void testClassLoaderOnNonJavaClass() {
        CustomClassLoader loader = new CustomClassLoader();

        try {
            Class<?> loadedClass = loader.loadClass("org.example.TestClass");
            Field[] fields = loadedClass.getDeclaredFields();

            List<String> expectedFieldsNames = List.of("name", "age", "date");
            List<String> actualFieldsNames = new ArrayList<>();

            for (Field field : fields) {
                actualFieldsNames.add(field.getName());
            }

            int classModifiers = loadedClass.getModifiers();

            assertTrue(Modifier.isPublic(classModifiers));
            assertFalse(Modifier.isAbstract(classModifiers));
            assertTrue(Modifier.isFinal(classModifiers));

            assertEquals(expectedFieldsNames, actualFieldsNames);
            assertEquals(Serializable.class, loadedClass.getInterfaces()[0]);
        } catch (ClassNotFoundException e) {
            fail();
        }

    }

    @Test
    void testClassLoaderOnJavaClass() {
        CustomClassLoader loader = new CustomClassLoader();

        try {
            Class<?> loadedClass = loader.loadClass("java.util.ArrayList");

            assertEquals(AbstractList.class, loadedClass.getSuperclass());

            int classModifiers = loadedClass.getModifiers();

            assertTrue(Modifier.isPublic(classModifiers));
            assertFalse(Modifier.isAbstract(classModifiers));
            assertFalse(Modifier.isFinal(classModifiers));

            assertEquals(4, loadedClass.getInterfaces().length);
        } catch (ClassNotFoundException e) {
            fail();
        }
    }

    @Test
    void testPrint() {
        ClassPrinter printer = new ClassPrinter("org.example.TestClass");
        printer.printAll();
    }
}