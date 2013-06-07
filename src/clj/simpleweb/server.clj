(ns simpleweb.server
  (:use compojure.core
        [clojure.tools.logging :only [info debug warn error]]
        ;; for view
        [hiccup.core :only [html]]
        [hiccup.page :only [html5 include-css include-js]])
  (:require [org.httpkit.server :as server]
            [clojure.tools.nrepl.server :as nrepl]
            [cemerick.shoreleave.rpc :refer (defremote) :as rpc]
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
      [:div.content "counter: " [:span#counter @counter]]
      [:button.btn "click me"]]
     (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js")
     (include-js "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js")
     (include-js "/js/cljs.js")
     ]))

;; handler

; remotes
(defremote inc-counter []
  (swap! counter inc))

; routes
(defroutes app-routes
  (GET "/" [] (index))
  (route/resources "/")
  (route/not-found "Not Found"))

(def all-routes (rpc/wrap-rpc app-routes))

(def app
  (if @prod?
    (handler/site app-routes)
    (reload/wrap-reload (handler/site all-routes))))

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
