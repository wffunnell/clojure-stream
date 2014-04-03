(ns clojure-stream.client
 (:require [org.httpkit.client :as http]))

(http/get "http://host.com/path")

(def options {:timeout 200             ; ms
              :basic-auth ["user" "pass"]
              :query-params {:param "value" :param2 ["value1" "value2"]}
              :user-agent "User-Agent-string"
              :headers {"X-Header" "Value"}})
(http/get "http://host.com/path" options
          (fn [{:keys [status headers body error]}] ;; asynchronous handle response
            (if error
              (println "Failed, exception is " error)
              (println "Async HTTP GET: " status))))


