(ns figwheel
  (:require [figwheel.main.api :as fig]
            [clojure.java.io :as io]))

(defn start []
  (fig/start 
    {:mode :serve}
    {:id      "dev"
     :options {:main 'widdindustries.synchronous-promise-test}
     :config  {:watch-dirs   ["src" "test"]
               :open-url false
               :auto-testing true}}))

(defn repl []
  (fig/cljs-repl "dev"))

(comment 
  (start)
  (fig/stop-all)
  (repl)
  
  )