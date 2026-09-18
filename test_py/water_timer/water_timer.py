from win10toast import ToastNotifier
from time import sleep

toast = ToastNotifier()

while True:
    toast.show_toast(
        "Water timer",
        "Go take a break and drink some water!",
        duration = 9999,
        icon_path = "water_bottle.ico",
        threaded = True,
    )
    sleep(60*60*2)