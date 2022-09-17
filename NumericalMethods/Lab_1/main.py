from sympy import *

# https://www.wolframalpha.com/input?i=5x%5E3+-+2x%5E2*sin%28x%29+-+2%2F5+%3D+0

x = symbols('x')
test_function = 5 * (x ** 3) - 2 * (x ** 2) * sin(x) - 2 / 5
EPS = 1e-5


def dichotomy_method(function, a, b, eps=EPS):
    if function.subs(x, a).evalf() * function.subs(x, b).evalf() > 0:
        print("There's even number of roots, can't apply dichotomy method")
        return

    c = a
    while abs(b - a) > eps:
        c = (a + b) / 2
        if function.subs(x, a).evalf() * function.subs(x, c).evalf() < 0:
            b = c
        else:
            a = c

    print(f"Found root by dichotomy method: {c}")



def check_sufficient_condition(function, a, b):
    derivative = diff(test_function, x)
    interval = Interval(a, b)
    min_derivative = abs(minimum(derivative, x, interval))
    max_derivative = abs(maximum(derivative, x, interval))
    # TODO : check sufficient condition for relaxations method
    print(f'requirements: t є (0, 2/M1) for convergence, 2/M1: {2/ max_derivative}')



# def relaxation_method(function, a, b, eps = EPS):

def newton_method(function, a, b, eps=EPS):
    point = b
    next_point = a
    derivative = diff(test_function, x)

    while abs(next_point - point) > eps:
        point = next_point
        next_point = point - function.subs(x, point).evalf() / derivative.subs(x, point).evalf()

    print(f"Found root by Newton method: {next_point}")


dichotomy_method(test_function, -5, 5)
newton_method(test_function, -5, 5)
