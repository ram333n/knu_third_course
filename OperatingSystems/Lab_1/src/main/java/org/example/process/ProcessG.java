package org.example.process;

import org.example.Constants;

import java.io.IOException;

public class ProcessG {
    public static void main(String[] args) throws InterruptedException, IOException {
        new ProcessComputer(Constants.HOST, Constants.PORT, 'G');
    }
}
