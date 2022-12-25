package org.example;

import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertEquals;

class CustomReentrantLockTest {
    @Test
    void testReentrantLock() {
        TestClass testClass = new TestClass();

        Thread t1 = new Thread(testClass::increment);
        Thread t2 = new Thread(testClass::increment);
        Thread t3 = new Thread(testClass::increment);
        Thread t4 = new Thread(testClass::increment);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(4, testClass.getCounter());
    }
}