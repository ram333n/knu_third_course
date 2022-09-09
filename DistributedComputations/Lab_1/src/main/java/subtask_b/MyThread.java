package subtask_b;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

//B
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
                if (sliderValue > 10 && sliderValue < 90) {
                    slider.setValue(sliderValue + step);
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted thread with id : " + getId());
            }
        }
        semaphore.set(0);
    }
}