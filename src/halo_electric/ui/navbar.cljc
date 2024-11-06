(ns halo-electric.ui.navbar
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.electric-svg :as svg]
            [halo-electric.ui.icon :as icon]
            #_[heroicons.electric3.v24.outline :refer [bars-3-center-left]]))


(e/defn NavBarGuest
  [title LoginItem]
  (e/client
    (dom/div
      (dom/props {:class "navbar bg-base-100"})
      (dom/div
        (dom/props {:class "flex-1"})
        (dom/a
          (dom/props {:class "btn btn-ghost text-xl"
                      :href "#"})
          (dom/text title)))
      (dom/div
        (dom/props {:class "flex-none gap-2"})
        (LoginItem.)))))

(e/defn NavBar
  [title Search Profile]
  (e/client
    (dom/div (dom/props {:class "navbar bg-primary text-primary-content rounded-box" #_"bg-base-100 "})
      (dom/div (dom/props {:class "flex-1"})
        (dom/a (dom/props {:class "btn btn-ghost text-xl"
                           :href "#"})
          (dom/text title)))
      (dom/div (dom/props {:class "flex-none gap-2"})
        (Search.)
        (Profile.)))))

#_(e/defn DaisyExampleNav
  []
  (e/client
    (dom/div
      (dom/props {:class "navbar bg-base-100"})
      (dom/div
        (dom/props {:class "navbar-start"})
        (dom/div
          (dom/props {:class "dropdown"})
          (dom/div
            (dom/props {:class "btn btn-ghost lg:hidden"
                        :tabindex "0" :role "button"})
            #_(bars-3-center-left (dom/props {:class "h-5 w-5"}))
            (icon/HamburgerLeftLSL.))
          (dom/ul
            (dom/props {:class "menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow"
                        :tabindex "0"})
            (dom/li
              (dom/props {})
              (dom/a
                (dom/props {})
                (dom/text "Item 1")))
            (dom/li
              (dom/props {})
              (dom/a
                (dom/props {})
                (dom/text "Parent"))
              (dom/ul
                (dom/props {:class "p-2"})
                (dom/li
                  (dom/props {})
                  (dom/a
                    (dom/props {})
                    (dom/text "Submenu 1")))
                (dom/li
                  (dom/props {})
                  (dom/a
                    (dom/props {})
                    (dom/text "Submenu 2")))))
            (dom/li
              (dom/props {})
              (dom/a
                (dom/props {})
                (dom/text "Item 3")))))
        (dom/a
          (dom/props {:class "btn btn-ghost text-xl"})
          (dom/text "daisyUI")))
      (dom/div
        (dom/props {:class "navbar-center hidden lg:flex"})
        (dom/ul
          (dom/props {:class "menu menu-horizontal px-1"})
          (dom/li
            (dom/props {})
            (dom/a
              (dom/props {})
              (dom/text "Item 1")))
          (dom/li
            (dom/props {})
            (dom/details
              (dom/props {})
              (dom/summary
                (dom/props {})
                (dom/text "Parent"))
              (dom/ul
                (dom/props {:class "p-2"})
                (dom/li
                  (dom/props {})
                  (dom/a
                    (dom/props {})
                    (dom/text "Submenu 1")))
                (dom/li
                  (dom/props {})
                  (dom/a
                    (dom/props {})
                    (dom/text "Submenu 2"))))))
          (dom/li
            (dom/props {})
            (dom/a
              (dom/props {})
              (dom/text "Item 3")))))
      (dom/div
        (dom/props {:class "navbar-end"})
        (dom/a
          (dom/props {:class "btn"})
          (dom/text "Button"))))))
