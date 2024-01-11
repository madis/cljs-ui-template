(ns is.mad.browser-app.pages)

(defn greetings []
  [:h1 "Hello!"])

(defn users []
  [:article {:class [:message :is-info]}
    [:div {:class [:message-header]}
     [:p "Info"]
     [:button {:class [:delete]
               :aria-label "delete"}]]
    [:div {:class [:message-body]}
      "This is message body"]])

(defn landing []
  [:div
   [:h1.title "Welcome to the landing page!"]])
