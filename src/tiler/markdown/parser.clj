(ns tiler.markdown.parser
  (:require [instaparse.core :as insta]
            [tiler.markdown.meta :as meta]
            [tiler.markdown.block :as block]
            [tiler.markdown.span :as span]))

(defn- make-str-end-with-LF [text]
  (if (= \newline (last text))
    text
    (str text "\n")))

(defn parse [text]
  (let [문장분석 span/parse]
    (->> text
         make-str-end-with-LF
         block/parse
         (insta/transform {:문장 문장분석})
         (insta/transform {:링크텍스트 문장분석}))))

(parse "---\nasdfasfd\n---")

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

