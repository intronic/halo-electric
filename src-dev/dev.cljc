(ns dev
  (:require
   [com.halo9000.app :as app]
   [hyperfiddle.electric :as e]
   #?(:clj [halo-electric.server-jetty :as jetty])
   #?(:clj [shadow.cljs.devtools.api :as shadow])
   #?(:clj [shadow.cljs.devtools.server :as shadow-server])
   #?(:clj [clojure.tools.logging :as log])
   #?(:clj [clojure.edn :as edn])
   #?(:clj [clojure.string :as str])))

(comment (-main)
  ) ; repl entrypoint

#?(:clj ;; Server Entrypoint

   (do
     (defn client-ctx [profile]
       (select-keys profile [:launch-uri
                             :landing-uri
                             :logout-ring-uri
                             :logout-oidc-uri]))

     (let [oidc-profile-key (as-> "HALO_OIDC_PROFILE_KEY" k (System/getenv k) (when (not (str/blank? k)) (keyword k)))
           oidc-profile (as-> "HALO_OIDC_PROFILE" k (System/getenv k) (if k (slurp k) "{}") (edn/read-string k)
                          (select-keys k [oidc-profile-key]))]
       (def config
         {:host "0.0.0.0"
          :port 8080
          :resources-path "public/halo_electric"
          :manifest-path ; contains Electric compiled program's version so client and server stays in sync
          "public//halo_electric/js/manifest.edn"
          :oidc-profile oidc-profile
          :oidc-profile-key oidc-profile-key}))

     (defn -main [& args]
       (let [ctx (client-ctx (get-in config [:oidc-profile (get config :oidc-profile-key)]))]
         (when (= {} ctx) (throw (ex-info "Missing OIDC config or key"
                                   (into {} (for [[k v] (select-keys config [:oidc-profile-key :oidc-profile])]
                                              [k (if (map? v) (into {} (for [[k v'] v] [k (keys v')])) v)])))))
         (log/info "Starting Electric compiler and server..." ctx)

         (shadow-server/start!)
         (shadow/watch :dev)
         (comment (shadow-server/stop!))

         (def server (jetty/start-server!
                       (fn [ring-request]
                         (e/boot-server {} app/Main ring-request ctx))
                       config))

         (comment (.stop server))))))

#?(:cljs ;; Client Entrypoint
   (do
     (def electric-entrypoint (e/boot-client {} app/Main nil nil))

     (defonce reactor nil)

     (defn ^:dev/after-load ^:export start! []
       (set! reactor (electric-entrypoint
                       #(js/console.log "Reactor success:" %)
                       #(js/console.error "Reactor failure:" %))))

     (defn ^:dev/before-load stop! []
       (when reactor (reactor)) ; stop the reactor
       (set! reactor nil))))
