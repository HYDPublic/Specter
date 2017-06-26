(ns loganalyzer.core-test
  (:require [clojure.test :refer :all]
            [loganalyzer.core :refer :all]
            [clojure.data.json :as json]))

;; MAYBE NOT THE RIGHT PLACE: TODO implement a memento here
(defn fname_content [fname]
  (-> fname (slurp) (clojure.string/split #"\n")))


(def logs
  (let [simulation-dir "resources/"]
    {:wfc      (str simulation-dir "wfc.log")
     :catalina (str simulation-dir "catalina.out")
     :access   (str simulation-dir "access.log")}))

(def samples {:line1
              "10.129.62.6 - - 21/Jun/2017:19:02:35 +0000 \"POST /wfc/XmlService?tenantId=healthcare HTTP/1.1\" 499 0"
              :line8
              "10.129.62.6 - - 21/Jun/2017:19:28:56 +0000 \"POST /wfc/restcall/v1/scheduling/schedule/multi_read HTTP/1.1\" 200 933246"})

(deftest logs-exist-test
  (testing "wfc.log file exist"
    (is (.exists (clojure.java.io/file (logs :wfc))))
    (is (.exists (clojure.java.io/file (logs :catalina))))
    (is (.exists (clojure.java.io/file (logs :access))))))




;;
;;remote_addr dummy remote_user $time_local time_local2 "$request" $status $body_bytes_sent $request_time
(deftest log-access-content-test
  (testing "access.log content from Nginx can be loaded in memory"
    (let [content (fname_content (logs :access))]
      (is (= (samples :line1) (nth content 1)))
      (is (= (samples :line8) (nth content 8))))))

;; TODO implement a memento here instead
(defn access_structurify
  [fname]                                        ; do something here
  (map (fn[x]
         (let [p      (clojure.string/split x #"\"")
               p1     (clojure.string/split (first p) #"\s")
               p2     (clojure.string/split (nth p 1) #"\s")
               p3     (clojure.string/split (nth p 2) #"\s")
               remote-addr (nth p1 0)
               tstamp (str (nth p1 3) " " (nth p1 4))
               verb    (nth p2 0)
               request (nth p2 1)
               status          (nth p3 1)
               body-byte-sent  (nth p3 2)
               ]
           {:ts             tstamp
            :remote-addr    remote-addr
            :verb           verb
            :request        request
            :http-status    status
            :body-byte-sent body-byte-sent}))
       (fname_content fname)))



(deftest log-access-is-parsable
  (testing "access.log from nginx is parsable"
    (let [content  (access_structurify (logs :access))
          presplit content]
            (is (=
           {:ts          "21/Jun/2017:19:02:35 +0000"
            :remote-addr "10.129.62.6"
            :request     "/wfc/XmlService?tenantId=healthcare"
            :verb        "POST"
            :http-status    "499"
            :body-byte-sent "0"
            }
           (nth presplit 1))))))
