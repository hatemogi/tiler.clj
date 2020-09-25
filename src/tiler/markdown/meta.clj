(ns tiler.markdown.meta
  (:require [instaparse.core :as insta]))

(def ^{:doc "헤더와 본문을 분리"}
  parse
  (insta/parser 
   "문서   :=  (헤더 본문) / 본문
    헤더   :=  <'---' LF> (ANYS LF)+ <'---' LF>
    본문   := (ANYS LF)*
    <ANYS> := #'.*'
    <LF>     := '\\n' 
   "))
  
(defn ->map [[head & tail]]
  (assert (= :문서 head))
  (reduce (fn [r [x & xs]]
            (case x
              :헤더 (assoc r :헤더 (apply str xs))
              :본문 (assoc r :본문 (apply str xs))
              r))
          {}
          tail))
          
;; (->map (parse "---\nasdfasdf\nasdfasdf\n---\nasdf\n"))
;; (->map (parse ""))


