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
        openServer();

        Signal.handle(new Signal("INT"), signal -> toCancel = true);

        runProcess(Constants.PROCESS_F_PATH);
        runProcess(Constants.PROCESS_G_PATH);

        Future<AsynchronousSocketChannel> channelFutureF = serverChannel.accept();
        Future<AsynchronousSocketChannel> channelFutureG = serverChannel.accept();

        AsynchronousSocketChannel channelF = channelFutureF.get();
        AsynchronousSocketChannel channelG = channelFutureG.get();

        passArgument(channelF, channelG);

        ByteBuffer bufferF = ByteBuffer.allocate(2 * Integer.BYTES);
        ByteBuffer bufferG = ByteBuffer.allocate(2 * Integer.BYTES);

        Future<Integer> responseFutureF = channelF.read(bufferF);
        Future<Integer> responseFutureG = channelG.read(bufferG);

        handleFunctionComputation(responseFutureF, bufferF, resultF, 'F');
        handleFunctionComputation(responseFutureG, bufferG, resultG, 'G');

        if (!toCancel) {
            int result = Math.min(resultF.result, resultG.result);
            System.out.printf("[INFO] Successfully computed. Value : %d%n", result);
        }

        serverChannel.close();
    }

    private void openServer() throws IOException {
        serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(Constants.HOST, port);
        serverChannel.bind(address);
    }

    private Process runProcess(String path) throws IOException {
        return Runtime.getRuntime().exec(String.format("java -cp %s %s", Constants.PATH_TO_JAR, path));
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

    private void handleFunctionComputation(Future<Integer> future,
                                           ByteBuffer buffer,
                                           ComputationOutput computationOutput,
                                           char functionName) {
        boolean isPrinted = toCancel;

        while (!isPrinted) {
            if (toCancel) {
                System.out.println("[INFO] Computation canceled. Terminate.");
                break;
            }

            if (!future.isDone()) {
                continue;
            }

            computationOutput = new ComputationOutput(buffer);

            if(computationOutput.computationCode == 0) {
                System.out.printf("[INFO] Function %s result is %d%n", functionName, computationOutput.result);
                isPrinted = true;
            } else {
                System.out.printf("[HARD FAIL] Function %s failed on given input. Terminate.%n", functionName);
                toCancel = true;
            }
        }

        if(toCancel) {
            destroyProcesses();
        }
    }

    private void destroyProcesses() {
        processF.destroy();
        processG.destroy();
    }
}
