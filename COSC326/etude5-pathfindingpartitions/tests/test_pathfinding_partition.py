"""Testing for Etude 5 Pathfinding Partitions
Author: Beckham Wilson 7564267 wilbe447
"""
from pathfinding_partition import process
from logger import Logger

def test_i0():
  test_input_filename = "./assets/i0.txt"
  input_file_contents = open(test_input_filename).readlines()
  parsed_file_contents = process(input_file_contents)
  expected_file_contents = open('./assets/o0.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents