=begin
  ((|x-2|) / (x^2 * cos(x)))^(1/7) at x = -2
  1 / (tg(x+1/e^x)/sin^2(x))^(7/2) at x = 4.5
  (2x + 1) / (x^2 + 3x + 1) at x = 13
=end

def evaluate_first(x)
  ((x - 2).abs / (x ** 2 * Math.cos(x))) ** (1.0/7)
end

def evaluate_second(x)
  1 / (Math.tan(x + Math.exp(-x)) / (Math.sin(x) ** 2)) ** (7.0/2)
end

#i have simplified this fraction to write function more clearly
def evaluate_third(x)
  (2 * x + 1).to_f / (x ** 2 + 3 * x + 1)
end

def evaluate_if(x)
  if x > -4 && x <= 0
    evaluate_first(x)
  elsif x > 0 && x <= 12
    evaluate_second(x)
  elsif x < -4 || x > 12
    evaluate_third(x)
  end
end

def evaluate_case(x)
  case x
  when -4..0 then
    if x != -4
      evaluate_first(x)
    end
  when 0..12 then
    if x != 0
      evaluate_second(x)
    end
  else
    if x != -4
      evaluate_third(x)
    end
  end
end

x = -2
puts "With if, x = #{x} #{evaluate_if(x)}"
puts "With case, x = #{x} #{evaluate_case(x)}"

x = 4.5
puts "With if, x = #{x} #{evaluate_if(x)}"
puts "With case, x = #{x} #{evaluate_case(x)}"

x = 13
puts "With if, x = #{x} : #{evaluate_if(x)}"
puts "With case, x = #{x} #{evaluate_case(x)}"