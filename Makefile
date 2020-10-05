all: compile

.PHONY: run test

compile:
	clj -A:native-image

run:
	clj -M -m tiler.core

test:
	clj -A:test -M -m tiler.test
