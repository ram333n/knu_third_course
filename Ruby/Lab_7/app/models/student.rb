class Student < ApplicationRecord
  validates :surname, presence: true
  validates :group, presence: true, length: {maximum: 15}
  validates :geometry_score, presence: true, :inclusion => 2..5
  validates :algebra_score, presence: true, :inclusion => 2..5
  validates :informatics_score, presence: true, :inclusion => 2..5
end
