(ns halo-electric.appinfo)

(defn host-uri
  [ctx]
  (:host-uri ctx))

(defn base-path
  [ctx]
  (:landing-uri ctx))

(defn login-path
  [ctx]
  (:launch-uri ctx))

(defn logout-path
  [ctx]
  (:logout-ring-uri ctx))

(defn single-sign-out-path
  [ctx]
  (:logout-oidc-uri ctx))
