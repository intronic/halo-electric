(ns halo-electric.main
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [halo-electric.ui.login :as login]
            [halo-electric.ui.navbar :as nav]
            [halo-electric.ui.user :as user]
            [halo-electric.ui.search :as search]
            [halo-electric.userinfo :as u]
            [halo-electric.appinfo :as app]
            [halo-electric.authinfo :as auth]))

;; Saving this file will automatically recompile and update in your browser

(e/defn LoggedIn []
  (e/client
    #_(prn "; client auth" auth/auth-context)
    #_(prn "; client user" u/user-context)
    (nav/NavBar. "Halo 9000" search/AppSearchField user/UserProfile)))

(e/defn LoggedOut []
  (e/client
    (nav/NavBarGuest.
      "Welcome to Halo9000"
      (e/fn [] (login/LoginLink. (app/login-path.) "Sign In")))))

(e/defn Main []
  (e/server
    #_(println "; main server-auth " auth/auth-context)
    #_(println "; main server-user " u/user-context)
    #_(println "; main server-ctx " app/app-context)
    (e/client
      #_(println "; main client-auth " auth/auth-context)
      #_(println "; main client-user " u/user-context)
      #_(println "; main client-ctx " app/app-context)
      (binding [dom/node js/document.body]
        (if u/user-context
          (LoggedIn.)
          (LoggedOut.))))))

(e/defn AuthContext [ring-request ctx]
  (e/server
    #_(println :ctx (keys ctx) :app-ctx app/app-context)
    (let [user (u/request-user ring-request)]
      (binding [auth/auth-context (auth/request-auth ring-request)
                u/user-context user
                app/app-context ctx]
        (let [user (u/client-user-context user)
              ctx (app/client-app-context ctx)]
          (e/client
            (binding [u/user-context user
                      app/app-context ctx]
              (Main.))))))))
