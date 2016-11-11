(ns simpleweb.server
  (:require [hiccup.core :as hiccup]
            [compojure.core :refer [defroutes GET]]
            [org.httpkit.server :as server]
            [clojure.tools.nrepl.server :as nrepl]
            [compojure.route :as route])
  (:gen-class))

;; templates
(defn index []
  (hiccup/html
   [:head [:title "Hello World!"]]
   [:body "Hello world"]))

;; handler

; routes
(defroutes app-routes
  (GET "/" [] (index))
  (route/not-found "Not Found"))

(defn go! []
  (server/run-server #'app-routes {:port 8080 :join? false}))

(defn -main [& args]
  (go!)
  (nrepl/start-server :port 7888))
