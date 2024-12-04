(require '[clojure.string :as str])

(def grid (map seq (str/split-lines (slurp "input.txt"))))
(defn is-in-bounds [grid i j] (and (>= i 0) (>= j 0) (< i (count grid)) (< j (count (first grid)))))
(defn character-at [grid i j] (if (is-in-bounds grid i j) (nth (nth grid i) j) nil))

(def result-one (atom 0))
(def result-two (atom 0))

(defn search-one [grid i j]
  (if (= (character-at grid i j) \X)
    (count (filter identity
                   [(and (= (character-at grid (+ i 1) j) \M)
                         (= (character-at grid (+ i 2) j) \A)
                         (= (character-at grid (+ i 3) j) \S))
                    (and (= (character-at grid (- i 1) j) \M)
                         (= (character-at grid (- i 2) j) \A)
                         (= (character-at grid (- i 3) j) \S))
                    (and (= (character-at grid i (+ j 1)) \M)
                         (= (character-at grid i (+ j 2)) \A)
                         (= (character-at grid i (+ j 3)) \S))
                    (and (= (character-at grid i (- j 1)) \M)
                         (= (character-at grid i (- j 2)) \A)
                         (= (character-at grid i (- j 3)) \S))
                    (and (= (character-at grid (+ i 1) (+ j 1)) \M)
                         (= (character-at grid (+ i 2) (+ j 2)) \A)
                         (= (character-at grid (+ i 3) (+ j 3)) \S))
                    (and (= (character-at grid (- i 1) (- j 1)) \M)
                         (= (character-at grid (- i 2) (- j 2)) \A)
                         (= (character-at grid (- i 3) (- j 3)) \S))
                    (and (= (character-at grid (+ i 1) (- j 1)) \M)
                         (= (character-at grid (+ i 2) (- j 2)) \A)
                         (= (character-at grid (+ i 3) (- j 3)) \S))
                    (and (= (character-at grid (- i 1) (+ j 1)) \M)
                         (= (character-at grid (- i 2) (+ j 2)) \A)
                         (= (character-at grid (- i 3) (+ j 3)) \S))])) 0))

(defn search-two [grid i j]
  (if (or
       (and (= (character-at grid (dec i) (dec j)) \M)
            (= (character-at grid (inc i) (inc j)) \S)
            (= (character-at grid (dec i) (inc j)) \S)
            (= (character-at grid (inc i) (dec j)) \M))
       (and (= (character-at grid (dec i) (dec j)) \S)
            (= (character-at grid (inc i) (inc j)) \M)
            (= (character-at grid (dec i) (inc j)) \S)
            (= (character-at grid (inc i) (dec j)) \M))
       (and (= (character-at grid (dec i) (dec j)) \S)
            (= (character-at grid (inc i) (inc j)) \M)
            (= (character-at grid (dec i) (inc j)) \M)
            (= (character-at grid (inc i) (dec j)) \S))
       (and (= (character-at grid (dec i) (dec j)) \M)
            (= (character-at grid (inc i) (inc j)) \S)
            (= (character-at grid (dec i) (inc j)) \M)
            (= (character-at grid (inc i) (dec j)) \S))) 1 0))

(doseq [i (range (count grid))]
  (doseq [j (range (count (first grid)))]
    ; Part 1
    (if (= (character-at grid i j) \X)
      (swap! result-one + (search-one grid i j)) nil)
    ; Part 2
    (if (= (character-at grid i j) \A)
      (swap! result-two + (search-two grid i j)) nil)))

(println @result-one)
(println @result-two)