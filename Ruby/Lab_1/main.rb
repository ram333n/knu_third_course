# var 9
# wolfram : (4.1 * 10^(-1.7))/((x-pi)*sin(5x)) + (((tg(|x|))^3 - log10(sqrt(a+phi))) / e^pi) at x = 1 a = 1 phi = 1
def scan_value(label)
  puts "Enter " + label
  gets.chomp.to_f
end


def evaluate_expression(x, a, phi)
  first_fraction = (4.1 * (10 ** -1.7)) / ((x - Math::PI) * Math.sin(5 * x))
  second_fraction = (Math.tan(x.abs) ** 3 - Math.log10(Math.sqrt(a + phi))) / Math.exp(Math::PI)
  first_fraction + second_fraction
end

x = scan_value("x")
a = scan_value("a")
phi = scan_value("phi")

puts "Value : #{evaluate_expression(x, a, phi)}"


