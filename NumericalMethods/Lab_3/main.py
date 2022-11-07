import math

import numpy as np

EPS = 1e-12

# ========================================= System 1
def f1(variables):
    return (variables[0] ** 2) / (variables[1] ** 2) - math.cos(variables[1]) - 2


def f2(variables):
    return variables[0] ** 2 + variables[1] ** 2 - 6


def f(variables):
    return np.array([
        f1(variables),
        f2(variables)
    ])


def df1dx(variables):
    return 2 * variables[0] / (variables[1] ** 2)


def df1dy(variables):
    return math.sin(variables[1]) - (2 * variables[0] ** 2) / (variables[1] ** 3)


def df2dx(variables):
    return 2 * variables[0]


def df2dy(variables):
    return 2 * variables[1]


def get_jacobi_matrix_for_first(variables):
    return np.array([
        [df1dx(variables), df1dy(variables)],
        [df2dx(variables), df2dy(variables)]
    ])


# ========================================= System 2

# ========================================= Solutions

def newton_method(function, jacobi_matrix_function, vars_count, eps=EPS, is_modified=False):
    result = np.ones(vars_count)
    jacobi_matrix = jacobi_matrix_function(result)

    while True:
        if not is_modified:
            jacobi_matrix = jacobi_matrix_function(result)

        f_val = function(result)

        z_val = np.linalg.solve(jacobi_matrix, f_val)
        result -= z_val

        if np.linalg.norm(z_val, 1) <= eps:
            break

    return result


def relaxation_method(function, vars_count, tau, eps=EPS):
    result = np.ones(vars_count)

    while True:
        prev_result = np.copy(result)
        result -= function(result) * tau

        if np.linalg.norm(result - prev_result, 1) <= eps:
            break

    return result


def log_solution(function, jacobi_matrix_function, vars_count, tau, eps=EPS):
    print("===========================Newton method===========================")
    result = newton_method(function, jacobi_matrix_function, vars_count, eps)
    print(f"Result: {result}")
    print(f"Incoherence: {function(result)}")

    print("===========================Newton method(modified)===========================")
    result = newton_method(function, jacobi_matrix_function, vars_count, eps, True)
    print(f"Result: {result}")
    print(f"Incoherence: {function(result)}")

    print(f"===========================Relaxation method(tau={tau})===========================")
    result = relaxation_method(function, vars_count, tau, eps)
    print(f"Result: {result}")
    print(f"Incoherence: {function(result)}")


log_solution(f, get_jacobi_matrix_for_first, 2, 0.001)
