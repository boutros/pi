(ns examples.spritesheet2.core
	(:require [pi.core :as pi]))

(defn build-frames
	[]
	(for [i (range 30) :let [num (if (< i 10) (str "0" i) i)]]
		(pi/texture-from-frame (str "rollSequence00" num ".png"))))

(def renderer (pi/renderer-auto 800 600))

(.appendChild js/document.body (.-view renderer))

(def stage (pi/stage 0xFFFFFF))

(defn animate
	[movie]
	(pi/rotate! movie 0.01)
	(.render renderer stage)
	(js/requestAnimFrame #(animate movie)))

(defn on-loaded
	[]
	(let [movie (pi/movie-clip (build-frames))]
		(pi/set-position! movie 300 300)
		(pi/set-anchor! movie 0.5 0.5)
		(.play movie)
		(set! (.-animationSpeed movie) 0.5)
		(.addChild stage movie)
		(animate movie)))

(def loader (pi/assets-loader ["fighter.json"] on-loaded))

(.load loader)




