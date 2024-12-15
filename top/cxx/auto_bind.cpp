#include <nvboard.h>
#include "VTop.h"

void nvboard_bind_all_pins(VTop* top) {
	nvboard_bind_pin( &top->io_Y, 2, SW1, SW0);
	nvboard_bind_pin( &top->io_X0, 2, SW3, SW2);
	nvboard_bind_pin( &top->io_X1, 2, SW5, SW4);
	nvboard_bind_pin( &top->io_X2, 2, SW7, SW6);
	nvboard_bind_pin( &top->io_X3, 2, SW9, SW8);
	nvboard_bind_pin( &top->io_F, 2, LD1, LD0);
}
