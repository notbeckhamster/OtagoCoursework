import pandas as pd
def mean_temp(filename, year, start_month, end_month):
    """
    >>> mean_temp('Tara_Hills_temp.csv', 1950, 'FEB', 'JUN')
    8.97
    >>> mean_temp('Tara_Hills_temp.csv', 1973, 'SEP', 'DEC')
    11.5175
    """
    df = pd.read_csv(filename)
    print(df)
    df = df[df["YEAR"] == year]
    df = df.T
    temp = df.loc[start_month:end_month].mean().values
    print(temp[0])
    
    
if __name__=='__main__':
    import doctest
    doctest.testmod(verbose=True)
