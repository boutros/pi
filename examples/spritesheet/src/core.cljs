(ns examples.spritesheet.core
  (:require [pi.core :as pi]))

(def max-aliens 100)
(def alien-frames ["eggHead.png" "flowerTop.png" "helmlok.png" "skully.png"])
(def aliens (atom []))
(def alien-container (pi/container))
(pi/set-position! alien-container [400 300])

(defn on-loaded
  []
  (doseq [i (range max-aliens)]
    (let [frame (nth alien-frames (mod i 4))
          alien (pi/sprite-from-frame frame)]
      (set! (.-tint alien) (* (js/Math.random) 0xFFFFFF))
      (pi/set-position! alien
                        [(- (* (js/Math.random) 800) 400)
                         (- (* (js/Math.random) 800) 300)])
      (pi/set-anchor! alien [0.5 0.5])
      (swap! aliens conj alien)
      (pi/add! alien-container alien))))

(pi/load-assets ["SpriteSheet.json"] on-loaded)

(def stage (pi/stage 0xFFFFFF))

(def renderer (pi/renderer-auto [800 600]))

(.appendChild js/document.body (:view renderer))

(pi/add! stage alien-container)

(defn animate
  [count]
  (doseq [alien @aliens]
    (pi/rotate! alien 0.1))
    (pi/set-scale! alien-container [(js/Math.sin count)
                                   (js/Math.sin count)])
  (pi/render! renderer stage)
  (js/requestAnimFrame #(animate (+ count 0.01))))

(animate 0)
