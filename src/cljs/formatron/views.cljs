(ns formatron.views
  (:require [re-frame.core :as re-frame]
            [firebase-cljs.core :as fb]))


;; login button
(defn login-button []
  (fn []
    [:button {:on-click (fn [] (re-frame/dispatch [:login]))} (str "Login")]
    ))
;; home

(defn home-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div (str "Hello from " @name ". This is the Home Page.")
       [login-button]
       [:div [:a {:href "#/about"} "go to About Page"]]])))


;; about

(defn about-panel []
  (let [username (re-frame/subscribe [:username])]
    (fn []
      [:div (str "Hello: " @username )
       [:div [:a {:href "#/"} "go to Home Page"]]])))


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [show-panel @active-panel])))
