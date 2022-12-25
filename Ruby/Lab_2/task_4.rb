def decimal_to_binary(num)
  return puts "Invalid argument passed" unless num.is_a? Integer
  result = ""
  mask = 1

  while num > 0
    result << (num & mask).to_s
    num = num >> 1
  end

  result.reverse!
end

var_input = 444
puts "Test 1 : #{decimal_to_binary(1024)}"
puts "Test 2 : #{decimal_to_binary(555)}"
puts "Variant input : #{decimal_to_binary(var_input)}"