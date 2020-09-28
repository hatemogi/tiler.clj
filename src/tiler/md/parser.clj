(ns tiler.md.parser
  (:require [instaparse.core :as insta]
            [tiler.md.meta :as meta]
            [tiler.md.block :as block]
            [tiler.md.span :as span]))

(defn- make-str-end-with-LF [text]
  (if (= \newline (last text))
    text
    (str text "\n")))

(defn parse
  "텍스트에서 헤더를 추출하고, 본문은 마크다운 분석"
  [text]
  (let [문장분석 span/parse
        헤더본문 (-> text make-str-end-with-LF meta/parse)
        본문     (:본문 헤더본문)]
    (->> 본문
     block/parse
     (insta/transform {:문장 문장분석})
     (insta/transform {:링크텍스트 문장분석}))))

(parse "---\nasdfasfd\n---\n#  안녕하세요")

(defn ->hiccup [tree]
  (insta/transform {:큰제목 :h1} tree))

(defn- extract [elements keyword]
  (->> elements
       (filter vector?)
       (filter #(= keyword (first %)))))

(def ^:private extract-first
  (comp first extract))

(defn- extract-str [elements keyword]
  (if-let [e (extract-first elements keyword)]
    (apply str (rest e))
    nil))
