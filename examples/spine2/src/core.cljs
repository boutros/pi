(ns examples.spine2.core
  (:require [pi.core :as pi]))

(def w (.-innerWidth js/window))
(def h (.-innerHeight js/window))

(def stage (pi/stage 0xFFFFFF true))

(def renderer
  (pi/renderer-auto [w h]))

(set! (.. renderer -view -style -display) "block")

(.appendChild js/document.body (:view renderer))

(defn init
  []
  (let [dragon (pi/spine "data/dragonBonesData.json")]
    (pi/set-position! dragon [(/ w 2) (+ (/ h 2) 450)])
    (pi/set-scale! dragon [1 1])
    (.setAnimationByName (:state dragon) "flying" true)
    (pi/add! stage dragon)))

(pi/load-assets ["data/dragonBones.json" "data/dragonBonesData.json"] init)

(defn animate
  []
  (js/requestAnimFrame animate)
  (pi/render! renderer stage))

(animate)
