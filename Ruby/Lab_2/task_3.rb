def binary_to_decimal(num)
  return puts "Invalid argument passed" unless num.is_a? String
  result = 0

  for i in 0...num.length
    result += num[num.length - i - 1].to_i * 2 ** i
  end

  result
end

var_input = "100011111001"
puts "Test 1: #{binary_to_decimal("10000000000")}"
puts "Test 2 : #{binary_to_decimal("10001010101001")}"
puts "Variant : #{binary_to_decimal(var_input)}"
