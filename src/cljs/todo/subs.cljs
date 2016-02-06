(ns todo.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
              [posh.core :as p]
              ))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))
