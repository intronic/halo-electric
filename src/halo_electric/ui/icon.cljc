(ns halo-electric.ui.icon
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.electric-svg :as svg]
            #_[heroicons.electric3.v24.outline :refer [bars-3-center-left]]))

;bars-3-center-left
(e/defn HamburgerLeftLSL
  []
  (e/client
    #_(bars-3-center-left (dom/props {:class "h-5 w-5"}))
    (svg/svg
      (dom/props {:class "h-5 w-5"
                  :fill "none" :stroke "currentColor" :viewBox "0 0 24 24" :data-xmlns "http://www.w3.org/2000/svg"})
      (svg/path (dom/props {:stroke-linecap "round" :stroke-linejoin "round" :stroke-width "2" :d "M4 6h16M4 12h8m-8 6h16"})))))
