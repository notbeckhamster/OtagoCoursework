import csv
csvfile = open("movie_avg_ratings.csv", "r")
csvreader = csv.reader(csvfile)
for row in csvreader:
    emoji = ""
    rating = float(row[1])
    if rating < 2:
        emoji = "\U0001F4A9"
    elif rating < 3.5:
        emoji = "\U0001F633"
    else:
        emoji= "\U0001F920"
    print(f'Movie name {row[0]}, Rating: {row[1]} {emoji}')