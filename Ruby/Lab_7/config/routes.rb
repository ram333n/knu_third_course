Rails.application.routes.draw do
  root "students#index"

  get "/students/academic-debt", to: "students#get_with_academic_debt"
  get "/students/successful-percentage", to: "students#get_successful_percentage"
  get "/students/successful-subjects", to: "students#get_successful_subjects"
  resources :students
end
