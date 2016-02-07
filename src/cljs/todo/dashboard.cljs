(ns todo.dashboard
  (:require [posh.core :as p]
            [todo.db :as db :refer [conn]]
            [todo.util :as util]
            [todo.tasks :as tasks]
            [todo.dashboard.tasks :as dbtasks]
            [todo.handlers :as ha]
            [todo.components :as comp]
            [re-frame.core :as re]))

(defn list-button [type label]
  [:button
   {:on-click (re/dispatch [:change-listing type])} label]
  )

(defn categories [category]
  (count (:task/_category category))

(defn dashboard-category [todo-id category]
  [:div
   [:button
    {:onClick #(re/dispatch [:dashboard-category category todo-id])}
    (:category/name category)] " (" categories ")"])


(defn list-buttons []
  [:div
    (list-button :all "All")
    (list-button :done "Checked")
    (list-button :not-done "Un-checked")])

(defn dashboard [todo-id]
  (let [cats (re/subscribe [:get-todo-categories todo-id])]
    [:div
     [:h2 "DASHBOARD: "]
     list-buttons
     (dbtasks/task-list cats todo-id)]))


(defn dashboard-button [todo-id]
  (let [current-category (re/subscribe [:current-category todo-id])]
    [:button
     {:onClick (re/dispatch [:act-on-category current-category todo-id])}
     "Dashboard"]))