(require '[clojure.java.io :as io])
(require '[clojure.string :as str])

; Read the input | O(n)
(def lines
  (with-open [rdr (io/reader "input.txt")]
    (str/split-lines (slurp rdr))))

(def lefts [])
(def rights [])

(doseq [line lines]
  (def numbers (map #(Integer/parseInt %) (str/split line #"   ")))
  (def lefts (conj lefts (first numbers)))
  (def rights (conj rights (second numbers))))

; Part 1 | O(nlogn)
(def sorted-lefts (sort lefts))
(def sorted-rights (sort rights))
(println (reduce (fn [acc i] (+ acc (Math/abs (- (nth sorted-lefts i) (nth sorted-rights i))))) 0 (range (count lefts))))

; Part 2 | O(n)
(def right-counts (frequencies rights))
(println (reduce (fn [acc currentNumber] (+ acc (* currentNumber (get right-counts currentNumber 0)))) 0 lefts))
