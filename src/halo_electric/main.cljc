(ns halo-electric.main
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [halo-electric.ui :as hui]
            [halo-electric.userinfo :as u]
            [halo-electric.appinfo :as app]))

;; Saving this file will automatically recompile and update in your browser

(e/defn LoggedIn [user ctx]
  (e/client
    (hui/NavBar. user ctx "Halo 9000" hui/AppSearchField hui/UserProfile)))

(e/defn LoggedOut [ctx]
  (e/client
    (dom/h1 (dom/props {:class "text-3xl font-bold underline"})
      (dom/text "Welcome to Halo9000 App"))
    (dom/p (dom/a (dom/props {:href (app/login-path ctx)})
             (dom/text "Login")))))

(e/defn Main [ring-request ctx]
  (e/server
    (let [user (some-> ring-request :com.halo9000.ring-oidc-session/userinfo #_u/client-userinfo)]
      (e/client
        #_(println user)
        (binding [dom/node js/document.body]
          (if user
            (LoggedIn. user ctx)
            (LoggedOut. ctx)))))))
