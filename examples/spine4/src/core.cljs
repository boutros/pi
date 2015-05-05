(ns examples.spine4.core
  (:require [pi.core :as pi]))

(def w (.-innerWidth js/window))
(def h (.-innerHeight js/window))

(def stage (pi/stage 0xFFFFFF true))

(def renderer (pi/renderer-auto [w h]))

(set! (.. renderer -view -style -display) "block")

(.appendChild js/document.body (:view renderer))

(defn animate
  []
  (js/requestAnimFrame animate)
  (pi/render! renderer stage))

(defn init
  []
  (let [goblin (pi/spine "data/goblins.json")]
    (pi/set-position! goblin [(/ w 2) h])
    (pi/set-scale! goblin [(/ h 400) (/ h 400)])
    (.setSkinByName (:skeleton goblin) "goblin")
    (.setSlotsToSetupPose (:skeleton goblin))
    (.setAnimationByName (:state goblin) 0 "walk" true)
    (pi/add! stage goblin)
    (pi/set-handler! stage [:click :tap]
      (fn [e]
        (let [skin     (.. goblin -skeleton -skin -name)
              new-skin (if (= skin "goblin") "goblingirl" "goblin")]
          (.setSkinByName (:skeleton goblin) new-skin)
          (.setSlotsToSetupPose (:skeleton goblin)))))
    (animate)))

(pi/load-assets ["data/goblins.json"] init)
