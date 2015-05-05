(ns examples.spine2.core
  (:require [pi.core :as pi]))

(def w (.-innerWidth js/window))
(def h (.-innerHeight js/window))

(def stage (pi/stage 0xFFFFFF true))

(def renderer
  (pi/renderer-auto [w h]))

(set! (.. renderer -view -style -display) "block")

(.appendChild js/document.body (:view renderer))

(defn animate
  [dragon]
  (.update dragon 0.01666666666667) ; hardcoded frame rate
  (js/requestAnimFrame #(animate dragon))
  (pi/render! renderer stage))

(defn init
  []
  (let [dragon (pi/spine "data/dragon.json")
        cage (pi/container)
        local-rect (.getLocalBounds dragon)]
    (.setToSetupPose (.-skeleton dragon))
    (.update dragon 0)
    (set! (.-autoUpdate dragon) false)
    (pi/set-position! dragon [(- (:x local-rect))(- (:y local-rect))])
    (pi/add! cage dragon)
    (let [scale (.min js/Math (/ (* w 0.7) (:width cage))
                            (/ (* h 0.7) (:height cage)))]
      (pi/set-scale! cage [(/ (* h 0.7) (:height cage)) scale]))
    (pi/set-position! cage [(* (- w (:width cage)) 0.5)
                            (* (- h (:height cage)) 0.5)])
    (pi/add! stage cage)
    (.setAnimationByName (:state dragon) 0 "flying" true)
    (animate dragon)))

(pi/load-assets ["data/dragon.json"] init)
