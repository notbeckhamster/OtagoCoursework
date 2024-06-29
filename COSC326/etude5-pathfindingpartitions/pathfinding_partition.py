"""Script for Etude 5 Pathfinding Partitions
Author: Beckham Wilson 7564267 wilbe447
"""
import sys
from partition_parser import PartitionParser
from typing import List 
class PathFinder:
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
                new_partition = tuple(partition_list)
                if new_partition != partition:
                    result.add(tuple(partition_list))
        return result
    
    def simple_dijkstra(self, partition: tuple, output_partition: tuple) -> None:
        self.parentMap = {}
        queue = []
        queue.append(partition)
        self.parentMap[partition] = None

        while len(queue) != 0:
            curr_partition = queue.pop(0)
            for neighbor_partition in self.get_all_moves(curr_partition):
                if neighbor_partition not in self.parentMap.keys():
                    self.parentMap[neighbor_partition] = curr_partition
                    queue.append(neighbor_partition)
                    if neighbor_partition == output_partition:
                        queue.clear()
                        break

        
    def get_path(self, endPartition) -> List[tuple]:
        if endPartition not in self.parentMap.keys():
            return []
        path_list = []
        current_partition = endPartition
        while (current_partition != None):
            path_list.append(current_partition)
            current_partition = self.parentMap[current_partition]
        path_list.reverse()
        return path_list


def process(input_contents) -> str:
    partion_parser = PartitionParser(False)
    output_contents = partion_parser.parse(input_contents)
    pathfinder = PathFinder()
    output_pathfinding = ""
    input_partition = ""
    output_partition = ""
    scenarios = output_contents.split("--------\n")
    for each_scenario in scenarios:
        each_scenario = [line for line in each_scenario.split("\n") if line.strip() != ""]
        if len(each_scenario) == 2:
            #Find moves
            input_partition = tuple(int(each_num) for each_num in each_scenario[0].split())
            output_partition =  tuple(int(each_num) for each_num in each_scenario[1].split())
            pathfinder.simple_dijkstra(input_partition, output_partition)
            moves = pathfinder.get_path(output_partition)
            if len(moves) != 0:
                output_pathfinding += f"# Moves required: {len(moves)-1}\n"
                for each_move in moves:
                    each_move_str = ' '.join(str(x) for x in each_move)
                    output_pathfinding += each_move_str + "\n"
            else:
                output_pathfinding+="# No solution possible\n" + each_scenario[0] + "\n" + each_scenario[1] + "\n"
            
        else: 
            output_pathfinding+="# Invalid Scenario, detected more than 2 partitions\n" + f"Partitions Detected: {each_scenario}\n"
        output_pathfinding += "-"*8 + "\n"
    
    return output_pathfinding

if __name__ == "__main__":
    input_contents = sys.stdin.readlines()
    print(process(input_contents))
    
