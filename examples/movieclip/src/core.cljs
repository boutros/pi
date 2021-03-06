(ns examples.movieclip.core
  (:require [pi.core :as pi]))

(def max-explosions 50)

(def renderer (pi/renderer-auto [800 600]))

(.appendChild js/document.body (:view renderer))

(def stage (pi/stage 0xFFFFFF))

(defn build-frames
  []
  (for [i (range 26)]
    (pi/texture-from-frame (str "Explosion_Sequence_A " (inc i) ".png"))))

(defn on-loaded
  []
  (let [frames (build-frames)]
    (doseq [i (range max-explosions)
            :let [x (pi/movie-clip frames)]]
      (pi/set-position! x [(* (js/Math.random) 800)
                           (* (js/Math.random) 600)])
      (pi/set-anchor! x [0.5 0.5])
      (pi/set-rotation! x (* (js/Math.random) js/Math.PI))
      (.gotoAndPlay x (* (js/Math.random) 27))
      (pi/add! stage x))))

(pi/load-assets ["SpriteSheet.json"] on-loaded)

(defn animate
  []
  (pi/render! renderer stage)
  (js/requestAnimFrame animate))

(animate)


