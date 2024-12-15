#include <nvboard.h>
#include "VTop.h"

void nvboard_bind_all_pins(VTop* top) {
	nvboard_bind_pin( &top->io_a, 1, SW0);
	nvboard_bind_pin( &top->io_b, 1, SW1);
	nvboard_bind_pin( &top->io_c, 1, LD0);
}
