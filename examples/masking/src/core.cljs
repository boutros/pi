(ns examples.masking.core
  (:require [pi.core :as pi]))

(def stage (pi/stage 0xffffff true))
(pi/set-interactive! stage true)

(def container (pi/container))
(pi/set-position! container [310 190])

(def bg (pi/sprite-from-image "BGrotate.jpg"))
(pi/set-anchor! bg [0.5 0.5])
(pi/set-position! bg [310 190])

(def bg-front (pi/sprite-from-image "SceneRotate.jpg"))
(pi/set-anchor! bg-front [0.5 0.5])

(def light-1 (pi/sprite-from-image "LightRotate1.png"))
(pi/set-anchor! light-1 [0.5 0.5])

(def light-2 (pi/sprite-from-image "LightRotate2.png"))
(pi/set-anchor! light-2 [0.5 0.5])

(def panda (pi/sprite-from-image "panda.png"))
(pi/set-anchor! panda [0.5 0.5])

(def thing (pi/graphics))
(pi/set-position! thing [310 190])
(pi/line-style thing 0)

(set! (.-mask container) thing)
(pi/set-handler! stage [:click :tap]
  (fn [e]
    (if-not (.-mask container)
      (set! (.-mask container) thing)
      (set! (.-mask container) nil))))

(def help-text
  (pi/text "Click to turn masking on / off."
           {:font "bold 12pt Arial" :fill "white"}))

(pi/set-position! help-text [10 350])

(pi/add! container [bg-front light-1 light-2 panda])
(pi/add! stage [bg container thing help-text])

(def renderer (pi/renderer-auto [620 380]))

(doseq [[k v]
  ;; TODO create set-styles! fn, in an util namespace?
  {:position "absolute" :marginLeft "-310px" :marginTop "-190px"
   :top "50%" :left "50%" :display "block"}]
  (aset (.. renderer -view -style) (name k) v))

(.appendChild js/document.body (:view renderer))

(defn animate
  [c]
  (let [sin (* (js/Math.sin c) 20)
        cos (* (js/Math.cos c) 20)]
    (pi/rotate! bg 0.01)
    (pi/rotate! bg-front 0.01)
    (pi/rotate! light-1 -0.02)
    (pi/rotate! light-2 0.01)
    (-> thing
        (pi/clear)
        (pi/begin-fill 0x8bc5ff 0.4)
        (pi/move-to [(- sin 120) (- cos 100)])
        (pi/line-to [(- cos 320) (+ sin 100)]
                    [(+ cos 120) (- sin 100)]
                    [(+ sin 120) (+ cos 100)]
                    [(- cos 120) (+ sin 100)]
                    [(- sin 120) (- cos 300)]
                    [(- sin 320) (- cos 100)])
        (pi/set-rotation! (* c 0.1)))
    (pi/set-scale! panda [(inc (* (js/Math.sin c) 0.04))
                          (inc (* (js/Math.cos c) 0.04))])
    (js/requestAnimFrame #(animate (+ c 0.1)))
    (pi/render! renderer stage)))

(animate 0)