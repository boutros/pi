(ns examples.interactivity.core
  (:require [pi.core :as pi]))

(def stage (pi/stage 0x000000 true))

(def renderer (pi/renderer-auto [620 400]))

(.appendChild js/document.body (:view renderer))

(def bg (pi/sprite-from-image "button_test_BG.jpg"))
(def button-texture (pi/texture-from-image "button.png"))
(def button-down-texture (pi/texture-from-image "buttonDown.png"))
(def button-over-texture (pi/texture-from-image "buttonOver.png"))

(pi/add! stage bg)

(defn mouse-down
  [e]
  (this-as button
    (set! (.-isdown button) true)
    (.setTexture button button-down-texture)))

(defn mouse-up
  [e]
  (this-as button
    (set! (.-isdown button) false)
    (if (:isOver button)
      (.setTexture button button-over-texture)
      (.setTexture button button-texture))))

(defn mouse-over
  [e]
  (let [button (:target e)] ;; TODO consider this way instead of this-as macro?
    (set! (.-isOver button) true)
    (when-not (:isdown button)
      (.setTexture button button-over-texture))))

(defn mouse-out
  [e]
  (this-as button
    (set! (.-isOver button) true)
    (when-not (:isdown button)
      (.setTexture button button-texture))))

(def button-positions [[175 75] [455 75] [280 210] [175 325] [485 305]])

(doseq [i (range 5)
       :let [pos (nth button-positions i)
             button (pi/sprite-from-texture button-texture)]]
  (pi/set-anchor! button [0.5 0.5])
  (pi/set-position! button [(first pos) (last pos)])
  (pi/set-interactive! button true)
  (pi/set-handler! button :mouseover mouse-over)
  (pi/set-handler! button [:mouseup :touchend] mouse-up)
  (pi/set-handler! button [:mousedown :touchstart] mouse-down)
  (pi/set-handler! button [:mouseout] mouse-out)
  (pi/set-handler! button :click (fn [e] (js/console.log "click")))
  (case i
    0 (pi/set-scale! button [1.2 1])
    1 (pi/set-scale! button [1 1.2])
    2 (pi/set-rotation! button (/ js/Math.PI 10))
    3 (pi/set-scale! button [0.8 0.8])
    4 (do
        (pi/set-scale! button [0.8 1.2])
        (pi/set-rotation! button js/Math.PI)))
  (pi/add! stage button))

(def logo (pi/sprite-from-image "pixi.png"))
(pi/set-position! logo [564 368])
(pi/set-interactive! logo true)
(set! (.-click logo)
  (fn [e] (.open js/window "https://github.com/GoodBoyDigital/pixi.js" "_blank")))
(pi/add! stage logo)

(defn animate
  []
  (pi/render! renderer stage)
  (js/requestAnimFrame animate))

(animate)

