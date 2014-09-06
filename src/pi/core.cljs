(ns pi.core
  (:import goog.object))

;; Javascript interop -----------------------------------------------
;;
;; Extend javascript objects by implementing the ILookup protocol, so
;; that we can access the object properties the same way we would
;; using a normal clojure map.

(defn strkey
  [x]
  (if (keyword? x) (name x) x))

(extend-type object
  ILookup
  (-lookup
    ([o k]
     (aget o (strkey k)))
    ([o k not-found]
     (let [s (strkey k)]
       (if (goog.object.containsKey o s)
         (aget o s)
         not-found)))))


;; Protocols --------------------------------------------------------

(defprotocol IContain
  (add! [this entity]))

(defprotocol IRender
  (render! [this entity]))

(defprotocol IInteractive
  (set-interactive! [this interactive])
  (set-handler! [this events handler]))


;; Renderers --------------------------------------------------------

(extend-type js/PIXI.CanvasRenderer
  IRender
  (render! [this entity]
    (.render this entity)))

(extend-type js/PIXI.WebGLRenderer
  IRender
  (render! [this entity]
    (.render this entity)))

(defn renderer-auto
  ([[width height]]
    (js/PIXI.autoDetectRenderer width height))
  ([[width height] canvas]
    (js/PIXI.autoDetectRenderer width height canvas))
  ([[width height] canvas antialias]
    (js/PIXI.autoDetectRenderer width height canvas antialias))
  ([[width height] canvas antialias transparent]
    (js/PIXI.autoDetectRenderer width height canvas antialias transparent)))

(defn renderer-canvas
  [[width height]]
  (js/PIXI.CanvasRenderer width height))

(defn renderer-webgl
  [[width height]]
  (js/PIXI.WebGLRenderer width height))


;; Stage ------------------------------------------------------------

(extend-type js/PIXI.Stage
  IContain
  (add! [this entity]
    (if (vector? entity)
      (doseq [e entity]
        (.addChild this e))
      (.addChild this entity)))
  IInteractive
  (set-interactive! [this interactive]
    (set! (.-interactive this) interactive))
  (set-handler! [this events handler]
    (if (vector? events)
      (doseq [e events]
        (aset this (strkey e) handler))
      (aset this (strkey events) handler))))

(defn stage
  ([]
    (stage 0x000000 false))
  ([color]
    (stage color false))
  ([color interactivity]
    (js/PIXI.Stage. color interactivity)))


;; Graphics primitives ----------------------------------------------
;;
;; The functions all return the graphics object, to facilitate
;; chaining with the thread-first macro:
;;
;;  (def g (graphics))
;;  (-> g
;;      (begin-fill 0x00ff00)
;;      (draw-rect 0 0 100 100)
;;      (end-fill))

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
  ([g width]
    (line-style g width 0x000000 1))
  ([g width color]
    (line-style g width color 1))
  ([g width color alpha]
    (.lineStyle g width color alpha)
    g))

(defn move-to
  [g [x y]]
  (.moveTo g x y)
  g)

(defn line-to
  [g [x y]]
  (.lineTo g x y)
  g)

(defn draw-rect
  [g [x1 y1 x2 y2]]
  (.drawRect g x1 y1 x2 y2)
  g)

(defn draw-circle
  [g [x y] r]
  (.drawCircle g x y r)
  g)


;; Assets -----------------------------------------------------------

(defn assets-loader
  [assets on-complete]
  (let [loader (js/PIXI.AssetLoader. (into-array assets))]
    (set! (.-onComplete loader) on-complete)
    loader))

(defn load-assets
  [assets on-complete]
  (let [loader (assets-loader assets on-complete)]
    (.load loader)))


;; Containers -------------------------------------------------------

(extend-type js/PIXI.DisplayObjectContainer
  IContain
  (add! [this entity]
    (if (vector? entity)
      (doseq [e entity]
        (.addChild this e))
      (.addChild this entity))))

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

(defn render-texture
  [[width height]]
  (js/PIXI.RenderTexture. width height))

;; Sprites ----------------------------------------------------------

(extend-type js/PIXI.Sprite
  IInteractive
  (set-interactive! [this interactive]
    (set! (.-interactive this) interactive))
  (set-handler! [this events handler]
    (if (vector? events)
      (doseq [e events]
        (aset this (strkey e) handler))
      (aset this (strkey events) handler))))

(defn sprite-from-texture
  [texture]
  (js/PIXI.Sprite. texture))

(defn sprite-from-image
  [image]
  (js/PIXI.Sprite.fromImage image true))

(defn sprite-from-frame
  [frame]
  (js/PIXI.Sprite.fromFrame frame))

(defn tiling-sprite
  [texture [w h]]
  (js/PIXI.TilingSprite. texture w h))


;; Spine ------------------------------------------------------------

(defn spine
  [data]
  (js/PIXI.Spine. data))


;; Text -------------------------------------------------------------

(defn text
  [font options]
  (js/PIXI.Text. font (clj->js options)))

(defn bitmap-text
  [font options]
  (js/PIXI.BitmapText. font (clj->js options)))

;; MovieClip --------------------------------------------------------

(defn movie-clip
  [frames]
  (js/PIXI.MovieClip. (into-array frames)))


;; Sprite/Container/MovieClip mutate functions ----------------------

(defn set-position!
  [entity [x y]]
  (do
    (set! (.-position.x entity) x)
    (set! (.-position.y entity) y)))

(defn set-scale!
  [entity [x y]]
  (do
    (set! (.-scale.x entity) x)
    (set! (.-scale.y entity) y)))

(defn set-anchor!
  [entity [x y]]
  (do
    (set! (.-anchor.x entity) x)
    (set! (.-anchor.y entity) y)))

(defn set-pivot!
  [entity [x y]]
  (.set (.-pivot entity) x y))

(defn set-rotation!
  [entity r]
  (set! (.-rotation entity) r))

(defn rotate!
  [entity r]
  (set! (.-rotation entity) (+ (.-rotation entity) r)))
