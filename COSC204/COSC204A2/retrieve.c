#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>

#include "libmemdrv.h"
#include "fs.h"
#include <stdint.h>
#include <sys/sysmacros.h>
#include <ctype.h>
#include <math.h>

static char buf[BLOCK_SIZE] = {0};

void checkerror(int val, char *msg)
{
    if (val < 0)
    {
        perror(msg);
        exit(val);
    }
}
void writeOutput(int argc, int fd, int block_idx, int numberOfBlocks, int file_size)
{
    int bytes_writ;
    int size_in_block = BLOCK_SIZE;
    if (block_idx == numberOfBlocks)
    {
        size_in_block = file_size % BLOCK_SIZE;
        if (size_in_block == 0) size_in_block = BLOCK_SIZE;

    }
    if (argc == 1)
    {
        bytes_writ = write(STDOUT_FILENO, buf, size_in_block);
    }
    else
    {
        bytes_writ = write(fd, buf, size_in_block);
    }
    checkerror(bytes_writ, "write");
}

int main(int argc, char *argv[])
{

    int fd, res, n;

    if (argc > 2)
    {
        printf("Usage: %s <file>\n", argv[0]);
        exit(0);
    }

    if (argc != 1)
    {
        if (access(argv[1], F_OK) == 0)
        {
            // file exists
            char c;
            do
            {
                printf("File %s already exists. Do you want to overwrite? [y/n]:\n", argv[1]);
                scanf(" %c", &c);
                c = tolower(c);

                // Consume space after the read character
                while (getchar() != '\n')
                    ;
            } while (c != 'n' && c != 'y');

            if (c == 'n')
                exit(0);

            // File exists O_TRUNC to overwrite it
            fd = open(argv[1], O_TRUNC | O_RDWR, S_IRWXU);
            checkerror(fd, "open");
        }
        else
        {
            // file doesn't exist, create
            fd = open(argv[1], O_CREAT | O_RDWR, S_IRWXU);
            checkerror(fd, "open create");
            printf("%s\n", "File doesnt exist, file created");
        }
    }

    open_device();

    // Read and load inode from block 0
    read_block(0, buf);
    Inode inode = *((Inode *)buf);

    // Find out size of file
    int size = inode.size;
    int numberOfBlocks = ceil((size / (double)BLOCK_SIZE));
    int block_idx = 0;
    // loop throught direct indexes
    for (int index = 0; index < NDIRECT && index < numberOfBlocks; index++)
    {
        block_idx++;
        read_block(inode.addrs[index], buf);
        writeOutput(argc, fd, block_idx, numberOfBlocks, size);
    }

    char indirect[BLOCK_SIZE];
    // Load Indirect block into buf
    read_block(inode.addrs[NDIRECT], indirect);
    // loop through indirect indexes
    for (int index = 0; index < BLOCK_SIZE && index + NDIRECT < numberOfBlocks; index++)
    {
        block_idx++;
        read_block(indirect[index], buf);
        writeOutput(argc, fd, block_idx, numberOfBlocks, size);
    }

    close_device();

    return EXIT_SUCCESS;
}