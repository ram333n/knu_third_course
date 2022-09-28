package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const Duration = 5

type Route struct {
	destination string
	price       int
}

type Graph struct {
	adjacencyList map[string][]Route
	lock          sync.RWMutex
}

func (graph *Graph) initialize() {
	graph.adjacencyList = make(map[string][]Route)
}

func (graph *Graph) getRoute(from, to string) (*Route, int) {
	if graph.adjacencyList[from] == nil {
		return nil, -1
	}

	var resultTicket *Route = nil
	var resultIdx int = -1

	for i := 0; i < len(graph.adjacencyList[from]); i++ {
		current := graph.adjacencyList[from][i]
		if current.destination == to {
			resultTicket = &current
			resultIdx = i
			break
		}
	}

	return resultTicket, resultIdx
}

func (graph *Graph) addCity(name string) bool {
	if graph.adjacencyList[name] != nil {
		return false
	}

	graph.adjacencyList[name] = make([]Route, 0)

	return true
}

func (graph *Graph) addRoute(from, to string, price int) bool {
	_, routeIdx := graph.getRoute(from, to)

	if routeIdx != -1 || graph.adjacencyList[from] == nil {
		return false
	}

	graph.adjacencyList[from] = append(graph.adjacencyList[from], Route{to, price})
	graph.adjacencyList[to] = append(graph.adjacencyList[to], Route{from, price})

	return true
}

func removeByIndex[T any](slice []T, i int) []T {
	slice[i] = slice[len(slice)-1]
	return slice[:len(slice)-1]
}

func (graph *Graph) removeCity(city string) bool {
	if graph.adjacencyList[city] == nil {
		return false
	}

	for currentCity, _ := range graph.adjacencyList {
		if currentCity == city {
			continue
		}

		graph.removeRoute(currentCity, city)
	}

	graph.adjacencyList[city] = nil

	return true
}

func (graph *Graph) removeRoute(from, to string) bool {
	_, idxInFromAdjList := graph.getRoute(from, to)

	if idxInFromAdjList == -1 {
		return false
	}

	_, idxInToAdjList := graph.getRoute(to, from)

	graph.adjacencyList[from] = removeByIndex(graph.adjacencyList[from], idxInFromAdjList)
	graph.adjacencyList[to] = removeByIndex(graph.adjacencyList[to], idxInToAdjList)

	return true
}

var expected *Route

func (graph *Graph) editRoutePrice(from, to string, newPrice int) bool {
	routeTo, _ := graph.getRoute(from, to)

	if routeTo == nil {
		return false
	}

	routeFrom, _ := graph.getRoute(to, from)

	routeTo.price = newPrice
	routeFrom.price = newPrice
	expected = routeTo

	return true
}

func (graph *Graph) print() {
	for city, list := range graph.adjacencyList {
		fmt.Printf("From : %s\n", city)

		for i := 0; i < len(list); i++ {
			fmt.Printf("%s : %d\n", list[i].destination, list[i].price)
		}

		fmt.Println("----------------------------------------")
	}
}

func performPriceEditor(graph *Graph, cities []string) {
	for {
		graph.lock.Lock()
		time.Sleep(Duration * time.Second)

		from := cities[rand.Int()%len(cities)]
		to := cities[rand.Int()%len(cities)]

		if from != to {
			route, _ := graph.getRoute(from, to)
			if route == nil {
				fmt.Printf("PriceEditor: there isn't %s or %s in graph\n", from, to)
			} else {
				oldPrice := route.price
				newPrice := rand.Intn(2000)
				if graph.editRoutePrice(from, to, newPrice) {
					fmt.Printf("PriceEditor: changed newPrice from %s to %s(before = %d, after = %d)\n", from, to, oldPrice, newPrice)
				} else {
					fmt.Printf("PriceEditor: there isn't route from %s to %s\n", from, to)
				}
			}
		} else {
			fmt.Println("PriceEditor: there isn't loop edges")
		}

		graph.lock.Unlock()
	}
}

func performRouteEditor(graph *Graph, cities []string) {
	for {
		graph.lock.Lock()
		time.Sleep(Duration * time.Second)

		from := cities[rand.Int()%len(cities)]
		to := cities[rand.Int()%len(cities)]
		toRemove := rand.Intn(2)%2 == 0

		if toRemove {
			if graph.removeRoute(from, to) {
				fmt.Printf("RouteEditor: removed route from %s to %s\n", from, to)
			} else {
				fmt.Printf("RouteEditor: there isn't route from %s to %s\n", from, to)
			}
		} else {
			price := rand.Intn(2000)

			if graph.addRoute(from, to, price) {
				fmt.Printf("RouteEditor: added route from %s to %s, price: %d\n", from, to, price)
			} else {
				route, _ := graph.getRoute(from, to)

				if route != nil {
					fmt.Printf("RouteEditor: the route from %s to %s already exists\n", from, to)
				} else {
					fmt.Printf("RouteEditor: there isn't %s or %s in graph\n", from, to)
				}
			}
		}

		graph.lock.Unlock()
	}
}

func performCityEditor(graph *Graph, cities []string) {
	for {
		graph.lock.Lock()
		time.Sleep(Duration * time.Second)

		city := cities[rand.Int()%len(cities)]
		toRemove := rand.Intn(2)%2 == 0

		if toRemove {
			if graph.removeCity(city) {
				fmt.Printf("CityEditor: successfully removed %s city\n", city)
			} else {
				fmt.Printf("CityEditor: %s doesn't exist\n", city)
			}
		} else {
			if graph.addCity(city) {
				fmt.Printf("CityEditor: successfully added %s city\n", city)
			} else {
				fmt.Printf("CityEditor: %s already exists\n", city)
			}
		}

		graph.lock.Unlock()
	}
}

func performRouteFinder(graph *Graph, cities []string) {
	for {
		graph.lock.RLock()
		time.Sleep(Duration * time.Second)

		from := cities[rand.Int()%len(cities)]
		to := cities[rand.Int()%len(cities)]
		route, _ := graph.getRoute(from, to)

		if route != nil {
			fmt.Printf("RouteFinder: found route from %s to %s, price: %d\n", from, to, route.price)
		} else {
			fmt.Printf("RouteFinder: there isn't route from %s to %s\n", from, to)
		}

		graph.lock.RUnlock()
	}
}

func main() {
	cities := []string{"Kyiv", "Odesa", "Kharkiv", "Lviv"}
	graph := Graph{}
	graph.initialize()

	for i := 0; i < len(cities); i++ {
		graph.addCity(cities[i])
	}

	graph.addRoute("Kyiv", "Odesa", 1)
	graph.addRoute("Kyiv", "Kharkiv", 2)
	graph.addRoute("Kyiv", "Lviv", 3)
	graph.addRoute("Kharkiv", "Lviv", 4)

	graph.editRoutePrice("Kharkiv", "Lviv", 143)
	fromRoute, _ := graph.getRoute("Kharkiv", "Lviv")
	toRoute, _ := graph.getRoute("Lviv", "Kharkiv")
	fmt.Println(fromRoute.price)
	fmt.Println(toRoute.price)

	//go performPriceEditor(&graph, cities)
	//go performRouteEditor(&graph, cities)
	//go performCityEditor(&graph, cities)
	//go performRouteFinder(&graph, cities)

	//fmt.Println(graph.addRoute("Kharkiv", "Lviv", 1337))

	for {
	}
}
