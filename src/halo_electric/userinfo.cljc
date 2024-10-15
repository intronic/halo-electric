(ns halo-electric.userinfo
  (:require [clojure.string :as str]
            [hyperfiddle.electric :as e]
            [hyperfiddle.rcf :refer [tests]]))

(defn request-user
  "Return userinfo from request if authenticated."
  [request]
  (:com.halo9000.ring-oidc-session/userinfo request))

;; all ok for client on the websocket wire.. just keeping out of saved session state or tokens it GET requests..
(defn client-user-context [userinfo]
  userinfo)

(e/def user-context nil) ; user context when authenticated

;; These could come instead from the user-context as zero-param functions

(defn initials [name given-name family-name]
  (as-> [given-name family-name] i
    (map first i)
    (apply str i)
    (str/upper-case i)
    (if (= 2 (count i))
      i
      (apply str (take 2 name)))))

(e/defn user-username []
  (:preferred_username user-context))

(e/defn user-name []
  (:name user-context))

(e/defn user-given-name []
  (:given_name user-context))

(e/defn user-family-name []
  (:family_name user-context))

(e/defn user-initials []
  (initials (user-name.) (user-given-name.) (user-family-name.)))

(e/defn user-email []
  (:email user-context))

(e/defn user-email-verified? []
  (boolean (:email_verified user-context)))

(e/defn user-phone []
  (:phone_number user-context))

(e/defn user-phone-verified? []
  (boolean (:phone_number_verified user-context)))

(e/defn user-org []
  (:urn:zitadel:iam:user:resourceowner:name user-context))

(e/defn user-org-id []
  (:urn:zitadel:iam:user:resourceowner:id user-context))

(e/defn user-role? [role]
  (some? (get-in user-context [:urn:zitadel:iam:org:project:roles role])))

(e/defn role-developer? []
  (user-role?. :halo-developer))


(tests
  "initials"
  (initials "Bob The Builder" "Bob" "The Builder") := "BT"
  (initials "The Builder" "" "The Builder") := "Th"
  (initials "Bob" "Bob" "") := "Bo"
  (initials ""  nil nil) := ""
  (initials "T" nil nil) := "T"
  (initials "T" "T" nil) := "T"
  (initials "T" nil "T") := "T"
  (initials nil nil nil) := ""
  :rcf)

(comment
  :rcf)
