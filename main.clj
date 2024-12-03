(require '[clojure.java.io :as io])

(def input
  (with-open [rdr (io/reader "input.txt")]
    (re-seq #"mul\([0-9]+\,[0-9]+\)|don\'t\(\)|do\(\)" (slurp rdr))))

(def instructions (filter #(re-find #"mul" %) input))
(def enabled-instructions [])

(doseq [instruction input]
  (cond
    (= instruction "do()") (def is-enabled true)
    (= instruction "don't()") (def is-enabled false)
    :else (if is-enabled (def enabled-instructions (conj enabled-instructions instruction)) nil)))

(defn extract-numbers [match] (map read-string (re-seq #"\d+" match)))

(defn multiplicate [instructions]
  (reduce (fn [result match] (+ result (* (first match) (second match)))) 0 (map extract-numbers instructions)))

; Part 1
(println (multiplicate instructions))

; Part 2
(println (multiplicate enabled-instructions))
