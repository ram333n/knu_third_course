import numpy as np

# https://www.wolframalpha.com/input?i=5x%5E3+-+2x%5E2*sin%28x%29+-+2%2F5+%3D+0


LEFT = -1.0
RIGHT = -0.5
EPS = 1e-5


def test_function(x):
    return 5 * x ** 3 - 2 * x ** 2 * np.sin(x) - 2 / 5


def derivative(function, x, h=1e-6):
    return (function(x + h) - function(x)) / h


def dichotomy_method(function, a, b, eps=EPS):
    if function(a) * function(b) > 0:
        print("Can't apply dichotomy method")
        return

    c = a
    while abs(b - a) > eps:
        c = (a + b) / 2
        if function(a) * function(c) < 0:
            b = c
        else:
            a = c

    print(f"Found root by dichotomy method: {c}")


def relaxation_method(function, a, b, eps=EPS):
    n = 2000
    points = np.linspace(a, b, n)

    min_derivative = np.min(np.abs(derivative(function, points)))
    max_derivative = np.max(np.abs(derivative(function, points)))

    if min_derivative == 0:
        print("Function doesn't satisfy sufficient condition. Can't apply relaxation method")
        return

    tau = 2 / (min_derivative + max_derivative)
    point = a
    prev_point = b

    while abs(point - prev_point) > eps:
        sign = 1
        prev_point = point

        if derivative(function, point) > 0:
            sign = -1

        point = prev_point + sign * tau * function(point)

    print(f"Found root by relaxation method: {point}")


def newton_method(function, a, b, eps=EPS):
    point = a
    prev_point = b

    while abs(point - prev_point) > eps:
        prev_point = point
        point = prev_point - function(prev_point) / derivative(function, prev_point)

    print(f"Found root by Newton method: {point}")


dichotomy_method(test_function, LEFT, RIGHT)
relaxation_method(test_function, LEFT, RIGHT)
newton_method(test_function, LEFT, RIGHT)