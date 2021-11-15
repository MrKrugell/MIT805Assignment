import colorsys, csv
import pandas as pd

data = pd.read_csv('RawScan.csv')

for i in range(len(data)):
    r = data['R'].iloc[i]
    g = data['G'].iloc[i]
    b = data['B'].iloc[i]

    if pd.isnull(r) and pd.isnull(g) and pd.isnull(b):
        continue
    elif r==0 and g==0 and b==0:
        continue

    r = r/255
    g = g/255
    b = b/255

    h,l,s = colorsys.rgb_to_hls(r,g,b)

    hue = h*360
    light = l*100
    sat = s*100

    if light < 0.2:
        color = 'black'
    elif light > 0.8:
        color = 'white'

    if sat < 0.25: color = 'gray'

    if hue < 30:
        color = 'red'
    elif hue < 90:
        color = 'yellow'
    elif hue < 150:
        color = 'green'
    elif hue < 210:
        color = 'cyan'
    elif hue < 270:
        color = 'blue'
    elif hue < 330:
        color = 'magenta'

    data.at[i, 'Hue'] = hue
    data.at[i, 'Light'] = light
    data.at[i, 'Sat'] = sat
    data.at[i, 'color'] = color

data.to_csv('RawScanEdit.csv')