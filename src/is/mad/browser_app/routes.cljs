(ns is.mad.browser-app.routes
  (:require
    [re-frame.core :as re]
    [reitit.core :as r]
    [reitit.coercion.spec :as rss]
    [reitit.frontend :as rf]
    [reitit.frontend.controllers :as rfc]
    [reitit.frontend.easy :as rfe]
    [is.mad.browser-app.pages :as pages]))

(def routes
  ["/"
   [["users" {:view pages/users}]
    ["greetings" {:view pages/greetings}]
    ["" {:view pages/landing}]]])

(re/reg-fx :push-state
  (fn [route]
    (apply rfe/push-state route)))

(re/reg-event-fx ::push-state
  (fn [_ [_ & route]]
    {:push-state route}))

(re/reg-event-db ::navigated
  (fn [db [_ new-match]]
    (let [old-match   (:current-route db)
          controllers (rfc/apply-controllers (:controllers old-match) new-match)]
      (assoc db :current-route (assoc new-match :controllers controllers)))))

(re/reg-sub ::current-route
  (fn [db]
    (:current-route db)))

(defn on-navigate [new-match]
  (when new-match
    (re/dispatch [::navigated new-match])
    (re/dispatch [:navigation-close])))

(def router
  (rf/router
    routes
    {:conflicts (constantly nil)
     :data {:coercion rss/coercion}}))

(defn init-routes! []
  (rfe/start!
    router
    on-navigate
    {:use-fragment false}))
