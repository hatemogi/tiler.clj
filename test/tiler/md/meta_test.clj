(ns tiler.md.meta-test
  (:require [clojure.test :refer [testing is deftest]]
            [tiler.md.meta :as meta]))

(deftest meta-parse
  (testing "마크다운 문서 헤더 분리"
    (is (= {:tags [] :title "문서 제목"}
           (-> (slurp "res/fixture/sample.md")
               meta/parse
               :헤더)))
    (is (= {} (-> "---abc---\n" meta/parse :헤더)))))
