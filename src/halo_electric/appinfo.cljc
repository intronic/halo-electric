(ns halo-electric.appinfo
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.rcf :refer [tests tap %]]))

(defn client-app-context [ctx]
  (select-keys ctx [:id :launch-uri :landing-uri :logout-ring-uri :logout-oidc-uri]))

(e/def app-context nil)

(e/defn host-uri []
  (:host-uri app-context))

(e/defn base-path []
  (:landing-uri app-context))

(e/defn login-path []
  (:launch-uri app-context))

(e/defn logout-path []
  (:logout-ring-uri app-context))

(e/defn single-sign-out-path []
  (:logout-oidc-uri app-context))
