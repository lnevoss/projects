import random

uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
lowercase = "abcdefghijklmnopqrstuvwxyz"
digits = "0123456789"
symbols = "^@()[]%^@()[]%^@()[]%"

combined = uppercase+lowercase+digits+symbols
shuffled = list(combined)
random.shuffle(shuffled)

output = ""

for i in range(int(input('password length: '))):
    output += shuffled[random.randrange(0, len(shuffled)-1)]

print(output)