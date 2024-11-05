(ns prod
  (:require
   #?(:clj [clojure.edn :as edn])
   #?(:clj [clojure.java.io :as io])
   #?(:clj [clojure.tools.logging :as log])
   [contrib.assert :refer [check]]
   halo-electric.main
   #?(:clj [halo-electric.server-jetty :as jetty])
   [hyperfiddle.electric :as e])
  #?(:cljs (:require-macros [prod :refer [compile-time-resource]])))

(defmacro compile-time-resource [filename] (some-> filename io/resource slurp edn/read-string))

(def config
  (merge
    ;; Client program's version and server program's versions must match in prod (dev is not concerned)
    ;; `src-build/build.clj` will compute the common version and store it in `resources/electric-manifest.edn`
    ;; On prod boot, `electric-manifest.edn`'s content is injected here.
    ;; Server is therefore aware of the program version.
    ;; The client's version is injected in the compiled .js file.
    (doto (compile-time-resource "electric-manifest.edn") prn)
    {:host "localhost", :port 8080,
     :resources-path "public/halo_electric"
     ;; shadow build manifest path, to get the fingerprinted main.sha1.js file to ensure cache invalidation
     :manifest-path "public/halo_electric/js/manifest.edn"}))

;;; Prod server entrypoint

#?(:clj
   (defn -main [& {:strs [] :as args}] ; clojure.main entrypoint, args are strings
     (log/info (pr-str config))
     (check string? (::e/user-version config))
     (jetty/start-server!
       (fn [ring-req] (e/boot-server {} halo-electric.main/Main ring-req))
       config)))

;;; Prod client entrypoint

#?(:cljs
   (do
     (def electric-entrypoint (e/boot-client {} halo-electric.main/Main nil))
     (defn ^:export start! []
       (electric-entrypoint
         #(js/console.log "Reactor success:" %)
         #(js/console.error "Reactor failure:" %)))))
