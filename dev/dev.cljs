(ns pi.dev
  (:require [pi.core :as pi]))

(def renderer (pi/renderer-auto [640 400]))

(.appendChild js/document.body (:view renderer))

(def g (pi/graphics))

(def circle (pi/circle [200 200] 100))

(-> g
  (pi/line-style 10 0x10aa33 1)
  (pi/begin-fill 0xaa0000)
  (pi/move-to [10 10])
  (pi/line-to [50 100])
  (pi/line-to [50 200])
  (pi/draw-rect [0 0 50 50])
  (pi/draw circle)
  (pi/end-fill))

(def stage (pi/stage 0xfaffff))

(pi/add! stage g)

(defn animate
  []
  (do
    (pi/render! renderer stage)
    (js/requestAnimFrame animate)))

(animate)


