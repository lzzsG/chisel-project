BUILD_DIR = ./build-sv

PRJ = playground

test:
	mill -i $(PRJ).test

run:
	mill -i $(PRJ).runMain top.TopToV --target-dir $(BUILD_DIR)