import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import random as rd
import statistics

# arr = []
#
# for i in range(10):
#     arr.append((rd.randrange(0,100) + 1)/100)
#
# print(arr)
#
# sns.histplot(arr,
#              bins=10,
#              stat='density',
#              color='#0b5c7a',
#              edgecolor='black',
#              kde=True,
#              line_kws={'linewidth':3, 'color':'green'})
#
# plt.show()

dmg_val = 1000

dmg_arr = []

for i in range(10000):
    if rd.random() >= 0.5:
        dmg_arr.append(dmg_val*2)
    else:
        dmg_arr.append(dmg_val*0.5)


print(statistics.mean(dmg_arr))

sns.histplot(
    dmg_arr,
    bins=2,
    stat="density",
    kde=True,
    color="#0b5c7a",
    edgecolor="black",
    line_kws={"linewidth": 3}
)

plt.xlabel("Damage")
plt.ylabel("Density")
plt.title("Damage Distribution")
plt.show()