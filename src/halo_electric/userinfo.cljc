(ns halo-electric.userinfo
  (:require [clojure.string :as str]
            [hyperfiddle.rcf :refer [tests tap %]]))

(defn user-initials [user]
  (as-> ((juxt :given_name :family_name) user) i
    (map first i)
    (apply str i)
    (str/upper-case i)
    (if (= 2 (count i))
      i
      (apply str (take 2 (:name user))))))

(defn user-username [user]
  (:preferred_username user))

(defn user-name [user]
  (:name user))

(defn user-email [user]
  (:email user))

(defn user-email-verified? [user]
  (boolean (:email_verified user)))

(defn user-phone [user]
  (:phone_number user))

(defn user-phone-verified? [user]
  (boolean (:phone_number_verified user)))

(tests
  "initials"
  (user-initials {:name "Bob The Builder" :given_name "Bob" :family_name "The Builder"}) := "BT"
  (user-initials {:name "The Builder" :given_name "" :family_name "The Builder"}) := "Th"
  (user-initials {:name "Bob" :given_name "Bob" :family_name ""}) := "Bo"
  (user-initials {:name ""}) := ""
  (user-initials {:name "T"}) := "T"
  (user-initials {:name "T" :given_name "T"}) := "T"
  (user-initials {:name "T" :family_name "T"}) := "T"
  (user-initials {}) := "")

(comment
  :rcf)

#?(:clj
   (defn client-userinfo
     "Filter userinfo properties that are safe to send to client, and
           add server uri request information."
     [userinfo request]
     (-> userinfo
       (select-keys [:email
                     :email_verified
                     :family_name
                     :given_name
                     :locale
                     :name
                     :phone_number
                     :preferred_username
                     :sub
                     :updated_at])
            ;; https://github.com/ring-clojure/ring/blob/master/SPEC.md
       (merge {:request
               (select-keys request [:uri :request-method :scheme :server-name :server-port :remote-addr])}))))
