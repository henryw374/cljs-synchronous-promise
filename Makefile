test-cljs-shadow:
			clojure -Adev -X com.widdindustries.tiado-cljs2/tests-ci-shadow :compile-mode :release
clean:
			clj -T:build clean
install:
			make clean && clj -T:build jar && clj -T:build install \
			&& mkdir -p tmp && cd tmp
deploy:
			clj -T:build deploy

.PHONY: list
list:
		@$(MAKE) -pRrq -f $(lastword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$' | xargs
