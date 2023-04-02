(ns Solution
  (:gen-class))

; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(def datacount -1)

(defn ->mins
  [allmins]
  (loop [index (dec datacount) curmin Integer/MAX_VALUE]
     (let [newmin (min (aget #^ints allmins index) curmin)]
       (aset #^ints allmins index newmin)
       (when (> index 0)
         (recur (dec index) newmin)))))

(defn -main [& args]
  (let [n (read)]
    (def datacount n)
    (def mins (make-array Integer/TYPE datacount))
    (def maxs (make-array Integer/TYPE datacount))

    (loop [i 0
           curmax -1]
      (when (< i n)
        (let [v (read)
              newmax (max curmax v)]
          (aset #^ints maxs i newmax)
          (aset #^ints mins i v)
          (recur (inc i) newmax))))

    (->mins mins)
    (loop [i 0 answer 0]
      (if (< i datacount)
        (let [candidate (- (aget #^ints mins i) (aget #^ints maxs i))]
          (if (< candidate answer)
            (recur (inc i) candidate)
            (recur (inc i) answer)))
        (println answer)))))
