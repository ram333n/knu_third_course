import random

import numpy as np
from scipy.linalg import hilbert

# https://www.wolframalpha.com/input?i=systems+of+equations+calculator
EPS = 1e-5
LOWER_BOUND = -50
UPPER_BOUND = 50


def generate_random_matrix(dimension, generate_hilbert=False):
    if generate_hilbert:
        return np.array(hilbert(dimension))

    result = np.empty([dimension, dimension])

    for i in range(dimension):
        for j in range(dimension):
            result[i, j] = random.randint(LOWER_BOUND, UPPER_BOUND)
        result[i, i] = np.sum(np.abs(result[i])) + 1

    return result


def generate_random_vector(dimension):
    result = np.zeros(dimension)

    for i in range(dimension):
        result[i] = random.randint(LOWER_BOUND, UPPER_BOUND)

    return result


def log_solution(matrix, b, solution):
    print("Matrix: ")
    print(matrix)
    print("Rhs vector: ")
    print(b)
    print("Solution: ")
    print(solution)
    print("Matrix * solution: ")
    print(matrix @ solution)
    print(f"||Matriix * solution - b|| : {get_vector_norm(matrix @ solution - b)}")


# Gauss method
def get_permutation_matrix(dimension, k, l):
    result = np.identity(dimension)
    result[[k, l]] = result[[l, k]]
    return result


def get_m_matrix(matrix, k):
    dimension = matrix.shape[0]
    result = np.identity(dimension)
    result[k, k] = 1 / matrix[k, k]
    for i in range(k + 1, dimension):
        result[i, k] = -matrix[i, k] / matrix[k, k]

    return result


def find_leading_element(matrix, column, start):
    dimension = matrix.shape[0]
    result = start

    for i in range(start + 1, dimension):
        if abs(matrix[i, column]) > abs(matrix[result, column]):
            result = i

    return result


def gauss_method(matrix, b):
    dimension = matrix.shape[0]
    cur_matrix = np.copy(matrix)
    cur_b = np.copy(b)
    result = np.zeros(dimension)

    for i in range(dimension):
        leading_idx = find_leading_element(cur_matrix, i, i)
        p = get_permutation_matrix(dimension, i, leading_idx)
        cur_matrix = p @ cur_matrix
        m = get_m_matrix(cur_matrix, i)
        cur_matrix = m @ cur_matrix
        cur_b = m @ (p @ cur_b)

    for i in range(dimension - 1, -1, -1):
        result[i] = cur_b[i]
        for j in range(0, i):
            cur_b[j] -= cur_matrix[j, i] * result[i]

    return result


# Jacobi
def get_vector_norm(x):
    return np.max(np.abs(x))


def jacobi_method(matrix, b, eps=EPS):
    dimension = b.shape[0]
    result = np.ones(dimension)

    while True:
        prev_result = np.array(result)

        for i in range(dimension):
            delta = 0
            for j in range(dimension):
                if i != j:
                    delta += matrix[i, j] * prev_result[j]

            result[i] = (b[i] - delta) / matrix[i, i]

        if get_vector_norm(result - prev_result) <= eps:
            break

    return result


#Seidel
def seidel_method(matrix, b, eps=EPS):
    dimension = b.shape[0]
    result = np.ones(dimension)

    while True:
        prev_result = np.array(result)

        for i in range(dimension):
            delta = 0

            for j in range(dimension):
                if j < i:
                    delta += matrix[i, j] * result[j]
                elif j > i:
                    delta += matrix[i, j] * prev_result[j]

            result[i] = (b[i] - delta) / matrix[i, i]

        if get_vector_norm(result - prev_result) <= eps:
            break

    return result


# test_matrix = np.array([[1, 1, 0], [1, 3, 2], [0, 1, 2]])
# test_input = np.array([1, 1, 1])
# log_solution(test_matrix, test_input, gauss_method(test_matrix, test_input))

test_matrix = generate_random_matrix(4)
test_b = generate_random_vector(4)

print("=============================Gauss=============================")
log_solution(test_matrix, test_b, gauss_method(test_matrix, test_b))
print("=============================Jacobi=============================")
log_solution(test_matrix, test_b, jacobi_method(test_matrix, test_b))
print("=============================Seidel=============================")
log_solution(test_matrix, test_b, seidel_method(test_matrix, test_b))
