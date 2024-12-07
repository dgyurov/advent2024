(require '[clojure.string :as str])

(def input (->>
            (slurp "input.txt")
            (str/split-lines)
            (map (fn [line] (str/split line #"\ ")))
            (map (fn [line] (flatten [(str/replace (first line) ":" "") (rest line)])))
            (map (fn [line] (map #(. Long parseLong %) line)))))

(defn concatenate [left right] (->> (str left right) (. Long parseLong)))
(defn can-calibrate [part target current numbers]
  (if (= (count numbers) 0)
    (= current target)
    (or
     (can-calibrate part target (+ current (first numbers)) (rest numbers))
     (can-calibrate part target (* current (first numbers)) (rest numbers))
     (if (= part 2)
       (can-calibrate part target (concatenate current (first numbers)) (rest numbers))
       nil))))

(defn calibrate [line part]
  (let [[target numbers] [(first line) (rest line)]]
    (if (can-calibrate part target (first numbers) (rest numbers)) target 0)))

; Part 1
(->> input (map #(calibrate % 1)) (reduce +) println)

; Part 2
(->> input (map #(calibrate % 2)) (reduce +) println)
