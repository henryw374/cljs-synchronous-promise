(ns widdindustries.synchronous-promise-test
  (:require [clojure.test :refer [deftest testing is]]
            [widdindustries.synchronous-promise :as sut]
            [promesa.core :as p]
            [promesa.impl :as pi]))



(comment
  (-> (sut/resolved :val)
      (.then (fn [v] [v]))
      (.catch (fn [_] (throw (js/Error. "never called"))))
      (.then (fn [x] (js/console.log x) x)))
  
  (-> (sut/resolved :val)
      (.then (fn [] (throw (js/Error. "boom"))))
      (.then (fn [_] (js/console.log "never called")))
      (.catch (fn [] (js/console.log "catching") :continue))
      (.then (fn [x] (js/console.log "and then.." x))))
  
  (-> (sut/rejected (js/Error. "oh no"))
      (.then (fn [_] (js/console.log "never called")))
      (.catch (fn [] (js/console.log "catching") :continue))
      (.then (fn [x] (js/console.log "and then.." x)))
      )
  
  (pi/extend-promise! sut/SyncPromise)
  (-> (sut/resolved :val)
      (p/then (fn [v] (js/console.log "hello " v ))))
  
  (is "foo" @(sut/resolved "foo"))
  (-> (sut/resolved "foo") deref)
  )
