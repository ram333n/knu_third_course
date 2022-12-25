=begin
  integrate 1 / (1 + sqrt(x))dx from 0.2 to 2.1
  integrate 1 / (5 - 3cos(x))dx from 0.2 to 3/(2pi)
=end

def rectangle_method(func, a, b, n, step = (b - a) / n.to_f)
  sum = 0.0
  x = a + step / 2
  while x < b
    sum += func.call(x)
    x += step
  end
  sum * step
end

def trapezoid_method(func, a, b, n, step = (b - a) / n.to_f)
  sum = func.call(a) / 2 + func.call(b) / 2
  x = a + step
  while x <= b - step
    sum += func.call(x)
    x += step
  end
  sum * step
end

f1 = lambda { |x|
  1.0 / (1 + Math.sqrt(x))
}

f2 = lambda { |x|
  1.0 / (5 - 3 * Math.cos(x))
}
n = 2000

a1 = 0.2
b1 = 2.1

a2 = 0.2
b2 = 3 / (2 * Math::PI)

puts "Rectangle method for f1 : #{rectangle_method(f1, a1, b1, n)}"
puts "Trapezoid method for f1 : #{trapezoid_method(f1, a1, b1, n)}"
puts "Rectangle method for f2 : #{rectangle_method(f2, a2, b2, n)}"
puts "Trapezoid method for f2 : #{trapezoid_method(f2, a2, b2, n)}"