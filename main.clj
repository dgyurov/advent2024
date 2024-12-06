(require '[clojure.string :as str])

(def input (str/split (slurp "input.txt") #"\n\n"))
(def rules (->> (first input)
                (str/split-lines)
                (map (fn [x] (map Integer/parseInt (str/split x #"\|"))))))
(def updates (->> (second input)
                  (str/split-lines)
                  (map (fn [x] (map Integer/parseInt (str/split x #"\,"))))))

(defn predicate [x y] (cond (.contains rules [x y]) -1
                            (.contains rules [y x]) 1 :else 0))
(defn is-sorted [p] (= (sort predicate p) p))
(defn middle-page [xs] (nth xs (quot (count xs) 2)))

; Part 1
(println (->> (filter is-sorted updates) (map middle-page) (reduce +)))

; Part 2
(println (->> (remove is-sorted updates) (map (comp middle-page (partial sort predicate))) (reduce +)))
