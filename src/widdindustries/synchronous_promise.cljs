(ns widdindustries.synchronous-promise)


(deftype SyncPromise [ok? v]
  ; https://github.com/clojure/clojurescript/wiki/Working-with-Javascript-classes
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
      (SyncPromise. true (f v)))))

(defn resolved [v]
  (SyncPromise. true v))

(defn rejected [v]
  (SyncPromise. false v))

#_(defn resolve [v]
    #js{:then (fn [f]
                (try (resolve (f v))
                     (catch js/Error e
                       (reject e)))
                )
        :catch (fn [_]
                 (resolve v))})

#_(defn reject [ex]
    #js{:catch (fn [f]
                 (resolve (f ex)))
        :then (fn [_] (reject ex))})
