(ns cljstr.server
  (:use [compojure.core :only (defroutes GET)]
        ring.util.response
        ring.middleware.cors
        org.httpkit.server
        clj-kafka.core
        clj-kafka.consumer.zk)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.reload :as reload]
            [cheshire.core :refer :all]))

(def clients (atom {}))

(defn ws
  [req]
  (with-channel req con
                (swap! clients assoc con true)
                (println con " connected")
                (on-close con (fn [status]
                                (swap! clients dissoc con)
                                (println con " disconnected. status: " status)))))

(def config {"zookeeper.connect" "localhost:2189"
             "group.id" "clj-kafka.consumer"
             "auto.offset.reset" "smallest"
             "auto.commit.enable" "false"})


(future
  (with-resource [c (consumer config)]
                shutdown
                (doseq [m (messages c ["test"])]
                  (doseq [client @clients]
                    (send! (key client)
                           (String. (:value m))
                           false)))))

(defroutes routes
           (GET "/kafka" [] ws))

(def application (-> (handler/site routes)
                     reload/wrap-reload
                     (wrap-cors
                       :access-control-allow-origin #".+")))

(defn -main [& args]
  (let [port (Integer/parseInt
               (or (System/getenv "PORT") "8080"))]
    (run-server application {:port port :join? false})))