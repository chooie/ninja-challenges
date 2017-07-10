(ns challenge-2.core)

(declare read-lines-from-std-in
         read-information-from-lines
         get-maximum-revenue)

(defn -main
  []
  (let [lines (vec (read-lines-from-std-in))]
    (println lines)))

(declare get-integer
         get-tail-of-vector)

(defn read-information-from-lines
  [lines]
  (let [first-line (get lines 0)
        ;; Also known as 'n'
        strings-on-first-line (clojure.string/split first-line #" ")
        number-of-signs (get-integer (get strings-on-first-line 0))
        k-value (get-integer (get strings-on-first-line 1))
        signs-strings (get-tail-of-vector lines)
        signs-revenues (map get-integer signs-strings)]
    {:number-of-signs number-of-signs
     :k-value k-value
     :signs signs-revenues}))

(declare split-revenues-by-k-value)

(defn get-maximum-revenue
  [street-info]
  (let [sign-revenues-with-indexes (map-indexed (fn [index revenue]
                                                  {:revenue revenue
                                                   :index index})
                                                (:signs street-info))
        sorted-sign-revenues-with-indexes (sort-by
                                           (juxt :revenue :index)
                                           sign-revenues-with-indexes)
        descending-revenues-with-indexes (reverse
                                          sorted-sign-revenues-with-indexes)]
    descending-revenues-with-indexes))

(declare get-adjacent-signs-for-first-element
         get-difference-between-vectors)
(defn split-revenues-by-k-value
  [number-of-signs signs signs-info k-value]
  (loop [signs signs
         signs-info signs-info
         revenues-split-by-k-value []]
    (let [next-adjacents (get-adjacent-signs-for-first-element number-of-signs
                                                               signs
                                                               signs-info
                                                               k-value)
          signs-remaining (get-difference-between-vectors next-adjacents
                                                          signs-info)])))

(defn get-difference-between-vectors
  [vector1 vector2]
  ())

(declare get-adjacent-signs-within-k-value)

(defn get-adjacent-signs-for-first-element
  [number-of-signs signs signs-info k-value]
  (let [first-sign (get signs-info 0)
        revenue (:revenue first-sign)
        index (:index first-sign)
        other-signs (get-tail-of-vector signs-info)
        adjacent-signs-within-k-value (get-adjacent-signs-within-k-value
                                       index
                                       number-of-signs
                                       signs
                                       other-signs
                                       (dec k-value))]
    (concat [first-sign] adjacent-signs-within-k-value)))


(defn get-adjacent-signs-within-k-value
  [pivot-index number-of-signs signs sorted-sign-revenues-with-indexes
   k-remaining]
  (let [min-starting-index (- pivot-index k-remaining)
        max-ending-index (+ pivot-index k-remaining)
        starting-index (if (<= min-starting-index 0)
                         0
                         min-starting-index)
        ending-index (if (> max-ending-index (dec number-of-signs))
                       (dec number-of-signs)
                       max-ending-index)
        adjacent-indexes (range starting-index ending-index)
        adjacent-indexes-within-k (take k-remaining adjacent-indexes)
        adjacent-indexes-within-k-less-pivot-index (filter
                                                    (fn [index]
                                                      (not= index pivot-index))
                                                    adjacent-indexes-within-k)
        adjacent-signs (map (fn [index]
                              {:revenue (get signs index)
                               :index index})
                            adjacent-indexes-within-k-less-pivot-index)]
    (vec adjacent-signs)))

(defn- read-lines-from-std-in
  []
  (line-seq (java.io.BufferedReader. *in*)))

(defn- get-integer
  [number-string]
  (Integer/parseInt number-string))

(defn- get-tail-of-vector
  [lines]
  (subvec lines 1))
