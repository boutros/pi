(ns examples.spine3.core
  (:require [pi.core :as pi]))

(def stage (pi/stage 0xFFFFFF true))

(def renderer (pi/renderer-auto [1024 640]))

(set! (.. renderer -view -style -display) "block")
(set! (.. renderer -view -style -width) "100%")
(set! (.. renderer -view -style -height) "100%")
;; TODO multi-set! fn?

(.appendChild js/document.body (:view renderer))

(defn animate
  [bg bg2 fg fg2 pos]
  ;; bg
  (set! (.. bg -position -x) (- (* pos 0.6)))
  (set! (.. bg -position -x) (mod (.. bg -position -x) (* 1286 2)))
  (when (< (.. bg -position -x) 0)
    (set! (.. bg -position -x) (+ (.. bg -position -x) (* 1286 2))))
  (set! (.. bg -position -x) (- (.. bg -position -x) 1286))
  ;; bg2
  (set! (.. bg2 -position -x) (+ (- (* pos 0.6)) 1286))
  (set! (.. bg2 -position -x) (mod (.. bg2 -position -x) (* 1286 2)))
  (when (< (.. bg2 -position -x) 0)
    (set! (.. bg2 -position -x) (+ (.. bg2 -position -x) (* 1286 2))))
  (set! (.. bg2 -position -x) (- (.. bg2 -position -x) 1286))
  ;; fg
  (set! (.. fg -position -x) (- pos))
  (set! (.. fg -position -x) (mod (.. fg -position -x) (* 1286 2)))
  (when (< (.. fg -position -x) 0)
    (set! (.. fg -position -x) (+ (.. fg -position -x) (* 1286 2))))
  (set! (.. fg -position -x) (- (.. fg -position -x) 1286))
  ;; fg2
  (set! (.. fg2 -position -x) (+ (- pos) 1286))
  (set! (.. fg2 -position -x) (mod (.. fg2 -position -x) (* 1286 2)))
  (when (< (.. fg2 -position -x) 0)
    (set! (.. fg2 -position -x) (+ (.. fg2 -position -x) (* 1286 2))))
  (set! (.. fg2 -position -x) (- (.. fg2 -position -x) 1286))
  (js/requestAnimFrame #(animate bg bg2 fg fg2 (+ pos 10)))
  (pi/render! renderer stage))

(defn init
  []
  (let [bg    (pi/sprite-from-image "data/iP4_BGtile.jpg")
        bg2   (pi/sprite-from-image "data/iP4_BGtile.jpg")
        fg    (pi/sprite-from-image "data/iP4_ground.png")
        fg2   (pi/sprite-from-image "data/iP4_ground.png")
        fgy   (- 640 (:height fg2))
        pixie (pi/spine "data/PixieSpineData.json")
        x     341
        y     500]
    (pi/set-position! fg [0 fgy])
    (pi/set-position! fg2 [0 fgy])
    (pi/add! stage [bg bg2 fg fg2 pixie])
    (pi/set-scale! pixie [0.3 0.3])
    (pi/set-position! pixie [x y])
    (.setMixByName (:stateData pixie) "running" "jump" 0.2)
    (.setMixByName (:stateData pixie) "jump" "running" 0.4)
    (.setAnimationByName (:state pixie) "running" true)
    (pi/set-handler! stage [:mousedown :touchstart]
      (fn [e]
        (.setAnimationByName (:state pixie) "jump" false)
        (.addAnimationByName (:state pixie) "running" true)))
    (animate bg bg2 fg fg2 0)))

(pi/load-assets
  ["data/PixieSpineData.json" "data/Pixie.json"
   "data/iP4_BGtile.jpg" "data/iP4_ground.png"]
  init)

