import numpy as np
from matplotlib import image

import matplotlib.pyplot as plt
import matplotlib
matplotlib.use('TkAgg')

input_image = np.array(image.imread('x1.bmp'))
output_image = np.array(image.imread('y6.bmp'))
EPS = 1e-12
DELTA = 1e-8


def evaluate_approximation(matrix, delta):
    e = np.identity(matrix.shape[0])
    return np.dot(
        matrix.T,
        np.linalg.inv(np.dot(matrix, matrix.T) + (delta ** 2) * e)
    )


def moore_penrose_method(matrix, start_delta=DELTA, eps=EPS):
    np_matrix = np.array(matrix, float)
    result = evaluate_approximation(np_matrix, start_delta)
    current_delta = start_delta

    while True:
        current_delta /= 2
        prev_result = result
        result = evaluate_approximation(np_matrix, current_delta)

        if np.linalg.norm(result - prev_result) <= eps: # Frobenius norm
            break

    return result


def greville_method(matrix):
    np_matrix = np.array(matrix, float)
    current_a = np_matrix[0:1]
    result = np.zeros_like(current_a.T)

    if np.count_nonzero(current_a[0] != 0):
        result = current_a.T / np.dot(current_a, current_a.T)

    n = np_matrix.shape[0]

    for i in range(1, n):
        current_a = matrix[i:i + 1]
        z = np.identity(result.shape[0]) - np.dot(result, np_matrix[:i])
        r = np.dot(result, result.T)
        cond_expr = np.dot(np.dot(current_a, z), current_a.T)

        if np.count_nonzero(cond_expr) == 1:
            row_to_add = np.dot(z, current_a.T) / cond_expr
        else:
            row_to_add = np.dot(r, current_a.T) / (1 + np.dot(np.dot(current_a, r), current_a.T))

        result -= np.dot(row_to_add, np.dot(current_a, result))
        result = np.concatenate((result, row_to_add), axis=1)

    return result


def check_pseudoinverse_matrix(matrix, pseudoinverse):
    assert np.dot(np.dot(matrix, pseudoinverse), matrix).all() == matrix.all()
    assert np.dot(np.dot(pseudoinverse, matrix), pseudoinverse).all() == pseudoinverse.all()
    assert np.allclose(np.dot(matrix, pseudoinverse), np.dot(matrix, pseudoinverse).T)
    assert np.allclose(np.dot(pseudoinverse, matrix), np.dot(pseudoinverse, matrix).T)


pseudoinverse_moore_penrouse = moore_penrose_method(input_image, DELTA)
pseudoinverse_greville = greville_method(input_image)

check_pseudoinverse_matrix(input_image, pseudoinverse_moore_penrouse)
check_pseudoinverse_matrix(input_image, pseudoinverse_greville)

operator_moore_penrose = np.dot(output_image, pseudoinverse_moore_penrouse)
operator_greville = np.dot(output_image, pseudoinverse_greville)

plt.imshow(input_image, 'gray')
plt.show()
plt.imshow(output_image, 'gray')
plt.show()
plt.imshow(np.dot(operator_moore_penrose, input_image), 'gray')
plt.show()
plt.imshow(np.dot(operator_greville, input_image), 'gray')
plt.show()
