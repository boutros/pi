(ns examples.tiling.core
  (:require [pi.core :as pi]))

(def stage (pi/stage))

(def w js/window.innerWidth)
(def h js/window.innerHeight)

(def renderer (pi/renderer-auto [w h]))

(.appendChild js/document.body (:view renderer))

(def texture (pi/texture-from-image "p2.jpeg"))
(def tile (pi/tiling-sprite texture [w h]))
(pi/add! stage tile)

(defn animate
  [c]
  (js/requestAnimFrame #(animate (+ c 0.005)))
  (set! (.. tile -tileScale -x) (+ 2 (js/Math.sin c)))
  (set! (.. tile -tileScale -y) (+ 2 (js/Math.cos c)))
  (set! (.. tile -tilePosition -x) (inc (.. tile -tilePosition -x)))
  (set! (.. tile -tilePosition -y) (inc (.. tile -tilePosition -y)))
  (pi/render! renderer stage))

(animate 0)