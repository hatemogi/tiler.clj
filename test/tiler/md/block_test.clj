(ns tiler.md.block-test
  (:require [clojure.test :refer [testing is deftest]]
            [tiler.md.block :as block]))

(deftest block-parse
  (testing "구조분석"
    (is (= [:문서 [:문단 [:문장 "테스트"]]] (block/parse "테스트\n")))
    (is (= [:문서 [:소스코드 [:소스헤더 "python http://asdfasdf.com"] [:소스내용 "def ab" "\n"]]]
           (block/parse "```python http://asdfasdf.com\ndef ab\n```\n")))))


