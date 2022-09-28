package main

import (
	"fmt"
)

type Route struct {
	destination string
	price       int
}

type Graph struct {
	adjacencyList map[string][]Route
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

func (graph *Graph) addCity(name string) {
	if graph.adjacencyList[name] != nil {
		return
	}

	graph.adjacencyList[name] = make([]Route, 0)
}

func (graph *Graph) addRoute(from, to string, price int) {
	_, routeIdx := graph.getRoute(from, to)

	if routeIdx != -1 {
		return
	}

	graph.adjacencyList[from] = append(graph.adjacencyList[from], Route{to, price})
	graph.adjacencyList[to] = append(graph.adjacencyList[to], Route{from, price})
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

	delete(graph.adjacencyList, city)

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

func (graph *Graph) editRoutePrice(from, to string, newPrice int) bool {
	routeTo, _ := graph.getRoute(from, to)

	if routeTo == nil {
		return false
	}

	routeFrom, _ := graph.getRoute(to, from)

	routeTo.price = newPrice
	routeFrom.price = newPrice

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

func main() {
	graph := Graph{}
	graph.initialize()
	graph.addCity("Kyiv")
	graph.addCity("Zhytomyr")
	graph.addCity("Rivne")
	graph.addCity("Nova Borova")

	graph.addRoute("Kyiv", "Zhytomyr", 200)
	graph.addRoute("Zhytomyr", "Rivne", 300)
	graph.addRoute("Kyiv", "Rivne", 450)
	graph.addRoute("Kyiv", "Nova Borova", 200)
	graph.addRoute("Zhytomyr", "Nova Borova", 1000)

	//graph.removeRoute("Kyiv", "Rivne")
	//graph.removeCity("Zhytomyr")
	//graph.removeCity("Kyiv")

	graph.print()
}
