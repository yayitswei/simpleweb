(ns client
  (:require [shoreleave.remotes.http-rpc :as rpc])
  (:use [jayq.core :only [$ inner attr parents find bind val data append prepend on
                          document-ready
                          parent children
                          remove
                          toggle-class
                          add-class remove-class has-class
                          show hide
                          text css html]]
        [jayq.util :only [log wait]]))

(set! shoreleave.remotes.http-rpc/*remote-uri* "/_fetch")

(defn handle-click []
  (rpc/remote-callback
        :inc-counter [] #(text ($ :#counter) %)))

(.click ($ :.btn) handle-click)
