(defproject pi "0.1.0-SNAPSHOT"
  :description "Clojurecsript wrapper for pixi.js"
  :url "http://github.com/boutros/pi"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [goodboydigital/pixi "1.6.1"]
                 ]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :source-paths ["src"]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src"]
              :compiler {
                :output-to "dev/pi.js"
                :output-dir "dev/out"
                :preamble ["pixi/pixi.dev.js"]
                :externs ["pixi/pixi.dev.js"]
                :optimizations :none
                :source-map true}}
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
                                   ]})