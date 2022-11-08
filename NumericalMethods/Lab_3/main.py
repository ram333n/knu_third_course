import math

import numpy as np

EPS = 1e-12

# ========================================= System 1


def f1(variables):
    return (variables[0] ** 2) / (variables[1] ** 2) - math.cos(variables[1]) - 2


def first_f2(variables):
    return variables[0] ** 2 + variables[1] ** 2 - 6


def first_f(variables):
    return np.array([
        f1(variables),
        first_f2(variables)
    ])


def first_df1dx(variables):
    return 2 * variables[0] / (variables[1] ** 2)


def first_df1dy(variables):
    return math.sin(variables[1]) - (2 * variables[0] ** 2) / (variables[1] ** 3)


def first_df2dx(variables):
    return 2 * variables[0]


def first_df2dy(variables):
    return 2 * variables[1]


def get_jacobi_matrix_for_first(variables):
    return np.array([
        [first_df1dx(variables), first_df1dy(variables)],
        [first_df2dx(variables), first_df2dy(variables)]
    ])


# ========================================= System 2
def second_fi(variables, cube_idx):
    rhs_val = 0
    lhs_val = 0

    for i in range(variables.shape[0]):
        rhs_to_add = (i + 1) ** 2
        lhs_to_add = variables[i] ** 2

        if i == cube_idx:
            rhs_to_add *= (i + 1)
            lhs_to_add *= variables[i]

        rhs_val += rhs_to_add
        lhs_val += lhs_to_add

    return lhs_val - rhs_val


def second_dfi_dxj(variables, i, j):
    return 3 * variables[j] ** 2 if i == j else 2 * variables[j]


def second_f(variables):
    dimension = variables.shape[0]
    result = np.zeros(dimension)

    for i in range(dimension):
        result[i] = second_fi(variables, i)

    return result


def get_jacobi_matrix_for_second(variables):
    dimension = variables.shape[0]
    result = np.empty([dimension, dimension])

    for i in range(dimension):
        for j in range(dimension):
            result[i, j] = second_dfi_dxj(variables, i, j)

    return result

# ========================================= Solutions


def newton_method(x_start, function, jacobi_matrix_function, eps=EPS, is_modified=False):
    result = np.copy(x_start)
    jacobi_matrix = jacobi_matrix_function(result)

    while True:
        if not is_modified:
            jacobi_matrix = jacobi_matrix_function(result)

        f_val = function(result)
        z_val = np.linalg.solve(jacobi_matrix, f_val)
        result = result - z_val
        # print(result)
        if np.linalg.norm(z_val, 1) <= eps:
            break

    return result


def relaxation_method(x_start, function, tau, eps=EPS):
    result = np.copy(x_start)

    while True:
        prev_result = np.copy(result)
        result -= function(result) * tau

        if np.linalg.norm(result - prev_result, 1) <= eps:
            break

    return result


print("===========================Newton method(first system)===========================")
x_0 = np.ones(2)
result = newton_method(x_0, first_f, get_jacobi_matrix_for_first)
print(f"Result: {result}")
print(f"Incoherence: {np.linalg.norm(first_f(result), 1)}")

print("===========================Modified Newton method(first system)===========================")
x_0 = np.ones(2)
result = newton_method(x_0, first_f, get_jacobi_matrix_for_first, EPS, True)
print(f"Result: {result}")
print(f"Incoherence: {np.linalg.norm(first_f(result), 1)}")

tau = 0.001
print(f"===========================Relaxation method(first system)(tau={tau})============================")
x_0 = np.ones(2)
result = relaxation_method(x_0, first_f, tau)
print(f"Result: {result}")
print(f"Incoherence: {np.linalg.norm(first_f(result), 1)}")

print("===========================Newton method(second system)===========================")
x_0 = np.ones(10) * 2
result = newton_method(x_0, second_f, get_jacobi_matrix_for_second)
print(f"Result: {result}")
print(f"Incoherence: {np.linalg.norm(second_f(result), 1)}")

print("===========================Modified Newton method(second system)===========================")
x_0 = np.array([1.2, 1.8, 2.5, 5, 4, 4.5, 6.5, 9, 9, 12])
result = newton_method(x_0, second_f, get_jacobi_matrix_for_second, EPS, True)
print(f"Result: {result}")
print(f"Incoherence: {np.linalg.norm(second_f(result), 1)}")

tau = 0.001
print(f"===========================Relaxation method(second system)(tau={tau})============================")
x_0 = np.ones(10)
result = relaxation_method(x_0, second_f, tau)
print(f"Result: {result}")
print(f"Incoherence: {np.linalg.norm(second_f(result), 1)}")
