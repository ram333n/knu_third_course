=begin
  https://www.wolframalpha.com/input?i=solve+equation+system+of+4+equations&assumption=%7B%22F%22%2C+%22SolveSystemOf4EquationsCalculator%22%2C+%22equation1%22%7D+-%3E%222a+%2B+11b+%2B+11c+%2B+11d+%3D+1%22&assumption=%7B%22F%22%2C+%22SolveSystemOf4EquationsCalculator%22%2C+%22equation2%22%7D+-%3E%2211a+%2B+2b+%2B+11c+%2B+11d+%3D+2%22&assumption=%7B%22F%22%2C+%22SolveSystemOf4EquationsCalculator%22%2C+%22equation3%22%7D+-%3E%2211a+%2B+11b+%2B+2c+%2B+11d+%3D+3%22&assumption=%7B%22F%22%2C+%22SolveSystemOf4EquationsCalculator%22%2C+%22equation4%22%7D+-%3E%2211a+%2B+11b+%2B+11c+%2B+2d+%3D+4%22
=end

def generate_matrix(n)
  matrix = (0...n).map { Array.new(n, 2.0) }
  matrix.each_index do |i|
    matrix[i].each_index do |j|
      if i != j
        matrix[i][j] = 11.0
      end
    end
  end

  matrix
end

def generate_vector(n)
  (0...n).map do|i|
    i + 1
  end
end

def print_matrix(matrix)
  matrix.each do |row|
    row.each do |elem|
      print "#{elem}   "
    end
    puts
  end
end

def solve_gauss(matrix, vector)
  n = vector.length
  result = (0...n).map{ Array.new(n) }

  for k in 0...n
    max = matrix[k][k].abs
    leading_idx = k
    (k + 1...n).each do |i|
      if matrix[i][k].abs > max #mb_problem
        max = matrix[i][k].abs
        leading_idx = i
      end
    end

    (0...n).each do |j|
      temp = matrix[k][j]
      matrix[k][j] = matrix[leading_idx][j]
      matrix[leading_idx][j] = temp
    end

    temp = vector[k]
    vector[k] = vector[leading_idx]
    vector[leading_idx] = temp

    for i in k...n
      temp = matrix[i][k]
      (0...n).each do |j|
        matrix[i][j] = matrix[i][j].to_f / temp
      end

      vector[i] = vector[i].to_f / temp

      if i == k
        next
      end

      (0...n).each do |j|
        matrix[i][j] -= matrix[k][j]
      end

      vector[i] -= vector[k]
    end
  end

  (n - 1).downto(0) do |k|
    result[k] = vector[k]
    (0..k).each do |i|
      vector[i] = vector[i] - matrix[i][k] * result[k];
    end
  end

  result
end

n = 4
matrix = generate_matrix(n)
vector = generate_vector(n)

puts "Matrix : "
print_matrix(matrix)

puts "Vector : "
puts vector
puts "Result : "
puts solve_gauss(matrix, vector)