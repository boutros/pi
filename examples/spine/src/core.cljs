(ns examples.spine.core
  (:require [pi.core :as pi]))

(def w (.-innerWidth js/window))
(def h (.-innerHeight js/window))

(def stage (pi/stage 0xFFFFFF true))

(def renderer
  (pi/renderer-auto [w h]))

(set! (.. renderer -view -style -display) "block")

(.appendChild js/document.body (:view renderer))

(defn init
  []
  (let [spine-boy (pi/spine "data/spineboySpineData.json")]
    (pi/set-position! spine-boy [(/ w 2) h])
    (pi/set-scale! spine-boy [(/ h 400) (/ h 400)])
    (.setMixByName (:stateData spine-boy) "walk" "jump" 0.2)
    (.setMixByName (:stateData spine-boy) "jump" "walk" 0.4)
    (.setAnimationByName (:state spine-boy) "walk" true)
    (pi/add! stage spine-boy)
    (pi/set-handler! stage [:click :tap]
      (fn [e]
        (.setAnimationByName (:state spine-boy) "jump" false)
        (.addAnimationByName (:state spine-boy) "walk" true)))))

(pi/load-assets ["data/spineboy.json" "data/spineboySpineData.json"] init)

(defn animate
  []
  (js/requestAnimFrame animate)
  (pi/render! renderer stage))

(animate)
