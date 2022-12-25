package org.example.monitors;

import org.example.Garden;

public class ConsoleMonitor extends Monitor {
    public ConsoleMonitor(Garden garden) {
        super(garden, "Console monitor");
    }

    @Override
    protected void monitor() {
        garden.outputField();
    }
}
