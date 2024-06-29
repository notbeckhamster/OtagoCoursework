import pandas as pd
def mean_temp(filename, start_year, end_year):
    """
    >>> mean_temp('Tara_Hills_temp.csv', 1950, 1960)
    JAN mean = 15.834
    FEB mean = 15.488
    MAR mean = 13.317999999999998
    APR mean = 9.615
    MAY mean = 5.385
    JUN mean = 2.636
    JUL mean = 1.441
    AUG mean = 3.7211111111111115
    SEP mean = 7.514999999999999
    OCT mean = 9.839
    NOV mean = 12.228
    DEC mean = 14.162
    >>> mean_temp('Tara_Hills_temp.csv', 1990, 1999)
    JAN mean = 16.158571428571427
    FEB mean = 16.067777777777778
    MAR mean = 12.4075
    APR mean = 9.087142857142858
    MAY mean = 6.363333333333333
    JUN mean = 2.9425
    JUL mean = 2.565
    AUG mean = 4.207777777777778
    SEP mean = 6.883333333333334
    OCT mean = 9.867142857142856
    NOV mean = 11.46375
    DEC mean = 14.22625
    """
    df = pd.read_csv(filename)
    filter_year_df = df[(df["YEAR"] >= start_year) & (df["YEAR"] < end_year)]
    print(df)
    print(filter_year_df.mean().loc["JAN":"DEC"])
mean_temp('Tara_Hills_temp.csv', 1990, 1999)    

# if __name__=='__main__':
#     import doctest
#     doctest.testmod(verbose=True)
