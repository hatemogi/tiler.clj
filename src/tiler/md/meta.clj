(ns tiler.md.meta
  "마크다운 텍스트의 헤더와 본문을 분리해서 헤더는 맵으로 준비"
  (:require [instaparse.core :as insta]
            [clojure.string :refer [trim]]))

(def ^{:doc "헤더와 본문을 분리"}
  parser
  (insta/parser 
   "문서   :=  (헤더 본문) / 본문
    헤더   :=  <'---' LF> (ANYS LF)+ <'---' LF>
    본문   := (ANYS LF)*
    <ANYS> := #'.*'
    <LF>     := '\\n' 
   "))
  
(defn ->map [[head & tail]]
  (let [parse-header 
        (fn [xs]
          (try (-> (apply str xs) trim read-string) 
            (catch Throwable _ {})))]
    (assert (= :문서 head))
    (reduce (fn [r [x & xs]]
              (case x
                :헤더 (assoc r :헤더 (parse-header xs))
                :본문 (assoc r :본문 (apply str xs))
                r))
            {:헤더 {} :본문 ""}
            tail)))

(defn parse 
  "마크다운 텍스트 헤더 맵과 본문 문자열로 분리"
  [text]
  (->map (parser text)))
