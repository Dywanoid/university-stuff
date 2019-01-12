from sympy import *
import tkinter as tk
import numpy as np
from math import exp as exp_fun
import matplotlib as mpl
import matplotlib.pyplot as plt
# import matplotlib.backends.tkagg as tkagg
from matplotlib.backends.backend_agg import FigureCanvasAgg

# x, y, z = symbols('x y z')
# init_printing(use_unicode=True)
# print(diff(cos(x), x))

DELTA_TIME = 0.01

SPECIFIC_HEAT_OF_WATER = 4.186  # specific heat of water J/g *K

P = 1300  # power of heater
Eta = 0.99  # efficiency of heater
CELSIUS_CONSTANT = 273.15
AMBIENT_TEMPERATURE_IN_CELSIUS = 20
AMBIENT_TEMPERATURE = AMBIENT_TEMPERATURE_IN_CELSIUS + CELSIUS_CONSTANT  # ambient temperature in Kelvin
CONFIGURATION_CONSTANT = 0.000503  # constant of I don't know


class Simulation:
    def __init__(self,
                 heater_power: float,
                 eta: float,
                 ambient_temperature: float,
                 mass_of_water: float,
                 constant: float,
                 water_temp: float,
                 water_temp_after: float):
        self.power = heater_power
        self.eta = eta
        self.ambient_temperature = convert_to_kelvin(ambient_temperature)
        self.mass_of_water = mass_of_water
        self.constant = constant
        self.water_temperature =  convert_to_kelvin(water_temp)
        self.wanted_water_temperature =  convert_to_kelvin(water_temp_after)
        self.time = 0
        self.cooling_start_time = 0
        self.on = True
        self.begin()

    def __str__(self):
        return "XD"

    def begin(self):
        going = True
        arr_x = []
        arr_y = []
        heater_water = 0

        while(going):
            arr_x.append(self.time)
            rising_temperature = temp(self.power,
                                      self.eta,
                                      self.water_temperature,
                                      self.time,
                                      self.mass_of_water)
            arr_y.append(convert_to_celsius(rising_temperature))
            self.time += DELTA_TIME
            if rising_temperature >= self.wanted_water_temperature:
                going = False
                heater_water = rising_temperature

        self.cooling_start_time = self.time
        going = True

        while(going):
            arr_x.append(self.time)
            heater_water = lowering_temp(self.ambient_temperature,
                                         self.constant,
                                         self.time - self.cooling_start_time,
                                         heater_water)
            arr_y.append(convert_to_celsius(heater_water))
            self.time += DELTA_TIME
            if heater_water <= self.ambient_temperature * 1.01:
                going = false

        plt.plot(arr_x, arr_y)
        plt.show()




def temp(heater_power: float, eta: float, temp0: float, time: float, mass_of_water: float) -> float:
    return temp0 + heater_power * eta * time / (mass_of_water * SPECIFIC_HEAT_OF_WATER)


def lowering_temp(ambient_temperature: float, config_constant: float, time: float, temp0: float) -> float:
    return ambient_temperature + (temp0 - ambient_temperature) * exp_fun(-config_constant * time)


def convert_to_celsius(temp_in_kelvin: float) -> float:
    return temp_in_kelvin - CELSIUS_CONSTANT


def convert_to_kelvin(temp_in_celsius: float) -> float:
    return temp_in_celsius + CELSIUS_CONSTANT


# print(convert_to_celsius(temp(25, 250, 1000)))
# print(convert_to_celsius(lowering_temp(3600, 80)))


def start_new_simulation():
    p = P
    eta = Eta
    ambient_temperature = AMBIENT_TEMPERATURE_IN_CELSIUS
    configuration_constant = CONFIGURATION_CONSTANT
    water_mass = 1000
    water_temp = 25
    water_temp_after = 100

    try:
        if textfield_p.get() != "":
            p = float(textfield_p.get())
        else:
            print("Domyślna moc: ", p)

        if textfield_eta.get() != "":
            eta = float(textfield_eta.get())
        else:
            print("Domyślna sprawność: ", eta)

        if textfield_ambient.get() != "":
            ambient_temperature = float(textfield_ambient.get())
        else:
            print("Domyślna temp. otoczenia: ", ambient_temperature)

        if textfield_constant.get() != "":
            configuration_constant = float(textfield_constant.get())
        else:
            print("Domyślna stała układu: ", configuration_constant)

        if textfield_mass_of_water.get() != "":
            water_mass = float(textfield_mass_of_water.get())
        else:
            print("Domyślna ilość wody: ", water_mass)

        if textfield_water_temp_now.get() != "":
            water_temp = float(textfield_water_temp_now.get())
        else:
            print("Domyślna temp. wody: ", water_temp)

        if textfield_water_temp_after.get() != "":
            water_temp_after = float(textfield_water_temp_after.get())
        else:
            print("Domyślna docelowa temp. wody: ", water_temp_after)

    except (ValueError, TypeError):
        print('Złe wartości!')

    simulation = Simulation(p,
                            eta,
                            ambient_temperature,
                            water_mass,
                            configuration_constant,
                            water_temp,
                            water_temp_after)


if __name__ == '__main__':
    window = tk.Tk()
    window.geometry("450x150")
    window.title("Symulacja")
    label = tk.Label(window, text="hello there")

    p_label = tk.Label(window, text="Wprowadź moc grzałki (P): ").grid(row=0, column=0)
    eta_label = tk.Label(window, text="Wprowadź sprawność grzałki (Eta): ").grid(row=1, column=0)
    ambient_label = tk.Label(window, text="Wprowadź temperaturę otoczenia (*C): ").grid(row=2, column=0)
    constant_label = tk.Label(window, text="Wprowadź stałą układu: ").grid(row=3, column=0)
    mass_of_water_label = tk.Label(window, text="Wprowadź ilość wody (ml): ").grid(row=4, column=0)
    water_temp_now_label = tk.Label(window, text="Wprowadź temperaturę wody (*C): ").grid(row=5, column=0)
    water_temp_after_label = tk.Label(window, text="Wprowadź oczekiwaną temperaturę wody (*C): ").grid(row=6, column=0)

    textfield_p = tk.Entry(window)
    textfield_p.grid(row=0, column=1)

    textfield_eta = tk.Entry(window)
    textfield_eta.grid(row=1, column=1)

    textfield_ambient = tk.Entry(window)
    textfield_ambient.grid(row=2, column=1)

    textfield_constant = tk.Entry(window)
    textfield_constant.grid(row=3, column=1)

    textfield_mass_of_water = tk.Entry(window)
    textfield_mass_of_water.grid(row=4, column=1)

    textfield_water_temp_now = tk.Entry(window)
    textfield_water_temp_now.grid(row=5, column=1)

    textfield_water_temp_after = tk.Entry(window)
    textfield_water_temp_after.grid(row=6, column=1)

    button = tk.Button(window, text="OK!", command=start_new_simulation).grid(padx=(20, 20), row=2, column=2, rowspan=2)
    window.mainloop()
