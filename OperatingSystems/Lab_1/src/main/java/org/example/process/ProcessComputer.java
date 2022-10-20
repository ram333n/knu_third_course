package org.example.process;

import os.lab1.compfuncs.basic.IntOps;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Optional;

public class ProcessComputer {
    private SocketChannel socketChannel;
    private final char functionName;
    private Optional<Integer> result;

    public ProcessComputer(String host, int port, char functionName) throws IOException, InterruptedException {
        connectToManager(host, port);

        this.functionName = functionName;
        int argument = parseArgument();
        compute(argument);

        int computationCode = 0;

        if (result.isEmpty()) {
            computationCode = -1;
        }

        sendResult(computationCode);
    }

    private void connectToManager(String host, int port) throws IOException {
        socketChannel = SocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(host, port);
        socketChannel.connect(address);
    }

    private int parseArgument() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        socketChannel.read(buffer);
        buffer.rewind();
        return buffer.getInt();
    }

    private void compute(int argument) throws InterruptedException {
        switch (Character.toUpperCase(functionName)) {
            case 'F' -> result = IntOps.trialF(argument);
            case 'G' -> result = IntOps.trialG(argument);

            default ->
                throw new IllegalArgumentException("Invalid name function provided");
        }
    }

    private void sendResult(int computationCode) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(2 * Integer.BYTES);
        buffer.putInt(computationCode);
        buffer.putInt(result.orElse(0));
        buffer.rewind();
        socketChannel.write(buffer);
        socketChannel.close();
    }
}
