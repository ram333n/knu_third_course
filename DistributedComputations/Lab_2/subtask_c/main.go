package main

import "fmt"

func getWinner(monks []int, left, right int) int {
	result := left
	if monks[result] < monks[right] {
		result = right
	}

	return result
}

func getMonastery(idx int) string {
	switch idx % 2 {
	case 0:
		return "Guan-in'"
	case 1:
		return "Guan-yan'"
	}
	return ""
}

func championship(monks []int, left, right int, pipe chan<- int) {
	winner := left
	if right-left < 2 {
		winner = getWinner(monks, left, right)
	} else {
		mid := (left + right) / 2
		var subpipe = make(chan int, 2)
		go championship(monks, left, mid, subpipe)
		championship(monks, mid+1, right, subpipe)
		firstWinner := <-subpipe
		secondWinner := <-subpipe
		winner = getWinner(monks, firstWinner, secondWinner)
	}
	pipe <- winner
}

func main() {
	testInput := []int{50, 70, 75, 65, 100, 90, 85, 89, 74, 76, 40, 30, 93, 13, 42, 1}
	var pipe = make(chan int, 1)
	championship(testInput, 0, len(testInput)-1, pipe)
	var winnerIdx = <-pipe
	fmt.Printf("Winner is %d with power %d, %s monastery", winnerIdx, testInput[winnerIdx], getMonastery(winnerIdx))
}
