(defproject pi "0.1.0-SNAPSHOT"
  :description "Clojurecsript wrapper for pixi.js"
  :url "http://github.com/boutros/pi"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2322"]
                 [goodboydigital/pixi "1.6.1"]
                 ]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["dev"]
              :compiler {
                :output-to "dev/main.js"
                :preamble ["pixi/pixi.js"]
                :externs ["pixi/pixi.js"]
                :optimizations :advanced
                :closure-warnings {:externs-validation :off
                                   :non-standard-jsdoc :off}}}
              {:id "basics"
               :source-paths ["src" "examples/basics/src"]
               :compiler {
                :output-to "examples/basics/main.js"
                :output-dir "examples/basics/out"
                :source-map true
                :optimizations :none}}
              {:id "basics2"
               :source-paths ["src" "examples/basics2/src"]
               :compiler {
                :output-to "examples/basics2/main.js"
                :output-dir "examples/basics2/out"
                :source-map true
                :optimizations :none}}
              {:id "spritesheet"
               :source-paths ["src" "examples/spritesheet/src"]
               :compiler {
                :output-to "examples/spritesheet/main.js"
                :output-dir "examples/spritesheet/out"
                :source-map true
                :optimizations :none}}
              {:id "spritesheet2"
               :source-paths ["src" "examples/spritesheet2/src"]
               :compiler {
                :output-to "examples/spritesheet2/main.js"
                :output-dir "examples/spritesheet2/out"
                :source-map true
                :optimizations :none}}
              {:id "movieclip"
               :source-paths ["src" "examples/movieclip/src"]
               :compiler {
                :output-to "examples/movieclip/main.js"
                :output-dir "examples/movieclip/out"
                :source-map true
                :optimizations :none}}
              {:id "balls"
               :source-paths ["src" "examples/balls/src"]
               :compiler {
                :output-to "examples/balls/main.js"
                :output-dir "examples/balls/out"
                :source-map true
                :optimizations :none}}
              {:id "interactivity"
               :source-paths ["src" "examples/interactivity/src"]
               :compiler {
                :output-to "examples/interactivity/main.js"
                :output-dir "examples/interactivity/out"
                :source-map true
                :optimizations :none}}
              {:id "transparency"
               :source-paths ["src" "examples/transparency/src"]
               :compiler {
                :output-to "examples/transparency/main.js"
                :output-dir "examples/transparency/out"
                :source-map true
                :optimizations :none}}
              {:id "multitouch"
               :source-paths ["src" "examples/multitouch/src"]
               :compiler {
                :output-to "examples/multitouch/main.js"
                :output-dir "examples/multitouch/out"
                :source-map true
                :optimizations :none}}
              {:id "tiling"
               :source-paths ["src" "examples/tiling/src"]
               :compiler {
                :output-to "examples/tiling/main.js"
                :output-dir "examples/tiling/out"
                :source-map true
                :optimizations :none}}
              {:id "text"
               :source-paths ["src" "examples/text/src"]
               :compiler {
                :output-to "examples/text/main.js"
                :output-dir "examples/text/out"
                :source-map true
                :optimizations :none}}
              {:id "textures"
               :source-paths ["src" "examples/textures/src"]
               :compiler {
                :output-to "examples/textures/main.js"
                :output-dir "examples/textures/out"
                :source-map true
                :optimizations :none}}
              {:id "spine"
               :source-paths ["src" "examples/spine/src"]
               :compiler {
                :output-to "examples/spine/main.js"
                :output-dir "examples/spine/out"
                :source-map true
                :optimizations :none}}
              {:id "spine2"
               :source-paths ["src" "examples/spine2/src"]
               :compiler {
                :output-to "examples/spine2/main.js"
                :output-dir "examples/spine2/out"
                :source-map true
                :optimizations :none}}
              {:id "spine3"
               :source-paths ["src" "examples/spine3/src"]
               :compiler {
                :output-to "examples/spine3/main.js"
                :output-dir "examples/spine3/out"
                :source-map true
                :optimizations :none}}
              {:id "spine4"
               :source-paths ["src" "examples/spine4/src"]
               :compiler {
                :output-to "examples/spine4/main.js"
                :output-dir "examples/spine4/out"
                :source-map true
                :optimizations :none}}
              {:id "graphics"
               :source-paths ["src" "examples/graphics/src"]
               :compiler {
                :output-to "examples/graphics/main.js"
                :output-dir "examples/graphics/out"
                :source-map true
                :optimizations :none}}
              {:id "masking"
               :source-paths ["src" "examples/masking/src"]
               :compiler {
                :output-to "examples/masking/main.js"
                :output-dir "examples/masking/out"
                :source-map true
                :optimizations :none}}
              {:id "filters"
               :source-paths ["src" "examples/filters/src"]
               :compiler {
                :output-to "examples/filters/main.js"
                :output-dir "examples/filters/out"
                :source-map true
                :optimizations :none}}
              {:id "filters2"
               :source-paths ["src" "examples/filters2/src"]
               :compiler {
                :output-to "examples/filters2/main.js"
                :output-dir "examples/filters2/out"
                :source-map true
                :optimizations :none}}
              {:id "filters3"
               :source-paths ["src" "examples/filters3/src"]
               :compiler {
                :output-to "examples/filters3/main.js"
                :output-dir "examples/filters3/out"
                :source-map true
                :optimizations :none}}
                                   ]})
