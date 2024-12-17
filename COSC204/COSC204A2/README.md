# COSC 204 Asgn 2 Dev log

Issues:
file type in inode returns correct value but is negative maybe using signed short by default, although all these fields except size are not important for funcitonality
minor device? doesnt exist....
nlink not sure how to track set to 1 for now
-lm appended to gcc cmds when math.h is used as not included with standard C lib?

18/09/2023 - Started writing Inode to memdrv succesfully displayed via debug, Modified Inode to allocate all the available blocks, added truncated size to stderr. (Writing to memdrv via sequentially done just need the random implemented), (end of day) Finished retrieve-prog with random and created retrieve
19/09/2023 - Finished retrieve.c 
25/09/2023 - Removed reading block when setting indirect block
29/09/2023 - Replaces exit(0) when file small no more content to read now it breaks so it still writes inode rather than ending program, Use size for shuffling and for reading in retrieve. Removed unneeded if else loop to select which index the block has
2/10/2023 - Reverted change for size on 29th now shuffling between 1 to MAX_BID, intialized direct and indirect indexes to zero so debug looks nice and more similar to sample, also clear buf before read to prevent previous line data in buf on partial reads. In retrieve use ceil to calc # of blocks so it now reads the last block.
5/10/2023 - if the last block size is not BLOCK_SIZE = 64 then it will now read up the last block size which removes the appended nulls. 