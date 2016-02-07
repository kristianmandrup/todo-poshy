(ns todo.util
  (:require [datascript.core :as d]
            [todo.db :as db]))

;;; util
(defn pairmap [pair] (apply merge (map (fn [[a b]] {a b}) pair)))

(defn ents [db ids] (map (partial d/entity db) ids))


