package main

import (
	"fmt"
	"math/rand"
	"time"
)

const Duration = 5000

type Components struct {
	componentsArray [3]bool
}

func generateComponent() (*Components, int) {
	array := [3]bool{true, true, true}
	idx := rand.Int() % len(array)
	array[idx] = false
	return &Components{array}, idx
}

func mediator(pingChanArray []chan *Components, isDone chan bool) {
	for {
		toPush, idx := generateComponent()
		fmt.Println("Mediator generated : ", toPush.componentsArray)
		pingChanArray[idx] <- toPush
		<-isDone
	}
}

type Smoker struct {
	name         string
	mediatorChan chan *Components
}

func (smoker *Smoker) smoke(responseChan chan<- bool) {
	for {
		<-smoker.mediatorChan
		fmt.Printf("%s started smoking...\n", smoker.name)
		time.Sleep(Duration * time.Millisecond)
		fmt.Printf("%s finished smoking...\n", smoker.name)
		responseChan <- true
	}
}

func main() {
	tobaccoOwner := Smoker{"Tobacco owner", make(chan *Components)}
	paperOwner := Smoker{"Paper owner", make(chan *Components)}
	matchOwner := Smoker{"Match owner", make(chan *Components)}

	responseChan := make(chan bool, 1)
	pingChanArray := []chan *Components{tobaccoOwner.mediatorChan, paperOwner.mediatorChan, matchOwner.mediatorChan}

	go mediator(pingChanArray, responseChan)
	go tobaccoOwner.smoke(responseChan)
	go paperOwner.smoke(responseChan)
	go matchOwner.smoke(responseChan)

	for {
	}
}
