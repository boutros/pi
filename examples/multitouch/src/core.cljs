(ns examples.multitouch.core
  (:require [pi.core :as pi]))

(enable-console-print!)

(def stage (pi/stage 0x97c56e true))

(def w (.-innerWidth js/window))
(def h (.-innerHeight js/window))

(def renderer (pi/renderer-auto [w h]))
(.appendChild js/document.body (:view renderer))

(def bunny-texture (pi/texture-from-image "bunny.png"))
(def max-bunnies 10)

(doseq [i (range max-bunnies)
        :let [x (* (js/Math.random) w)
              y (* (js/Math.random) h)
              bunny (pi/sprite-from-texture bunny-texture)]]
  (pi/set-interactive! bunny true)
  (set! (.-buttonMode bunny) true)
  (pi/set-anchor! bunny [0.5 0.5])
  (pi/set-scale! bunny [3 3])
  (pi/set-handler! bunny [:mousedown :touchstart]
    (fn [e]
      (this-as this
        (do
          (.preventDefault (.-originalEvent e))
          (set! (.-data this) e)
          (set! (.-alpha this) 0.8)
          (set! (.-dragging this) true)))))
  (pi/set-handler! bunny [:mouseup :mouseupoutside :touchend :touchendoutside]
    (fn [e]
      (this-as this
        (do
          (set! (.-alpha this) 1)
          (set! (.-dragging this) false)
          (set! (.-data this) nil)))))
  (pi/set-handler! bunny [:mousemove :touchmove]
    (fn [e]
      (this-as this
        (when (:dragging this)
          (let [pos (.getLocalPosition (:data this) (:parent this))]
            (pi/set-position! this [(:x pos) (:y pos)]))))))
  (pi/set-position! bunny [x y])
  (pi/add! stage bunny))

(defn animate
  []
  (js/requestAnimFrame animate)
  (pi/render! renderer stage))

(animate)