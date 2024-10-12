(ns halo-electric.ui
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [halo-electric.dom :as hd]
            [halo-electric.userinfo :as u]
            [halo-electric.appinfo :as app]))

(e/defn UserCard [user]
  (e/client
    (hd/divp {:class "card bg-base-100 w-96 shadow-xl"}
      (hd/divp {:class "card-body"})
      (dom/h2 (dom/props {:class "card-title"})
        (dom/text (u/user-username user)))
      (dom/p (dom/props {})
        (dom/text (u/user-name user))))))

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

(e/defn UserProfile [user ctx]
  (e/client
    (let [c-link "link link-hover"
          ;; simulate disabled link (browser ignores disabled property on A tags)
          c-link-disabled "link opacity-75 no-underline cursor-default px-3 py-1"]
      (hd/divp {:class "dropdown dropdown-end"}
        (AvatarTextButton. (u/user-initials user) :xl "Profile menu")
        (dom/ul (dom/props {:class "menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow"
                            :tabindex "0"})
          (dom/li (dom/props {})
            (UserCard. user))
          (dom/li (dom/props {:class c-link-disabled
                              :aria-disabled true :role "link"})
            (dom/text "Profile"))
          (dom/li (dom/props {:class c-link-disabled
                              :aria-disabled true :role "link"})
            (dom/text "Settings"))
          (dom/li
            (dom/a (dom/props {:class c-link
                               :href (app/logout-path ctx)})
              (dom/text "Log Out")))
          (dom/li
            (dom/a (dom/props {:class c-link
                               :href (app/single-sign-out-path ctx)})
              (dom/text "Single Sign Out"))))))))

(e/defn AppSearchField []
  (e/client
    (hd/divp {:class "form-control"}
      (dom/input (dom/props {:class "input input-bordered w-24 md:w-auto"
                             :type "text"
                             :placeholder "Search" :aria-label "Search"})))))

(e/defn NavBar
  [user ctx title Search Profile]
  (e/client
    (hd/divp {:class "navbar bg-base-100"}
      (hd/divp {:class "flex-1"}
        (dom/a (dom/props {:class "btn btn-ghost text-xl"
                           :href "#"})
          (dom/text title)))
      (hd/divp {:class "flex-none gap-2"}
        (Search.)
        (Profile. user ctx)))))
