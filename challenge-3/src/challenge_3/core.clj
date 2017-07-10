(ns challenge-3.core)

(defn- read-lines-from-std-in
  []
  (line-seq (java.io.BufferedReader. *in*)))

(defn- get-integer
  [number-string]
  (Integer/parseInt number-string))

(defn get-list-information
  [lines]
  (let [a-list-length (get-integer (get lines 0))
        a-line (get lines 1)
        a-integers (map get-integer (clojure.string/split a-line #" "))
        b-list-length (get-integer(get lines 2))
        b-line (get lines 3)
        b-integers (map get-integer (clojure.string/split b-line #" "))]
    {:a-list a-integers
     :b-list b-integers}))

(defn get-missing-numbers
  [a-list b-list]
  (let [sorted-a-list (vec (sort a-list))
        sorted-b-list (vec (sort b-list))]
    (loop [a-index 0
           b-index 0
           missing-numbers []]
      (let [a-number (get sorted-a-list a-index)
            b-number (get sorted-b-list b-index)]
        (if-not a-number
          missing-numbers
          (cond
            (some #{a-number} missing-numbers) (recur (inc a-index)
                                                     b-index
                                                     missing-numbers)
            (= a-number b-number) (recur (inc a-index)
                                         (inc b-index)
                                         missing-numbers)
            :else
            (do
              (let [b-index-with-different-number (atom b-index)]
                (while (= b-number (get sorted-b-list
                                        @b-index-with-different-number))
                  (swap! b-index-with-different-number inc))
                (recur a-index
                       @b-index-with-different-number
                       (conj missing-numbers b-number))))))))))

(defn -main
  []
  (let [lines (vec (read-lines-from-std-in))
        {:keys [a-list b-list]} (get-list-information lines)
        missing-numbers (get-missing-numbers a-list b-list)
        missing-numbers-with-spaces (interpose " " missing-numbers)]
    (doseq [missing-number missing-numbers-with-spaces]
        (print missing-number))))

(-main)
