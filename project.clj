(defproject cljstr "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [ring/ring-json "0.3.0"]
                 [http-kit "2.1.18"]
                 [ring/ring-devel "1.2.2"]
                 [compojure "1.1.6"]
                 [ring-cors "0.1.0"]
                 [clj-kafka "0.1.2-0.8"]]
  :main cljstr.server)