package org.example;

public class Process {
  public int pid;
  public int cpuTime;
  public int ioBlocking;
  public int cpuDone;
  public int ioNext;
  public int numBlocked;
  public long ticketsCount;

  public Process(int pid, int cpuTime, int ioBlocking, int cpuDone, int ioNext, int numBlocked, long ticketsCount) {
    this.pid = pid;
    this.cpuTime = cpuTime;
    this.ioBlocking = ioBlocking;
    this.cpuDone = cpuDone;
    this.ioNext = ioNext;
    this.numBlocked = numBlocked;
    this.ticketsCount = ticketsCount;
  }

  public boolean isCompleted() {
    return cpuDone >= cpuTime;
  }
}
