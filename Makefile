BUILD_DIR = ./top/sv_gen
PRJ = top

# 仿真器和顶层模块配置
SIM ?= verilator                 # 使用 Verilator 作为仿真器
TOPLEVEL ?= Top					 # 顶层模块名（你的 Verilog 模块名称）

RTL_DIR := build_sv
CXX_DIR := src/cxx
WAVE_DIR := wave

VERILOG_SOURCES = $(wildcard $(RTL_DIR)/*.sv)

ifeq ($(VERILATOR_ROOT),)
  VERILATOR = verilator
else
  export VERILATOR_ROOT
  VERILATOR = $(VERILATOR_ROOT)/bin/verilator
endif


# NVBoard 相关配置
ifeq ($(NVBOARD_HOME),)
  $(error NVBOARD_HOME is not set. Please export NVBOARD_HOME to your environment.)
endif
include $(NVBOARD_HOME)/scripts/nvboard.mk
NVBOARD_SCRIPTS := $(NVBOARD_HOME)/scripts
NVBOARD_PIN_BIND_SCRIPT := $(NVBOARD_SCRIPTS)/auto_pin_bind.py
NVBOARD_INC := $(NVBOARD_HOME)/include
CXXFLAGS += -I$(NVBOARD_USR_INC) -I$(NVBOARD_INC) $(shell sdl2-config --cflags) -D_REENTRANT -DNVBOARD
LDFLAGS += $(NVBOARD_ARCHIVE) $(shell sdl2-config --libs) -lSDL2_image -lSDL2_ttf
ifdef COMB
CXXFLAGS += -DCOMB
endif

.PHONY: mrun mtest config clean-all build wave dir nvb nvboard-bind vhelp

dir:
	mkdir -p $(WAVE_DIR) obj_dir 

clean: clean
	rm -rf $(SIM_BUILD)
	rm -rf obj_dir
	rm -rf $(BUILD_DIR)
	rm -rf dump.vcd

build: dir mrun
	@echo "Building project..."
	$(VERILATOR) -cc --exe --build \
		--top-module $(TOPLEVEL) \
		$(VERILOG_SOURCES) $(CXX_DIR)/main.cpp\
		-CFLAGS "-std=c++11 -I./src-cxx" \
		-Mdir obj_dir
	@echo "Build complete."
	@echo "-- RUN --------"
	./obj_dir/V$(TOPLEVEL)
	@echo "-- DONE --------------------"

wave: dir mrun
	@echo "Building project..."
	$(VERILATOR) -cc --exe --trace --build \
		--top-module $(TOPLEVEL) \
		$(VERILOG_SOURCES) $(CXX_DIR)/main.cpp\
		-CFLAGS "-std=c++11 -I./src-cxx -DTRACE" \
		-Mdir obj_dir
	@echo "Build complete (with waveform)."
	@echo "-- RUN with tracing --------"
	./obj_dir/V$(TOPLEVEL)
	@echo "-- DONE --------------------"
	gtkwave wave/dump.vcd wave/gtks.gtkw

nvboard-bind:
	@echo "Generating NVBoard pin bindings..."
	python3 $(NVBOARD_PIN_BIND_SCRIPT) src/rtl/pins.nxdc $(CXX_DIR)/auto_bind.cpp
	@echo "Pin bindings generated."

nvb: nvboard-bind mrun
	@echo "Building NVBoard project..."
	$(VERILATOR) -cc --exe --build \
		--top-module $(TOPLEVEL) \
		$(VERILOG_SOURCES) $(CXX_DIR)/main.cpp top/auto_bind.cpp \
		--CFLAGS "$(CXXFLAGS)" \
		--LDFLAGS "$(LDFLAGS)" \
		-Mdir obj_dir
	@echo "NVBoard build complete."
	@echo "-- RUN NVBoard simulation --------"
	./obj_dir/V$(TOPLEVEL)
	@echo "-- DONE --------------------"



mtest:
	mill -i $(PRJ).test

mrun:
	mill -i $(PRJ).runMain top.TopToV --target-dir $(BUILD_DIR)