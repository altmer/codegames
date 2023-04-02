package main

import (
	"bufio"
	"bytes"
	"fmt"
	"os"
	"strings"
)

//import "strconv"

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

type Letter struct {
	pattern []string
}

func main() {
	lettersLibKeys := bytes.Runes([]byte("ABCDEFGHIJKLMNOPQRSTUVWXYZ?"))
	lettersLib := map[rune]Letter{}

	for _, letter := range lettersLibKeys {
		lettersLib[letter] = Letter{pattern: []string{}}
	}

	scanner := bufio.NewScanner(os.Stdin)
	scanner.Buffer(make([]byte, 1000000), 1000000)

	var L int
	scanner.Scan()
	fmt.Sscan(scanner.Text(), &L)

	var H int
	scanner.Scan()
	fmt.Sscan(scanner.Text(), &H)

	scanner.Scan()
	text := bytes.Runes([]byte(strings.ToUpper(scanner.Text())))

	for i := 0; i < H; i++ {
		scanner.Scan()
		ROW := scanner.Text()
		lettersElements := SplitSubN(ROW, L)

		for letterIndex, el := range lettersElements {
			letter := lettersLibKeys[letterIndex]
			currentLetter := lettersLib[letter]
			lettersLib[letter] = Letter{pattern: append(currentLetter.pattern, el)}
		}
	}

	for i := 0; i < H; i++ {
		for _, letter := range text {
			letterStruct, ok := lettersLib[letter]
			if !ok {
				letterStruct = lettersLib['?']
			}
			fmt.Print(letterStruct.pattern[i])
		}
		fmt.Print("\n")
	}

	// fmt.Fprintln(os.Stderr, "Debug messages...")
}

func SplitSubN(s string, n int) []string {
	sub := ""
	subs := []string{}

	runes := bytes.Runes([]byte(s))
	l := len(runes)
	for i, r := range runes {
		sub = sub + string(r)
		if (i+1)%n == 0 {
			subs = append(subs, sub)
			sub = ""
		} else if (i + 1) == l {
			subs = append(subs, sub)
		}
	}

	return subs
}
