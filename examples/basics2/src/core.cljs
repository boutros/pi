(ns examples.basics2.core
  (:require [pi.core :as pi]))

(def stage (pi/stage))

(def renderer (pi/renderer-auto 1024 768 nil true true))

(.appendChild js/document.body (.-view renderer))

(def img-url "http://upload.wikimedia.org/wikipedia/commons/thumb/0/0d/Ski_trail_rating_symbol-blue_square.svg/600px-Ski_trail_rating_symbol-blue_square.svg.png")

(def sprite (pi/sprite-from-image img-url))

(pi/set-position! sprite 0 0)

(pi/set-pivot! sprite (/ (.-width sprite) 2) (/ (.-height sprite) 2))

(.addChild stage sprite)

(defn animate
  []
  (js/requestAnimFrame animate)
  (pi/rotate! sprite 0.1)
  (.render renderer stage))

(animate)