(ns todo.views
  (:require [re-frame.core :as re]
            [reagent.core :as r]
            [todo.db :as db]
            [datascript.core :as d]
            [posh.core :as p]
            [todo.util :as util :refer [tempid]]
            [todo.categories :as cats]
            [todo.dashboard :as dash]))

(defn main-panel []
  (let [name (re/subscribe [:name])]
    (fn []
      [:div "Hello from " @name])))


(defn app [todo-id]
  (let [todo (re/subscribe [:todo-by-id todo-id])]
    [:div
     [:h1 (:todo/name todo)]
     [dash/dashboard-button todo-id]
     [cats/category-menu todo-id]
     [cats/add-new-category todo-id]
     [cats/category-panel todo-id]]))

(defn mount-root []
  (let [todo-id (re/subscribe [:todo-by-name])]
    (r/render-component
      [app todo-id]
      (.getElementById js/document "app"))))

