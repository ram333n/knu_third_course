Rails.application.routes.draw do
  root "students#index"

  get "/students/academic-debt", to: "students#get_with_academic_debt"
  get "/students/successful-percentage", to: "students#get_successful_percentage"
  get "/students/successful-subjects", to: "students#get_successful_subjects"
  get "/students/groups-by-success", to: "students#get_groups_by_success"
  post "students", to: "students#create"

  resources :students
end
