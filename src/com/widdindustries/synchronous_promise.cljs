(ns com.widdindustries.synchronous-promise
  (:require [goog.object]))

(deftype SyncPromise [ok? v]
  ; https://github.com/clojure/clojurescript/wiki/Working-with-Javascript-classes
  IDeref
  (-deref [_] v)
  Object
  (then [_ f]
    (if ok?
      (try (SyncPromise. true (f v))
           (catch js/Error e
             (SyncPromise. false e)))
      (SyncPromise. false v)))
  (catch [_ f]
    (if ok?
      (SyncPromise. true v)
      (SyncPromise. true (f v))))
  (finally [this f]
    (f)
    this))

(defn resolved [v]
  (SyncPromise. true v))

(defn rejected [v]
  (SyncPromise. false v))

;(set! (.-all ^js SyncPromise))
(goog.object/set SyncPromise "all"
  (fn [proms]
    (let [err (some (fn [^js p] (and (not (.-ok? p)) p)) proms)]
      (if err
        (SyncPromise. false @err)
        (SyncPromise. true (into-array (map deref proms)))))))

;(set! (.-resolve ^js SyncPromise) resolved)
;(set! (.-reject ^js SyncPromise) rejected)
(goog.object/set SyncPromise "resolve" resolved)
(goog.object/set SyncPromise "reject" rejected)
