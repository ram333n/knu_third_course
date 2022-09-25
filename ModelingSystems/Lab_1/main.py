import numpy as np
import scipy.fft as fft
from scipy.signal import argrelextrema
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt


FILE_NAME = "f6.txt"
SIGNALS = np.array(open("f6.txt").read().split(), float)
TIME = 5.0
DT = 0.01


class Model:
    def __init__(self, signals, time, dt):
        self.signals = signals
        self.signals_count = signals.size
        self.time = time
        self.dt = dt
        self.trasformed = fft.fft(signals)
        self.frequencies = fft.fftfreq(self.signals_count, dt)[:self.signals_count//2]


    def plot_fourier_trasform(self):
        plt.plot(self.frequencies, 2.0 / self.signals_count * np.abs(self.trasformed[0:self.signals_count//2]))
        plt.grid()
        plt.show()


model = Model(SIGNALS, TIME, DT)
model.plot_fourier_trasform()

