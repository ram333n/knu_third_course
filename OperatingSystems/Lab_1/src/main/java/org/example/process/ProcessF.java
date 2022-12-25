package org.example.process;

import org.example.Constants;

import java.io.IOException;

public class ProcessF {
    public static void main(String[] args) throws InterruptedException, IOException {
        new ProcessComputer(Constants.HOST, Constants.PORT, 'F');
    }
}
