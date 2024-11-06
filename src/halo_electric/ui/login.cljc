(ns halo-electric.ui.login
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]))


(e/defn LoginLink [uri text]
  (e/client
    (let [c-link-default "btn btn-outline btn-primary"
          c-link "btn btn-outline btn-neutral-content text-neutral-content"]
      (dom/div {}
        (dom/a
          (dom/props {:class c-link-default
                      :href uri})
          (dom/text text))))))

