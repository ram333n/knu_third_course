class StudentsController < ApplicationController
  def index
    @students = Student.all
  end

  def show
    @student = Student.find(params[:id])
  end

  def new
    @student = Student.new
  end

  def create
    @student = Student.new(student_params)

    if @student.save
      redirect_to @student
    else
      render :new, status: :unprocessable_entity
    end
  end

  def edit
    @student = Student.find(params[:id])
  end

  def update
    @student = Student.find(params[:id])

    if @student.update(student_params)
      redirect_to @student
    else
      render :edit, status: :unprocessable_entity
    end
  end

  def destroy
    @student = Student.find(params[:id])
    @student.destroy

    redirect_to root_path
  end

  def get_with_academic_debt
    @students = Student.where("geometry_score < 3 OR algebra_score < 3 OR informatics_score < 3")
  end

  def get_successful_percentage
    @total_count = Student.count

    if @total_count == 0
      @successful_percentage = 0
    else
      @successful_count = Student.where("geometry_score > 3 AND algebra_score > 3 AND informatics_score > 3").count
      @successful_percentage = @successful_count * 100 / @total_count
    end
  end

  def get_successful_subjects
    @scores_sum = Hash.new
    @scores_sum["Geometry"] = Student.sum(:geometry_score)
    @scores_sum["Algebra"] = Student.sum(:algebra_score)
    @scores_sum["Informatics"] = Student.sum(:informatics_score)
    @scores_sum.sort_by { |k, v| v}.reverse!

    max_score_sum = @scores_sum.values[0]
    subj_array = []

    @scores_sum.each do |k, v|
      if v != max_score_sum || max_score_sum == 0
        break
      end

      subj_array.push(k)
    end

    if !subj_array.empty?
      @successful_subjects = subj_array.join(", ")
    else
      @successful_subjects = "There isn't any student in db"
    end
  end

private
  def student_params
    params.require(:student).permit(:surname, :group, :geometry_score, :algebra_score, :informatics_score)
  end
end
