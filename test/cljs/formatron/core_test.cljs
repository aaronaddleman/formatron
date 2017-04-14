(ns formatron.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [formatron.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
