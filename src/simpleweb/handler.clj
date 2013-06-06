(ns simpleweb.handler
  (:use compojure.core)
  (:require [org.httpkit.server :as server]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

;; running the server
(defn start-app [port]
  (server/run-server app {:port port :join? false}))

(defn -main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (start-app port)))
