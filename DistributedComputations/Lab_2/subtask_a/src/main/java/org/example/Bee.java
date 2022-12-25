package org.example;

public class Bee extends Thread {
    private final Manager manager;

    public Bee(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        while(true) {
            int rowIdx = manager.getTask();
            if(rowIdx == -1) {
                System.out.printf("Bee %d finished its work%n", getId());
                break;
            }

            boolean[] row = manager.getField()[rowIdx];
            System.out.printf("Bee %d started checking row %d%n", getId(), rowIdx);

            for(int i = 0; i < row.length; i++) {
                try {
                    sleep(Constants.SLEEP_INTERVAL);
                    if(row[i]) {
                        manager.bearFound();
                        System.out.printf("Bee %d found bear at (%d, %d)%n", getId(), rowIdx, i);
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.printf("Bee %d finished checking row %d%n", getId(), rowIdx);
        }
    }
}