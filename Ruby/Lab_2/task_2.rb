def get_range(base, t, r)
  (base ** r * (1 - base ** -t) + 1).to_i
end

var_base = 2
var_t = 64
var_r = 16

puts "Range to : #{get_range(var_base, var_t, var_r)}"
