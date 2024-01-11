(ns is.mad.browser-app.core
  (:require
    [re-frame.core :as re]
    [reagent.dom.client :as rdc]
    [is.mad.browser-app.events] ; Need to require to get handlers registered
    [is.mad.browser-app.subscriptions] ; Need to require to get handlers registered
    [is.mad.browser-app.routes :as routes]
    [is.mad.browser-app.layout :as layout]))

(defonce root-container
  (rdc/create-root (js/document.getElementById "app")))

(defn router-component [{:keys [router]}]
  (let [current-route @(re/subscribe [::routes/current-route])]
    [layout/c-layout
     (when current-route
       (-> current-route :data :view))]))

(defn mount-ui
  []
  (rdc/render root-container [router-component]))

(defn ^:dev/after-load clear-cache-and-render!
  []
  ;; The `:dev/after-load` metadata causes this function to be called
  ;; after shadow-cljs hot-reloads code. We force a UI update by clearing
  ;; the Reframe subscription cache.
  (re/clear-subscription-cache!)
  (re/dispatch [:initialize-db])
  (routes/init-routes!)
  (mount-ui))

(defn main []
  (println "is.mad.browser-app.core/main : App starting")
  (re/dispatch [:initialize-db])
  (routes/init-routes!)
  (mount-ui))
