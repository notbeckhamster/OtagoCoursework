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

static char buf[BLOCK_SIZE] = {0};

void checkerror(int val, char *msg)
{
    if (val < 0)
    {
        perror(msg);
        exit(val);
    }
}

int find_major_num()
{
    // Read major number from /sys/module/memdrv/parameters/major
    char major_buf[10];
    int major_fd = open("/sys/module/memdrv/parameters/major", O_RDONLY);
    int major_res = read(major_fd, major_buf, 10);
    int major_num;
    sscanf(major_buf, "%d", &major_num);
    return major_num;
}

// use this code to shuffle the storage position of data blocks
void shuffle(int8_t *array, int n)
{
    for (int i = 0; i < n - 1; i++)
    {
        int j = i + rand() / (RAND_MAX / (n - i) + 1);
        int8_t t = array[j];
        array[j] = array[i];
        array[i] = t;
    }
}

int main(int argc, char *argv[])
{

    int fd, res, n;

    if ((argc != 2 && argc != 3) || (argc == 3 && strcmp(argv[2], "-r") != 0))
    {
        printf("Usage: %s <file>\n", argv[0]);
        exit(0);
    }

    open_device();
    // open file given as param
    fd = open(argv[1], O_RDONLY);
    checkerror(fd, "open");

    // struct for stat
    struct stat st;
    fstat(fd, &st);

    // restrict size print truncated.. -2 as we dont count indirect and inode
    int size = st.st_size;

    if (size > BLOCK_SIZE * (MAX_BID - 2))
    {
        size = BLOCK_SIZE * (MAX_BID - 2);
        fprintf(stderr, "%s", "truncated\n");
    }

    // Create an fs_inode instance
    Inode inode;
    inode.type = st.st_mode & 0xF000; // consider only the bits for file type correct but should be +ve not -ve
    inode.major = find_major_num();
    inode.minor = 0; //?
    inode.nlink = 1; //? // Set example values for now for minor and nlink
    inode.size = size;
  
    // Create char arr for indirect since we have to write block to indirect at the end
    char indirect_arr[BLOCK_SIZE];

    // the free list should be initialised with values 0 to MAX_BID - 1
    int8_t free_list[MAX_BID];
    for (int i = 0; i < MAX_BID; i++)
    {
        free_list[i] = i;
    }

    //number of blocks needed for file content
     int numberOfBlocks = (int)(size/(double)BLOCK_SIZE);
    //Determine blocks used, file content + indirect block (excludes the inode block as we write it before the loop and dont consider it to be shuffled)
    if (numberOfBlocks>NDIRECT) numberOfBlocks++;

    // if -r option shuffle indexes
    if (argc == 3 && strcmp(argv[2], "-r") == 0)
    {

        // shuffle based on size..
        shuffle(free_list + 1, MAX_BID-1);
    }



    //intialize direct and indirect to zero so debug looks nice 
    for (int i = 0; i<NDIRECT; i++) inode.addrs[i] = 0;
    for (int i = 0; i<BLOCK_SIZE; i++) indirect_arr[i] = 0;

    // Loop through all blocks to exclude inode block
    for (int b = 0; b < MAX_BID-1; b++)
    {
        int idx;

        // Change use idx of free list
        idx = free_list[b+1];

        // continue since we dont want to read the block for indirect
        if (b == NDIRECT)
        {
            inode.addrs[b] = idx;
            continue;
        }

        //clear buffer 
        for (int i = 0; i<BLOCK_SIZE;i++) buf[i] = 0;

        res = read(fd, buf, BLOCK_SIZE);

        // error checking if read -1
        checkerror(res, "read");

        // Direct blocks exlcuding indirect block
        if (b < NDIRECT)
        {
            //set only if there is data 
            if (res>0) inode.addrs[b] = idx;
        }
        else // indirect blocks
        {
            if (res>0) indirect_arr[b - (NDIRECT+1)] = idx;
        }

        write_block(idx, buf);
    }

    // write the indirect adrs in (also overwrites it)
    write_block(inode.addrs[NDIRECT], indirect_arr);

    // Write inode to block 0
    write_block(0, (char *)(&inode));
    fprintf(stderr, "%s", "Success: File written into memdrv\n");

    close_device();

    return EXIT_SUCCESS;
}