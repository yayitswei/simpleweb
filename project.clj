(defproject simpleweb "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.1.3"]
                 [org.clojure/tools.logging "0.2.6"]
                 [hiccup "1.0.3"]
                 [compojure "1.1.5"]]
  :plugins [[lein-ring "0.8.5"]]
  :main simpleweb.handler
  :ring {:handler simpleweb.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
