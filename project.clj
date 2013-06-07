(defproject simpleweb "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.1.3"]
                 [org.clojure/tools.logging "0.2.6"]
                 [hiccup "1.0.3"]
                 [ring/ring-devel "1.2.0-beta3"]
                 [ring/ring-core "1.2.0-beta3"]
                 [compojure "1.1.5"]
                 [org.clojure/tools.nrepl "0.2.3"]]
  :plugins [[lein-ring "0.8.5"]
            [lein-cljsbuild "0.3.2"]]
  :main simpleweb.server
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}}

  ;; for cljs
  :hooks [leiningen.cljsbuild]
  :source-paths ["src/clj"]
  :cljsbuild {
    :builds {
      :main {
        :source-paths ["src/cljs"]
        :compiler {:output-to "resources/public/js/cljs.js"
                   :optimizations :simple
                   :pretty-print true}
        :jar true}}})
