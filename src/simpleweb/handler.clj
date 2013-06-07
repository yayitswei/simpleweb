(ns simpleweb.handler
  (:use compojure.core
        [clojure.tools.logging :only [info debug warn error]]
        ;; for view
        [hiccup.core :only [html]]
        [hiccup.page :only [html5 include-css include-js]])
  (:require [org.httpkit.server :as server]
            [clojure.tools.nrepl.server :as nrepl]
            [ring.middleware.reload :as reload]
            [compojure.handler :as handler]
            [compojure.route :as route]))

;; state
(defonce prod? (atom (System/getenv "LEIN_NO_DEV")))
(defonce counter (atom 0))

;; templates
(defn index []
  (html
    [:head
     [:title "Simple Clojure Webapp Example"]
     (include-css "//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/css/bootstrap-combined.min.css"
                  "/css/styles.css")]
    [:body
     [:div.container
      [:div.content "counter: " @counter]]
     (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js")
     (include-js "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js")]))

;; handler
(defroutes app-routes
  (GET "/" [] (index))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (if @prod?
    (handler/site app-routes)
    (reload/wrap-reload (handler/site app-routes))))

;; init
(defn start-nrepl-server [port]
  (info "Starting nrepl server on port" port)
  (defonce server (nrepl/start-server :port port)))

(defn start-app [port]
  (info "Starting server on port" port)
  (server/run-server app {:port port :join? false}))

(defn -main [& args]
  (when-not @prod? (start-nrepl-server 7888))
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (start-app port)))
