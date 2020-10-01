(ns tiler.md.converter-test
  (:require [clojure.test :refer [testing is deftest]]
            [tiler.md.converter :refer [->hiccup]]))

(deftest converter->hiccup
  (testing "분석내용을 hiccup 데이터로 변환"
    (is (= [:html [:h1 {} "123"] [:ul [:li "아이템1"]]]
           (->hiccup [:문서
                      [:제목 {} "123"]
                      [:일반목록 [:항목 "아이템1"]]])))
    (is (= [:pre {:lang "python" :loc "https://gist/abc"}
            [:code {} "def a():\n  return 3"]]
           (->hiccup [:소스코드
                      [:소스헤더 "python https://gist/abc"]
                      [:소스내용 "def a():\n  return 3"]])))))
