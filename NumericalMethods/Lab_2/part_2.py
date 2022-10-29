import random
import numpy as np

EPS = 1e-6
DAMPING_FACTOR = 0.15


def generate_random_webgraph(vertices_count):
    result = np.zeros([vertices_count, vertices_count])

    for i in range(vertices_count):
        for j in range(vertices_count):
            result[i, j] = bool(random.getrandbits(1))

    return result


def power_method_for_pagerank(matrix, eps=EPS):
    result = np.ones(matrix.shape[0])

    while True:
        prev_result = result
        result = matrix @ result

        if np.linalg.norm(result - prev_result, 1) <= eps:
            break

    return result


def modify_adj_matrix(adj_matrix, dangling_nodes):
    for node in dangling_nodes:
        adj_matrix[node] = np.repeat(1, adj_matrix.shape[0])


def get_a_matrix(adj_matrix):
    dangling_nodes = np.where(adj_matrix.sum(axis=1) == 0)[0]
    modify_adj_matrix(adj_matrix, dangling_nodes)
    return adj_matrix.T / adj_matrix.sum(axis=1)


def get_google_matrix(adj_matrix, damping_factor=DAMPING_FACTOR):
    dimension = adj_matrix.shape[0]
    matrix_a = get_a_matrix(adj_matrix)
    return (1 - damping_factor) * matrix_a + damping_factor * np.ones((dimension, dimension)) / dimension


def perform_pagerank(adj_matrix, damping_factor=DAMPING_FACTOR, eps=EPS):
    matrix_m = get_google_matrix(adj_matrix, damping_factor)
    return power_method_for_pagerank(matrix_m, eps)


def log_pagerank(adj_matrix):
    print("==================Adjacency matrix==================")
    print(adj_matrix)
    print("==================A matrix==================")
    print(get_a_matrix(adj_matrix))
    print(f"==================Google matrix(damping factor = {DAMPING_FACTOR})==================")
    print(get_google_matrix(adj_matrix, DAMPING_FACTOR))
    print("==================Result==================")
    print(perform_pagerank(adj_matrix))

#
# input = np.array([
#     [0., 1., 1., 0., 1., 0., 0.],
#     [0., 0., 1., 0., 1., 1., 1.],
#     [0., 0., 0., 0., 0., 0., 0.],
#     [1., 1., 0., 0., 0., 1., 1.],
#     [0., 0., 0., 1., 0., 1., 1.],
#     [1., 0., 0., 0., 1., 0., 1.],
#     [0., 1., 0., 0., 1., 1., 0.]
# ])
#



log_pagerank(generate_random_webgraph(10))
