package org.example;

// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results run(int runtime, Vector<Process> processVector, Results result) {
    int i = 0;
    int compTime = 0;
    int currentProcessId = 0;
    int previousProcessId = 0;
    int size = processVector.size();
    int completed = 0;
    String resultsFile = "Summary-Processes";

    result.schedulingType = "Batch (Nonpreemptive)";
    result.schedulingName = "First-Come First-Served";

    try (PrintStream out = new PrintStream(new FileOutputStream(resultsFile))) {
      Process process = processVector.elementAt(currentProcessId);
      logProcess(out, process, "registered");

      while (compTime < runtime) {
        if (process.cpuDone == process.cpuTime) {
          completed++;
          logProcess(out, process, "completed");

          if (completed == size) {
            result.compuTime = compTime;
            return result;
          }

          for (i = size - 1; i >= 0; i--) {
            process = processVector.elementAt(i);
            if (process.cpuDone < process.cpuTime) {
              currentProcessId = i;
            }
          }

          process = processVector.elementAt(currentProcessId);
          logProcess(out, process, "registered");
        }

        if (process.ioBlocking == process.ioNext) {
          logProcess(out, process, "I/O blocked");

          process.numBlocked++;
          process.ioNext = 0;
          previousProcessId = currentProcessId;

          for (i = size - 1; i >= 0; i--) {
            process = processVector.elementAt(i);
            if (process.cpuDone < process.cpuTime && previousProcessId != i) {
              currentProcessId = i;
            }
          }

          process = processVector.elementAt(currentProcessId);
          logProcess(out, process, "registered");
        }

        process.cpuDone++;

        if (process.ioBlocking > 0) {
          process.ioNext++;
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

}
