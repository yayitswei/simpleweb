(defproject simpleweb "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [http-kit "2.2.0"]
                 [hiccup "1.0.5"]
                 [ring/ring-core "1.5.0"]
                 [compojure "1.5.1"]
                 [org.clojure/tools.nrepl "0.2.12"]]
  :main simpleweb.server
  :min-lein-version "2.0.0")
