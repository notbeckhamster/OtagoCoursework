"""Testing for Etude 1 Parsing Partitions
Author: Beckham Wilson 7564267 wilbe447
"""
from partition_parser import PartitionParser
from logger import Logger

def test_i0():
  test_input_filename = "./assets/i0.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=False)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o0.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i1(): 
  test_input_filename = "./assets/i1.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=False)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o1.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i2():
  test_input_filename = "./assets/i2.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=False)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o2.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i3():
  test_input_filename = "./assets/i3.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o3.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i4():
  test_input_filename = "./assets/i4.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o4.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i5():
  test_input_filename = "./assets/i5.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o5.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i6():
  test_input_filename = "./assets/i6.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o6.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i7():
  test_input_filename = "./assets/i7.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o7.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i8():
  test_input_filename = "./assets/i8.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o8.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i9():
  test_input_filename = "./assets/i9.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o9.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents


def test_i10():
  test_input_filename = "./assets/i10.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o10.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i11():
  test_input_filename = "./assets/i11.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o11.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents

def test_i12():
  test_input_filename = "./assets/i12.txt"
  input_file_contents = open(test_input_filename).readlines()
  partition_parser = PartitionParser(is_verbose=True)
  parsed_file_contents = partition_parser.parse(input_file_contents)
  expected_file_contents = open('./assets/o12.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents