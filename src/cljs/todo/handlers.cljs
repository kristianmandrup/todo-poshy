(ns todo.handlers
    (:require [re-frame.core :as re-frame]
              [todo.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/conn))
