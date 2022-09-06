require 'matrix'

def generate_matrix(n, m)
  Matrix.build(n, m) do |i, j|
    i == j ? 1 : rand(10)
  end
end

def print_matrix(matrix)
  for i in 0...matrix.row_count
    for j in 0...matrix.column_count
      print matrix[i, j].to_s + " "
    end
    puts
  end
end

def multiply(matrix, vector)
  Matrix.build(vector.row_count, 1) do |vector_i, vector_j|
    cur_value = 0
    for i in 0...vector.row_count
      cur_value += matrix[vector_i, i] * vector[i, vector_j]
    end
    cur_value
  end
end

n = 8
input_matrix = generate_matrix(n, n)
input_vector = generate_matrix(n, 1)

puts "Matrix\n"
print_matrix(input_matrix)

puts "Vector\n"
print_matrix(input_vector)

puts "Result\n"
print_matrix(multiply(input_matrix, input_vector))