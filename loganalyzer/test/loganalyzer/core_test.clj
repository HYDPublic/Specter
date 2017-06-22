(ns loganalyzer.core-test
  (:require [clojure.test :refer :all]
            [loganalyzer.core :refer :all]
            [clojure.data.json :as json]))

(def
  simulation-dir
  "resources/")

(def logs
  {:wfc      (str simulation-dir "wfc.log")
   :catalina (str simulation-dir "catalina.out")
   :access   (str simulation-dir "access.log")})

(deftest logs-exist-test
  (testing "wfc.log file exist"
    (is (.exists (clojure.java.io/file (logs :wfc))))
    (is (.exists (clojure.java.io/file (logs :catalina))))
    (is (.exists (clojure.java.io/file (logs :access))))))

;; (deftest log-wfc-is-parsable
;;   (testing "wfc.log is parsable"
;;     (let [content (json/read-str (slurp (logs :wfc)) :key-fn keyword)]
;;       (is (= "" content))))

(deftest log-access-is-parsable
  (testing "access.log from nginx is parsable"
    (let [content  (-> :access (logs) (slurp) (clojure.string/split #"\n"))
          presplit (map (fn[x] (clojure.string/split x #"\"")) content)]
      (is (=
           "10.129.62.6 - - 21/Jun/2017:19:02:35 +0000 \"POST /wfc/XmlService?tenantId=healthcare HTTP/1.1\" 499 0"
           (first content))))))
