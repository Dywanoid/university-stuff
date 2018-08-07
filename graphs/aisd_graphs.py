from random import seed
from random import choices
from itertools import product
from time import time
from sys import setrecursionlimit
seed(1)
setrecursionlimit(10000)


class Vortex:
    def __init__(self, number: int):
        self.number = number
        self.next = None

    def __str__(self):
        return "Wierzchołek: " + str(self.number)

    def __repr__(self):
        return str(self.number)


def generowanie_grafu(vortex_count: int, d: float) -> list:
    t = [i + 1 for i in range(vortex_count)]
    return choices([x for x in product(t, t)], k=int(vortex_count * vortex_count * d))


def generowanie_listy_nastepnikow(graph: list, n: int) -> list:
    lista = [Vortex(x) for x in range(1, n + 1)]
    for arc in graph:
        obecny = lista[arc[0] - 1]
        nastepnik = lista[arc[1] - 1]

        if obecny.next is None:
            obecny.next = Vortex(nastepnik.number)
        else:
            temp = obecny.next
            while True:
                if temp.next is None:
                    temp.next = Vortex(nastepnik.number)
                    break
                else:
                    temp = temp.next
    return lista


def generowanie_macierzy_sasiedztwa(graph: list, n: int) -> list:
    macierz = [[0 for _ in range(n)] for _ in range(n)]
    for arc in graph:
        macierz[arc[0]-1][arc[1]-1] = 1
    return macierz


def generowanie_listy_lukow(graph: list) -> list:
    return list(graph)


def topologiczne_nastepnikow(graph: list) -> list:
    dfs = []
    dfs_nastepnikow(graph, dfs)
    return dfs


def topologiczne_sasiedztwa(macierz: list) -> (list, list, list, float):
    d = [-1 for _ in range(len(macierz))]
    f = [-1 for _ in range(len(macierz))]
    dfs = []
    i = [1]
    start_time = time()
    for x in range(1, len(macierz)):
        dfs_sasiedztwa(macierz, dfs, x, i, d, f)
    return dfs, d, f, time() - start_time


def topologiczne_lukow(n: int, lista: list) -> list:
    dfs = []
    for x in range(1, n):
        dfs_lukow(lista, n, dfs, x)
    return dfs


def dfs_nastepnikow(graph: list, visited: list, sprawdzany: int = 0, stos: list = list()):
    if sprawdzany < len(graph):
        v = graph[sprawdzany]
        if v not in visited:
            visited.append(v)
            stos.append(v)
            if v.next is None:
                if len(stos):
                    dfs_nastepnikow(graph, visited, stos[-1].number - 1, stos)
                else:
                    dfs_nastepnikow(graph, visited, sprawdzany + 1, stos)
            else:
                nastepny = v.next
                while nastepny is not None:
                    if graph[nastepny.number - 1] not in visited:
                        dfs_nastepnikow(graph, visited, nastepny.number - 1, stos)
                    else:
                        nastepny = nastepny.next
                if len(stos):
                    dfs_nastepnikow(graph, visited, stos[-1].number - 1, stos)
                else:
                    dfs_nastepnikow(graph, visited, sprawdzany + 1, stos)
        if not len(stos):
            sprawdzany += 1
            dfs_nastepnikow(graph, visited, sprawdzany, stos)


def dfs_sasiedztwa(macierz: list, visited: list, sprawdzany: int, i: list, d: list, f: list):
    if sprawdzany not in visited:
        visited.append(sprawdzany)
        d[sprawdzany - 1] = i[0]
        i[0] += 1
        for nastepny in range(len(macierz)):
            if macierz[sprawdzany - 1][nastepny] == 1:
                dfs_sasiedztwa(macierz, visited, nastepny + 1, i, d, f)
        f[sprawdzany - 1] = i[0]
        i[0] += 1


def dfs_lukow(arcs: list, n: int, visited: list, sprawdzany: int):
    if sprawdzany not in visited:
        visited.append(sprawdzany)
        for arc in arcs:
            if arc[0] == sprawdzany:
                dfs_lukow(arcs, n, visited, arc[1])


def powrotne_sasiedztwa(macierz: list, d: list, f: list) -> int:
    count = 0
    for x in range(len(d)):
        for y in range(len(f)):
            if macierz[x][y] == 1 and d[y] < d[x] < f[x] < f[y]:
                count += 1
    return count


def powrotne_lukow(arcs: list, d: list, f: list) -> int:
    count = 0
    for arc in arcs:
        if d[arc[1] - 1] < d[arc[0] - 1] < f[arc[0] - 1] < f[arc[1] - 1]:
            count += 1
    return count


def powrotne_nastepnikow(graph: list, d: list, f: list) -> int:
    count = 0
    for v in graph:
        nastepnik = v.next
        while nastepnik is not None:
            if d[nastepnik.number - 1] < d[v.number - 1] < f[v.number - 1] < f[nastepnik.number - 1]:
                count += 1
            nastepnik = nastepnik.next
    return count


n_wartosci = [100]
d_wartosci = [0.2, 0.4]

for gestosc in d_wartosci:
    print("Dla gestosci d = "+str(gestosc))
    for liczba in n_wartosci:
        print("Dla n = " + str(liczba))
        time_generowanie_grafu = time()
        graf = generowanie_grafu(liczba, gestosc)

        print("Czas generowania grafu (s): " + str(time() - time_generowanie_grafu))

        macierz_sasiedztwa = generowanie_macierzy_sasiedztwa(graf, liczba)
        lista_nastepnikow = generowanie_listy_nastepnikow(graf, liczba)
        lista_lukow = generowanie_listy_lukow(graf)
        dfs_lista, dlista, flista, czas = topologiczne_sasiedztwa(macierz_sasiedztwa)
        print("Czas zliczania etykiet (s): " + str(czas))

        start_time_sasiadow = time()
        powrotnych = powrotne_sasiedztwa(macierz_sasiedztwa, dlista, flista)
        print("Liczba lukow powrotnych: " + str(powrotnych))
        print("Czas zliczania lukow powrotnych (macierz_sasiedztwa): " + str((time() - start_time_sasiadow) * 1000))

        start_time_nastepnikow = time()
        powrotne_nastepnikow(lista_nastepnikow, dlista, flista)
        print("Czas zliczania lukow powrotnych (lista_nastepnikow): " + str((time() - start_time_nastepnikow) * 1000))

        start_time_lukow = time()
        powrotne_lukow(lista_lukow, dlista, flista)
        print("Czas zliczania lukow powrotnych (lista_lukow): " + str((time() - start_time_lukow) * 1000))
        print('\n')
    print('\n\n\n')
