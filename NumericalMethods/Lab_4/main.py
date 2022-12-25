import numpy as np
import numpy.polynomial.polynomial as poly
import matplotlib

matplotlib.use('TkAgg')
import matplotlib.pyplot as plt


def func(x):
    return x ** 2 * np.sin(x)


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


def find_steps(points):
    intervals_count = points.shape[0] - 1
    result = np.zeros(intervals_count)

    for i in range(intervals_count):
        result[i] = points[i + 1][0] - points[i][0]

    return result


def spline_interpolation(points):
    points_count = points.shape[0]
    intervals_count = points_count - 1
    result_pols = np.empty([intervals_count], poly.Polynomial)
    steps = find_steps(points)
    matrix_for_c = np.zeros([intervals_count - 1, intervals_count - 1])
    rhs_vector = np.zeros(intervals_count - 1)
    c = np.zeros(points_count)

    for i in range(intervals_count - 1):
        if i != 0:
            matrix_for_c[i][i - 1] = steps[i]

        matrix_for_c[i][i] = 2 * (steps[i] + steps[i + 1])

        if i != intervals_count - 2:
            matrix_for_c[i][i + 1] = steps[i + 1]

        rhs_vector[i] = 6 * ((points[i + 2][1] - points[i + 1][1]) / steps[i + 1] -
                             (points[i + 1][1] - points[i][1]) / steps[i])

    c[1:points_count - 1] = np.linalg.solve(matrix_for_c, rhs_vector)

    for i in range(1, intervals_count + 1):
        d_i = (c[i] - c[i - 1]) / steps[i - 1]
        c_i = c[i]
        b_i = steps[i - 1] * c[i] / 2 - steps[i - 1] ** 2 * d_i / 6 + (points[i][1] - points[i - 1][1]) / steps[i - 1]
        a_i = points[i][1]

        current_pol = poly.Polynomial([a_i])
        current_pol += b_i * poly.Polynomial([-points[i][0], 1])
        current_pol += (c_i / 2) * poly.Polynomial([-points[i][0], 1]) ** 2
        current_pol += (d_i / 6) * poly.Polynomial([-points[i][0], 1]) ** 3

        result_pols[i - 1] = current_pol

    return [result_pols, points[:, 0]]


def get_points_for_spline_plot(spline, args):
    points_count = args.shape[0]
    result = np.zeros(points_count)
    current_spline = 0

    for i in range(points_count):
        arg = args[i]

        if arg > spline[1][current_spline + 1]:
            current_spline += 1

        result[i] = poly.polyval(arg, spline[0][current_spline].coef)

    return result


def log_interpolation(func, x_start, x_end, points_count, points_to_plot=1000):
    args_to_plot = np.linspace(x_start, x_end, points_to_plot)
    args = np.linspace(x_start, x_end, points_count)
    values = func(args)
    points = np.array([args, values]).T

    lagrange = lagrange_interpolation(points)
    newton = newton_interpolation(points)
    spline = spline_interpolation(points)

    plt.plot(args, values, 'ko')
    plt.plot(args_to_plot, func(args_to_plot), 'b', label='Function')
    plt.plot(args_to_plot, lagrange(args_to_plot), 'r', label='Lagrange')
    plt.plot(args_to_plot, newton(args_to_plot), 'g', label='Newton')
    plt.plot(args_to_plot, get_points_for_spline_plot(spline, args_to_plot), 'y', label='Spline')
    plt.title("Interpolation")
    plt.xlabel('x')
    plt.ylabel('y')
    plt.grid()
    plt.legend()
    plt.show()

# points = np.array([
#     [0, 0],
#     [1, 0.5],
#     [2, 2],
#     [3, 1.5],
# ])


log_interpolation(func, -10, 10, 10)
