import pandas as pd

data = pd.read_csv("raw scan.txt", sep="\s+", index_col=False, engine="python", skiprows=20)
data.columns = ["X", "Y", "Z", "Intensity", "R", "G", "B"]

data.to_csv('RawScan.csv')