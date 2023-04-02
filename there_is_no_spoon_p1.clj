(ns Player
  (:gen-class))

; Don't let the machines win. You are humanity's last hope...

(def scheme [])

(defn traverse
  [field x y neighbour-fn]
  (let [[nx ny] (neighbour-fn x y)
        sym (get-in field [ny nx])]
    (case sym
      nil "-1 -1"
      \0 (str nx " " ny)
      \. (recur field nx ny neighbour-fn))))

(defn bottom-n
  "Returns coordinates of bottom neighbour"
  [x y]
  [x (inc y)])

(defn right-n
  "Returns coordinates of right neighbour if one exists"
  [x y]
  [(inc x) y])

(defn print-node
  [[x y]]
  (println
        (str x " " y " "
             (traverse scheme x y right-n) " "
             (traverse scheme x y bottom-n))))


(defn -main [& args]
  (let [width (read) height (read) _ (read-line)]
    ; width: the number of cells on the X axis
    ; height: the number of cells on the Y axis
    (loop [i height]
     (when (> i 0)
       (let [line (read-line)]
         (def scheme (conj scheme (vec line))))

          ; line: width characters, each either 0 or .
       (recur (dec i))))

    (doall
      (map
        print-node
        (for [x (range 0 width)
              y (range 0 height)
              :let [sym (get-in scheme [y x])]
              :when (= sym \0)]
           [x y])))))
