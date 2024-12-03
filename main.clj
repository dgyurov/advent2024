(require '[clojure.java.io :as io])
(require '[clojure.string :as str])

(def lines
  (with-open [rdr (io/reader "input.txt")]
    (str/split-lines (slurp rdr))))

(def records [])

(doseq [line lines]
  (def records (into records [(map #(Integer/parseInt %) (str/split line #" "))])))

(defn is-record-valid [record]
  (or (apply <= record) (apply >= record)))

(defn is-record-safe [record] (let [pairs (map (fn [left, right] (let [diff (Math/abs (- left right))] (and (>= diff 1) (<= diff 3)))) record (rest record))] (every? true? pairs)))

(defn is-record-safe-and-valid [record] (and (is-record-valid record) (is-record-safe record)))

(defn is-alternative-safe-and-valid [record] (reduce (fn [acc i] (or acc (let [alternative-record (concat (take i record) (drop (inc i) record))] (and (is-record-valid alternative-record) (is-record-safe alternative-record))))) false (range (count record))))

; Part 1
(println (reduce (fn [acc record] (+ acc (if (is-record-safe-and-valid record) 1 0))) 0 records))

; Part 2
(println (reduce (fn [acc record] (+ acc (if (or (is-record-safe-and-valid record) (is-alternative-safe-and-valid record)) 1 0))) 0 records))