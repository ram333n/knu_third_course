def f(x, a, b, c)
  result = 0

  if a < 0 && x != 0
    result = a * x**2 + b**2 * x
  elsif a > 0 && x == 0
    result = x - a / (x - c)
  else
    result = 1 + x / c
  end

  result
end

def function_table(a, b, c, x_begin, x_end, dx)
  to_truncate = ~(a.to_i | b.to_i) & (b.to_i | c.to_i)
  puts to_truncate

  (x_begin..x_end).step(dx).each do |x|
    result = f(x, a, b, c)
    puts "x = #{x}, f(x) = #{to_truncate == 0 ? result.to_i : result}"
  end
end

puts "========================Test 1=============================="
function_table(-1, 0, 1, 0, 2, 0.5)

puts "========================Test 2=============================="
function_table(0, 0, 1, 0, 2, 0.5)

# puts "Enter x_begin:"
# x_begin = gets.chomp.to_f
# puts "Enter x_end:"
# x_end = gets.chomp.to_f
#
# puts"Enter a:"
# a = gets.chomp.to_f
# puts "Enter b:"
# b = gets.chomp.to_f
# puts "Enter c:"
# c = gets.chomp.to_f
#
# puts "Enter dx:"
# dx = gets.chomp.to_f
#
# function_table(a, b, c, x_begin, x_end, dx)