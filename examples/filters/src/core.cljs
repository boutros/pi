(ns examples.filters.core
  (:require [pi.core :as pi]))

(def renderer (pi/renderer-auto [620 380]))

(def stage (pi/stage 0xFFFFFF true))
(pi/set-interactive! stage true)

(def bg (pi/sprite-from-image "BGrotate.jpg"))
(pi/set-anchor! bg [0.5 0.5])
(pi/set-position! bg [310 190])

(def color-matrix
  [1 0 0 0
   0 1 0 0
   0 0 1 0
   0 0 0 1])

(def fltr (pi/color-matrix-filter))

(def container (pi/container))
(pi/set-position! container [310 190])

(def bg-front (pi/sprite-from-image "SceneRotate.jpg"))
(pi/set-anchor! bg-front [0.5 0.5])

(def light-1 (pi/sprite-from-image "LightRotate1.png"))
(pi/set-anchor! light-1 [0.5 0.5])

(def light-2 (pi/sprite-from-image "LightRotate2.png"))
(pi/set-anchor! light-2 [0.5 0.5])

(def panda (pi/sprite-from-image "panda.png"))
(pi/set-anchor! panda [0.5 0.5])

(doseq [[k v]
  {:position "absolute" :display "block"
   :width (str (.-innerWidth js/window) "px")
   :height (str (.-innerHeight js/window) "px")}]
  (aset (.. renderer -view -style) (name k) v))

(set! (.-filters stage) #js [fltr])

(def filtered (atom true))

(pi/set-handler! stage [:click :tap]
  (fn [e]
    (if-not @filtered
      (set! (.-filters stage) #js [fltr])
      (set! (.-filters stage) nil))
    (swap! filtered not)))

(def help-text
  (pi/text "Click to turn filters on / off."
           {:font "bold 12pt Arial" :fill "white"}))

(pi/set-position! help-text [10 350])

(pi/add! container [bg-front light-1 light-2 panda])

(pi/add! stage [container help-text])

(.appendChild js/document.body (.-view renderer))

(defn animate
  [c matrix]
  (pi/rotate! bg 0.01)
  (pi/rotate! bg-front 0.01)
  (pi/rotate! light-1 -0.02)
  (pi/rotate! light-2 0.01)
  (pi/set-scale! panda [(inc (* (js/Math.sin c) 0.04))
                        (inc (* (js/Math.cos c) 0.04))])
  (set! (.-matrix fltr) (into-array matrix))
  (js/requestAnimFrame #(animate (+ c 0.1)
    (-> matrix
      (assoc 1 (* (js/Math.sin c) 3))
      (assoc 2 (js/Math.cos c))
      (assoc 3 (* (js/Math.cos c) 1.5))
      (assoc 4 (* (js/Math.sin (/ c 3))))
      (assoc 5 (js/Math.sin (/ c 2)))
      (assoc 6 (js/Math.sin (/ c 4))))))
  (pi/render! renderer stage))

(animate 0 color-matrix)