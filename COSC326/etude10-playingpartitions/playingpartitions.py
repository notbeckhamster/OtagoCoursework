"""Main Class for Etude 10 Playing Partitions
Author: Beckham Wilson 7564267 wilbe447
"""
import sys
from logger import Logger
from parsing_partitions import PartitionParser
import traceback
class PlayingPartitions:


    def __init__(self):
        self.happy_set = set()
        self.sad_set = set()



    def get_all_moves(self, partition: tuple) -> set[tuple]:
        result = set()
        for col_idx in range(0, partition[0]):
            partition_list = list(partition)
            length_of_column = 0
            for row_idx, each_num in enumerate(partition_list):
                if col_idx<each_num:
                    partition_list[row_idx] -= 1
                    length_of_column += 1
                else: 
                    break
            partition_list = [each_num for each_num in partition_list if each_num > 0]
            if length_of_column > 0:
                partition_list.append(length_of_column)
                partition_list.sort(reverse=True)
                result.add(tuple(partition_list))
        return result
    

    def get_all_reverse_moves(self, partition: tuple) -> set[tuple]:
        result = set()
        for row_idx_to_remove in range(0, len(partition)):
            partition_list = list(partition)
            new_col_len = partition_list.pop(row_idx_to_remove)
            curr_row = 0
            while curr_row < new_col_len:
                if curr_row < len(partition_list):
                    partition_list[curr_row] += 1
                else:
                    partition_list.append(1)
                curr_row+=1
            result.add(tuple(partition_list))
        return result
     
    def process_scenario(self, starting_parition: tuple, target_partitions: set[tuple]):
        queue = []
        for target in target_partitions:
            self.sad_set.add(target)
            queue.append(target)
        while len(queue) > 0:
            curr_partition = queue.pop(0)
            if curr_partition in self.happy_set:
                for each_reverse_move in self.get_all_reverse_moves(curr_partition):
                    if (each_reverse_move in self.happy_set or each_reverse_move in self.sad_set):
                        continue
                    #if all forward moves result in happy then this reverse_move sad
                    is_all_happy = True
                    for forward_move in self.get_all_moves(each_reverse_move):
                        if forward_move not in self.happy_set:
                            is_all_happy = False
                            break
                    if is_all_happy:
                        self.sad_set.add(each_reverse_move)
                        queue.append(each_reverse_move)
            elif curr_partition in self.sad_set:
                for each_reverse_move in self.get_all_reverse_moves(curr_partition):
                    if (each_reverse_move in self.happy_set or each_reverse_move in self.sad_set):
                            continue
                    #by definition all reverse_moves of a sad move is a happy move 
                    self.happy_set.add(each_reverse_move)
                    queue.append(each_reverse_move)
        result = self.move_to_string(starting_parition)
        return result 
    
    def move_to_string(self,starting_position) -> str:
        if starting_position in self.happy_set:
            return "# WIN"
        elif starting_position in self.sad_set:
            return "# LOSE"
        else:
            return "# DRAW"
    def process(self, input_text: list[str]) -> str:


        result = ""
        partition_parser = PartitionParser(is_verbose=False)
        is_starting_partition = True        
        starting_partition = None
        target_partitions = []
        for each_line in input_text:

            if partition_parser.is_comment(each_line).is_valid or partition_parser.is_empty_line(each_line).is_valid:
                continue
            elif partition_parser.is_partition(each_line).is_valid:
                each_line = each_line.strip()
                if each_line.isdigit():
                    partition = [int(each_line)]
                else:
                    partition = list(int(num) for num in each_line.split())
                partition = tuple(sorted(partition, reverse=True))
                if is_starting_partition:
                    starting_partition = partition
                    is_starting_partition = False
                else:
                    if partition not in target_partitions:
                        target_partitions.append(partition)
            elif partition_parser.is_separator(each_line).is_valid:
                try:
                    Logger.logger.debug(starting_partition)
                    Logger.logger.debug(target_partitions)
                    if len(target_partitions) == 0:
                        target_partitions.add(starting_partition)
                    state = self.process_scenario(starting_partition, target_partitions)
                    output_starting_partition = " ".join(str(element) for element in starting_partition)
                    output_target_partitions = [" ".join(str(element) for element in target_partition) for target_partition in target_partitions]
                    result += output_starting_partition + "\n\n" + "\n".join(output_target_partitions) + "\n" + state + "\n--------\n"
                    is_starting_partition = True
                    starting_partition = None
                    target_partitions = []
                    self.happy_set = set()
                    self.sad_set = set()

                except Exception as e:
                    print(e)
                    traceback.print_exc()
                    result += "Invalid scenario\n"
        
        if is_starting_partition == False and starting_partition != None:
            if len(target_partitions) == 0:
                target_partitions.add(starting_partition)
            state = self.process_scenario(starting_partition, target_partitions)
            output_starting_partition = " ".join(str(element) for element in starting_partition)
            output_target_partitions = [" ".join(str(element) for element in target_partition) for target_partition in target_partitions]
            result += output_starting_partition + "\n\n" + "\n".join(output_target_partitions) + "\n" + state + "\n--------\n"
        if len(result)>0 and result[-1:] == "\n":
            result = result[:-1]
        if len(result)>3 and result[-9:] == "\n--------":
            result = result[:-9]
        
        return result

            
        


if __name__=="__main__":
    input_contents = sys.stdin.readlines()
    playing_partitions = PlayingPartitions()
    print(playing_partitions.process(input_contents))


    

