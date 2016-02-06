(ns todo.components
  (:require [posh.core :as p]
            [todo.db :as db :refer [conn]]
            [reagent.core :as r]
            [todo.util :as util]))

;;; General Purpose Components

;;;;; input box that sends the value of the text back to add-fn

;; TODO: Fix and REFACTOR!!
(defn add-box [add-fn]
  (let [edit (r/atom "")]
    (fn [add-fn]
      [:span
       [:input
        {:type "text"
         :value @edit
         :onChange #(reset! edit (-> % .-target .-value))}]
       [:button
        {:onClick #(when-not (empty? @edit)
                    (add-fn @edit)
                    (reset! edit ""))}
        "Add"]])))

;;;;; edit box

(defn edit-box [edit-id id attr]
  (let [edit (re/subscribe [:get-edit])]
    [:span
     [:input
      {:type "text"
       :value (:edit/val edit)
       :onChange (re/dispatch [:edit-task edit-id])}]
     [:button
      {:onClick (re/dispatch [:task-done edit-id])}
      "Done"]
     [:button
      {:onClick (re/dispatch [:task-cancel edit-id])}
      "Cancel"]]))

(defn new-task [id attr]


(defn editable-label [id attr]
  (let [val  (re/subscribe [:get-value id attr])
        edit (re/subscribe [:edit-by-id id attr])]
    (if-not edit
      [:span val
       [:button
        {:onClick }
        "Edit"]]
      [edit-box conn edit id attr])))

;;; check box

(defn checkbox [id attr checked?]
  [:input
   {:type "checkbox"
    :checked checked?
    :onChange (re/dispatch [:checked checked?])}])

;; stage button

(defn stage-button [stages finish-fn]
  (let [stage (r/atom 0)]
    (fn [stages finish-fn]
      (when (= @stage (count stages))
        (do (finish-fn)
            (reset! stage 0)))
      [:button
       {:onClick    #(swap! stage inc)
        :onMouseOut #(reset! stage 0)}
       (nth stages @stage)])))



