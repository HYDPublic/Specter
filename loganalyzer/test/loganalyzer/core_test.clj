(ns loganalyzer.core-test
  (:require [clojure.test :refer :all]
            [loganalyzer.core :refer :all]
            [clojure.data.json :as json]))

(def
  simulation-dir
  "/home/stn/cache/cxseed/scheduling.schedule.MultiReadDesktopSimulation.COLD/cxseed-a-back/")

(def logs
  {:wfc      (str simulation-dir "wfc.log")
   :catalina (str simulation-dir "catalina.out")
   :access   (str simulation-dir "access.log")})

(deftest logs-exist-test
  (testing "wfc.log file exist"
    (is (.exists (clojure.java.io/file (logs :wfc))))
    (is (.exists (clojure.java.io/file (logs :catalina))))
    (is (.exists (clojure.java.io/file (logs :access))))))




;; (def wfc-json
;;   (json/read-str (slurp (logs :wfc)) :key-fn keyword))


;; (deftest wfc-log-json-test
;;   (testing "wfc.log file can be read in json format"
;;     (is (= "" wfc-json))))
