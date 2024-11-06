(ns halo-electric.ui.search
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [halo-electric.dom :as hd]))

(e/defn AppSearchField []
  (e/client
    (hd/divp {:class "form-control"}
      (dom/input (dom/props {:class "input input-bordered w-24 md:w-auto"
                             :type "text"
                             :placeholder "Search" :aria-label "Search"})))))
