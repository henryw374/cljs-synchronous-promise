# Cljs Synchronous Promise 

Handy when you want to keep tests synchronous.

[![Clojars Project](https://img.shields.io/clojars/v/com.widdindustries/cljs-synchronous-promise.svg)](https://clojars.org/com.widdindustries/cljs-synchronous-promise)

[![Tests build](https://github.com/henryw374/cljs-synchronous-promise/actions/workflows/tests.yaml/badge.svg)](https://github.com/henryw374/cljs-synchronous-promise/actions/workflows/tests.yaml)

this is only a partial impl atm. There's [this pure js one](https://github.com/fluffynuts/synchronous-promise#readme) if you need more
## Usage 

Fake the sources of your js/Promises (e.g. http calls) to return sync promises

```
(require '[com.widdindustries.synchronous-promise :as sp])

(-> (sp/resolved :val)
      (.then (fn [v] [v]))
      (.catch (fn [_] (throw (js/Error. "never called"))))
      (.then (fn [x] (js/console.log x) x)))

; make it work with promesa fns
(promesa.impl/extend-promise! sp/SyncPromise) 
(set! promesa.impl/*default-promise* sp/SyncPromise)

```

see the test nss for more examples 

### [license](./LICENSE)

Copyright © 2020 Widd Industries Ltd 
