MODULE_NAME = memdrv

obj-m	:= $(MODULE_NAME).o

KDIR	:= /lib/modules/$(shell uname -r)/build
PWD	:= $(shell pwd)

CFLAGS := -W -Wall -pedantic -g

all: module debug store retrieve

module:
	$(MAKE) -C $(KDIR) M=$(PWD) modules

debug: debug.c libmemdrv.c libmemdrv.h
	gcc $(CFLAGS) -o debug debug.c libmemdrv.c

clean:
	$(MAKE) -C $(KDIR) M=$(PWD) clean
	rm -f debug

store: store.c libmemdrv.c libmemdrv.h
	gcc -g -o store-prog store.c libmemdrv.c

retrieve: retrieve.c libmemdrv.c libmemdrv.h
	gcc -g -o retrieve-prog retrieve.c libmemdrv.c -lm