(ns client
  (:require [shoreleave.remotes.http-rpc :as rpc])
  (:use [jayq.core :only [$ text]]
        [jayq.util :only [log wait]]))

(set! shoreleave.remotes.http-rpc/*remote-uri* "/_fetch")

(defn handle-click []
  (rpc/remote-callback
        :inc-counter [] #(text ($ :#counter) (str "The counter value is:" %))))

(defn test-fn [a b c]
  (log "you entered: " a b c)
  (a b c))

(.click ($ :.btn) handle-click)
