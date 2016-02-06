(ns todo.views
    (:require [re-frame.core :as re]
              [reagent.core :as r]
              [todo.db :as db]
              [datascript.core :as d]
              [posh.core :as p]
              ))

(defn main-panel []
  (let [name (re/subscribe [:name])]
    (fn []
      [:div "Hello from " @name])))


(defn app [conn todo-id]
  (let [todo @(p/pull conn '[:todo/name] todo-id)]
    [:div
     [:h1 (:todo/name todo)]
     ]
   ))

(defn start [conn]
  (let [todo-id (d/q '[:find ?todo . :where [?todo :todo/name _]] @conn)]
    (r/render-component
      [app conn todo-id]
      (.getElementById js/document "app"))))

(p/posh! db/conn)

(start db/conn)