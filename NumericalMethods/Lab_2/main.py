import numpy as np

# def generate_random_input(dimension):


# Gauss method
def get_permutation_matrix(dimension, k, l):
    result = np.identity(dimension)
    result[[k, l]] = result[[l, k]]
    return result


def get_m_matrix(A, k):
    dimension = A.shape[0]
    result = np.identity(dimension)
    result[k, k] = 1 / A[k, k]
    for i in range(k + 1, dimension):
        result[i, k] = -A[i, k] / A[k, k]

    return result


def find_leading_element(A, column, start):
    dimension = A.shape[0]
    result = start

    for i in range(start + 1, dimension):
        if abs(A[i, column]) > abs(A[result, column]):
            column = i

    return result


def gauss(A, b):
    dimension = A.shape[0]
    cur_matrix = A
    cur_b = b
    result = np.arange(dimension)

    for i in range(dimension):
        leading_idx = find_leading_element(cur_matrix, i, i)
        p = get_permutation_matrix(dimension, i, leading_idx)
        cur_matrix = p @ cur_matrix
        m = get_m_matrix(cur_matrix, i)
        cur_matrix = m @ cur_matrix
        cur_b = m @ (p @ cur_b)

    print(cur_matrix)

    for i in range(dimension - 1, -1, -1):
        result[i] = cur_b[i]
        for j in range(0, i + 1):
            cur_b[j] = cur_b[j] - cur_matrix[j, i] * result[i]

    return result


test_matrix = np.array([[1, 1, 0], [1, 3, 2], [0, 1, 2]])
test_input = np.array([1, 1, 1])
print(gauss(test_matrix, test_input))
