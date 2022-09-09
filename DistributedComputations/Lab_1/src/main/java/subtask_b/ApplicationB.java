package subtask_b;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ApplicationB {
    private static final JSlider slider = new JSlider();
    private static AtomicInteger semaphore = new AtomicInteger(0);
    private static MyThread firstThread;
    private static MyThread secondThread;
    private static void setGUI() {
        JFrame frame = new JFrame("ApplicationB");

        slider.setBounds(40, 40 ,420, 40);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(10);


        JButton buttonStartFirstThread = new JButton("Start 1");
        buttonStartFirstThread.setBounds(60, 150,100, 50);

        JButton buttonStopFirstThread = new JButton("Stop 1");
        buttonStopFirstThread.setBounds(60, 210,100, 50);
        buttonStopFirstThread.setEnabled(false);

        JButton buttonStartSecondThread = new JButton("Start 2");
        buttonStartSecondThread.setBounds(335, 150,100, 50);

        JButton buttonStopSecondThread = new JButton("Stop 2");
        buttonStopSecondThread.setBounds(335, 210,100, 50);
        buttonStopSecondThread.setEnabled(false);

        JLabel label = new JLabel("Critical region is occupied!");
        label.setBounds(170, 300, 150,15);
        label.setVisible(false);

        buttonStartFirstThread.addActionListener(e -> {
            buttonStopFirstThread.setEnabled(true);
            buttonStartFirstThread.setEnabled(false);
            buttonStartSecondThread.setEnabled(false);
            buttonStopSecondThread.setEnabled(false);
            label.setVisible(true);

            firstThread = new MyThread(slider, -1, semaphore);
            firstThread.start();
        });

        buttonStartSecondThread.addActionListener(e -> {
            buttonStopSecondThread.setEnabled(true);
            buttonStartSecondThread.setEnabled(false);
            buttonStartFirstThread.setEnabled(false);
            buttonStopFirstThread.setEnabled(false);
            label.setVisible(true);

            secondThread  = new MyThread(slider, 1, semaphore);
            secondThread.start();
        });

        buttonStopFirstThread.addActionListener(e -> {
            buttonStopFirstThread.setEnabled(false);
            buttonStartFirstThread.setEnabled(true);
            buttonStartSecondThread.setEnabled(true);
            label.setVisible(false);

            firstThread.interrupt();
        });

        buttonStopSecondThread.addActionListener(e -> {
            buttonStopSecondThread.setEnabled(false);
            buttonStartFirstThread.setEnabled(true);
            buttonStartSecondThread.setEnabled(true);
            label.setVisible(false);

            secondThread.interrupt();
        });

        frame.add(slider);
        frame.add(buttonStartFirstThread);
        frame.add(buttonStopFirstThread);
        frame.add(buttonStartSecondThread);
        frame.add(buttonStopSecondThread);
        frame.add(label);

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        setGUI();
    }
}
