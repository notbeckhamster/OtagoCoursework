"""Script for Etude 1 Parsing Partitions
Author: Beckham Wilson 7564267 wilbe447
"""

import sys
import re
from typing import List, Optional

class ValidationResult:
    def __init__(self, is_valid: bool, error_message: str):
        self.is_valid = is_valid
        self.error_message = error_message

class PartitionParser:
    

    def __init__(self, is_verbose: bool):
        self.is_verbose = is_verbose
        self.is_empty_scenario = True
    
    def is_leading_zeros(self, number: str):
        if (len(number)>0 and number[0] == "0"):
            return True
        else: 
            return False
        
    def is_sequence_positive_ints(self, sequence: List[str]) -> ValidationResult:
        """
        Checks if the provided sequence contains only positive integers.

        Args:
            sequence (List[str]): The list of strings to be checked for containing positive integers.

        Returns:
            ValidationResult: A ValidationResult indicating whether the sequence is valid with positive integers.
        """
        if all(num.isdigit() and int(num) > 0 and self.is_leading_zeros(num) == False for num in sequence):
            return ValidationResult(is_valid=True, error_message=None)
        elif (any(self.is_leading_zeros(num) == True for num in sequence)):
            return ValidationResult(is_valid=False, error_message="Suggestion: For partitions numbers cannot contain leading zeros.")
        elif (any(num.isdigit() == False for num in sequence)):
            non_number_terms = [term for term in sequence if term.isdigit() == False]
            return ValidationResult(is_valid=False, error_message="Suggestion: For partitions must contain only numbers, detected non number terms:" + " ".join(non_number_terms))
        else:
            return ValidationResult(is_valid=False, error_message="Suggestion: For partitions use only positive integers")
    def get_separator_list(self, line:str) -> str:
        separator_list = re.split(r"\d+", line)
        #Remove first and last index as it might be empty 
        if separator_list[0] == "": 
            separator_list.pop(0)
        if separator_list[len(separator_list)-1] == "":
            separator_list.pop(len(separator_list)-1)
        return separator_list


    def contains_whitespace(self, line: str) -> bool:
        return any(char.isspace() for char in line)
    
    def compress_blank_lines(self, input_lines: list) -> str:
        last_line_empty = False
        output_lines = []
        for each_line in input_lines:
            if self.is_empty_line(each_line).is_valid == True:
                if (last_line_empty == False):
                    last_line_empty = True  
                    output_lines.append(each_line)
                #Don't append if last_line_was empty
            else:
                last_line_empty = False
                output_lines.append(each_line)

        return output_lines

    def count_numbers(self, input_string: str) -> int:
        numbers = re.findall(r'\d+', input_string)
        return len(numbers)
    
    def is_comment(self, line: str) -> Optional[ValidationResult]:
        """
        Checks if the line is a comment, where the first character must be '#'.

        Args:
            line (str): The line to be checked for being a comment.

        Returns:
            Optional[ValidationResult]: A ValidationResult indicating whether the line is a valid comment and error error message (if required).
        """
        if len(line) > 0 and line[0] == "#":
            return ValidationResult(is_valid=True, error_message=None)
        elif "#" in line:
            return ValidationResult(is_valid=False, error_message="Suggestion: The first character of a comment line must begin with #.")
        elif (re.findall(r'[^\w\s,-]',line)):
            special_characters_arr = re.findall(r'[^\w\s,-]',line)
            return ValidationResult(is_valid=False, error_message=f"Suggestion: Detected special characters {', '.join(special_characters_arr)}. Use # for comments and , for partitions.")
        return ValidationResult(is_valid=None, error_message=None)
        
    def is_separator(self, line: str) -> Optional[ValidationResult]:
        """
        Checks if the line is a separator line, which should contain only a sequence of at least 3 hyphens.

        Args:
            line (str): The line to be checked for being a separator.

        Returns:
            Optional[ValidationResult]: A ValidationResult indicating whether the line is a valid separator and a error message (if required).
        """
        if re.fullmatch(r"-{3,}\s*", line):
            return ValidationResult(is_valid=True, error_message=None)
        elif "---" in line and re.search(r'[^\s-]', line):
            return ValidationResult(is_valid=False, error_message="Suggestion: Detected non-hyphen characters; separator lines must contain (only) hyphens.")
        elif "-" in line and re.search(r"\d+", line) == None:
            return ValidationResult(is_valid=False, error_message="Suggestion: Separator lines must contain at least 3 hyphens in sequence.")
        return ValidationResult(is_valid=None, error_message=None)
        
    def is_empty_line(self, line: str) -> Optional[ValidationResult]:
        """
        Checks if the line is empty, containing no characters or just whitespace.

        Args:
            line (str): The line to be checked for being an empty line.

        Returns:
            Optional[ValidationResult]: A ValidationResult indicating whether the line is empty and a error message (if required).
        """
        #Since we split on \n a empty string would of been a \n char
        if line.isspace() or len(line)==0:
            return ValidationResult(is_valid=True, error_message=None)
        return ValidationResult(is_valid=None, error_message=None)
                    
    def is_partition(self, line: str) -> ValidationResult:
        """Checks if line is a partition ensure it is sequence of positive integers separated by commas or by whitespace (but not both). 

        Args:
            line (str): The line to be checked for partition validity.

        Returns:
            ValidationResult: Contains if line was valid partition and error message (if required).
        """
        line = line.strip()
        count_of_numbers = self.count_numbers(line)
        if count_of_numbers == 0: 
            return ValidationResult(is_valid=False, error_message="Suggestion: No numbers detected; partition must contain a sequence of numbers.")
        elif re.search(r"-\d", line):
            return ValidationResult(is_valid=False, error_message="Suggestion: Detected negative numbers, use only positive numbers in partitions")
        elif line.isdigit(): # 1 number sequence
            return ValidationResult(is_valid=True, error_message=None)
        elif "," in line and self.contains_whitespace(line):
            separator_list = self.get_separator_list(line)
            for each_sep in separator_list:
                if "," not in each_sep:
                    return ValidationResult(is_valid=False, error_message="Suggestion: Use either a comma or whitespace as delimiters, not both.")
                if not re.fullmatch(r"\s*,?\s*", each_sep ):
                    return ValidationResult(is_valid=False, error_message="Suggestion: Invalid separator: " + each_sep)
            
            partition_sequence = re.split(r"\s*,\s*", line)
            return self.is_sequence_positive_ints(partition_sequence)
        elif "," in line:
            partition_sequence = line.split(",")
            if len(partition_sequence) == count_of_numbers:
                separator_list = self.get_separator_list(line)
                for each_sep in separator_list:
                    if "," not in each_sep:
                        return ValidationResult(is_valid=False, error_message="Suggestion: Missing comma separator.")
                    if not re.fullmatch(r",?", each_sep ):
                        return ValidationResult(is_valid=False, error_message="Suggestion: Invalid separator: " + each_sep)
                return self.is_sequence_positive_ints(partition_sequence)
            else:
                return ValidationResult(is_valid=False, error_message=f"Suggestion: Expected {len(partition_sequence)} numbers. Got {count_of_numbers}.")
            
            
        elif self.contains_whitespace(line):
            partition_sequence = line.split()
            if len(partition_sequence) == count_of_numbers:
                separator_list = self.get_separator_list(line)
                for each_sep in separator_list:
                    if not re.fullmatch(r"\s+", each_sep ):
                        return ValidationResult(is_valid=False, error_message="Suggestion: Invalid separator: " + each_sep)
                return self.is_sequence_positive_ints(partition_sequence)
            else:
                return ValidationResult(is_valid=False, error_message=f"Suggestion: Expected {len(partition_sequence)} numbers. Got {count_of_numbers}.")
        else:
            return ValidationResult(is_valid=False, error_message="Suggestion: Unknown error, probably no valid sequence delimiters.")  
  
 
    def append_invalid_line(self, arr_to_append: List[str], line: str, validation_result_obj: ValidationResult) -> None:
        """Appends invalid basic or verbose message to arr_to_append

        Args:
            arr_to_append (List[str]): Array which invalid message will be append to
            line (str): the invalid line
            validation_result_obj (ValidationResult): contains if line is invalid and the error message 
        """
        self.is_empty_scenario = False
        if self.is_verbose:
            arr_to_append.append(f"# INVALID: {validation_result_obj.error_message} Line: {line}")
        else:
            arr_to_append.append(f"# INVALID: {line}")
    
    def is_separator_in_last_lines(self, output_lines_arr: List[str]):
        return output_lines_arr[len(output_lines_arr)-1] == "--------\n" or (len(output_lines_arr) > 2 and output_lines_arr[len(output_lines_arr)-1] == "\n" and output_lines_arr[len(output_lines_arr)-2] == "--------\n")
    
    def parse(self, input_lines: List[str]) -> str:
        """Parse line by line input_contents to output standardised version 

        Args:
            input_lines (List): input file lines 

        Returns:
            str: standardised version of the input file contents
        """
        input_lines = self.compress_blank_lines(input_lines)
        output_lines_arr = []
        last_separator_idx=0
        is_valid_scenario = False 
        for line in input_lines:
            try:

                
                empty_line_validation_result = self.is_empty_line(line)
                if empty_line_validation_result.is_valid:
                    # Warning: append "" since we will join array on \n
                    output_lines_arr.append("\n")
                    continue
                elif empty_line_validation_result.is_valid == False:
                    self.append_invalid_line(output_lines_arr,line,empty_line_validation_result)
                    continue
                
                comment_validation_result = self.is_comment(line)
                if comment_validation_result.is_valid:
                    output_lines_arr.append(line)
                    self.is_empty_scenario = False
                    continue
                elif comment_validation_result.is_valid == False:
                    self.append_invalid_line(output_lines_arr,line,comment_validation_result)
                    continue

                separator_validation_result = self.is_separator(line)
                if separator_validation_result.is_valid:
                    if self.is_empty_scenario == True:
                        output_lines_arr = output_lines_arr[:last_separator_idx] 
                    elif is_valid_scenario == False:
                        if last_separator_idx ==0 and len(output_lines_arr) > 0 and output_lines_arr[0] != "--------\n":
                            last_separator_idx-=1
                        output_lines_arr.insert(last_separator_idx+1, "# INVALID SCENARIO\n")
                    output_lines_arr.append("--------\n")
                    last_separator_idx = len(output_lines_arr)-1
                    is_valid_scenario = False 
                    self.is_empty_scenario = True
                    continue
                elif separator_validation_result.is_valid == False:
                    self.append_invalid_line(output_lines_arr,line,separator_validation_result)
                    continue
                
                partition_validation_result = self.is_partition(line)
                if partition_validation_result.is_valid:
                    line = line.strip()
                    is_valid_scenario = True
                    if line.isdigit(): # 1 number sequence
                        partition_sequence = [line]
                    elif "," in line and self.contains_whitespace(line):
                        partition_sequence = partition_sequence = re.split(r"\s*,\s*", line)
                    elif self.contains_whitespace(line):
                        partition_sequence = line.split()
                    elif "," in line:
                        partition_sequence = line.split(",")
                    self.is_empty_scenario = False
                    partition_sequence.sort(reverse=True)
                    output_lines_arr.append(" ".join(partition_sequence) + "\n")
                    continue
                else:
                    self.append_invalid_line(output_lines_arr,line,partition_validation_result)
            except Exception:
                self.append_invalid_line(output_lines_arr, line, ValidationResult(False, f"Unexpected error, exception raised."))
        #Add new line to last line if it doesn't contain \n]
        is_last_line_missing_newline = len(output_lines_arr) > 0 and len(output_lines_arr[len(output_lines_arr)-1]) > 1 and output_lines_arr[len(output_lines_arr)-1][-1:] != "\n"
        if is_last_line_missing_newline:
            output_lines_arr[len(output_lines_arr)-1] = output_lines_arr[len(output_lines_arr)-1] + "\n"
        #Trigger check for empty scenario since we might not have a separator at the end
        if (len(output_lines_arr) > 0 and output_lines_arr[len(output_lines_arr)-1] != "--------\n") and self.is_empty_scenario == True:
            output_lines_arr = output_lines_arr[:last_separator_idx] 
        #If separator not present at the end then we must check for invalid scenario
        if len(output_lines_arr) > 0 and self.is_separator_in_last_lines(output_lines_arr) == False and is_valid_scenario == False and self.is_empty_scenario == False:
            if last_separator_idx == 0:
                last_separator_idx-=1
            output_lines_arr.insert(last_separator_idx+1, "# INVALID SCENARIO\n")
        #Standard output cannot contain separators at the start or end
        if (len(output_lines_arr) > 0 and output_lines_arr[0] == "--------\n"):
            output_lines_arr.pop(0)    
        if (len(output_lines_arr) > 0 and output_lines_arr[len(output_lines_arr)-1] == "--------\n"):
            output_lines_arr.pop()
        elif (len(output_lines_arr) > 2 and output_lines_arr[len(output_lines_arr)-1] == "\n" and output_lines_arr[len(output_lines_arr)-2] == "--------\n"):
            output_lines_arr.pop(len(output_lines_arr)-2)
        #Recompress blank lines as we are doing alot of modification to the original input 
        output_lines_arr = self.compress_blank_lines(output_lines_arr)
        parsed_str = "".join(output_lines_arr)
        return parsed_str


if __name__ == "__main__":
    is_verbose = "-v" in sys.argv
    input_contents = sys.stdin.readlines()
    partion_parser = PartitionParser(is_verbose)
    output_contents = partion_parser.parse(input_contents)
    print(output_contents)


