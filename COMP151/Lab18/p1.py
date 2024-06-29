import pandas as pd

ghg = pd.read_csv("greenhouse-gas-emissions.csv")
print(ghg)
print(ghg['region'].unique())

d = {'col1': [1, 2, 3], 'col2': [3, 4, 5]}
df = pd.DataFrame(d, index=['rowa', 'rowb', 'rowc'])
print(df)

print(df.loc["rowa":"rowc"])

print(ghg[ghg["region"] == "Otago"])