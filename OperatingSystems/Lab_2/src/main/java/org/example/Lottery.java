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

  public int performLottery(Vector<Process> processes) {
    long luckyTicket = random.nextLong(ticketsCount) + 1;
    long checkedTickets = 0;

    for (int i = 0; i < processes.size(); i++) {
      Process process = processes.elementAt(i);

      checkedTickets += process.ticketsCount;

      if (luckyTicket <= checkedTickets) {
        return i;
      }
    }

    return -1;
  }
}
