package org.example;

// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results run(int runtime, Vector<Process> processVector, Results result) {
    int compTime = 0;
    int currentProcessIdx = 0;
    int size = processVector.size();
    int completed = 0;

    String resultsFile = "Summary-Processes";
    result.schedulingType = "Interactive (Non-preemptive)";
    result.schedulingName = "Lottery";

    try (PrintStream out = new PrintStream(new FileOutputStream(resultsFile))) {
      Lottery lottery = new Lottery(processVector);
      currentProcessIdx = lottery.performLottery(processVector);
      Process currentProcess = processVector.elementAt(currentProcessIdx);
      logProcess(out, currentProcess, "registered");

      while (compTime < runtime) {
        if (currentProcess.isCompleted()) {
          completed++;
          logProcess(out, currentProcess, "completed");

          if (completed == size) {
            result.compuTime = compTime;
            return result;
          }

          currentProcessIdx = getNextProcess(lottery, processVector);
          currentProcess = processVector.elementAt(currentProcessIdx);
          logProcess(out, currentProcess, "registered");
        }

        if (currentProcess.ioBlocking == currentProcess.ioNext) {
          logProcess(out, currentProcess, "I/O blocked");

          currentProcess.numBlocked++;
          currentProcess.ioNext = 0;

          currentProcessIdx = getNextProcess(lottery, processVector);

          currentProcess = processVector.elementAt(currentProcessIdx);
          logProcess(out, currentProcess, "registered");
        }

        currentProcess.cpuDone++;

        if (currentProcess.ioBlocking > 0) {
          currentProcess.ioNext++;
        }

        compTime++;
      }
    } catch (IOException e) {
      /* Handle exceptions */
    }

    result.compuTime = compTime;
    return result;
  }

  private static void logProcess(PrintStream out, Process process, String state) {
    out.println(String.format("Process: %d %s...(%d %d %d) ",
        process.pid,
        state,
        process.cpuTime,
        process.ioBlocking,
        process.cpuDone
    ));
  }

  private static int getNextProcess(Lottery lottery, Vector<Process> processes) {
    while (true) {
      int idx = lottery.performLottery(processes);
      Process current = processes.elementAt(idx);

      if (!current.isCompleted()) {
        return idx;
      }
    }
  }
}
