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
    newton = newton_interpolation(points)

    print(f"Lagrange: {lagrange}")
    print(f"Newton: {newton}")

    plt.plot(args, values, 'ko')
    plt.plot(args_to_plot, func(args_to_plot), 'b', label='Function')
    plt.plot(args_to_plot, lagrange(args_to_plot), 'r', label='Lagrange')
    plt.plot(args_to_plot, newton(args_to_plot), 'g', label='Newton')
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


def get_divided_diff_table(points):
    points_count = points.shape[0]
    result = np.zeros([points_count, points_count])
    result[:, 0] = np.copy(points[:, 1])

    for column in range(1, points_count):
        for row in range(points_count - column):
            x_left = points[row][0]
            x_right = points[row + column][0]
            dx = x_right - x_left
            df = result[row + 1][column - 1] - result[row][column - 1]
            result[row][column] = df / dx

    return result


def newton_interpolation(points):
    result = poly.Polynomial([points[0][1]])
    points_count = points.shape[0]
    diff_table = get_divided_diff_table(points)
    current_pol = poly.Polynomial([1])

    for i in range(1, points_count):
        current_pol *= poly.Polynomial([-points[i - 1][0], 1])
        result += diff_table[0][i] * current_pol

    return result


points = np.array([
    [-1, 1/3],
    [0, 1],
    [1, 3]
])

print(newton_interpolation(points))

log_interpolation(func, -10, 10, 10)



