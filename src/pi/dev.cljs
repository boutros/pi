(ns pi.dev
  (:require [pi.core :as pi]))

(def renderer (pi/renderer-auto 640 400))

(.appendChild js/document.body (.-view renderer))

(def g (pi/graphics))

(-> g
  (pi/line-style 10 0x10aa33 1)
  (pi/begin-fill 0xaa0000)
  (pi/move-to 10 10)
  (pi/line-to 50 100)
  (pi/line-to 50 200)
  (pi/draw-rect 0 0 50 50)
  (pi/end-fill))

(def stage (pi/stage 0xfaffff))

(.addChild stage g)

(defn animate
  []
  (do
    (.render renderer stage)
    (js/requestAnimFrame animate)))

(animate)


