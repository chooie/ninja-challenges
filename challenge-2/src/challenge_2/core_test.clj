(ns challenge-2.core-test
  (:require [clojure.test :refer :all]
            [challenge-2.core :refer :all]))

(def test-user-input
  ["6 2"
   "1"
   "2"
   "3"
   "1"
   "6"
   "10"])

(def test-k-value 2)

(def test-street-info
  {:number-of-signs 6
   :k-value 2
   :signs [1 2 3 1 6 10]})

(def test-revenues
  [{:revenue 10, :index 5}
   {:revenue 6, :index 4}
   {:revenue 3, :index 2}
   {:revenue 2, :index 1}
   {:revenue 1, :index 3}
   {:revenue 1, :index 0}])

(deftest challenge-2-test
  (testing "Gets all the information from the user input"
    (is (= test-street-info
           (read-information-from-lines test-user-input))))
  (testing "Gets the revenues split by k-value"
    (is (= #_[
            [
             {:revenue 10, :index 5}
             {:revenue 6, :index 4}]
            [
             {:revenue 3, :index 2}
             {:revenue 2, :index 1}]]
           [
            {:revenue 10, :index 5}
            {:revenue 6, :index 4}]
           (split-revenues-by-k-value 6
                                      (:signs test-street-info)
                                      test-revenues
                                      2))))
  #_(testing "Gets the maximum revenue"
    (is (= 21
           (get-maximum-revenue test-street-info)))))
