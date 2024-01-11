(ns is.mad.browser-app.events
  (:require
    [re-frame.core :as re]))

(def initial-db-state
  {:current-route ""
     :navigation-menu
     {:open? false}})

(re/reg-event-db
  :initialize-db
  (fn [db _]
    (merge db initial-db-state)))

(re/reg-event-db
  :navigation-close
  (fn [db _]
    (assoc-in db [:navigation-menu :open?] false)))

(re/reg-event-db
  :navigation-open
  (fn [db _]
    (assoc-in db [:navigation-menu :open?] true)))
