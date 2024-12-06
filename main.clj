(require '[clojure.string :as str])

(def grid (map seq (str/split-lines (slurp "input.txt"))))

(def start (first (for [i (range (count grid))
                        j (range (count (nth grid i)))
                        :when (= \^ (nth (nth grid i) j))] [i j])))

(defn is-in-bounds [grid i j]
  (and (>= i 0) (>= j 0) (< i (count grid)) (< j (count (first grid)))))

(defn next-coordinate [direction i j]
  (case direction 0 [(dec i) j] 1 [i (inc j)] 2 [(inc i) j] 3 [i (dec j)]))

(defn rotate [direction] (mod (+ direction 1) 4))

(defn search [grid direction i j visited]
  (loop [grid grid direction direction i i j j visited visited]
    (if (and (is-in-bounds grid (int i) (int j)) (not (contains? visited [i j direction])))
      (let [[k l] (next-coordinate direction i j)]
        (if (and (is-in-bounds grid k l) (= (nth (nth grid k) l) \#))
          (let [[m n] (next-coordinate (rotate direction) i j)]
            (if (and (is-in-bounds grid m n) (= (nth (nth grid m) n) \#))
              (let [[o p] (next-coordinate (rotate (rotate direction)) i j)]
                (recur grid (rotate (rotate direction)) o p (into visited [[i j direction]])))
              (recur grid (rotate direction) m n (into visited [[i j direction]]))))
          (recur grid direction k l (into visited [[i j direction]]))))
      [visited (contains? visited [i j direction])])))

(defn search-result [grid] (search grid 0 (first start) (second start) #{}))

; Part 1
(println (count (set (map butlast (first (search-result grid))))))

; Part 2
(def result (atom 0))

(doseq [i (range (count grid))
        j (range (count (nth grid i)))]
  (when (= (nth (nth grid i) j) \.)
    (let [new-grid (to-array-2d grid)]
      (aset new-grid i j \#)
      (let [looped (second (search-result new-grid))]
        (if looped (swap! result inc) nil)))))

(println @result)
