(defproject hkafka "0.1.0-SNAPSHOT"
  :description "Create a Kafka producer and high level consumer"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.kafka/kafka_2.9.2 "0.8.2.2"
                  :exclusions [javax.jms/jms
                               com.sun.jdmk/jmxtools
                               com.sun.jmx/jmxri]]]
  :aot [hello-world-kafka.core]
  :main hello-world-kafka.core)
