$N = 9

def evaluate_y(x, c, n)
  first_fraction = (x - c) / (x ** (3.0/4) + (x ** (1.0 / n)) * c ** (1.0 / 4))
  second_fraction = ((x ** 0.5) * (c ** (1.0 / 4)) + (n ** (1.0 / 4))) / ((x ** (1.0 / 2)) + (c ** (1.0 / 2)))
  third_fraction = ((x ** (1.0 / 4) * (c ** (-1.0 / 4)))) / ((x ** (1.0 / 2)) - 2 * (x ** (1.0 / 4)) * (c ** (1.0 / 4)) + c ** (1.0 / n))

  first_fraction * second_fraction * third_fraction
end

def evaluate_z(x)
  first_member = Math.sin(2 * x) ** 2
  second_member = Math.cos(Math::PI / 3 - 2 * x) * Math.sin(2 * x - Math::PI / 6)
  third_member = (1 / Math.tan((Math::PI + x) / (x + 1))) ** (2.0 / x)

  first_member - second_member - third_member
end

def first_task(n, c)
  dx = (n - 1.0) / (n + c)
  x = 1

  for i in 1..(n + c)
    puts "dx = #{dx}"
    puts "x = #{x}, y(x) = #{evaluate_y(x, c, n)}"
    x += dx
  end
end

def second_task(n, c)
  dx = (Math::PI - Math::PI / n) / (1.5 * n + c)
  x = Math::PI - Math::PI / n

  while x < Math::PI
    puts "x = #{x}, z(x) = #{evaluate_z(x)}"
    x += dx
  end
end

def third_task(n, c)
  dx = (c - 2.0) / (2 * n)
  x = 2.0

  while x < c
    if x > 2 && x < n
      puts "x = #{x}, f(x) = #{evaluate_y(x, c, n)}"
    elsif  x > n && x < 2 * n
      puts "x = #{x}, f(x) = #{evaluate_z(x)}"
    else
      puts "x = #{x}, f(x) = #{evaluate_y(x, c, n) + evaluate_z(x)}"
    end

    x += dx
  end
end

c = 5

puts "-------------------------------------First-------------------------------------"
first_task($N, c)

puts "-------------------------------------Second-------------------------------------"
second_task($N, c)

puts "-------------------------------------Third-------------------------------------"
third_task($N, c)