package org.example;

public class Observer implements Runnable {
    private final Vault vault;

    public Observer(Vault vault) {
        this.vault = vault;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(Constants.DURATION / 2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (vault.isFull(100)) {
                int vaultMoney = vault.getCurrentAmount();
                vault.withdraw(vaultMoney / 2, -1);
                System.out.println("Observer withdrew money");
            } else if (vault.isEmpty()) {
                vault.add(vault.getCapacity() / 2, -1);
                System.out.println("Observer added money");
            }
        }
    }
}
