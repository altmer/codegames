package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
func main() {
	scanner := bufio.NewScanner(os.Stdin)
	scanner.Buffer(make([]byte, 1000000), 1000000)

	// n: the number of temperatures to analyse
	var n int
	scanner.Scan()
	fmt.Sscan(scanner.Text(), &n)

	scanner.Scan()
	temps := scanner.Text() // the n temperatures expressed as integers ranging from -273 to 5526

	res := math.MaxInt32
	for _, temp := range strings.Split(temps, " ") {
		tempI, err := strconv.Atoi(temp)
		if err != nil {
			continue
		}
		if abs(tempI) < abs(res) || (abs(tempI) == abs(res) && tempI > 0) {
			res = tempI
		}
	}

	// fmt.Fprintln(os.Stderr, "Debug messages...")
	if res == math.MaxInt32 {
		fmt.Println(0)
	} else {
		fmt.Println(res)
	}
}

func abs(n int) int {
	if n < 0 {
		return -n
	}
	return n
}
