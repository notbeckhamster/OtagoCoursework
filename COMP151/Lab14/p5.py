v1 = 1e9
v2 = 1e-6
temp = 0
for i in range(int(1e6)):
    temp += v2
v1= v1+temp
print('answer is', v1 - 1e9)