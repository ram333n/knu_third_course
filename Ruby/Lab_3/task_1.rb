#subtask 1
a = true
b = true
c = false
x = -20
y = 60
z = 4

puts "---------------------------Subtask 1---------------------------"
puts "a) #{!(a || b) && (a && !b)}"
puts "b) #{(z != y ? 1 : 0) <= (6 >= y ? 1 : 0) && a || b && c && x >= 1.5}"
puts "c) #{(8 - x * 2 <= z) && (x ** 2 != y ** 2) || z >= 15}"
puts "d) #{x > 0 && y < 0 || z >= (x * y + (-y / x)) - z}"
puts "e) #{!(a || b && !(c || (!a || b)))}"
puts "f) #{x ** 2 + y ** 2 >= 1 && x >= 0 && y >= 0}"
puts "g) #{(a && (c && b != b || a) || c) && b}"

puts "---------------------------Subtask 2---------------------------"
n = 3
m = -6
p = false
q = false
puts "a) #{(n / m + m / n > 3) && (p && q || !p && q)}"