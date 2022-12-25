# Benchmark

- 100x100 

| Algorithm\Threads | 1     | 2    | 4     |
|-------------------|-------|------|-------|
| Naive             | 6 ms  | 5 ms | 9 ms  |
| Tape              | 8 ms  | 6 ms | 5 ms  |
| Fox               | 10 ms | -*   | 25 ms |
| Cannon            | 11 ms | -    | 17 ms |

- 1000x1000

| Algorithm\Threads | 1       | 2       | 4        |
|-------------------|---------|---------|----------|
| Naive             | 4217 ms | 4549 ms | 5297 ms  |
| Tape              | 3424 ms | 1903 ms | 969  ms  |
| Fox               | 3504 ms | -       | 1585 ms  |
| Cannon            | 4021 ms | -       | 1225  ms |

- 2000x2000

| Algorithm\Threads | 1        | 2        | 4        |
|-------------------|----------|----------|----------|
| Naive             | 21548 ms | 21926 ms | 25367 ms |
| Tape              | 22286 ms | 13370 ms | 7728 ms  |
| Fox               | 28476 ms | -        | 13621 ms |
| Cannon            | 27649 ms | -        | 10600 ms |

*- threads count isn't a square, that's why we can't create square grid