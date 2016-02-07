(ns todo.categories
  (:require [posh.core :as p]
            [todo.util :as util]
            [todo.tasks :as tasks]
            [todo.components :as comp]
            [todo.dashboard :as dash]
            [re-frame.core :as re]))

;; todo components

(defn delete-category [category-id]
  (let [category (re/subscribe [:category-name])]
    [comp/stage-button
     [(str "Delete \"" (:category/name category) "\" Category") "This will delete all its tasks, ok?"]
     (re/dispatch [:delete-category])]))

(defn category-panel [todo-id]
  (let [cat (re/subscribe [:get-todo-category])]
    (if (not cat)
      [dash/dashboard todo-id]
      [:div
       [:h2 [comp/editable-label cat :category/name]]
       [delete-category cat]
       [tasks/task-panel cat]
       [add-task cat]
       ])))

(defn add-category! [todo-id category-name]
  (util/new-entity! {:category/name category-name :category/todo todo-id}))

(defn add-new-category [conn todo-id]
  [:div "Add new category: " [comp/add-box conn (partial add-category! conn todo-id)]])

(defn category-item [conn todo-id category]
  [:button
   {:onClick #(p/transact!
               conn
               [[:db/add todo-id :todo/display-category (:db/id category)]])}
   (:category/name category)
   " (" (count (:task/_category category)) ")"])

(defn category-menu [conn todo-id]
  (let [cats (subscribe [:category-menus])]
    [:span
     (for [c cats]
       ^{:key (:db/id c)}
       [category-item conn todo-id c])]))
