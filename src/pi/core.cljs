(ns pi.core)

;; Renderer ---------------------------------------------------------

(defn renderer-auto
	([width height]
		(js/PIXI.autoDetectRenderer width height))
	([width height canvas]
		(js/PIXI.autoDetectRenderer width height canvas))
	([width height canvas antialias]
		(js/PIXI.autoDetectRenderer width height canvas antialias))
	([width height canvas antialias transparent]
		(js/PIXI.autoDetectRenderer width height canvas antialias transparent)))

(defn renderer-canvas
	[width height]
	(js/PIXI.CanvasRenderer width height))

(defn renderer-webgl
	[width height]
	(js/PIXI.WebGLRenderer width height))


;; Stage ------------------------------------------------------------

(defn stage
	([]
		(js/PIXI.Stage. 0x000000))
	([color]
		(js/PIXI.Stage. color))
	([color interactivity]
		(js/PIXI.Stage. color interactivity)))

;; Graphics primitives ----------------------------------------------
;;
;; The functions all return the graphics object, to facilitate
;; chaining with the thread-first macro:
;;
;;  (def g (graphics))
;; 	(-> g
;;		  (begin-fill 0x00ff00)
;;			(draw-rect 0 0 100 100)
;;    	(end-fill))

(defn graphics
	[]
	(js/PIXI.Graphics.))

(defn clear
	[g]
	(.clear g)
	g)

(defn begin-fill
	([g color]
		(begin-fill g color 1))
	([g color alpha]
		(.beginFill g color alpha)
		g))

(defn end-fill
	[g]
	(.endFill g)
	g)

(defn line-style
	[g width color alpha]
	(.lineStyle g width color alpha)
	g)

(defn move-to
	[g x y]
	(.moveTo g x y)
	g)

(defn line-to
	[g x y]
	(.lineTo g x y)
	g)

(defn draw-rect
	[g x1 y1 x2 y2]
	(.drawRect g x1 y1 x2 y2)
	g)

;; Assets -----------------------------------------------------------

(defn assets-loader
	[assets on-complete]
	(let [loader (js/PIXI.AssetLoader. (into-array assets))]
		(set! (.-onComplete loader) on-complete)
		loader))

;; Containers -------------------------------------------------------

(defn container
	[]
	(js/PIXI.DisplayObjectContainer.))

;; Textures ---------------------------------------------------------

(defn texture-from-image
	[image]
	(js/PIXI.Texture.fromImage image))

(defn texture-from-frame
	[frame]
	(js/PIXI.Texture.fromFrame frame))


;; Sprites ----------------------------------------------------------

(defn sprite-from-texture
	[texture]
	(js/PIXI.Sprite. texture))

(defn sprite-from-image
	[image]
	(js/PIXI.Sprite.fromImage image false))

(defn sprite-from-frame
	[frame]
	(js/PIXI.Sprite.fromFrame frame))

;; MovieClip --------------------------------------------------------

(defn movie-clip
	[frames]
	(js/PIXI.MovieClip. (into-array frames)))

;; Sprite/Container/MovieClip util functions -------------------------

(defn set-position!
	[entity x y]
	(do
		(set! (.-position.x entity) x)
		(set! (.-position.y entity) y)))

(defn set-scale!
	[entity x y]
	(do
		(set! (.-scale.x entity) x)
		(set! (.-scale.y entity) y)))

(defn set-anchor!
	[entity x y]
	(do
		(set! (.-anchor.x entity) x)
		(set! (.-anchor.y entity) y)))

(defn set-pivot!
	[entity x y]
	(.set (.-pivot entity) x y))

(defn rotate!
	[entity r]
	(set! (.-rotation entity) (+ (.-rotation entity) r)))
