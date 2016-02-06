(ns todo.handlers
  (:require [re-frame.core :as re]
            [todo.db :as db]
            [posh.core :as p]
            [todo.util :as util]))

;; atom is db/conn, the DataScript DB configured in db.cljs
;;; setup database
(re/register-handler
  :initialize-db
  ;;; setup database
  (fn  [_ _]
    (do
      (db/populate! db/conn)
      (p/posh! db/conn))))

(re/register-handler
  :dashboard-category
  (fn  [db, [category todo-id]]
    (p/transact!
      db
      [[:db/add todo-id :todo/display-category (:db/id category)]])))

(re/register-handler
  :change-listing
  (fn  [db, [todo-id v]]
    (p/transact! db [[:db/add todo-id :todo/listing v]]))

(re/register-handler
  :edit-task
  (fn  [db, [edit-id v]]
    (p/transact! db [[:db/add edit-id :edit/val (-> % .-target .-value)]])))

(re/register-handler
  :task-done
  (fn  [db, [edit-id id attr edit]]
    (p/transact! db [[:db/add id attr (:edit/val edit)]
                      [:db.fn/retractEntity edit-id]])

(re/register-handler
  :checked
  (fn  [db, [id attr]]
    (p/transact! db[[:db/add id attr (not checked?)]])))

(re/register-handler
  :new-task
  (fn  [db, [id val attr]]
    (util/new-entity! db {:edit/id id :edit/val val :edit/attr attr})))

(re/register-handler
  :task-cancel
  (fn  [db, [edit-id]]
    (p/transact! db [[:db.fn/retractEntity edit-id]])))

(re/register-handler
  :act-on-category
  (fn  [db, [current-category todo-id]]
    (p/transact!
      db
      (if current-category
        [[:db/retract todo-id :todo/display-category current-category]
         [:db/add todo-id :todo/listing :all]]
        [])))

(re/register-handler
  :delete-tasks
  (fn  [db, [tasks]]
    (p/transact! db (map (fn [t] [:db.fn/retractEntity t]) tasks))))

(re/register-handler
  :delete-task
  (fn  [db, [task-id]]
    (p/transact! db [[:db.fn/retractEntity task-id]]))

(re/register-handler
  :add-task
  (fn  [db [task-id]]
    (p/transact!
      db
      [[:db/add task-id :task/category
        (cljs.reader/read-string (.. % -target -value))]])))

(re/register-handler
  :change-listing
  (fn  [db [todo-id type]]
    (change-listing! db todo-id type))