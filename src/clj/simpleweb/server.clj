(ns simpleweb.server
  (:gen-class)
  (:require [compojure.core :refer :all]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-css include-js]]
            [clojure.tools.logging :refer [info debug warn error]]
            [org.httpkit.server :as server]
            [clojure.tools.nrepl.server :as nrepl]
            [cemerick.shoreleave.rpc :refer (defremote) :as rpc]
            [monger.core :as mg]
            [monger.collection :as mc]
            [monger.operators :as mo]
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
      [:button.btn "click me"]
      [:div "some edit"]]
     (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js")
     (include-js "//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js")
     (include-js "/js/cljs.js")]))

;; handler

; remotes
(defremote inc-counter []
  (swap! counter inc))

; routes
(defroutes app-routes
  (GET "/" [] (index))
  (GET "/blah" [] "yay")
  (route/resources "/")
  (route/not-found "Not Found"))

(def all-routes (rpc/wrap-rpc app-routes))

(def app
  (if @prod?
    (handler/site all-routes)
    (reload/wrap-reload (handler/site #'all-routes))))

;; init
(defn start-nrepl-server [port]
  (info "Starting nrepl server on port" port)
  (defonce server (nrepl/start-server :port port)))

(defn start-app [port]
  (info "Starting server on port" port)
  (server/run-server app {:port port :join? false}))

(defn connect-mongo! [uri]
  (info "Connecting to mongo via" uri)
  (mg/connect-via-uri! uri))

(defn -main [& args]
;  (connect-mongo! "mongodb://localhost:27017/simple")
  (when-not @prod? (start-nrepl-server 7888))
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (start-app port)))
