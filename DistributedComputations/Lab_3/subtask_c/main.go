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

func (c *Components) printComponents() string {
	componentsName := [3]string{"tobacco", "paper", "match"}
	result := ""
	useDelimeter := true

	for i := 0; i < len(c.componentsArray); i++ {
		if !c.componentsArray[i] {
			continue
		}

		result += componentsName[i]
		if useDelimeter {
			result += ", "
			useDelimeter = false
		}
	}

	return result
}

func generateComponent() (*Components, int) {
	array := [3]bool{true, true, true}
	idx := rand.Int() % len(array)
	array[idx] = false
	return &Components{array}, idx
}

func mediator(pingChanArray []chan *Components, isDone chan bool) {
	for {
		<-isDone
		toPush, idx := generateComponent()
		fmt.Println("Mediator generated : ", toPush.printComponents())
		pingChanArray[idx] <- toPush
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

	responseChan <- true

	for {
		time.Sleep(1000 * time.Millisecond)
	}
}
