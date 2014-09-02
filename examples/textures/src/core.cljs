(ns examples.textures.core
  (:require [pi.core :as pi]))

(def stage (pi/stage))

(def renderer (pi/renderer-auto [800 600]))
(set! (.. renderer -view -style -width)
  (str (.-innerWidth js/window) "px"))
(set! (.. renderer -view -style -height)
  (str (.-innerHeight js/window) "px"))
(set! (.. renderer -view -style -display) "block")
(.appendChild js/document.body (:view renderer))

(def t1 (atom (pi/render-texture [800 600])))
(def t2 (atom (pi/render-texture [800 600])))

(def output (pi/sprite-from-texture @t1))
(pi/set-position! output [400 300])
(pi/set-anchor! output [0.5 0.5])

(pi/add! stage output)

(def container (pi/container))
(pi/set-position! container [400 300])
(pi/add! stage container)

(def sprites
  (into []
    (for [i (range 1 9)]
      (str "spinObj_0" i ".png"))))

(def fruits (atom []))

(doseq [i (range 20)
        :let [sprite (nth sprites (mod i 8))
              fruit (pi/sprite-from-image sprite)
              x (- (* (js/Math.random) 400) 200)
              y (- (* (js/Math.random) 400) 200)]]
  (pi/set-anchor! fruit [0.5 0.5])
  (pi/set-position! fruit [x y])
  (pi/add! container fruit)
  (swap! fruits conj fruit))


(defn animate
  [c c2]
  (let [tmp @t1
        scale (inc (* (js/Math.sin c) 0.2))]
    (js/requestAnimFrame #(animate (+ c 0.01) (inc c2)))
    (doseq [f @fruits]
      (pi/rotate! f 0.1))
    (pi/rotate! container -0.01)
    (reset! t1 @t2)
    (reset! t2 tmp)
    (.setTexture output @t1)
    (pi/set-scale! output [scale scale])
    (.render @t2 stage false)
    (pi/render! renderer stage)))

(animate 0 0)
