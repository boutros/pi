(ns examples.transparency.core
  (:require [pi.core :as pi]))

(def stage (pi/stage 0x66FF99))

(def renderer (pi/renderer-auto [400 300] nil true))
(.appendChild js/document.body (:view renderer))
(set! (.. renderer -view -style -position) "absolute")
(set! (.. renderer -view -style -top) "0px")
(set! (.. renderer -view -style -left) "0px")

(def bunny (pi/sprite-from-image "bunny.png"))
(pi/set-anchor! bunny [0.5 0.5])
(pi/set-position! bunny [200 150])

(pi/add! stage bunny)

(defn animate
  []
  (js/requestAnimFrame animate)
  (pi/rotate! bunny 0.1)
  (pi/render! renderer stage))

(animate)