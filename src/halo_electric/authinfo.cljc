(ns halo-electric.authinfo
  (:require [hyperfiddle.electric :as e]))

(defn request-auth
  "Return [id token-map] for ring-oauth2 session in request, or nil."
  [request]
  (some-> request
    (get-in [:session :ring.middleware.oauth2/access-tokens])
    first))

(e/def auth-context nil) ; server-side ring-oauth2 auth and token keys (when present)

(e/defn auth-id []
  (some-> auth-context first))

(e/defn auth-expires []
  (some-> auth-context second :expires))

(e/defn auth-token []
  (some-> auth-context second :token))

(e/defn auth-refresh-token []
  (some-> auth-context second :refresh-token))

(e/defn auth-id-token []
  (some-> auth-context second :id-token))
