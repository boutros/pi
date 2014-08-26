(ns examples.interactivity.core
  (:require [pi.core :as pi]))

(def stage (pi/stage 0x000000 true))

(def renderer (pi/renderer-auto 620 400))

(.appendChild js/document.body (.-view renderer))

(def bg (pi/sprite-from-image "button_test_BG.jpg"))
(def button-texture (pi/texture-from-image "button.png"))
(def button-down-texture (pi/texture-from-image "buttonDown.png"))
(def button-over-texture (pi/texture-from-image "buttonOver.png"))

(.addChild stage bg)

(defn mouse-down
  [e]
  (this-as this
    (set! (.-isdown this) true)
    (.setTexture this button-down-texture)))

(defn mouse-up
  [e]
  (this-as this
    (set! (.-isdown this) false)
    (if (.-isOver this)
      (.setTexture this button-over-texture)
      (.setTexture this button-texture))))

(defn mouse-over
  [e]
  (this-as this
    (set! (.-isOver this) true)
    (when-not (.-isdown this)
      (.setTexture this button-over-texture))))

(defn mouse-out
  [e]
  (this-as this
    (set! (.-isOver this) true)
    (when-not (.-isdown this)
      (.setTexture this button-texture))))

(def button-positions [[175 75] [455 75] [280 210] [175 325] [485 305]])

(doseq [i (range 5)
       :let [pos (nth button-positions i)
             button (pi/sprite-from-texture button-texture)]]
  (pi/set-anchor! button 0.5 0.5)
  (pi/set-position! button (first pos) (last pos))
  (set! (.-interactive button) true)
  (set! (.-mouseover button) mouse-over)
  (set! (.-mouseup button) mouse-up)
  (set! (.-mousedown button) mouse-down)
  (set! (.-mouseout button) mouse-out)
  (set! (.-click button) (fn [e] (js/console.log "click")))
  (case i
    0 (pi/set-scale! button 1.2 1)
    1 (pi/set-scale! button 1 1.2)
    2 (set! (.-rotation button) (/ js/Math.PI 10))
    3 (pi/set-scale! button 0.8 0.8)
    4 (do
        (pi/set-scale! button 0.8 1.2)
        (set! (.-rotation button) js/Math.PI))
    nil)
  (.addChild stage button))

(def logo (pi/sprite-from-image "pixi.png"))
(pi/set-position! logo 564 368)
(set! (.-interactive logo) true)
(set! (.-click logo)
  (fn [e] (.open js/window "https://github.com/GoodBoyDigital/pixi.js" "_blank")))
(.addChild stage logo)

(defn animate
  []
  (.render renderer stage)
  (js/requestAnimFrame animate))

(animate)

