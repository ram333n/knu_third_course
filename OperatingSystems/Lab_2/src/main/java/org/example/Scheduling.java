package org.example;

// This file contains the main() function for the Scheduling
// simulation.  Init() initializes most of the variables by
// reading from a provided file.  SchedulingAlgorithm.Run() is
// called from main() to run the simulation.  Summary-Results
// is where the summary results are written, and Summary-Processes
// is where the process scheduling summary is written.

// Created by Alexander Reeder, 2001 January 06

import java.io.*;
import java.util.*;

public class Scheduling {

  private static int processNum = 5;
  private static int meanDev = 1000;
  private static int standardDev = 100;
  private static int runtime = 1000;
  private static Vector<Process> processVector = new Vector<>();
  private static Results result = new Results("null","null",0);
  private static String resultsFile = "Summary-Results";

  private static void init(String file) {
    File f = new File(file);
    String line;
    int cpuTime = 0;
    int ioBlocking = 0;
    long ticketsCount = 0L;
    int pidCounter = 0;
    double X = 0.0;

    try (DataInputStream in = new DataInputStream(new FileInputStream(f))){
      while ((line = in.readLine()) != null) {
        if (line.startsWith("numprocess")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          processNum = Common.s2i(st.nextToken());
        }

        if (line.startsWith("meandev")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          meanDev = Common.s2i(st.nextToken());
        }

        if (line.startsWith("standdev")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          standardDev = Common.s2i(st.nextToken());
        }

        if (line.startsWith("process")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          ioBlocking = Common.s2i(st.nextToken());
          ticketsCount = Long.parseLong(st.nextToken());

          X = Common.R1();

          while (X == -1.0) {
            X = Common.R1();
          }

          X = X * standardDev;
          cpuTime = (int) X + meanDev;
          processVector.addElement(new Process(pidCounter, cpuTime, ioBlocking, 0, 0, 0, ticketsCount));
          pidCounter++;
        }

        if (line.startsWith("runtime")) {
          StringTokenizer st = new StringTokenizer(line);
          st.nextToken();
          runtime = Common.s2i(st.nextToken());
        }
      }
    } catch (IOException e) {
      /* Handle exceptions */
    }
  }

  private static void debug() {
    int i = 0;

    System.out.println("processnum " + processNum);
    System.out.println("meandevm " + meanDev);
    System.out.println("standdev " + standardDev);
    int size = processVector.size();

    for (i = 0; i < size; i++) {
      Process process = processVector.elementAt(i);
      System.out.println("process " + i + " " + process.cpuTime + " " + process.ioBlocking + " " + process.cpuDone
          + " " + process.numBlocked);
    }

    System.out.println("runtime " + runtime);
  }

  public static void main(String[] args) {
    int i = 0;

    if (args.length != 1) {
      System.out.println("Usage: 'java Scheduling <INIT FILE>'");
      System.exit(-1);
    }

    File f = new File(args[0]);

    if (!(f.exists())) {
      System.out.println("Scheduling: error, file '" + f.getName() + "' does not exist.");
      System.exit(-1);
    }

    if (!(f.canRead())) {
      System.out.println("Scheduling: error, read of " + f.getName() + " failed.");
      System.exit(-1);
    }

    System.out.println("Working...");
    init(args[0]);

    if (processVector.size() < processNum) {
      long defaultTicketsCount = 100L;
      i = 0;

      while (processVector.size() < processNum) {
        double X = Common.R1();

        while (X == -1.0) {
          X = Common.R1();
        }

        X = X * standardDev;
        int cpuTime = (int) X + meanDev;
        processVector.addElement(new Process(i, cpuTime,i*100,0,0,0, defaultTicketsCount));
        i++;
      }
    }

    result = SchedulingAlgorithm.run(runtime, processVector, result);
    try (PrintStream out = new PrintStream(new FileOutputStream(resultsFile))) {
      out.println("Scheduling Type: " + result.schedulingType);
      out.println("Scheduling Name: " + result.schedulingName);
      out.println("Simulation Run Time: " + result.compuTime);
      out.println("Mean: " + meanDev);
      out.println("Standard Deviation: " + standardDev);
      out.println("Process #\tCPU Time\tIO Blocking\tCPU Completed\tCPU Blocked\tTickets");

      for (i = 0; i < processVector.size(); i++) {
        Process process = processVector.elementAt(i);
        out.print(Integer.toString(i));
        if (i < 100) { out.print("\t\t"); } else { out.print("\t"); }
        out.print(Integer.toString(process.cpuTime));
        if (process.cpuTime < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
        out.print(Integer.toString(process.ioBlocking));
        if (process.ioBlocking < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
        out.print(Integer.toString(process.cpuDone));
        if (process.cpuDone < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
        out.print(process.numBlocked + " times");
        if (process.numBlocked < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
        out.println(process.ticketsCount);
      }

    } catch (IOException e) {
      /* Handle exceptions */
    }

    System.out.println("Completed.");
  }
}

