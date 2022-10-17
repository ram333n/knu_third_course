package org.example;

public class Request {
    private final int amount;
    private final boolean isWithdrawal;

    public Request(int amount, boolean isWithdrawal) {
        this.amount = amount;
        this.isWithdrawal = isWithdrawal;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isWithdrawal() {
        return isWithdrawal;
    }

    @Override
    public String toString() {
        return "Request{" +
                "amount=" + amount +
                ", isWithdrawal=" + isWithdrawal +
                '}';
    }
}
