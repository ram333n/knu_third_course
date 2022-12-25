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
        self.signals_count = signals.shape[0]
        self.time = time
        self.dt = dt
        self.time_points = np.arange(0, self.time + self.dt, self.dt)
        self.transformed_signals = fft.fft(signals)
        self.frequencies = fft.fftfreq(self.signals_count, dt)[:self.signals_count // 2]

    def plot_fourier_transform(self):
        plt.plot(self.frequencies, 2.0 / self.signals_count * np.abs(self.transformed_signals[0:self.signals_count // 2]))
        plt.grid()
        plt.show()

    def find_max_frequencies(self):
        transformed_half = self.transformed_signals[:self.transformed_signals.shape[0] // 2 - 1]
        extremumus = np.array(argrelextrema(np.abs(transformed_half), np.greater))
        return extremumus / self.time

    def find_koefs(self):
        frequencies = self.find_max_frequencies()

        b = np.array([
            np.sum(self.signals * self.time_points ** 3),
            np.sum(self.signals * self.time_points ** 2),
            np.sum(self.signals * self.time_points),
            np.sum(self.signals * np.sin(2. * np.pi * frequencies[0][0] * self.time_points)),
            np.sum(self.signals)
        ])

        a = np.zeros((b.shape[0], b.shape[0]))

        func = [self.time_points ** 3,
                self.time_points ** 2,
                self.time_points,
                np.sin(2. * np.pi * frequencies[0][0] * self.time_points),
                np.ones(self.signals_count)]

        for i in range(b.shape[0]):
            for j in range(b.shape[0]):
                a[i, j] = np.sum(func[i] * func[j])

        return np.matmul(np.linalg.inv(a), b)


model = Model(SIGNALS, TIME, DT)
model.plot_fourier_transform()
print(f"Koefs : {model.find_koefs()}")
