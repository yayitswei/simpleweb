(defproject simpleweb "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1803"]
                 [http-kit "2.1.3"]
                 [org.clojure/tools.logging "0.2.6"]
                 [hiccup "1.0.3"]
                 [ring/ring-core "1.2.0-beta3"]
                 [compojure "1.1.5"]
                 [shoreleave/shoreleave-remote "0.3.0"]
                 [com.cemerick/shoreleave-remote-ring "0.0.2"]
                 [jayq "2.0.0"]
                 [com.novemberain/monger "1.6.0-beta2"]
                 [org.clojure/tools.nrepl "0.2.3"]]
  :plugins [[lein-cljsbuild "0.3.2"]]
  :main simpleweb.server
  :min-lein-version "2.0.0"

  ;; for cljs
;  :hooks [leiningen.cljsbuild]
  :source-paths ["src/clj"]
  :cljsbuild {
    :builds {
      :main {
        :source-paths ["src/cljs"]
        :compiler {:output-to "resources/public/js/cljs.js"
;                   :source-map "resources/public/js/cljs.js.map"
                   :optimizations :simple
                   :pretty-print true}
        :jar true}}})
