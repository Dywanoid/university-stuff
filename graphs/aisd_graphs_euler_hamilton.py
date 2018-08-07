from random import choices
from random import shuffle
from time import time
# from random import seed
from sys import setrecursionlimit
setrecursionlimit(10000)
# seed(0)


class Vortex:
    def __init__(self, number: int):
        self.number = number
        self.edges = []
        self.numEdges = 0

    def __str__(self):
        return "Wierzchołek: " + str(self.number)

    def __repr__(self):
        return str(self.number)

    def add_edge(self, vortex):
        self.edges.append(vortex)
        vortex.edges.append(self)
        self.numEdges += 1
        vortex.numEdges += 1

    def remove_edge(self, vortex):
        vortex.edges.remove(self)
        vortex.numEdges -= 1
        self.edges.remove(vortex)
        self.numEdges -= 1

    def get_next(self):
        return self.edges[0] if len(self.edges) else None


def generowaniegrafu(nu: int, de: float, indexes: list = list(), temp: list = list(), tr: list = list()) -> (list, int):
    if not len(indexes):
        limit = 0.5 * de * nu * (nu - 1)
        # print('granica: ' + str(limit))
        count = 0
        graph = [Vortex(x) for x in range(1, nu + 1)]
        indexes = [x for x in range(nu)]
        shuffle(indexes)
        for ind in range(nu - 1):
            graph[indexes[ind]].add_edge(graph[indexes[ind + 1]])
            count += 1
        temp = [v for v in graph if v.numEdges == 1]
        graph[temp[0].number - 1].add_edge(graph[temp[1].number - 1])
        count += 1
        while count < limit:
            tri = choices(graph, k=3)
            if tri[0] not in tri[1].edges and tri[1] not in tri[2].edges and tri[0] not in tri[2].edges \
                    and tri[0] != tri[1] != tri[2]:
                graph[tri[0].number - 1].add_edge(graph[tri[1].number - 1])
                graph[tri[1].number - 1].add_edge(graph[tri[2].number - 1])
                graph[tri[0].number - 1].add_edge(graph[tri[2].number - 1])
                count += 3
                tr.append(tri)
        for w in graph:
            shuffle(w.edges)
        return graph, count, indexes, temp, tr
    else:
        count = 0
        graph = [Vortex(x) for x in range(1, nu + 1)]
        for ind in range(nu - 1):
            graph[indexes[ind]].add_edge(graph[indexes[ind + 1]])
            count += 1
        graph[temp[0].number - 1].add_edge(graph[temp[1].number - 1])
        count += 1
        for tri in tr:
            graph[tri[0].number - 1].add_edge(graph[tri[1].number - 1])
            graph[tri[1].number - 1].add_edge(graph[tri[2].number - 1])
            graph[tri[0].number - 1].add_edge(graph[tri[2].number - 1])
            count += 3
        for w in graph:
            shuffle(w.edges)
        return graph, count, indexes, temp, tr


def cykl_eulera(graph: list, euler: list = list(), stack: list = list()):
    current = graph[0]
    stack.append(current)
    while len(stack):
        forward = current.get_next()
        if forward is not None:
            stack.append(forward)
            forward.remove_edge(current)
            current = forward
        else:
            current = stack.pop()
            euler.append(current)
    return euler


def cykl_hamiltona(graph: list, v: Vortex, l: list, path: list = list()):
    if not l[1]:
        path.append(v)
        if len(path) == len(graph) and graph[0] in path[-1].edges:
            # print(path)
            l[0] += 1
            if l[2]:
                l[1] = True
        for edge in v.edges:
            if edge not in path:
                cykl_hamiltona(graph, edge, l, list(path))


n_list = [16]
d_list = [0.4]
hamilton_wszystkie = True
for n in n_list:
    for d in d_list:
        i, te, trip = [], [], []
        print('Dla n = {}, d = {} \n'.format(n, d))
        graf, _, i, te, trip = generowaniegrafu(n, d, i, te, trip)
        # print('krawedzi: ' +str(c))
        t = time()
        cykl_eulera(graf, [], [])
        print('czas eulera: ' + str(time() - t))
        kopia_grafu = generowaniegrafu(n, d, i, te, trip)[0]
        licznik = [0, False, True]
        t = time()
        cykl_hamiltona(kopia_grafu, kopia_grafu[0], licznik, [])
        print('czas jednego hamiltona: ' + str(time() - t))
        if hamilton_wszystkie:
            licznik = [0, False, False]
            t = time()
            cykl_hamiltona(kopia_grafu, kopia_grafu[0], licznik, [])
            print('czas wszystkich hamiltona: ' + str(time() - t))
            print('licznik: ' + str(licznik[0]))
        print('\n\n')
