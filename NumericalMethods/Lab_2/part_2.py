import random
import numpy as np

EPS = 1e-12
DAMPING_FACTOR = 0.15


def get_vector_norm(x):
    return np.max(np.abs(x))


def generate_random_webgraph(vertices_count):
    result = np.zeros([vertices_count, vertices_count])

    for i in range(vertices_count):
        for j in range(vertices_count):
            if i != j:
                result[i, j] = bool(random.getrandbits(1))

    return result


def power_method_for_pagerank(matrix, eps=EPS):
    result = np.ones(matrix.shape[0])

    while True:
        prev_result = result
        result = matrix @ result

        if get_vector_norm(result - prev_result) <= eps:
            break

    return result


def get_a_matrix(adjacency_matrix):
    dimension = adjacency_matrix.shape[0]
    diag_multiplier = np.zeros((dimension, dimension))

    for i in range(dimension):
        row_sum = np.sum(adjacency_matrix[i])
        diag_multiplier[i, i] = 0 if row_sum == 0 else 1 / row_sum

    return adjacency_matrix.T @ diag_multiplier


def get_google_matrix(adjacency_matrix, damping_factor=DAMPING_FACTOR):
    dimension = adjacency_matrix.shape[0]
    matrix_a = get_a_matrix(adjacency_matrix)
    return (1 - damping_factor) * matrix_a + damping_factor * np.ones((dimension, dimension)) / dimension


def perform_pagerank(adjacency_matrix, damping_factor=DAMPING_FACTOR, eps=EPS):
    matrix_m = get_google_matrix(adjacency_matrix, damping_factor)
    return power_method_for_pagerank(matrix_m, eps)


def log_pagerank(adjacency_matrix):
    print("==================Adjacency matrix==================")
    print(adjacency_matrix)
    print("==================A matrix==================")
    print(get_a_matrix(adjacency_matrix))
    print(f"==================Google matrix(damping factor = {DAMPING_FACTOR})==================")
    print(get_google_matrix(adjacency_matrix, DAMPING_FACTOR))
    print("==================Result==================")
    print(perform_pagerank(adjacency_matrix))


# input = np.array([
#     [0, 1, 0, 0],
#     [1, 0, 0, 1],
#     [0, 1, 0, 0],
#     [0, 0, 1, 0]
# ])


input = np.array([
    [0, 1, 1, 0],
    [1, 0, 0, 0],
    [0, 1, 0, 0],
    [1, 1, 1, 0]
])

log_pagerank(input)
