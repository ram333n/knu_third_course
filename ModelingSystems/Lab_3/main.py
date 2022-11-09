import numpy as np

data = np.loadtxt('y6.txt').T
T0 = 0
TK = 50
DT = 0.2
b0 = np.array([0.1, 0.15, 19])  # (c1, c2, m2)
c3 = 0.2
c4 = 0.12
m1 = 12
m3 = 18
EPS = 1e-4


def get_matrix_a(b):
    return np.array([
        [0, 1, 0, 0, 0, 0],
        [-(b[1] + b[0]) / m1, 0, b[1] / m1, 0, 0, 0],
        [0, 0, 0, 1, 0, 0],
        [b[1] / b[2], 0, -(b[1] + c3) / b[2], 0, c3 / b[2], 0],
        [0, 0, 0, 0, 0, 1],
        [0, 0, c3 / m3, 0, -(c4 + c3) / m3, 0],
    ])


def get_f(y, b):
    return get_matrix_a(b) @ y


def diff_by_b(y, b):
    dc1 = np.array([
        [0, 0, 0, 0, 0, 0],
        [-1 / m1, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0]
    ])

    dc2 = np.array([
        [0, 0, 0, 0, 0, 0],
        [-1 / m1, 0, 1 / m1, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [1 / b[2], 0, -1 / b[2], 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0]
    ])

    dm2 = np.array([
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0],
        [- b[1] / (b[2] ** 2), 0, (b[1] + c3) / (b[2] ** 2), 0, -c3 / (b[2] ** 2), 0],
        [0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0]
    ])

    return np.array([dc1 @ y,
                     dc2 @ y,
                     dm2 @ y]).T


def runge_kutta_for_model(b, t0, tk, dt):
    timepoints = np.linspace(t0, tk, int((tk - t0) / dt) + 1)
    yy = np.zeros_like(data)
    yy[0] = np.copy(data[0])

    for i in range(1, timepoints.shape[0]):
        prev_y = yy[i - 1]
        k1 = dt * get_f(prev_y, b)
        k2 = dt * get_f(prev_y + k1 / 2, b)
        k3 = dt * get_f(prev_y + k2 / 2, b)
        k4 = dt * get_f(prev_y + k3, b)

        y = prev_y + (k1 + 2 * k2 + 2 * k3 + k4) / 6
        yy[i] = y

    return yy


def runge_kutta_for_sensitivity_matrix(yy, b, t0, tk, dt):
    timepoints = np.linspace(t0, tk, int((tk - t0) / dt) + 1)
    uu = np.zeros((timepoints.shape[0], 6, 3))
    db = diff_by_b(yy.T, b)
    a = get_matrix_a(b)

    for i in range(1, timepoints.shape[0]):
        prev_u = uu[i - 1]
        k1 = dt * ((a @ prev_u) + db[i - 1])
        k2 = dt * (a @ (prev_u + k1 / 2) + db[i - 1])
        k3 = dt * (a @ (prev_u + k2 / 2) + db[i - 1])
        k4 = dt * (a @ (prev_u + k3) + db[i - 1])
        u = prev_u + (k1 + 2 * k2 + 2 * k3 + k4) / 6
        uu[i] = u

    return uu


def get_params(b, t0, tk, dt, eps=EPS):
    obs_count = int((tk - t0) / dt) + 1

    while True:
        yy = runge_kutta_for_model(b, t0, tk, dt)
        uu = runge_kutta_for_sensitivity_matrix(yy, b, t0, tk, dt)

        first_integral = (np.array([u.T @ u for u in uu]) * dt).sum(0)
        first_integral = np.linalg.inv(first_integral)

        delta_y = (data - yy)
        second_integral = (np.array([uu[i].T @ delta_y[i] for i in range(obs_count)]) * dt).sum(0)
        delta_b = first_integral @ second_integral
        b += delta_b

        if np.linalg.norm(delta_b, 1) < eps:
            break

    return b


print(get_params(b0, T0, TK, DT))
