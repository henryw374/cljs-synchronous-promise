# Cljs Synchronous Promise 

Handy when you want to keep tests synchronous.

[![Clojars Project](https://img.shields.io/clojars/v/cljs-synchronous-promise.svg)](https://clojars.org/cljs-synchronous-promise)

this is only a partial impl atm. There's [this pure js one](https://github.com/fluffynuts/synchronous-promise#readme) if you need more
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
