(ns examples.filters2.core
  (:require [pi.core :as pi]))

(def renderer (pi/renderer-auto [620 380]))

(def stage (pi/stage 0xFFFFFF))

(def bg (pi/sprite-from-image "depth_blur_BG.jpg"))

(def dudes (pi/sprite-from-image "depth_blur_dudes.jpg"))
(pi/set-position! dudes [0 100])

(def robot (pi/sprite-from-image "depth_blur_moby.jpg"))
(pi/set-position! robot [120 0])

(def blur-1 (pi/blur-filter))
(def blur-2 (pi/blur-filter))

(set! (.-filters dudes) #js [blur-1])
(set! (.-filters robot) #js [blur-2])

(doseq [[k v]
  {:position "absolute" :display "block"
   :width (str (.-innerWidth js/window) "px")
   :height (str (.-innerHeight js/window) "px")}]
  (aset (.. renderer -view -style) (name k) v))

(pi/add! stage [bg dudes robot])

(.appendChild js/document.body (.-view renderer))

(defn animate
  [c]
  (set! (.-blur blur-1) (* 20 (js/Math.cos c)))
  (set! (.-blur blur-2) (* 20 (js/Math.sin c)))
  (js/requestAnimFrame #(animate (+ c 0.01)))
  (pi/render! renderer stage))

(animate 0)