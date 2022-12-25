=begin
  sum n = 2 to infinity ((n-1)!/(n+1)!)^(n(n+1))
  sum n = 1 to infinity ((3n-1)!*(n+1)!)/((4n)!*(2n)!)
=end

$eps = 0.00001

def factorial(n)
  n > 1 ? n * factorial(n - 1) : 1
end

def series_sum(member_formula, n)
  sum = member_formula.call(n)
  previous_sum = 0
  while (sum - previous_sum).abs > $eps
    n += 1
    previous_sum = sum
    sum += member_formula.call(n)
  end

  sum
end

#i use simplified formula : (n-1)!/(n+1)! = 1/n(n+1)
first_series = lambda { |n|
  (1.0 / (n * (n + 1))) ** (n * (n + 1))
}

def functional_series(a, x, n)
  #((x * Math.log(a)) ** n) / factorial(n)
  sum = ((x * Math.log(a)) ** n) / factorial(n)
  previous_sum = 0
  while (sum - previous_sum).abs > $eps
    n += 1
    previous_sum = sum
    sum += ((x * Math.log(a)) ** n) / factorial(n)
  end
  puts "Second series : real value = #{a ** x}, evaluated = #{sum}"
end

third_series = lambda { |n|
  (factorial(3 * n - 1) * factorial(n + 1)).to_f / (factorial(4 * n) * factorial(2 * n))
}

puts "First series : #{series_sum(first_series, 2)}"
functional_series(2, 1, 0)
puts "Third series : #{series_sum(third_series, 1)}"