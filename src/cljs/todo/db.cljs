(ns todo.db
  (:require [datascript.core :as d]
  ))

;; DataScript schema
(def schema {:task/category         {:db/valueType :db.type/ref}
             :category/todo         {:db/valueType :db.type/ref}
             :todo/display-category {:db/valueType :db.type/ref}
             :action/editing        {:db/cardinality :db.cardinality/many}})

;; Returns a connection to DataScript DB created via schema
(def conn (d/create-conn schema))

(defn get-tempids [varmap]
  (d/transact! conn [(merge varmap {:db/id -1})]) -1)

(defn new-entity! [varmap]
  ((:tempids get-tempids(varmap))

(def tempid
  (let [n (atom 0)]
    (fn [] (swap! n dec))))

;; Populate initial DataScript DB according to Schema
(defn populate! [conn]
  (let [todo-id    (new-entity! {:todo/name "Matt's List" :todo/listing :all})
        at-home    (new-entity! {:category/name "At Home" :category/todo todo-id})
        work-stuff (new-entity! {:category/name "Work Stuff" :category/todo todo-id})
        hobby      (new-entity! {:category/name "Hobby" :category/todo todo-id})]
    (d/transact!
      conn
      [{:db/id (tempid)
        :task/name "Clean Dishes"
        :task/done true
        :task/category at-home}
       {:db/id (tempid)
        :task/name "Mop Floors"
        :task/done true
        :task/pinned true
        :task/category at-home}
       {:db/id (tempid)
        :task/name "Draw a picture of a cat"
        :task/done false
        :task/category hobby}
       {:db/id (tempid)
        :task/name "Compose opera"
        :task/done true
        :task/category hobby}
       {:db/id (tempid)
        :task/name "stock market library"
        :task/done false
        :task/pinned true
        :task/category work-stuff}])))

