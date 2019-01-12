from sympy import *
import tkinter as tk
from math import exp as exp_fun
from matplotlib.backends.backend_tkagg import (
    FigureCanvasTkAgg, NavigationToolbar2Tk)
from matplotlib.backend_bases import key_press_handler
from matplotlib.figure import Figure
from random import random
import math

DELTA_TIME = 1

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
        self.water_temperature = convert_to_kelvin(water_temp)
        self.wanted_water_temperature = convert_to_kelvin(water_temp_after)
        self.time = 0
        self.on = True
        self.begin()

    def __str__(self) -> str:
        return f'\nSymulacja: \nMoc grzałki: {self.power}W\nSprawność: {self.eta}\n' + \
               f'Temp. otoczenia: {convert_to_celsius(self.ambient_temperature)}*C\n' + \
               f'Ilość wody: {self.mass_of_water}ml\nStała: {self.constant} 1/s\n'+\
               f'Temp. wody: {convert_to_celsius(self.water_temperature)}*C\n' + \
               f'Oczekiwana temp. wody: {convert_to_celsius(self.wanted_water_temperature)}*C\n\n'

    def begin(self):
        print(self)
        going = True
        arr_x = []
        arr_y = []

        # ambient will be +10 or -10 from given ambient
        diff_ambient = self.ambient_temperature + (math.floor(random() * 2) * 20 - 10)
        arr_y_diff_ambient = []

        # constant will be +0.0001 or -0.0001 from given constant
        diff_constant = self.constant + (math.floor(random() * 2) * 0.0002 - 0.0001)
        arr_y_diff_constant = []

        heated_water = 0

        while going:
            arr_x.append(self.time)
            rising_temperature = temp(self.power,
                                      self.eta,
                                      self.water_temperature,
                                      self.time,
                                      self.mass_of_water)

            rising_temperature_diff_ambient = temp(self.power,
                                                   self.eta,
                                                   self.water_temperature,
                                                   self.time,
                                                   self.mass_of_water)

            rising_temperature_diff_const = temp(self.power,
                                                 self.eta,
                                                 self.water_temperature,
                                                 self.time,
                                                 self.mass_of_water)

            arr_y.append(convert_to_celsius(rising_temperature))
            arr_y_diff_ambient.append(convert_to_celsius(rising_temperature_diff_ambient))
            arr_y_diff_constant.append(convert_to_celsius(rising_temperature_diff_const))

            self.time += DELTA_TIME
            if rising_temperature >= self.wanted_water_temperature or self.time >= 3600:
                going = False
                heated_water = rising_temperature

        cooling_start_time = self.time
        going = True

        while going:
            arr_x.append(self.time)
            lowering_temperature = lowering_temp(self.ambient_temperature,
                                                 self.constant,
                                                 self.time - cooling_start_time,
                                                 heated_water)

            lowering_temperature_diff_ambient = lowering_temp(diff_ambient,
                                                              self.constant,
                                                              self.time - cooling_start_time,
                                                              heated_water)

            lowering_temperature_diff_constant = lowering_temp(self.ambient_temperature,
                                                               diff_constant,
                                                               self.time - cooling_start_time,
                                                               heated_water)
            arr_y.append(convert_to_celsius(lowering_temperature))
            arr_y_diff_ambient.append(convert_to_celsius(lowering_temperature_diff_ambient))
            arr_y_diff_constant.append(convert_to_celsius(lowering_temperature_diff_constant))
            self.time += DELTA_TIME
            if lowering_temperature <= self.ambient_temperature * 1.01 or self.time >= 3600:
                going = false

        second_window = tk.Tk()
        second_window.wm_title("Symulacja - wykres")

        fig = Figure(figsize=(6, 6), dpi=100)
        fig.suptitle("Wykres zmiany temperatury w czasie", fontsize=12, fontweight='bold')
        my_plot = fig.add_subplot(111)
        my_plot.plot(arr_x, arr_y, "g", arr_x, arr_y_diff_ambient, "r", arr_x, arr_y_diff_constant, "b")

        my_plot.set_xlabel("Czas [s]")
        my_plot.set_ylabel("Temperatura [*C]")
        my_plot.legend(('Dane parametry',
                        f'Temp otoczenia: {convert_to_celsius(diff_ambient)}',
                        f'Stała układu: {diff_constant}'), loc="upper right")

        canvas = FigureCanvasTkAgg(fig, master=second_window)

        canvas.draw()
        canvas.get_tk_widget().pack(side=tk.TOP, fill=tk.BOTH, expand=1)

        toolbar = NavigationToolbar2Tk(canvas, second_window)
        toolbar.update()
        canvas.get_tk_widget().pack(side=tk.TOP, fill=tk.BOTH, expand=1)

        def on_key_press(event):
            # print("you pressed {}".format(event.key))
            key_press_handler(event, canvas, toolbar)

        canvas.mpl_connect("key_press_event", on_key_press)

        second_window.mainloop()


def temp(heater_power: float, eta: float, temp0: float, time: float, mass_of_water: float) -> float:
    return temp0 + heater_power * eta * time / (mass_of_water * SPECIFIC_HEAT_OF_WATER)


def lowering_temp(ambient_temperature: float, config_constant: float, time: float, temp0: float) -> float:
    return ambient_temperature + (temp0 - ambient_temperature) * exp_fun(-config_constant * time)


def convert_to_celsius(temp_in_kelvin: float) -> float:
    return temp_in_kelvin - CELSIUS_CONSTANT


def convert_to_kelvin(temp_in_celsius: float) -> float:
    return temp_in_celsius + CELSIUS_CONSTANT


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

    Simulation(p,
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
