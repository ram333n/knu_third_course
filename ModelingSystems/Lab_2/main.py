import numpy as np
from matplotlib import image

import matplotlib.pyplot as plt
import matplotlib
matplotlib.use('TkAgg')

input_image = np.array(image.imread('x1.bmp'), float)
output_image = np.array(image.imread('y6.bmp'), float)
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


def check_pseudoinverse_matrix(matrix, pseudoinverse):
    assert np.dot(np.dot(matrix, pseudoinverse), matrix).all() == matrix.all()
    assert np.dot(np.dot(pseudoinverse, matrix), pseudoinverse).all() == pseudoinverse.all()
    assert np.allclose(np.dot(matrix, pseudoinverse), np.dot(matrix, pseudoinverse).T)
    assert np.allclose(np.dot(pseudoinverse, matrix), np.dot(pseudoinverse, matrix).T)


check_pseudoinverse_matrix(input_image, moore_penrose_method(input_image, DELTA))

operator_moore_penrose = np.dot(output_image, moore_penrose_method(input_image))
plt.imshow(np.dot(operator_moore_penrose, input_image), 'gray')
plt.show()
