package org.example.manager;

import org.example.Constants;
import sun.misc.Signal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Manager {
    private AsynchronousServerSocketChannel serverChannel;
    private final int port;
    private final int argument;
    private Process processF;
    private Process processG;
    private boolean toCancel;
    private ComputationOutput resultF;
    private ComputationOutput resultG;

    public Manager(int port, int argument) {
        this.port = port;
        this.argument = argument;
        this.toCancel = false;
        this.resultF = null;
        this.resultG = null;
    }

    private static class ComputationOutput {
        final int computationCode;
        final int result;

        public ComputationOutput(ByteBuffer buffer) {
            buffer.rewind();
            this.computationCode = buffer.getInt();
            this.result = buffer.getInt();
        }
    }

    public void compute() throws IOException, ExecutionException, InterruptedException {
        Signal.handle(new Signal("INT"), signal -> {
            System.out.println("[INFO]\tComputation cancelled. Terminate.");
            destroyProcesses();

            try {
                serverChannel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.exit(0);
        });

        openServer();
        System.out.println("[INFO]\tOpened server");

        AsynchronousSocketChannel channelF = runProcess((process -> processF = process), Constants.PROCESS_F_PATH);
        System.out.println("[INFO]\tF connected");
        AsynchronousSocketChannel channelG = runProcess((process -> processG = process), Constants.PROCESS_G_PATH);
        System.out.println("[INFO]\tG connected");

        passArgument(channelF, channelG);
        System.out.println("[INFO]\tPassed arguments to clients");

        ByteBuffer bufferF = ByteBuffer.allocate(2 * Integer.BYTES);
        ByteBuffer bufferG = ByteBuffer.allocate(2 * Integer.BYTES);

        Future<Integer> responseFutureF = channelF.read(bufferF);
        Future<Integer> responseFutureG = channelG.read(bufferG);

        boolean isPrintedF = false;
        boolean isPrintedG = false;

        while ((!isPrintedF || !isPrintedG) && !toCancel) {
            if (responseFutureF.isDone() && !isPrintedF) {
                resultF = new ComputationOutput(bufferF);

                if(resultF.computationCode == 0) {
                    System.out.printf("[INFO]\tFunction F result is %d%n", resultF.result);
                    isPrintedF = true;
                } else {
                    System.out.println("[HARD FAIL] Function F failed on given input. Terminate.");
                    toCancel = true;
                }
            }

            if (responseFutureG.isDone() && !isPrintedG) {
                resultG = new ComputationOutput(bufferG);

                if(resultG.computationCode == 0) {
                    System.out.printf("[INFO]\tFunction G result is %d%n", resultG.result);
                    isPrintedG = true;
                } else {
                    System.out.println("[HARD FAIL] Function G failed on given input. Terminate.");
                    toCancel = true;
                }
            }
        }

        if (!toCancel) {
            int result = Math.min(resultF.result, resultG.result);
            System.out.printf("[INFO]\tSuccessfully computed. Result : %d%n", result);
        } else {
            destroyProcesses();
        }

        serverChannel.close();
    }

    private void openServer() throws IOException {
        serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(Constants.HOST, port);
        serverChannel.bind(address);
    }

    private AsynchronousSocketChannel runProcess(Consumer<Process> setter, String path)
            throws IOException, ExecutionException, InterruptedException {
        Process process = Runtime.getRuntime().
                exec(String.format("java -cp %s %s", Constants.PATH_TO_JAR, path));

        setter.accept(process);
        Future<AsynchronousSocketChannel> channelFuture = serverChannel.accept();

        return channelFuture.get();
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

    private void destroyProcesses() {
        if (processF != null) {
            processF.destroy();
        }

        if (processG != null) {
            processG.destroy();
        }
    }
}