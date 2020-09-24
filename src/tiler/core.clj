(ns tiler.core
  (:gen-class)
  (:require [hiccup.core :refer [html]]
            [hiccup.page :refer [html5]]
            [instaparse.core :as insta]
            [clojure.data.json :as json]
            [clojure.java.io :refer [file]]))

(set! *warn-on-reflection* true)

(defn html-page [elements]
  (html5
    [:head [:title {} "TIL"]]
    [:body elements]))

(defn layout [elements]
  (list [:nav "navigation"]
        [:main elements]
        [:footer "test"]))
  
(defn html-layout [& elements]
  (html-page (layout elements)))

(defn til-files []
  (->> (file "res/til")
       file-seq
       (filter (memfn ^java.io.File isFile))
       (sort-by (memfn ^java.io.File getName))
       reverse))
  
(defn render [file]
  (slurp file))

(defn -main [& args]
  (let [files (til-files)]
   (println (json/write-str (map #(.getName ^java.io.File %) files)))
   (println (map render files)))
   (println (html-layout [:div {:class "abc"} "안녕하세요"])))
