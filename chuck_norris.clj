(ns Solution
  (:require [clojure.string :as str])
  (:gen-class))

; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn leftpad [s]
  (if (>= (count s) 7)
    s
    (str (apply str (repeat (- 7 (count s)) "0")) s)))

(defn chuck-enc [symbol]
  (case symbol
    \0 "00"
    \1 "0"))

(defn encode [s]
  (apply str (map #(leftpad(Integer/toBinaryString (int %))) s)))

(defn series
  ([s] (series s -1 ""))
  ([s num acc]
   (if (empty? s)
     acc
     (let [cur-number (first s)
           rest (subs s 1)]
      (if (= cur-number num)
        (series rest num (str acc "0"))
        (series rest cur-number (str acc " " (chuck-enc cur-number) " 0")))))))

(defn -main [& args]
  (let [message (read-line)
        binary-message (encode message)]
    (println
      (-> binary-message
          (series)
          (str/trim)))))
