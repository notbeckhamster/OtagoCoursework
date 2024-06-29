# Etude4 Quilt
**Author:** Beckham Wilson \
**ID** 7564267 \
**Email** wilbe447@student.otago.ac.nz 

## Requirements
Java SDK 

## Usage
Within Etude4 directory \
**Compile class files**
```
    javac *.java

```

**Run Quilt Program**
```
    java GenQuilt < input.txt
```


## Input
Example input files located under **assets/** folder.

Input file consists of 3 lines with the following \
scale r g b \
scale is a floating point number for size of square \
r g b are integer values between 0-255 for red, green, blue channels 

1st line is central square \
2nd line is squares on central square corners \
3rd line is squares on 2nd line sqauares corners 

**Example**
```
1.0 255 0 0
0.8 0 255 0
0.1 0 0 255
```

## Saving images
Save button in menu bar \
Navigate to directory you want to store image \
Type image file name in File Name field and extension or overwrite a existing image file by clicking file \
Then click save in dialog box \
If no extension specified then set to .jpg extension


## External resources

* N/A All libraries are a part of Java standard library.