(ns tiler.md.converter
  "인스타파서로 분석한 것을 hiccup 표현으로 변환"
  (:require [clojure.string :refer [split]]))

(defn- extract [elements keyword]
  (->> elements
       (filter vector?)
       (filter #(= keyword (first %)))))

(defn- element-str [e]
  (cond
    (empty? e) ""
    (keyword? (first e)) (element-str (rest e))
    (map? (first e)) (element-str (rest e))
    :else (apply str e)))

(def ^:private extract-str (comp element-str first extract))

(defn- extract-source-attrs [elements]
  (let [소스헤더 (split (extract-str elements :소스헤더) #"\s")
        [소스언어 소스위치] 소스헤더]
    {:lang 소스언어 :loc (or 소스위치 "")}))
    
(defn ->hiccup [elements]
  (let [tag (fn [t] (into [t] (map ->hiccup (rest elements))))]
    (cond
      (vector? elements) 
      (case (first elements)
        :문서     (tag :html)
        :제목     (tag :h1)
        :작은제목 (tag :h2)
        :일반목록 (tag :ul)
        :숫자목록 (tag :ol)
        :항목     (tag :li)
        :소스코드 [:pre (extract-source-attrs elements) 
                        [:code {} (extract-str elements :소스내용)]]
        elements)
      :else elements)))

(->hiccup [:문서 
           [:제목 {} "123"] 
           [:일반목록 [:항목 "아이템1"]]
           [:소스코드 
            [:소스헤더 "python https://gist/abc"]
            [:소스내용 "def a():\n  return 3"]]])
