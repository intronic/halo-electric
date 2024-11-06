(ns halo-electric.ui.avatar
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [halo-electric.dom :as hd]))

(e/defn AvatarText [text size]
  "Avatar text size :xl or :2xl (w-10 or w-20)."
  (e/client
    (let [[c-div c-span]
          (case size
            :xl  ["bg-secondary text-secondary-content rounded-full w-10"
                  "text-xl"]
            :2xl ["bg-secondary text-secondary-content rounded-full w-20"
                  "text-2xl"])]
      (hd/divp {:class "avatar placeholder"}
        (hd/divp {:class c-div}
          (hd/spanp {:class c-span}
            (dom/text text)))))))

(e/defn AvatarTextButton [text size & aria-label]
  (e/client
    (dom/div (dom/props {:class "btn btn-ghost btn-circle avatar"
                         :tabindex "0" :role "button"
                         :aria-label (or aria-label text)})
      (hd/divp {:class "w-10 rounded-full"}
        (AvatarText. text size)))))