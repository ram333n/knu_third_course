class CreateStudents < ActiveRecord::Migration[7.0]
  def change
    create_table :students do |t|
      t.string :surname
      t.string :group
      t.integer :geometry_score
      t.integer :algebra_score
      t.integer :informatics_score

      t.timestamps
    end
  end
end
