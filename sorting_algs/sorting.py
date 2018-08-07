from time import time
from random import random
from math import floor

def insertion_sort(array):
    start_time = time()
    for i in range(1, len(array)):
        if array[i - 1] > array[i]:
            for x in range(i, 0, -1):
                if array[x - 1] > array[x]:
                    array[x], array[x - 1] = array[x - 1], array[x]
                else:
                    break
    return array, time() - start_time


def selection_sort(array):
    start_time = time()
    for i in range(len(array)):
        least = array[i:].index(min(array[i:])) + i
        if least != i:
            array[least], array[i] = array[i], array[least]
    return array, time() - start_time


def bubble_sort(array):
    start_time = time()
    change = True
    while change:
        change = False
        for i in range(len(array) - 1):
            if array[i] > array[i + 1]:
                change = True
                array[i], array[i + 1] = array[i + 1], array[i]
    return array, time() - start_time

def bubble_sort_xd(array):
    start_time = time()
    for _ in range(len(array)):
        for i in range(len(array) - 1):
            if array[i] > array[i + 1]:
                array[i], array[i + 1] = array[i + 1], array[i]
    return array, time() - start_time


def shake_sort(array):
    start_time = time()
    change = True
    while change:
        change = False
        for i in range(len(array) - 1):
            if array[i] > array[i + 1]:
                change = True
                array[i], array[i + 1] = array[i + 1], array[i]

        for i in range(len(array) - 1, 0, -1):
            if array[i] < array[i - 1]:
                change = True
                array[i], array[i - 1] = array[i - 1], array[i]
    return array, time() - start_time


def shell_sort(array):
    start_time = time()
    gap_factor = 2
    gaps = len(array) // gap_factor
    while gaps > 0:
        for starting in range(gaps):  # starting - indeks od ktorego startujemy przeskakwiwac co 'gap'
            for i in range(starting + gaps, len(array), gaps):
                for x in range(i, 0, -gaps):
                    if array[x - 1] > array[x]:
                        array[x], array[x - 1] = array[x - 1], array[x]
                    else:
                        break
        if gaps // gap_factor < 1 and gaps != 1:
            gaps = 1
        elif gaps == 1:
            gaps = 0
        else:
            gaps //= gap_factor
    return array, time() - start_time


def built_in(array):
    start_time = time()
    array = array.sort()
    return array, time() - start_time


def heap_sort(array):
    start_time = time()
    build_heap(array)
    for x in range(len(array) - 1):
        array[-(x + 1)], array[0] = array[0], array[-(x + 1)]
        build_heap(array, -(x + 1))
    return array, time() - start_time


def build_heap(array, lock=0):
    dlugosc = len(array) + lock  # lock ogranicza listę o elementy już posortowane
    half = dlugosc // 2
    for x in range(half, 0, - 1):
        parent = x
        child1 = 2 * parent
        child2 = child1 + 1
        if child2 > dlugosc:
            child2 = child1
        while child2 < dlugosc and array[parent - 1] < array[child1 - 1] or array[parent - 1] < array[child2 - 1]:
            if array[child1 - 1] > array[child2 - 1]:
                bigger = child1
            else:
                bigger = child2
            array[parent - 1], array[bigger - 1] = array[bigger - 1], array[parent - 1]
            if child1 * 2 + 1 <= dlugosc:
                parent = bigger
                child1 = parent * 2
                child2 = child1 + 1


def merge_sort_alg(array):
    if len(array) > 1:
        mid = len(array) // 2
        lefthalf = array[:mid]
        righthalf = array[mid:]

        merge_sort(lefthalf)
        merge_sort(righthalf)

        i = 0 # lewy
        j = 0 # prawy
        k = 0 # obecny
        while i < len(lefthalf) and j < len(righthalf):
            if lefthalf[i] < righthalf[j]:
                array[k] = lefthalf[i]
                i = i + 1
            else:
                array[k] = righthalf[j]
                j = j + 1
            k = k + 1

        while i < len(lefthalf):
            array[k] = lefthalf[i]
            i = i + 1
            k = k + 1

        while j < len(righthalf):
            array[k] = righthalf[j]
            j = j + 1
            k = k + 1
    return array


def merge_sort(array):
    start_time = time()
    merge_sort_alg(array)
    return array, time() - start_time


def quick_sort_alg_left(array):
    if len(array) < 2:
        return array
    pivot = array[0]
    less = [x for x in array[1:] if x <= pivot]
    greater = [x for x in array[1:] if x > pivot]
    return quick_sort_alg_left(less) + [pivot] + quick_sort_alg_left(greater)


def quick_sort_alg_ran(array):
    if len(array) < 2:
        return array
    pivot = array[len(array) // 2]
    less = [x for x in array[1:] if x <= pivot]
    greater = [x for x in array[1:] if x > pivot]
    return quick_sort_alg_ran(less) + [pivot] + quick_sort_alg_ran(greater)


def quick_sort_left(array):
    start_time = time()
    array = quick_sort_alg_left(array)
    return array, time() - start_time


def quick_sort_ran(array):
    start_time = time()
    array = quick_sort_alg_ran(array)
    return array, time() - start_time


def counting_sort(array):
    start_time = time()
    mi, ma = min(array), max(array)
    k = ma - mi + 1
    c = [0 for _ in range(k)]
    b = [0 for _ in range(len(array))]
    for x in array:
        c[x - mi] += 1
    for x in range(1, k):
        c[x] += c[x - 1]
    for x in array:
        b[c[x - mi] - 1] = x
        c[x - mi] -= 1
    return b, time() - start_time


# algorithms = ['insertion_sort', 'selection_sort', 'bubble_sort', 'heap_sort',
#               'quick_sort_left', 'quick_sort_ran', 'counting_sort', 'merge_sort']

algorithms = ['bubble_sort_xd', 'bubble_sort']
t = [100, 250, 400, 600, 800, 900, 1000, 5000]
tests = 1
start = True
if start:
    for elements in t:
        times = [0 for x in range(len(algorithms))]
        for _ in range(tests):
            # test = [1 + floor(random() * elements * 0.01) for _ in range(elements)]
            test = [x for x in range(elements)]
            for index, alg in enumerate(algorithms):
                times[index] += eval(alg + '(list(test))[1]') / tests

        print('For ' + str(elements) + ' elements: \n')
        for index, alg in enumerate(algorithms):
            print(alg + ': ' + "{0:.5f}".format(times[index]))
        print('\n\n\n')