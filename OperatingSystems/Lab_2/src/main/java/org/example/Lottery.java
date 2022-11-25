package org.example;

import java.util.Random;
import java.util.Vector;

public class Lottery {
  private static final Random random = new Random();
  private long ticketsCount = 0;

  public Lottery(long ticketsCount) {
    this.ticketsCount = ticketsCount;
  }

  public Lottery(Vector<Process> processes) {
    for (Process process : processes) {
      ticketsCount += process.ticketsCount;
    }
  }

  public long getTicketsCount() {
    return ticketsCount;
  }

  public void setTicketsCount(long ticketsCount) {
    this.ticketsCount = ticketsCount;
  }

  public int performLottery(Vector<Process> processes) {
    long luckyTicket = random.nextLong(ticketsCount) + 1;
    long checkedTickets = 0;

    for (Process process : processes) {
      if (process.isCompleted()) {
        continue;
      }

      checkedTickets += process.ticketsCount;

      if (luckyTicket <= checkedTickets) {
        return process.pid;
      }
    }

    return -1;
  }
}
