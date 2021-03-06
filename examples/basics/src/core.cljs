(ns examples.basics.core
  (:require [pi.core :as pi]))

(def renderer (pi/renderer-auto [400 300]))

(def stage (pi/stage 0x66FF99))

(.appendChild js/document.body (:view renderer))

(def bunny (pi/sprite-from-image "bunny.png"))
(pi/set-anchor! bunny [0.5 0.5])
(pi/set-position! bunny [200 150])
(pi/add! stage bunny)

(defn animate
  []
  (do
    (js/requestAnimFrame animate)
    (pi/rotate! bunny 0.1)
    (pi/render! renderer stage)))

(animate)

