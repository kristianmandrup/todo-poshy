(ns todo.subscribers
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
            [posh.core :as p]
            [datascript.core :as d]))

;; TODO: Extract subscriber into domain specific files

(defn todo-by-name
  [db]
  (d/q '[:find ?todo . :where [?todo :todo/name _]] @db))

(defn todo-by-id
  [db, [id]]
  (p/pull db '[:todo/name] id))

(defn get-edit
  [db, [edit-id]]
  (p/pull db [:edit/val] edit-id))

(defn get-todo-category
  [db, [todo-id]]
    (p/q db '[:find ?c .
                 :in $ ?t
                 :where
                 [?t :todo/display-category ?c]]
          todo-id))

(defn get-value
  [db, [attr id]]
    (attr @(p/pull db [attr] id)))

(defn edit-by-id
  [db, [edit-id id attr]]
    (p/q db '[:find ?edit .
                 :in $ ?id ?attr
                 :where
                 [?edit :edit/id ?id]
                 [?edit :edit/attr ?attr]]
          id attr))


(defn get-listing
  [db, [todo-id]]
  (-> @(p/pull db [:todo/listing] todo-id)
      :todo/listing))

(defn task-list-tasks
  [db, [listing todo-id]]
  (case listing
    :all     @(p/q db
                   '[:find [?t ...]
                     :in $ ?todo
                     :where
                     [?c :category/todo ?todo]
                     [?t :task/category ?c]]
                   todo-id)
    @(p/q db
          '[:find [?t ...]
            :in $ ?todo ?done
            :where
            [?c :category/todo ?todo]
            [?t :task/category ?c]
            [?t :task/done ?done]]
          todo-id (= listing :done))))

(defn task-by-id
  [db, [task-id]]
  (p/pull db [:db/id :task/done :task/pinned :task/name
                {:task/category [:db/id :category/name]}]
         task-id))


(defn current-category
  [db, [todo-id]]
  (-> @(p/pull db [:todo/display-category] todo-id)
     :todo/display-category
     :db/id))

(defn get-categories
  [db, [task-id]]
  (p/q db
      '[:find ?cat ?cat_name ?task_cat :in $ ?t
        :where
        [?t :task/category ?task_cat]
        [?task_cat :category/todo ?todo]
        [?cat :category/todo ?todo]
        [?cat :category/name ?cat_name]]
      task-id))

(defn get-todo-categories
  [db, [todo-id]]
  (->> @(p/pull db
                '[{:category/_todo [:db/id :category/name {:task/_category [:db/id]}]}]
                todo-id)
       :category/_todo
       (sort-by :category/name)))

(defn category-menus
  [db, [todo-id]]
    (->> @(p/pull db
                  '[{:category/_todo [:db/id :category/name {:task/_category [:db/id]}]}]
                  todo-id)
         :category/_todo
         (sort-by :category/name)))

(defn category-name
  [db, [category-id]]
    (p/pull db [:category/name] category-id))

;; Register subscribers by key
;; - should allow registration via record

(re-frame/register-sub
  :todo-by-name
  todo-by-name)

(re-frame/register-sub
  :todo-by-id
  todo-by-id)

(re-frame/register-sub
  :get-listing
  get-listing)

(re-frame/register-sub
  :get-categories
  get-categories)

(re-frame/register-sub
  :get-todo-categories
  get-todo-categories)


(re-frame/register-sub
  :task-by-id
  task-by-id)

(re-frame/register-sub
  :current-category
  current-category)

(re-frame/register-sub
  :get-todo-category
  get-todo-category)

