from sympy import *
import tkinter
from math import exp as exp_fun
import matplotlib.pyplot as plt


x, y, z = symbols('x y z')
init_printing(use_unicode=True)
print(diff(cos(x), x))


SPECIFIC_HEAT_OF_WATER = 4.186  # specific heat of water J/g *K


P = 1300  # power of heater
Eta = 0.99  # efficiency of heater
CELSIUS_CONSTANT = 273.15
AMBIENT_TEMPERATURE_IN_CELSIUS = 20
AMBIENT_TEMPERATURE = AMBIENT_TEMPERATURE_IN_CELSIUS + CELSIUS_CONSTANT  # ambient temperature in Kelvin
CONFIGURATION_CONSTANT = 0.000503  # constant of I don't know


def temp(time0: float, time: float, mass_of_water: float) -> float:
    return time0 + P * Eta * time / (mass_of_water * SPECIFIC_HEAT_OF_WATER)


def lowering_temp(time: float, temp0: float) -> float:
    temp0_in_kelvin = convert_to_kelvin(temp0)
    return AMBIENT_TEMPERATURE + (temp0_in_kelvin - AMBIENT_TEMPERATURE) * exp_fun(-CONFIGURATION_CONSTANT * time)


def convert_to_celsius(temp_in_kelvin: float) -> float:
    return temp_in_kelvin - CELSIUS_CONSTANT

def convert_to_kelvin(temp_in_celsius: float) -> float:
    return temp_in_celsius + CELSIUS_CONSTANT


print(convert_to_celsius(temp(25, 250, 1000)))
print(convert_to_celsius(lowering_temp(3600, 80)))


if __name__ == '__main__':
    root = tkinter.Tk()
    label = tkinter.Label(root, text="hello there")
    label.pack()

    root.mainloop()