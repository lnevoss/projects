import random

xpperlevel = 1000 
levels = 30
winrate = 0.6
xpgainwin = 650
xpgainlose = 250

xpreq = xpperlevel * levels
xpcur = 0

iterations = 0
iterationstotal = 0

print('enter # of simulated attempts:')
simulations = int(input())
for x in range(simulations):
    xpcur = 0
    iterations = 0
    while xpcur < xpreq:
        if random.random() <= winrate:
            xpcur += xpgainwin
        else:
            xpcur += xpgainlose
        iterations+=1
    print(f'iterations: {iterations}')
    iterationstotal+=iterations

print(f'average final: {iterationstotal/simulations}')