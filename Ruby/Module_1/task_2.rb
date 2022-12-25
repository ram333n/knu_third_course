require 'date'

class Product
  attr_accessor :id, :name, :upc, :producer, :price, :expiration_date, :amount

  def initialize(id, name, upc, producer, price, expiration_date, amount)
    @id = id
    @name = name
    @upc = upc
    @producer = producer
    @price = price
    @expiration_date = expiration_date
    @amount = amount
  end

  def to_s
    "{id=#{id}, name=#{name}, upc=#{upc}, producer=#{producer}, price=#{price}, expiration_date=#{expiration_date}, amount=#{amount}}"
  end
end

def get_by_predicate(products, &predicate)
  products.each do |product|
    if predicate.call(product)
      puts product
    end
  end
end

def get_by_name(products, name)
  get_by_predicate(products) do |product|
    product.name == name
  end
end

def get_by_name_and_price_bound(products, name, price_bound)
  get_by_predicate(products) do |product|
    product.name == name && product.price <= price_bound
  end
end

def get_by_expiration_date_bound(products, date_bound)
  get_by_predicate(products) do |product|
    product.expiration_date <= date_bound
  end
end

def create_array
  result = []

  result.push(Product.new(1, "Bread", "123456789012", "Kyivkhlib",20, Date.new(2022, 10, 23), 1))
  result.push(Product.new(2, "Bread", "523456789012", "Novus",30, Date.new(2022, 10, 22), 1))
  result.push(Product.new(3, "Bread", "023466789012", "Zhytomyrkhlib",25, Date.new(2022, 10, 24), 1))
  result.push(Product.new(4, "Chocolate", "344667890127", "Millenium",45, Date.new(2023, 10, 24), 1))
  result.push(Product.new(5, "Chocolate", "34466789777", "Roshen",35, Date.new(2023, 10, 24), 1))
  result.push(Product.new(6, "Beer", "34466789111", "Teteriv",40, Date.new(2024, 10, 27), 1))
  result.push(Product.new(7, "Zhivchik", "11111111111", "Obolon",45, Date.new(2024, 12, 24), 1))

  result
end

products = create_array
puts "=========================Test get_by_name========================="
get_by_name(products, "Bread")
puts "=========================Test get_by_name_and_price_bound========================="
get_by_name_and_price_bound(products, "Bread", 25)
puts "=========================Test get_by_expiration_date_bound========================="
get_by_expiration_date_bound(products, Date.new(2023, 10, 24))