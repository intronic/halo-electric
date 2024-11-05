(ns halo-electric.ui.select-theme
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]))

;; simulate saving theme on the server, in atom
#?(:clj (defonce !theme (atom "lemonade")))
(e/def theme (e/server (e/watch !theme)))
(e/def theme-list (e/server ["lemonade", "light", "dark", "corporate", "cyberpunk", "tailwindui", "prelineui"]))

(e/defn SelectTheme-change
  "change theme on browser and save setting on server"
  [event]
  (e/client
    (let [thm (.. event -target -value)]
      (println "> client change theme" (.. js/document -documentElement (getAttribute "data-theme")) "->" theme)
      (.. js/document -documentElement (setAttribute "data-theme" thm))
      (e/server
        (println "> server save theme" theme)
        (reset! !theme thm)))))

(e/defn SelectTheme []
  (e/client
    (dom/label (dom/props {:for ::theme-selector})
      (dom/text "Theme"))
    (dom/select (dom/props {:class "select"
                            :id ::theme-selector})
      (e/server
        (e/for [thm theme-list]
          (e/client
            (dom/option
              (dom/props {:value thm :selected (= thm theme)})
              (dom/text thm))))
        (e/client
          (dom/on "change"  SelectTheme-change))))))