def get_indices(array)
  result = Array.new(array.length)
  cur_idx = 0

  for i in 0...array.length
    if array[i] == 0
      result[cur_idx] = i
      cur_idx += 1
    end
  end

  for i in 0...array.length
    if array[i] < 0
      result[cur_idx] = i
      cur_idx += 1
    end
  end

  for i in 0...array.length
    if array[i] > 0
      result[cur_idx] = i
      cur_idx += 1
    end
  end

  result
end

def array_by_indices(array, indices)
  result = Array.new(indices.length)

  for i in 0...indices.length
    result[i] = array[indices[i]]
  end

  result
end

input_array = Array.new(15) {rand(-15..15)}
puts "Input : #{input_array}"
puts "Restored by indices ; #{array_by_indices(input_array, get_indices(input_array))}"
