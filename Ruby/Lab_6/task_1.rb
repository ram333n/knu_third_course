class Student
  attr_accessor :name, :group, :geometry_score, :algebra_score, :informatics_score
  def initialize(name, group, geometry_score = 0, algebra_score = 0, informatics_score = 0)
    @name = name
    @group = group
    @geometry_score = geometry_score
    @algebra_score = algebra_score
    @informatics_score = informatics_score
  end

  def has_academic_debt
    geometry_score < 3 || algebra_score < 3 || informatics_score < 3
  end

  def inspect
    "Name : #{@name}\nGroup : #{@group}\nGeometry score : #{@geometry_score}\nAlgebra score : #{@algebra_score}\nInformatics score : #{@informatics_score}"
  end
end


class Deanery
  attr_reader :students_map, :student_count
  def initialize
    @students_map = Hash.new
    @student_count = 0
  end

  def add_student(student)
    unless student.is_a? Student
      puts "Student instance expected"
      return false
    end

    if @students_map[student.group] == nil
      @students_map[student.group] = []
    end

    @students_map[student.group].push(student)
    @student_count += 1
    true
  end

  def get_students_by_predicate(predicate)
    result = []
    @students_map.each_value do |students|
      students.each do |student|
        result.append(student) if predicate.call(student)
      end
    end

    result
  end

  def print_students_with_academic_debt
    puts "Students with academic_debt : "
    get_students_by_predicate(lambda{|student| student.has_academic_debt }).each { |student|
      puts student.name
    }
  end

  def get_academic_success
    get_students_by_predicate(lambda{|student| student.has_academic_debt }).length * 100.0 / @student_count
  end

  def get_successful_students_percent
    get_students_by_predicate(lambda{|student|
      student.geometry_score > 3 && student.algebra_score > 3 && student.informatics_score > 3
    }).length * 100.0 / @student_count
  end

  def get_the_most_successful_subject
    counter = Hash.new(0)

    @students_map.each_value do |students|
      students.each do |student|
        counter["geometry"] += student.geometry_score
        counter["algebra"] += student.algebra_score
        counter["informatics"] += student.informatics_score
      end
    end

    counter = counter.sort_by do |k, v|
      v
    end

    counter[counter.length - 1][0]
  end

  def get_groups_by_success
    counter = Hash.new(0)
    @students_map.each do |group, students|
      sum = 0.0
      students.each do |student|
        sum += student.geometry_score + student.algebra_score + student.informatics_score
      end
      counter[group] = sum / students.length
    end

    counter = counter.sort_by do |k, v|
      v
    end

    counter.reverse!

    puts "Groups by success : "
    counter.each do |k, v|
      puts "#{k} : #{v}"
    end
  end
end


def init_deanery
  deanery = Deanery.new
  students = [
    Student.new("Petrenko", "IPS-31", 5, 5, 2), #26, 28, 27
    Student.new("Muzyka", "IPS-31", 5, 5, 5),
    Student.new("Ilchuk", "IPS-31", 5, 5, 5),
    Student.new("Prokopchuk", "IPS-32", 4, 4, 5),
    Student.new("Ivanchuk", "IPS-32", 2, 3, 4),
    Student.new("Abobchuk", "IPS-30", 2, 3, 4),
    Student.new("Ataman", "IPS-30", 3, 3, 2)
  ]

  students.each do |student|
    deanery.add_student(student)
  end

  deanery
end

deanery = init_deanery
deanery.print_students_with_academic_debt
puts "Academic success value : #{deanery.get_academic_success} %"
puts "Successful students count : #{deanery.get_successful_students_percent} %"
puts "The most successful subject : #{deanery.get_the_most_successful_subject}"
deanery.get_groups_by_success
