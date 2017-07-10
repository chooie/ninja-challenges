(ns challenge-3.core-test
  (:require
   [clojure.test :refer :all]
   [challenge-3.core :as core]))

(def test-user-input
  [
   "10"
   "203 204 205 206 207 208 203 204 205 206"
   "13"
   "203 204 204 205 206 207 205 208 203 206 205 206 204"
   ])

(def test-list-info
  {:a-list [203 204 205 206 207 208 203 204 205 206]
   :b-list [203 204 204 205 206 207 205 208 203 206 205 206 204]})

(deftest core-test

  (testing "Gets the user input"
    (is (= test-list-info
           (core/get-list-information test-user-input))))
  (testing "Gets the numbers that are missing in the a-list from b-list"
    (is (= [204 205 206]
           (core/get-missing-numbers (:a-list test-list-info)
                                     (:b-list test-list-info))))))
