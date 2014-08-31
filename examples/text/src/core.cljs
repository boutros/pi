(ns examples.text.core
  (:require [pi.core :as pi]))

(def stage (pi/stage 0x66ff99))
(def renderer (pi/renderer-auto [620 400]))
(.appendChild js/document.body (:view renderer))
(def bg (pi/sprite-from-image "textDemoBG.jpg"))
(pi/add! stage bg)

(defn animate
  [spinning r counting c score]
  (js/requestAnimFrame #(animate spinning r counting (if (< c 50) (inc c) 0) (if (== 0 c) (inc score) score)))
  (pi/rotate! spinning r)
  (.setText counting (str "COUNT 4EVAR: " score))
  (pi/render! renderer stage))

(defn assets-loaded
  []
  (set! (.. js/document -body -style -cursor) "auto")
  (let [bmp-text (pi/bitmap-text "bitmap fonts are\n now supported!"
                                 {:font "35px Desyrel" :align "right"})
        text-sample (pi/text "Pixi.js can has\nmultiline text!"
                             {:font "bold 30px Snippet" :fill "white" :align "left"})
        spinning (pi/text "I'm fun!" {:font "bold 60px Podkova" :fill "#cc00ff" :align "center"
                                      :stroke "#ffffff" :strokeThickness 6})
        counting (pi/text "COUNT 4EVAR: 0" {:font "bold italic 60px arvo" :fill "#3e1707"
                                            :align "center" :stroke "#a4410e" :strokeThickness 7})]
    (pi/set-position! bmp-text [(- 620 (:width bmp-text) 20) 20])
    (pi/set-position! text-sample [20 20])
    (pi/set-anchor! spinning [0.5 0.5])
    (pi/set-position! spinning [310 200])
    (pi/set-position! counting [310 320])
    (pi/set-anchor! counting [0.5 0])
    (pi/add! stage [bmp-text text-sample spinning counting])
    (animate spinning 0.03 counting 0 0)))

(defn load-fonts
  "Asynchronously load google web-fonts, and call suplied callback when done.
  See: https://github.com/typekit/webfontloader"
  [fonts done]
  (let [wf (.createElement js/document "script")
        s (aget (.getElementsByTagName js/document "script") 0)
        https (= "https:" (.. js/document -location -protocol))
        url (str (if https "https" "http") "://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js")
        web-fonts #js {"google" #js {"families" (into-array fonts)} "active" done}]
    (set! (.-src wf) url)
    (set! (.-type wf) "text/javascript")
    (set! (.-async wf) true)
    (aset js/window "WebFontConfig" web-fonts)
    (.insertBefore (.-parentNode s) wf s)))

(def loader
  (pi/assets-loader ["desyrel.xml"] assets-loaded))

(defn fonts-loaded
  []
  (.load loader))

(set! (.. js/document -body -style -cursor) "wait")
(load-fonts ["Snippet" "Arvo:700italic" "Podovka:700"] fonts-loaded)
