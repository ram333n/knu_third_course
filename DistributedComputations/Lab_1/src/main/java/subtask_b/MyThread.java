package subtask_b;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread {
    private final JSlider slider;
    private final int step;
    private final AtomicInteger semaphore;
    public MyThread(JSlider slider, int step, AtomicInteger semaphore) {
        this.slider = slider;
        this.step = step;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        if (!semaphore.compareAndSet(0, 1)){
            return;
        }

        while(!isInterrupted()) {
            try {
                sleep(100);
                int sliderValue = slider.getValue();
                boolean toMoveFromCorner = (sliderValue <= 10 && step > 0) || (sliderValue >= 90 && step < 0);
                if ((sliderValue > 10 && sliderValue < 90) || toMoveFromCorner ) {
                    slider.setValue(sliderValue + step);
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }
        semaphore.set(0);
    }
}