=begin
  sum i = 0 to infinity x^(4i + 1) / (4i + 1) at x = 0.5
  sum i = 0 to 32 x^(4i + 1) / (4i + 1) at x = 0.5
=end

def evaluate_sum(func, x, i = 0, n = 0, eps = 0.001)
  sum = 0
  if n != 0
    (i..n).each do |num|
      sum += func.call(x, num)
    end
  else
    prev_sum = -1
    while (sum - prev_sum).abs > eps
      prev_sum = sum
      sum += func.call(x, i)
      i += 1
    end
  end

  sum
end

func = lambda { |x, i|
  x ** (4 * i + 1) / (4 * i + 1)
}

x = 0.5
n = 32

puts "Infinity sum : #{evaluate_sum(func, x)}"
puts "Sum to n = #{n} : #{evaluate_sum(func, x, 0, n)}"