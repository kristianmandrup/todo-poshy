(ns todo.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [todo.handlers]
            [todo.subscribers]
            [todo.views :as views]
            [todo.config :as config]
            [todo.db :as db]))

;; print "Dev"  mode if configured for dev mode
(when config/debug?
  (println "DEV mode"))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (views/mount-root))



