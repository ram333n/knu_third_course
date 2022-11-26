import numpy as np
import numpy.polynomial.polynomial as poly
import matplotlib

matplotlib.use('TkAgg')
import matplotlib.pyplot as plt


def func(x):
    return np.cos(x)


def log_interpolation(func, x_start, x_end, points_count, points_to_plot=1000):
    args_to_plot = np.linspace(x_start, x_end, points_to_plot)
    args = np.linspace(x_start, x_end, points_count)
    values = func(args)
    points = np.array([args, values]).T
    lagrange = lagrange_interpolation(points)

    print(f"Lagrange: {lagrange}")

    plt.plot(args, values, 'ko')

    plt.plot(args_to_plot, func(args_to_plot), 'b', label='Function')
    plt.plot(args_to_plot, lagrange(args_to_plot), 'r', label='Lagrange')
    plt.title("Interpolation")
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid()
    plt.legend()
    plt.show()


def lagrange_interpolation(points):
    points_count = points.shape[0]
    result = poly.Polynomial([0])

    for k in range(points_count):
        current_pol = poly.Polynomial([1])
        multiplier = 1

        for j in range(points_count):
            if j != k:
                current_pol *= poly.Polynomial([-points[j][0], 1])
                multiplier /= points[k][0] - points[j][0]

        multiplier *= points[k][1]
        current_pol *= multiplier

        result += current_pol

    return result


log_interpolation(func, -4, 4, 5)



