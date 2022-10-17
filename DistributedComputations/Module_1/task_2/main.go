package main

import (
	"fmt"
	"math/rand"
	"sync"
	_ "sync"
	"time"
)

var random = rand.New(rand.NewSource(time.Now().UnixNano()))
var mutex = &sync.Mutex{}
var clientAccountIdGenerator = -1

const maxCashBoxMoney = 1000
const partCashBoxMoney = 500
const maxStorageMoney = 10000

type Vault struct {
	money int
}

type ClientAccount struct {
	id    int
	money int
}

type Cashbox struct {
	money int
}

type Bank struct {
	vault          *Vault
	cashBoxes      []Cashbox
	clientAccounts []ClientAccount
}

type Client struct {
	id int
}

func createStorage() *Vault {
	return &Vault{random.Intn(maxStorageMoney) + 1000}
}

func createCashbox() Cashbox {
	return Cashbox{random.Intn(maxCashBoxMoney) + 100}
}

func createClientAccount() ClientAccount {
	clientAccountIdGenerator++

	return ClientAccount{clientAccountIdGenerator, random.Intn(100)}
}

func (b *Bank) observe() {
	for i := range b.cashBoxes {
		mutex.Lock()

		if b.vault.money < 0 {
			panic("Not enough money in the bank!")
		}

		if b.cashBoxes[i].money <= 0 {
			if b.vault.money >= maxStorageMoney {
				b.cashBoxes[i].money += maxCashBoxMoney
			}
		}

		if b.cashBoxes[i].money >= maxCashBoxMoney {
			b.vault.money += partCashBoxMoney
			b.cashBoxes[i].money -= partCashBoxMoney
		}

		mutex.Unlock()
	}
}

func createBank() *Bank {
	cashBoxes := make([]Cashbox, 10)
	clientAccounts := make([]ClientAccount, 20)

	for i := range cashBoxes {
		cashBoxes[i] = createCashbox()
	}

	for i := range clientAccounts {
		clientAccounts[i] = createClientAccount()
	}

	return &Bank{createStorage(), cashBoxes, clientAccounts}
}

func (b *Bank) getCashbox() *Cashbox {
	return &b.cashBoxes[random.Intn(len(b.cashBoxes))]
}

func (b *Bank) withdrawMoney(id, amount int) {
	mutex.Lock()

	if b.clientAccounts[id].money-amount >= 0 {
		b.clientAccounts[id].money -= amount
		b.getCashbox().money += amount
	}

	mutex.Unlock()
}

func (b *Bank) replenishMoney(id, amount int) {
	mutex.Lock()
	b.getCashbox().money -= amount
	b.clientAccounts[id].money += amount
	mutex.Unlock()
}

func (b *Bank) transferMoney(id, otherClientId, amount int) {
	mutex.Lock()

	if b.clientAccounts[id].money >= amount {
		b.clientAccounts[id].money -= amount
		b.clientAccounts[otherClientId].money += amount
	}

	mutex.Unlock()
}

func (b *Bank) pay(id, amount int) {
	mutex.Lock()

	if b.clientAccounts[id].money >= amount {
		b.clientAccounts[id].money -= amount
		b.getCashbox().money += amount
	}

	mutex.Unlock()
}

func (b *Bank) serveClients(clients []Client, endChan chan bool) {
	for i := range clients {
		clients[i].work(b)
	}

	rand.Shuffle(len(clients), func(i, j int) {
		clients[i], clients[j] = clients[j], clients[i]
	})

	endChan <- true
}

func createClient(id int) Client {
	return Client{id}
}

func (c *Client) withdrawMoney(b *Bank) {
	amount := random.Intn(10)
	fmt.Printf("Client %v withdraws %v\n", c.id, amount)
	b.withdrawMoney(c.id, amount)
}

func (c *Client) supplyMoney(b *Bank) {
	amount := random.Intn(10)
	fmt.Printf("Client %v supplements %v\n", c.id, amount)
	b.replenishMoney(c.id, amount)
}

func (c *Client) transferMoney(b *Bank) {
	amount := random.Intn(10)
	otherClientId := random.Intn(clientAccountIdGenerator)
	fmt.Printf("Client %v transfer to %v amount %v\n", c.id, otherClientId, amount)
	b.transferMoney(c.id, otherClientId, amount)
}

func (c *Client) pay(b *Bank) {
	amount := random.Intn(10)
	fmt.Printf("Client %v paid %v\n", c.id, amount)
	b.pay(c.id, amount)
}

func (c *Client) work(b *Bank) {
	decision := rand.Intn(4)

	switch decision {
	case 0:
		c.withdrawMoney(b)
		break

	case 1:
		c.supplyMoney(b)
		break

	case 2:
		c.transferMoney(b)
		break

	case 3:
		c.pay(b)
		break
	}

	time.Sleep(time.Millisecond * time.Duration(500+rand.Intn(700)))
}

func main() {
	bank := createBank()
	clients := make([]Client, clientAccountIdGenerator+1)
	endChan := make(chan bool, 1)

	for i := range clients {
		clients[i] = createClient(i)
	}

	go bank.observe()
	go bank.serveClients(clients, endChan)

	<-endChan
}
