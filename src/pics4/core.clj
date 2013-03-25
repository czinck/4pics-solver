(ns pics4.core
  (:require [clojure.math.combinatorics :as combo]))

(defn unordered_hash [word] 
  (let [primes [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101]]
    (apply * (map (fn [letter] (get primes (- (int letter) 97))) word))
  )
)

(defn get_dictionary [n]
  (let [words (filter (fn [word] (and (= (count word) n) (re-find #"^[a-z]+$" word)))
      (clojure.string/split-lines (slurp (System/getenv "PICS_DICTIONARY"))))]
    (group-by unordered_hash words)
  )
)

(defn -main [& args]
  (let [dict (get_dictionary (read-string (second args)))]
    (print 
      (filter (fn [word] (boolean word)) 
        (map (fn [word] (get dict (unordered_hash word))) 
          (combo/combinations (first args) (read-string (second args)))
        )
      )
    )
  )
)
