(ns is.mad.browser-app.layout
  (:require
    [re-frame.core :as re]))

(defn c-navbar []
  (let [menu-open? (re/subscribe [:navigation-menu-open?])
        toggle-navigation-menu #(re/dispatch (if @menu-open? [:navigation-close] [:navigation-open]))]
    [:nav.navbar
     [:div.navbar-brand
      [:div.navbar-item
       [:a {:href "/"}
        [:img {:src "images/cljs-logo.svg" :width 112 :height 28}]]]
      [:a {:class [:navbar-burger (when @menu-open? :is-active)]
           :on-click toggle-navigation-menu
           :data-target "navbar-main"
           :role "button"
           :aria-label "menu"
           :aria-expanded false}
       [:span {:aria-hidden true}]
       [:span {:aria-hidden true}]
       [:span {:aria-hidden true}]]]
     [:div {:id "navbar-main"
            :class [:navbar-menu (when @menu-open? :is-active)]}
      [:div.navbar-start
       [:a.navbar-item {:href "/greetings"} "Greetings"]
       [:a.navbar-item {:href "/users"} "Users"]]]]))

(defn c-layout [content & args]
  [:div
   [c-navbar]
   [:section {:class [:section]}
    [:div {:class [:container]}
     (into [content] args)]]])
