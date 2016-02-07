(ns todo.dashboard.tasks
  (:require [posh.core :as p]
            [re-frame.core :as re]
            [todo.components :as comp]))

(defn category-select [task-id]
  (let [categories (:subscribe [:get-categories task-id])]
  [:span
   [:select {:on-change (re/dispatch [:add-task task-id])
             :default-value (nth (first categories) 2)}
    (for [cat categories]
      ^{:key (first cat)} [:option {:value (first cat)} (second cat)])]
   ]))

(defn dash-task [task-id]
  (let [task (re/subscribe [:task-by-id task-id])]
    [:span
     [comp/checkbox task-id :task/done (:task/done task)]
     [comp/editable-label task-id :task/name]
     [comp/stage-button ["X" "X?"]
      #(re/dispatch [:delete-task task-id])]
     [category-select task-id]]))

(defn delete-listed [tasks]
  [comp/stage-button
   ["Delete Listed" "Are you sure?" "They'll be gone forever, ok?"]
   #(re/dispatch [:delete-tasks tasks])])

(defn list-filter [listing]
  [:h3 (case listing
         :all "All Tasks"
         :done "Completed Tasks"
         :not-done "Uncompleted Tasks")]


(defn task-list [todo-id]
  (let [listing (re/subscribe [:get-listing])
        tasks   (re/subscribe [:task-list-tasks])]
    [:div
     (list-filter [listing])
     (if-not (empty? tasks)
       [:div
        (for [task tasks]
          ^{:key task} [:div [dash-task task]])
        [delete-listed tasks]]
       [:div "None"])]))

