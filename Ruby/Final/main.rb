# variant 3

class NetworkId
  attr_accessor :value

  def initialize(value)
    @value = value
  end

  def to_s
    @value
  end

  def <=>(rhs)
    value <=> rhs.value
  end
end

class Phone < NetworkId
  def to_s
    super
  end

  def <=>(rhs)
    super
  end
end

class Skype < NetworkId
  def to_s
    super
  end

  def <=>(rhs)
    super
  end
end

class Email < NetworkId
  def to_s
    super
  end

  def <=>(rhs)
    super
  end
end

class Social < NetworkId
  def to_s
    super
  end

  def <=>(rhs)
    super
  end
end

class Contact
  attr_accessor :name, :phone, :skype, :email, :social

  def initialize(name = nil, phone = nil, skype = nil, email = nil, social = nil)
    @name = name
    @phone = phone
    @skype = skype
    @email = email
    @social = social
  end

  def merge(another)
    Contact.new(@name   || another.name,
                @phone  || another.phone,
                @skype  || another.skype,
                @email  || another.email,
                @social || another.social)
  end

  def to_s
    "#{@name.to_s}: { #{@phone}, #{@skype}, #{@email}, #{@social} }"
  end
end

class Contacts
  def initialize
    @contacts = []
  end

  def add(contact)
    @contacts.push(contact)
  end

  def sort_by_phone
    @contacts.sort_by(&:phone)
  end

  def sort_by_name
    @contacts.sort_by(&:name)
  end

  def find_by_name(name)
    @contacts.find do |c|
      c.name.include? name
    end
  end

  def find_by_phone(phone)
    @contacts.find do |c|
      c.phone.value.include? phone.value
    end
  end

  def to_s
    result = ""
    @contacts.each do |с|
      result += "#{с}\n"
    end

    result
  end
end

def print_array(array)
  array.each do |elem|
    puts elem.to_s
  end
end

# TESTS

contact1_1 = Contact.new(nil, Phone.new("2"), Skype.new("skype"), nil, Social.new("inst"))
contact1_2 = Contact.new("Roman", Phone.new("4"), Skype.new("skype"), Email.new("test@test.com"))
contact1 = contact1_1.merge(contact1_2)

puts "Merged contact:\n #{contact1}"

contact2 = Contact.new("A", Phone.new("1234"), Skype.new("skype2"), nil, Social.new("inst2"))
contact3 = Contact.new("B", Phone.new("3"), Skype.new("skype3"), nil, Social.new("inst3"))
contact4 = Contact.new("C", Phone.new("5"), Skype.new("skype4"), nil, Social.new("inst4"))

contacts = Contacts.new

contacts.add(contact1)
contacts.add(contact4)
contacts.add(contact3)
contacts.add(contact2)

puts "\nContacts:"
puts contacts

puts "\nSort by name:"
print_array(contacts.sort_by_name)

puts "\nSort by phone:"
print_array(contacts.sort_by_phone)

puts "\nFind by phone(1234):"
puts contacts.find_by_phone(Phone.new("1234"))

puts "\nFind by name(Roman):"
puts contacts.find_by_name("Roman")

puts "\nFind by substring(Rom):"
puts contacts.find_by_name("Roman")

puts "\nFind by substring(D):"
puts contacts.find_by_name("D")
