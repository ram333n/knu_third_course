=begin
  sum i = 1 to 20 1/(sum j = 1 to i sin(j))
  sqrt(2 + sqrt(2 + sqrt(2 + sqrt(2 + sqrt(2)))))
=end

def sin_sum(n)
  return puts "Invalid argument provided" unless n.is_a? Integer

  sinus_sum = 0
  result = 0
  for i in 1..n
    sinus_sum += Math.sin(i)
    result += 1.0 / sinus_sum;
  end
  result
end

def sqrt_sum_impl(i, n)
  if i == n
    return Math.sqrt(2)
  end

  Math.sqrt(2 + sqrt_sum_impl(i + 1, n))
end

def sqrt_sum(n)
  sqrt_sum_impl(1, n)
end

n = 20
puts "Sinus sum for n = #{n} : #{sin_sum(n)}"

n = 5
puts "Sqrt sum for n = #{n} : #{sqrt_sum(n)}"
