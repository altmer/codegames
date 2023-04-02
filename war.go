package main

import "fmt"
import "strings"

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

var n, m int

func main() {
	// n: the number of cards for player 1
	fmt.Scan(&n)

	cardsp1 := []string{}
	cardsp2 := []string{}

	for i := 0; i < n; i++ {
		// cardp1: the n cards of player 1
		var cardp1 string
		fmt.Scan(&cardp1)
		cardsp1 = append(cardsp1, cardp1)
	}
	// m: the number of cards for player 2
	fmt.Scan(&m)

	for i := 0; i < m; i++ {
		// cardp2: the m cards of player 2
		var cardp2 string
		fmt.Scan(&cardp2)
		cardsp2 = append(cardsp2, cardp2)
	}

	numRounds := 0
	for {
		bufp1 := []string{}
		bufp2 := []string{}

		var res int
		if checkResult(cardsp1, cardsp2, numRounds) {
			return
		}
		cardsp1, bufp1 = head(cardsp1, bufp1)
		cardsp2, bufp2 = head(cardsp2, bufp2)

		for res = compare(bufp1, bufp2); res == 0; res = compare(bufp1, bufp2) {
			if len(cardsp1) < 3 || len(cardsp2) < 3 {
				fmt.Println("PAT")
				return
			}
			cardsp1, bufp1 = war(cardsp1, bufp1)
			cardsp2, bufp2 = war(cardsp2, bufp2)

			if len(cardsp1) == 0 || len(cardsp2) == 0 {
				fmt.Println("PAT")
				return
			}

			cardsp1, bufp1 = head(cardsp1, bufp1)
			cardsp2, bufp2 = head(cardsp2, bufp2)
		}

		if res == 1 {
			cardsp1 = transfer(cardsp1, bufp1, bufp2)
		} else {
			cardsp2 = transfer(cardsp2, bufp1, bufp2)
		}

		numRounds++
	}
}

func head(cards, buffer []string) ([]string, []string) {
	head, restCards := cards[0], cards[1:]
	buffer = append(buffer, head)
	return restCards, buffer
}

func war(cards, buffer []string) ([]string, []string) {
	head, restCards := cards[0:3], cards[3:]
	buffer = append(buffer, head...)
	return restCards, buffer
}

func compare(bufp1, bufp2 []string) int {
	valuep1 := cardValue(bufp1[len(bufp1)-1])
	valuep2 := cardValue(bufp2[len(bufp2)-1])
	if valuep1 > valuep2 {
		return 1
	} else if valuep1 < valuep2 {
		return 2
	} else {
		return 0
	}
}

func checkResult(cardsp1, cardsp2 []string, numRounds int) bool {
	if len(cardsp1) == 0 {
		fmt.Printf("2 %d", numRounds)
		return true
	}

	if len(cardsp2) == 0 {
		fmt.Printf("1 %d", numRounds)
		return true
	}
	return false
}

func cardValue(card string) int {
	for num, c := range []string{"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"} {
		if strings.HasPrefix(card, c) {
			return num
		}
	}
	panic(fmt.Errorf("Unknown card %s", card))
}

func transfer(cards, buf1, buf2 []string) []string {
	return append(append(cards, buf1...), buf2...)
}
