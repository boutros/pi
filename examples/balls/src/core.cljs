(ns examples.balls.core
  (:require [pi.core :as pi]))

(defn by-id
  [id]
  (.getElementById js/document id))

(defn set-html!
  [id s]
  (set! (.-innerHTML (by-id id)) s))

(def max-balls 2500)
(def w (atom 1024))
(def h (atom 768))
(def sx (atom (inc (/ (js/Math.random) 20))))
(def sy (atom (inc (/ (js/Math.random) 20))))
(def slide-X (atom (/ @w 2)))
(def slide-Y (atom (/ @h 2)))
(def balls #js [])

(def renderer (pi/renderer-auto [@w @h]))

(defn resize
  []
  (do
    (reset! w (- (.-innerWidth js/window) 16))
    (reset! h (- (.-innerHeight js/window) 16))
    (reset! slide-X (/ @w 2))
    (reset! slide-Y (/ @h 2))
    (.resize renderer @w @h)))

(resize)

(defn new-wave
  []
  (do
    (reset! sx (inc (/ (js/Math.random) 20)))
    (reset! sy (inc (/ (js/Math.random) 20)))
    (set-html! "sx" (str "SX: " @sx "<br />SY: " @sy))))

(.addEventListener (by-id "rnd") "click" new-wave false)

(.appendChild js/document.body (:view renderer))

(set! (.-onresize js/window) resize)

(def stage (pi/stage))

(def ball-texture (pi/texture-from-image "assets/bubble_32x32.png"))

(defn update
  []
  (doseq [b balls]
    (pi/set-position! (:sprite b)
                      [(+ (:x b) @slide-X)
                       (+ (:y b) @slide-Y)])
    (set! (.-x b) (* (.-x b) @sx))
    (set! (.-y b) (* (.-y b) @sy))
    (when (> (.-x b) @w)
      (set! (.-x b) (- (:x b) @w)))
    (when (< (.-x b) (- @w))
      (set! (.-x b) (+ (:x b) @w)))
    (when (> (.-y b) @h)
      (set! (.-y b) (- (:y b) @h)))
    (when (< (.-y b) (- @h))
      (set! (.-y b) (+ (:y b) @h))))
  (pi/render! renderer stage)
  (js/requestAnimFrame update))

(defn start
  []
  (doseq [i (range max-balls)
          :let [ball (pi/sprite-from-texture ball-texture)]]
    (pi/set-position! ball [(- (* (js/Math.random) @w) @slide-X)
                            (- (* (js/Math.random) @h) @slide-Y)])
    (pi/set-anchor! ball [0.5 0.5])
    (.push balls #js {"sprite" ball "x" (get-in ball [:position :x]) "y" (get-in ball [:position :y])})
    (pi/add! stage ball))
  (set! (.-innerHTML (by-id "sx")) (str "SX: " @sx "<br />SY: " @sy))
  (update))

(.addEventListener js/document "DOMContentLoaded" start false)
