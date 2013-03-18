(ns pics4.core
  (:require [clojure.math.combinatorics :as combo]))

(defn unordered_hash [word] 
  (let [primes [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101]]
    (apply * (map (fn [i] (nth primes i)) (map (fn [c] (- c 97)) (map int word))))
  )
)

(defn get_dictionary [n]
  (let [words (filter (fn [word] (re-find #"^[a-z]*$" word)) (clojure.string/split-lines (slurp "/home/christian/cracklib-small")))]
    (group-by unordered_hash (filter (fn [word] (= (count word) n) ) words))
  )
)

(defn -main [& args]
  (let [dict (get_dictionary (read-string (second args)))]
    (doseq [word (combo/combinations (first args) (read-string (second args)))]
      (if (get dict (unordered_hash word))
        (println (get dict (unordered_hash word)))
      )
    )
  )
)
