package org.example.subtask_a;

import javax.swing.*;

public class MyThread extends Thread {
    private final JSlider slider;
    private final int step;
    public MyThread(JSlider slider, int step) {
        this.slider = slider;
        this.step = step;
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            synchronized (slider) {
                try {
                    sleep(100);
                    int sliderValue = slider.getValue();
                    if(sliderValue > 10 && sliderValue < 90) {
                        slider.setValue(sliderValue + step);
                    } else {
                        interrupt();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupted thread with id : " + getId());
                }
            }
        }
        System.out.println("Finished thread with id : " + getId());
    }
}
