(ns user.demo-todos-simple
  (:require [contrib.str :refer [empty->nil]]
            #?(:clj [datascript.core :as d])
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.electric-ui4 :as ui]))

(defonce !conn #?(:clj (d/create-conn {}) :cljs nil)) ; database on server
(e/def db) ; injected database ref; Electric defs are always dynamic

(e/defn TodoItem [id]
  (e/server
    (let [e (d/entity db id)
          status (:task/status e)]
      (e/client
        (dom/div
          (ui/checkbox
            (case status :active false, :done true)
            (e/fn [v]
              (e/server
                (e/discard
                  (d/transact! !conn
                    [{:db/id id
                      :task/status (if v :done :active)}]))))
            (dom/props {:id id}))
          (dom/label (dom/props {:for id})
            (dom/text (e/server (:task/description e)))))))))

(e/defn InputSubmit [F]
  ; Custom input control using lower dom interface for Enter handling
  (dom/input (dom/props {:placeholder "Buy milk"})
    (dom/on "keydown" (e/fn [e]
                        (when (= "Enter" (.-key e))
                          (when-some [v (empty->nil (-> e .-target .-value))]
                            (new F v)
                            (set! (.-value dom/node) "")))))))

(e/defn TodoCreate []
  (e/client
    (InputSubmit. (e/fn [v]
                    (e/server
                      (e/discard
                        (d/transact! !conn [{:task/description v
                                             :task/status :active}])))))))

#?(:clj (defn todo-count [db]
          (count
            (d/q '[:find [?e ...] :in $ ?status
                   :where [?e :task/status ?status]]
              db :active))))

#?(:clj (defn todo-records [db]
          (->> (d/q '[:find [(pull ?e [:db/id :task/description]) ...]
                      :where [?e :task/status]] db)
            (sort-by :task/description))))

(e/defn TodoList []
  (e/server
    (binding [db (e/watch !conn)]
      (e/client
        #_(dom/h1 (dom/text "minimal todo list"))
        #_(dom/p (dom/text "it's multiplayer, try two tabs"))
        (dom/div (dom/props {:class "todo-list"})
          (TodoCreate.)
          (dom/div {:class "todo-items"}
            (e/server
              (e/for-by :db/id [{:keys [db/id]} (todo-records db)]
                (TodoItem. id))))
          (dom/p (dom/props {:class "counter"})
            (dom/span (dom/props {:class "count"})
              (dom/text (e/server (todo-count db))))
            (dom/text " items left")))))))