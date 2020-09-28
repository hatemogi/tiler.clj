(ns tiler.md.block-test
  (:require [clojure.test :refer [testing is deftest]]
            [tiler.md.block :as block]))

(deftest block-parse
  (testing "구조분석"
    (is (= (block/parse "테스트\n") [:문서 [:문단 [:문장 "테스트"]]]))))
