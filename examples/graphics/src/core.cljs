(ns examples.graphics.core
  (:require [pi.core :as pi]))

(def stage (pi/stage 0xFFFFFF true))
(pi/set-interactive! stage true)

(def renderer (pi/renderer-auto [620 380]))
(set! (.. renderer -view -style -display) "block")
(.appendChild js/document.body (.-view renderer))

(def g (pi/graphics))

(-> g
    (pi/begin-fill 0xFF3300)
    (pi/line-style 10 0xFFD900 1)
    (pi/move-to [50 50])
    (pi/line-to [250 50] [100 100] [250 200]
                [50 220] [50 50])
    (pi/end-fill)
    (pi/line-style 10 0xFF0000 0.8)
    (pi/begin-fill 0xFF700B 1)
    (pi/move-to [210 300])
    (pi/line-to [450 320] [570 350] [580 20]
                [330 120] [410 200] [210 300])
    (pi/end-fill)
    (pi/line-style 2 0x0000FF 1)
    (pi/draw-rect [50 250 100 100])
    (pi/line-style 0)
    (pi/begin-fill 0xFFFF0B 0.5)
    (pi/draw-circle [470 200] 100)
    (pi/line-style 20 0x33FF00)
    (pi/move-to [30 30])
    (pi/line-to [600 300]))


(def thing (pi/graphics))
(pi/set-position! thing [310 190])

(pi/add! stage [g thing])

(defn rnd-x [x] (* (js/Math.random) x))

(pi/set-handler! stage [:click :touch]
  (fn [e]
    (-> g
        (pi/line-style (rnd-x 30) (rnd-x 0xffffff) 1)
        (pi/move-to [(rnd-x 620) (rnd-x 380)])
        (pi/line-to [(rnd-x 620) (rnd-x 380)]))))

(defn animate
  [c]
  (let [sin (* (js/Math.sin c) 20)
        cos (* (js/Math.cos c) 20)]
    (-> thing
        (pi/clear)
        (pi/line-style 30 0xFF0000 1)
        (pi/begin-fill 0xFFFF00 0.5)
        (pi/move-to [(- sin 120) (- cos 100)])
        (pi/line-to [(+ cos 120) (- sin 100)]
                    [(+ sin 120) (+ cos 100)]
                    [(- cos 120) (+ sin 100)]
                    [(- sin 120) (- cos 100)])
        (pi/set-rotation! (* c 0.1)))
    (js/requestAnimFrame #(animate (+ c 0.1)))
    (pi/render! renderer stage)))

(animate 0)