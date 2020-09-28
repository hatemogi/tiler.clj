(ns tiler.test
  (:require [eftest.runner :refer [run-tests find-tests]]))

(defn -main [& args]
  (run-tests (find-tests "test")))
