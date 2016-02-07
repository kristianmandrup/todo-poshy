(ns todo.util
  (:require [datascript.core :as d]
            [todo.db :as db]))

;;; util
(defn pairmap [pair] (apply merge (map (fn [[a b]] {a b}) pair)))

(defn ents [db ids] (map (partial d/entity db) ids))

(defn get-tempids) [varmap]
  (d/transact! db/conn [(merge varmap {:db/id -1})])) -1)

(defn new-entity! [varmap]
  ((:tempids get-tempids(varmap))

;;; setup

(def tempid (let [n (atom 0)] (fn [] (swap! n dec))))
