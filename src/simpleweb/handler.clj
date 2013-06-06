(ns simpleweb.handler
  (:use compojure.core
        [clojure.tools.logging :only [info debug warn error]]
        ;; for view
        [hiccup.core :only [html]]
        [hiccup.page :only [html5 include-css include-js]])
  (:require [org.httpkit.server :as server]
            [ring.middleware.reload :as reload]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(def prod? (atom (System/getenv "LEIN_NO_DEV")))

(defn index []
  (html
    [:head
     [:title "Simple Clojure Webapp Example"]
     (include-css "//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/css/bootstrap-combined.min.css"
                  "/css/styles.css")]
    [:body
     [:div.container
      [:div.content
       "hello!"]]
     (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js")
     (include-js "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js")]))

(defroutes app-routes
  (GET "/" [] (index))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (if @prod?
    (handler/site app-routes)
    (reload/wrap-reload (handler/site app-routes))))

;; running the server
(defn start-app [port]
  (info "Starting server on port" port)
  (server/run-server app {:port port :join? false}))

(defn -main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (start-app port)))
