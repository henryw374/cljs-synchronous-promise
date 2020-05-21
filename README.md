# Cljs Synchronous Promise 

Handy when you want to keep tests synchronous.

## Usage 

Fake the sources of your js/Promises (e.g. http calls) to return sync promises

```
(require '[widdindustries.synchronous-promise :as sp])

(-> (sp/resolved :val)
      (.then (fn [v] [v]))
      (.catch (fn [_] (throw (js/Error. "never called"))))
      (.then (fn [x] (js/console.log x) x)))

; make it work with promesa fns
(promesa.impl/extend-promise! sp/SyncPromise) 

```

see the test nss for more examples 


U+00A9 2020 Widd Industries Ltd 
