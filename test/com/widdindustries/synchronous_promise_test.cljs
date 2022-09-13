(ns com.widdindustries.synchronous-promise-test
  (:require [clojure.test :refer [deftest testing is]]
            [com.widdindustries.synchronous-promise :as sut]
            [promesa.core :as p]
            [promesa.impl :as pi]))

(deftest prom-test
  (testing "resolved + deref"
    (is (= "foo" @(sut/resolved "foo"))))
  (testing "catch not called when all ok"
    (is (= [:val]
          (-> (sut/resolved :val)
              (.then (fn [v] [v]))
              (.catch (fn [_] (throw (js/Error. "never called"))))
              deref))))
  (testing "catch resets control flow"
    (let [signal (atom nil)]
      (is (= :continue (-> (sut/resolved :val)
                           (.then (fn [] (throw (js/Error. "boom"))))
                           (.then (fn [_] (reset! signal "never called")))
                           (.catch (fn [] (js/console.log "catching") :continue))
                           (.then (fn [x] (js/console.log "and then.." x) x))
                           deref)))
      (is (nil? @signal))))
  (testing "starting with rejected"
    (let [signal (atom nil)]
      (is (= :continue
            (-> (sut/rejected (js/Error. "oh no"))
                (.then (fn [_] (reset! signal "never called")))
                (.catch (fn [] (js/console.log "catching") :continue))
                (.then (fn [x] (js/console.log "and then.." x) x))
                deref)))
      (is (nil? @signal))))
  (testing "works with promesa"
    (pi/extend-promise! sut/SyncPromise)
    (is (= [:val]
          (-> (sut/resolved :val)
              (p/then (fn [v] [v]))
              deref)))))
