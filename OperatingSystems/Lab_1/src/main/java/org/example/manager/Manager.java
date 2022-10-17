package org.example.manager;

import org.example.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Manager {
    private AsynchronousServerSocketChannel serverChannel;
    private final int port;
    private final int argument;
    private Process processF;
    private Process processG;

    public Manager(int port, int argument) {
        this.port = port;
        this.argument = argument;
    }

    public void compute() throws IOException, ExecutionException, InterruptedException {
        openServer();
        //TODO: run processes
        //
        Future<AsynchronousSocketChannel> channelFutureF = serverChannel.accept();
        Future<AsynchronousSocketChannel> channelFutureG = serverChannel.accept();

        AsynchronousSocketChannel channelF = channelFutureF.get();
        AsynchronousSocketChannel channelG = channelFutureG.get();

        passArgument(channelF, channelG);

        ByteBuffer bufferF = ByteBuffer.allocate(2 * Integer.BYTES);
        ByteBuffer bufferG = ByteBuffer.allocate(2 * Integer.BYTES);

        Future<Integer> responseFutureF = channelF.read(bufferF);
        Future<Integer> responseFutureG = channelG.read(bufferG);

        //TODO: handle computations
    }

    private Process runProcess(String path) {
        return null; //TODO : impl it
    }

    private void passArgument(AsynchronousSocketChannel channelF,
                              AsynchronousSocketChannel channelG)
            throws ExecutionException, InterruptedException {

        ByteBuffer bufferF = ByteBuffer.allocate(Integer.BYTES);
        ByteBuffer bufferG = ByteBuffer.allocate(Integer.BYTES);

        bufferF.putInt(argument);
        bufferG.putInt(argument);

        bufferF.flip();
        bufferG.flip();

        Future<Integer> writeFutureF = channelF.write(bufferF);
        Future<Integer> writeFutureG = channelG.write(bufferG);

        writeFutureF.get();
        writeFutureG.get();

        bufferF.clear();
        bufferG.clear();
    }

    private void openServer() throws IOException {
        serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(Constants.HOST, port);
        serverChannel.bind(address);
    }
}
