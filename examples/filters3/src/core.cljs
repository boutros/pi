(ns examples.filters3.core
  (:require [pi.core :as pi]))

(def max-fishes 20)

(def renderer (pi/renderer-auto [630 410]))

(doseq [[k v]
  {:position "absolute" :display "block"
   :width (str (.-innerWidth js/window) "px")
   :height (str (.-innerHeight js/window) "px")}]
  (aset (.. renderer -view -style) (name k) v))

(.appendChild js/document.body (.-view renderer))

(def stage (pi/stage 0xFFFFFF true))

(def pond-container (pi/container))

(pi/add! stage pond-container)
(pi/set-interactive! stage true)

(def bg (pi/sprite-from-image "displacement_BG.jpg"))

(pi/add! pond-container bg)

(def padding 100)

(def bounds
  (pi/rectangle [(- padding) (- padding)]
                [(+ 630 (* padding 2)) (+ 410 (* padding 2))]))

(def fishes (atom []))

(doseq [i (range max-fishes)
        :let [id (inc (mod i 4))
              fish (pi/sprite-from-image (str "displacement_fish" id ".png"))
              scale (+ 0.8 (* (js/Math.random) 0.3))]]
  (pi/set-anchor! fish [0.5 0.5])
  (pi/add! pond-container fish)
  (set! (.-direction fish) (* (js/Math.random) js/Math.PI 2))
  (set! (.-speed fish) (+ 2 (* (js/Math.random) 2)))
  (set! (.-turnSpeed fish) (- (js/Math.random) 0.8))
  (pi/set-position! fish [(* (js/Math.random) (:width bounds))
                          (* (js/Math.random) (:height bounds))])
  (pi/set-scale! fish [scale scale])
  (swap! fishes conj fish))

(def overlay
  (pi/tiling-sprite (pi/texture-from-image "zeldaWaves.png")
                    [630 410]))
(set! (.-alpha overlay) 0.2)
(pi/add! pond-container overlay)

(def displacement-texture (pi/texture-from-image "displacement_map.jpg"))

(def displacement-filter (pi/displacement-filter displacement-texture))

(set! (.-filters pond-container) #js [displacement-filter])

(def filtered (atom true))

(pi/set-handler! stage [:click :tap]
  (fn [e]
    (if-not @filtered
      (set! (.-filters pond-container) #js [displacement-filter])
      (set! (.-filters pond-container) nil))
    (swap! filtered not)))

(pi/set-scale! displacement-filter [50 50])

(defn animate
  [c]
  (doseq [fish @fishes]
    (set! (.-direction fish) (+ (.-direction fish) (* (.-turnSpeed fish) 0.01)))
    (pi/set-position! fish [(- (.. fish -position -x)
                               (* (js/Math.sin (.-direction fish)) (.-speed fish)))
                            (- (.. fish -position -y)
                               (* (js/Math.cos (.-direction fish)) (.-speed fish)))])
    (pi/set-rotation! fish (- (/ Math.PI 2) (.-direction fish)))
    (when (< (.. fish -position -x) (:x bounds))
      (set! (.. fish -position -x) (+ (.. fish -position -x) (:width bounds))))
    (when (> (.. fish -position -x) (+ (:x bounds) (:width bounds)))
      (set! (.. fish -position -x) (- (.. fish -position -x) (:width bounds))))
    (when (< (.. fish -position -y) (:y bounds))
      (set! (.. fish -position -y) (+ (.. fish -position -y) (:height bounds))))
    (when (> (.. fish -position -y) (+ (:y bounds) (:height bounds)))
      (set! (.. fish -position -y) (- (.. fish -position -y) (:height bounds))))
    )
  (set! (.. displacement-filter -offset -x) (* 10 c))
  (set! (.. displacement-filter -offset -y) (* 10 c))
  (set! (.. overlay -tilePosition -x) (* c -10))
  (set! (.. overlay -tilePosition -y) (* c -10))
  (pi/render! renderer stage)
  (js/requestAnimFrame #(animate (+ c 0.1))))

(animate 0)