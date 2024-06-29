"""Testing for Etude 10 Playing Partitions
Author: Beckham Wilson 7564267 wilbe447
"""
from playingpartitions import PlayingPartitions
from logger import Logger

def test_i0():
  return run_test("0")

def test_i1():
  return run_test("1")


def test_i2():
  return run_test("2")


def test_i3():
  return run_test("3")


def test_i4():
  return run_test("4")


def test_i5():
  return run_test("5")


def test_i6():
  return run_test("6")


def test_i7():
  return run_test("7")


def test_i8():
  return run_test("8")

def test_i9():
  return run_test("9")

def test_i10():
  return run_test("10")

def run_test(file_num):
  test_input_filename = f"./assets/i{file_num}.txt"
  input_file_contents = open(test_input_filename).readlines()
  playing_partitions = PlayingPartitions()
  parsed_file_contents = playing_partitions.process(input_file_contents)
  expected_file_contents = open(f'./assets/o{file_num}.txt').read()
  Logger.logger.debug(f'Input file contents\n{input_file_contents}')
  Logger.logger.debug(f'Output file contents\n{parsed_file_contents}')
  Logger.logger.debug(f'Expected output file contents\n{expected_file_contents}')
  assert parsed_file_contents == expected_file_contents


