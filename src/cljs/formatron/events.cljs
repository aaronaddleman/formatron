(ns formatron.events
    (:require [re-frame.core :as re-frame]
              [formatron.db :as db]
              [firebase-cljs.core :as fb]
              [firebase-cljs.auth :as fa]
              [firebase-cljs.user :as fu]
              [firebase-cljs.auth.provider :as fp]))

(re-frame/reg-cofx
 :fb-db
 (fn [cofx _]
   (assoc cofx :fb-db (fb/get-db))))

(re-frame/reg-cofx
 :fb-auth
 (fn [cofx _]
   (assoc cofx :fb-auth (fb/get-auth))))

(re-frame/reg-event-fx
 :update-user
 [(re-frame/inject-cofx :db)
  (re-frame/inject-cofx :fb-auth)]
 (fn [{:keys [fb-auth db]} _]
   {:db {:user (-> fb-auth
                   (fa/current-user)
                   (fu/name))}}))


(re-frame/reg-event-fx
 :login
 [(re-frame/inject-cofx :fb-auth)]
 (fn [{:keys [fb-auth]} _]
   (->
    (fa/login-popup fb-auth (fp/google))
    (.then #(do
              (re-frame/dispatch [:update-user])
              (re-frame/dispatch [:set-active-panel :about-panel]))))
   {}))


(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-fx
 :init
 (fn [_ _]
   {:dispatch-n [[:initialize-db]
                 [:init-firebase]
                 [:set-active-panel :home-panel]]}
   ))

(re-frame/reg-event-db
 :init-firebase
 (fn [db _]
   (fb/init (:firebase-config db))
   db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))
